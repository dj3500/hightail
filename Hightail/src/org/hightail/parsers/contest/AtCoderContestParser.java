package org.hightail.parsers.contest;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.regex.Pattern;
import org.hightail.AuthenticationInfo;
import org.hightail.Problem;
import org.hightail.parsers.task.AtCoderTaskParser;
import org.hightail.parsers.task.TaskParser;
import org.htmlparser.util.ParserException;

public class AtCoderContestParser implements ContestParser {

    final static private String taskUrlRegExp = "/tasks/(.*)";
    // URLs look like: https://agc002.contest.atcoder.jp/tasks/agc002_a
    final static private TaskParser taskParser = new AtCoderTaskParser();
    static private WebClient webClient = null;
    
    private void check(boolean condition, String msg) throws ParserException {
        if (condition == false) {
            throw new ParserException(msg);
        }
    }
    
    private String createUrl(String url, String subPage) {
        if (subPage.contains("login")) {
            if (url.contains("http://")) url = url.replace("http://", "https://");
            if (!url.startsWith("https://")) url = "https://" + url;
        }
        return url + "/" + subPage;
    }
    
    public ArrayList<String> getProblemUrls(String baseUrl) throws ParserException, InterruptedException, IOException {
        // The initial url should be like:
        //     https://agc002.contest.atcoder.jp (can also end with / or /assignments, which we delete - Step 0)
        // Step 1: We go to link/login to login
        // Step 2: If we see "Join at contest" button in the link/assignments page, we click on it.
        // Step 3: We go to link/assignments to extract the links of the problems.
        // Step 4: We return the links.
        
        // Step 0
        baseUrl = baseUrl.replace("assignments", ""); // if has "assignments", remove it
        while (baseUrl.endsWith("/")) baseUrl = baseUrl.substring(0, baseUrl.length() - 1); // remove trailing slashes
 
        // Step 1
        if (AuthenticationInfo.getUsername().compareTo("") != 0) {
            HtmlPage loginPage = webClient.getPage(createUrl(baseUrl, "login"));
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
        
        // Step 2
        HtmlPage joinContestPage = webClient.getPage(createUrl(baseUrl, "assignments"));
        DomNodeList<DomElement> joinContestPageButtons = joinContestPage.getElementsByTagName("button");
        check(joinContestPageButtons.size() <= 1, "Atcoder: There should be at most one button in task page. The button can be join-contest-button.");
        if (joinContestPageButtons.size() == 1) {
            HtmlButton joinContestPageButton = (HtmlButton) joinContestPageButtons.get(0);
            joinContestPageButton.click();
        }
        
        // Step 3
        HtmlPage taskPage = webClient.getPage(createUrl(baseUrl, "assignments"));
        List<HtmlAnchor> aTagsInTaskPage = taskPage.getAnchors();
        ArrayList<String> links = new ArrayList<>();
        for (HtmlAnchor aTag : aTagsInTaskPage) {
            String taskLink = aTag.getHrefAttribute();
            if (Pattern.matches(taskUrlRegExp, taskLink)) {
                links.add(baseUrl + taskLink);
            }
        }

        // Step 4
        // remove link duplicates
        check(links.size() > 0, "Atcoder: No problem.");
        Set<String> s = new HashSet<>(links);
        links = new ArrayList<>(s);
        Collections.sort(links);
        
        return links;
    }
    
    @Override
    public ArrayList<String> getProblemURLListFromURL (String baseUrl) throws ParserException, InterruptedException {
        try {
            return getProblemUrls(baseUrl);
        } catch (IOException ex) {
            throw new ParserException(ex);
        }
    }

    @Override
    public ArrayList<Problem> getProblemListFromContestURL(String URL) throws ParserException, InterruptedException {
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF); 
        System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
        
        // make a new webClient (one per contest) (otherwise there can be bugs if multiple contests
        // are to be parsed during one execution of Hightail)
        webClient = new WebClient();

        ArrayList<String> problemURLList = getProblemURLListFromURL(URL);
        if (problemURLList.isEmpty()) {
            throw new ParserException("No links to tasks found.");
        }
        
        ArrayList<Problem> problems = new ArrayList<>();
        ParserException anyException = null;
        for (String link : problemURLList) {
            try {
                problems.add(((AtCoderTaskParser) getTaskParser()).parseUrl(link, webClient));
            } catch (IOException ex) {
                throw new ParserException(ex);
            }
        }
        if (anyException != null) {
            if (problems.isEmpty()) {
                // no problems parsed - we throw one of their exceptions
                throw anyException;
            } else {
                // well, some problems didn't parse, but some did, so we'll still return these
            }
        } else {
            // url list was nonempty and there were no exceptions, so problem list is also nonempty
        }

        return problems;

    }
        
    @Override
    public boolean isCorrectURL(String URL) {
        return URL.contains("atcoder.");
    }
    
    @Override
    public TaskParser getTaskParser () {
        return taskParser;
    }
}
