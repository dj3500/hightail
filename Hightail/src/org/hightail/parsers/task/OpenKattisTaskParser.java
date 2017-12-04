package org.hightail.parsers.task;

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
import org.htmlparser.util.Translate;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OpenKattisTaskParser implements TaskParser {
    @Override
    public Problem parse(String url) throws ParserException, InterruptedException {
        url = url.trim();

        FilterBean fb = new FilterBean();
        fb.setURL(url);

        String problemName = getProblemName(fb);
        if (problemName == null) {
            problemName = getProblemId(url);
        }

        int timeLimit = getTimeLimit(fb);

        ArrayList<String> samples = getSamples(fb);
        TestcaseSet testCases = new TestcaseSet();
        for (int t = 0; t < samples.size(); t += 2) {
            Testcase testCase = new Testcase(samples.get(t), samples.get(t + 1), timeLimit);
            testCases.add(testCase);
        }

        return new Problem(problemName, testCases, SupportedSites.OpenKattis);
    }

    private ArrayList<String> getSamples(FilterBean fb) {
        fb.setFilters(new NodeFilter[]{new CssSelectorNodeFilter("table.sample pre")});
        NodeList nodeList = fb.getNodes();
        Node[] nodes = nodeList.toNodeArray();
        ArrayList<String> samples = new ArrayList<>();
        for (Node node : nodes) {
            samples.add(Translate.decode(node.getNextSibling().getText().trim()));
        }
        return samples;
    }

    private int getTimeLimit(FilterBean fb) {
        fb.setFilters(new NodeFilter[]{new CssSelectorNodeFilter(".problem-sidebar")});
        String text = fb.getText();
        Pattern pattern = Pattern.compile("(\\d+)\\s+second");
        Matcher matcher = pattern.matcher(text);
        int timeLimit = Testcase.DEFAULT_TIME_LIMIT;
        if (matcher.find()) {
            timeLimit = (int) (Double.valueOf(matcher.group()) * 1000);
        }
        return timeLimit;
    }

    private String getProblemName(FilterBean fb) {
        fb.setFilters(new NodeFilter[]{new CssSelectorNodeFilter(".headline-wrapper>h1")});
        String text = fb.getText();
        if (text.contains("\n")) {
            int shift = "Problem ".length();
            return text.trim().substring(shift, shift + 1);
        }
        return null;
    }

    private String getProblemId(String URL) {
        int indexBeforeName = URL.lastIndexOf('/');
        return URL.substring(indexBeforeName + 1);
    }

    @Override
    public boolean isCorrectURL(String URL) {
        return URL.contains(".kattis.com/");
    }
}
