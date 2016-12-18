package org.hightail.parsers.task;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.hightail.Problem;
import org.hightail.SupportedSites;
import org.hightail.Testcase;
import org.hightail.TestcaseSet;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.beans.FilterBean;
import org.htmlparser.filters.CssSelectorNodeFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public class AtCoderTaskParser implements TaskParser {

    @Override
    public Problem parse(String URL, boolean wholeName) throws ParserException, InterruptedException {

        URL = URL.trim();
        
        FilterBean fb = new FilterBean();
        fb.setURL(URL);
        
        // extract problem name
        fb.setFilters(new NodeFilter[] {
            new CssSelectorNodeFilter("div#outer-inner"),
            new CssSelectorNodeFilter("h2")});
        String problemName = fb.getText();
        if (problemName.isEmpty()) {
            throw new ParserException("Problem name not extracted (probably incorrect url).");
        }
        problemName = String.valueOf(problemName.charAt(0));
        
        // extract the time limit
        int timeLimit = Testcase.DEFAULT_TIME_LIMIT;
        try {
            Node tlNode = fb.getNodes().elementAt(0).getNextSibling().getNextSibling().getNextSibling().getNextSibling().getFirstChild().getNextSibling();
            String timeLimitText = tlNode.toPlainTextString(); // we expect something like " : 2sec / "
            Pattern pattern = Pattern.compile("\\d+(\\.\\d+)?");
            Matcher matcher = pattern.matcher(timeLimitText);
            if (matcher.find()) {
                timeLimit = (int) (Double.valueOf(matcher.group()) * 1000);
            }
        } catch (NullPointerException e) {
            // didn't work. since there is no good way to report failure from here,
            // we just stick to the default time limit
        }
        
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        
        // extract inputs and outputs
        ArrayList<String> inputs = new ArrayList<>(), outputs = new ArrayList<>();
        fb.setFilters(new NodeFilter[] {new CssSelectorNodeFilter("div#task-statement div.part h3")});
        // not sure if this actually works (i.e. if it's any different from just "h3")...
        NodeList nodeList = fb.getNodes();
        if (nodeList == null || nodeList.size() == 0) {
            throw new ParserException("Parsing failed - no testcases were extracted.");
        }
        Node[] nodes = nodeList.toNodeArray();
        for (Node n : nodes) {
            if (n.toPlainTextString().startsWith("Sample ")) {
                Node nxt = n.getNextSibling(); // should be <pre>
                nxt = nxt.getNextSibling(); // should be the testcase
                (n.toPlainTextString().startsWith("Sample Input") ? inputs : outputs).add(nxt.toPlainTextString());
            }
        }
        
        if (inputs.size() != outputs.size()) {
            throw new ParserException("Different number of inputs than outputs.");
        }

        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        
        TestcaseSet testcaseSet = new TestcaseSet();
        for (int i = 0; i < inputs.size(); ++i) {
            testcaseSet.add(new Testcase(inputs.get(i).trim(), outputs.get(i).trim(), timeLimit));
        }
        
        return new Problem(problemName, testcaseSet, SupportedSites.AtCoder);
    }

    @Override
    public boolean isCorrectURL(String URL) {
        return URL.contains("atcoder.");
    }
}
