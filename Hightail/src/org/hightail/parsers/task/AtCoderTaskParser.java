package org.hightail.parsers.task;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.DomText;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlHeading2;
import com.gargoylesoftware.htmlunit.html.HtmlHeading3;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.hightail.AuthenticationInfo;
import org.hightail.Problem;
import org.hightail.SupportedSites;
import org.hightail.Testcase;
import org.hightail.TestcaseSet;
import org.htmlparser.util.ParserException;

public class AtCoderTaskParser implements TaskParser {

    private static void check(boolean condition, String msg) throws ParserException {
        if (condition == false) {
            throw new ParserException(msg);
        }
    }

    private static String createLoginUrl(String url) throws ParserException {
        if (url.startsWith(("http://"))) url = url.replace("http://", "https://");
        if (!url.startsWith("https://")) url = "https://" + url;
        int idOfTasks = url.indexOf("/tasks/");
        check(idOfTasks >= 0, "Atcoder: the problem url does not contain /tasks/");
        return url.substring(0, idOfTasks) + "/login";
    }
    
    public Problem parseUrl(String url, WebClient webClient) throws IOException, InterruptedException, ParserException {
        // The url should be like:
        //     https://agc002.contest.atcoder.jp/tasks/agc009_d
        // Step 1: We go to link/login and login (already done, and passed webClient)
        // Step 2: We go to problem page
        // Step 3: We extract problem name and time limit
        // Step 4: We extract all input/output and parse them
 
        // Step 2
        HtmlPage problemPage = webClient.getPage(url);
        
        // Step 3
        HtmlHeading2 titleH2Tag = (HtmlHeading2) problemPage.getFirstByXPath("//*[@id=\"outer-inner\"]/h2");
        String problemName = titleH2Tag.getTextContent();
        check(!problemName.isEmpty(), "Atcoder: Problem name not extracted (probably incorrect url).");

        problemName = String.valueOf(problemName.charAt(0));
        
        // extract the time limit
        int timeLimit = Testcase.DEFAULT_TIME_LIMIT;
        DomText timeLimitText = problemPage.getFirstByXPath("//*[@id=\"outer-inner\"]/p[1]/text()[1]");
        Pattern pattern = Pattern.compile("\\d+(\\.\\d+)?");
        
        Matcher matcher = pattern.matcher(timeLimitText.getTextContent());
        if (matcher.find()) {
            timeLimit = (int) (Double.valueOf(matcher.group()) * 1000);
        }
        
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        
        // Step 4
        // extract inputs and outputs
        ArrayList<String> inputs = new ArrayList<>();
        ArrayList<String> outputs = new ArrayList<>();
        List<DomNode> sampleIoTags = (List<DomNode>) problemPage.getByXPath("//*[@id=\"task-statement\"]/span/span/div");

        for (DomNode sampleIoTag : sampleIoTags) {
            List<DomNode> h3Tags = (List<DomNode>) sampleIoTag.getByXPath("h3");
            h3Tags.addAll((List<DomNode>) sampleIoTag.getByXPath("section/h3"));

            if (h3Tags.size() != 1) continue;
            HtmlHeading3 h3Tag = (HtmlHeading3) h3Tags.get(0);
            String sampleHeader = h3Tag.getTextContent();
            List<DomNode> preTags = (List<DomNode>) sampleIoTag.getByXPath("section/pre");
            if (preTags.size() != 1) continue;
            DomElement preTag = (DomElement) preTags.get(0);
            if (sampleHeader.contains("Sample Input") || sampleHeader.contains("Input Example")) {
                inputs.add(preTag.getTextContent());
            }
            if (sampleHeader.contains("Sample Output") || sampleHeader.contains("Output Example")) {
                outputs.add(preTag.getTextContent());
            }
        }

        check(inputs.size() == outputs.size(), "Atcoder: Different number of inputs than outputs.");

        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        
        TestcaseSet testcaseSet = new TestcaseSet();
        for (int i = 0; i < inputs.size(); ++i) {
            testcaseSet.add(new Testcase(inputs.get(i).trim(), outputs.get(i).trim(), timeLimit));
        }
        
        return new Problem(problemName, testcaseSet, SupportedSites.AtCoder);
    }
 
    private Problem parseUrl(String url) throws IOException, InterruptedException, ParserException {
        // The url should be like:
        //     https://agc002.contest.atcoder.jp/tasks/agc009_d
        // Step 1: We go to link/login and login
        // Step 2: Passdown the webClient to finish the job
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF); 
        System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
        
        WebClient webClient = new WebClient();
        // Step 1
        if (AuthenticationInfo.getUsername().compareTo("") != 0) {
            HtmlPage loginPage = webClient.getPage(createLoginUrl(url));
            List<HtmlForm> loginForms = loginPage.getForms();
            HtmlForm loginForm = loginForms.get(0);
            HtmlTextInput txtUsername = loginForm.getInputByName("name");
            txtUsername.setText(AuthenticationInfo.getUsername());
            HtmlPasswordInput txtPassword = loginForm.getInputByName("password");
            txtPassword.setText(AuthenticationInfo.getPassword());
            DomNodeList<HtmlElement> loginFormButtons = loginForm.getElementsByTagName("button");
            check(loginFormButtons.size() == 1, "Atcoder: There should be only one button in login page.");
            HtmlButton loginFormButton = (HtmlButton) loginFormButtons.get(0);
            loginFormButton.click();
        }
        
        return parseUrl(url, webClient);
    }

    @Override
    public Problem parse(String url) throws InterruptedException, ParserException {
        try {
            return parseUrl(url);
        } catch (IOException ex) {
            throw new ParserException(ex);
        }
    }

    @Override
    public boolean isCorrectURL(String URL) {
        return URL.contains("atcoder.");
    }
}
