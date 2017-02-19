package org.hightail.parsers.task;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.hightail.Config;
import org.hightail.Problem;
import org.hightail.SupportedSites;
import org.hightail.Testcase;
import org.hightail.TestcaseSet;
import org.hightail.util.ProblemNameFormatter;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.beans.FilterBean;
import org.htmlparser.filters.CssSelectorNodeFilter;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.Translate;

/**
 *
 * @author robertrosolek
 */
public class CodeForcesTaskParser implements TaskParser {
    
    private ArrayList<String> extractInputsOrOutputs (FilterBean fb, String cssSelector) throws ParserException {
        NodeFilter filter = new CssSelectorNodeFilter(cssSelector);
        fb.setFilters(new NodeFilter[] {filter});
        NodeList nodeList = fb.getNodes();
        if (nodeList == null || nodeList.size() == 0) {
            throw new ParserException("Parsing failed - no testcases were extracted.");
        }
        Node[] nodes = nodeList.toNodeArray();
        ArrayList<String> inputs = new ArrayList<>();
        for (Node n : nodes) {
            /* The children of each node look like this:
             * div class="title"
             * pre
             * 3 4 1
             * br /
             * 1 2 3
             * br /
             * 2 3 5
             * br /
             * /pre
             */
            NodeList childrenNodeList = n.getChildren();
            if (childrenNodeList == null) {
                throw new ParserException("Parsing failed.");
            }
            Node[] children = childrenNodeList.toNodeArray();
            StringBuilder contents = new StringBuilder();
            for (Node child : children) {
                if (child instanceof TextNode) {
                    contents.append(Translate.decode(child.getText()));
                } else if (child instanceof TagNode && child.getText().contains("br")) {
                    contents.append("\n");
                }
            }
            String contentsString = contents.toString();
            if (contentsString.isEmpty()) {
                throw new ParserException("Parsing failed.");
            } 
            inputs.add(contentsString);
        }
        return inputs;
    }

    @Override
    public Problem parse(String URL) throws ParserException, InterruptedException {

        URL = URL.trim();
        
        FilterBean fb = new FilterBean();
        fb.setURL(URL);
        
        // extract problem name
        fb.setFilters(new NodeFilter[] {
            new CssSelectorNodeFilter("div.header"),
            new CssSelectorNodeFilter("div.title")});
        String problemName = fb.getText();
        if (problemName.isEmpty()) {
            throw new ParserException("Problem name not extracted (probably incorrect url).");
        }
        
        if (!Config.getBoolean("putWholeName")) {
            problemName = String.valueOf(problemName.charAt(0));
        } else {
            // delete some annoying special characters that show up in the title
            problemName = ProblemNameFormatter.getFormattedName(problemName);
        }
        
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        
        // extract inputs
        ArrayList<String> inputs = extractInputsOrOutputs(fb, "div.input");

        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        
        // extract outputs
        ArrayList<String> outputs = extractInputsOrOutputs(fb, "div.output");
        
        if (inputs.size() != outputs.size()) {
            throw new ParserException("Number of inputs is not equal to number of outputs.");
        }
        
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        
        // extract time limit
        fb.setFilters(new NodeFilter[] {new CssSelectorNodeFilter("div.time-limit")});
        String timeLimitText = fb.getText(); // should be "time limit per testXXX second"
        Pattern pattern = Pattern.compile("\\d+(\\.\\d+)?");
        Matcher matcher = pattern.matcher(timeLimitText);
        int timeLimit = Testcase.DEFAULT_TIME_LIMIT;
        if (matcher.find()) {
            timeLimit = (int) (Double.valueOf(matcher.group()) * 1000);
        }
        
        
        TestcaseSet testcaseSet = new TestcaseSet();
        for (int i = 0; i < inputs.size(); ++i) {
            testcaseSet.add(new Testcase(inputs.get(i).trim(), outputs.get(i).trim(), timeLimit));
        }
        
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        
        Problem problem = new Problem(problemName, testcaseSet, SupportedSites.CodeForces);
        
        return problem;
    }

    @Override
    public boolean isCorrectURL(String URL) {
        return URL.contains("codeforces.");
    }
    
}
