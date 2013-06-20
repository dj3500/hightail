package org.hightail.parsers.task;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.hightail.Problem;
import org.hightail.SupportedSites;
import org.hightail.Testcase;
import org.hightail.TestcaseSet;
import org.htmlparser.NodeFilter;
import org.htmlparser.beans.FilterBean;
import org.htmlparser.filters.CssSelectorNodeFilter;
import org.htmlparser.filters.HasParentFilter;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.Translate;

/**
 *
 * @author robertrosolek
 */
public class CodeForcesTaskParser implements TaskParser {

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
        problemName = String.valueOf(problemName.charAt(0));
        
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        
        // extract inputs
        CssSelectorNodeFilter inputFilter = new CssSelectorNodeFilter("div.input");
        HasParentFilter preInputFilter = new HasParentFilter(inputFilter);
        fb.setFilters(new NodeFilter[] {preInputFilter});
        String[] inputs = Translate.decode(fb.getText()).split("Input");
        
        // extract outputs
        CssSelectorNodeFilter outputFilter = new CssSelectorNodeFilter("div.output");
        HasParentFilter preOutputFilter = new HasParentFilter(outputFilter);
        fb.setFilters(new NodeFilter[] {preOutputFilter});
        String[] outputs = Translate.decode(fb.getText()).split("Output");
        
        if (inputs.length != outputs.length) {
            throw new ParserException("Number of inputs is not equal to number of outputs.");
        }
        
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        
        // extract time limit
        fb.setFilters(new NodeFilter[] {new CssSelectorNodeFilter("div.time-limit")});
        String timeLimitText = fb.getText(); // should be "time limit per testXXX second"
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(timeLimitText);
        int timeLimit = Testcase.DEFAULT_TIME_LIMIT;
        if (matcher.find()) {
            timeLimit = (int) (Double.valueOf(matcher.group()) * 1000);
        }
        
        
        TestcaseSet testcaseSet = new TestcaseSet();
        for (int i = 1; i < inputs.length; ++i) {
            testcaseSet.add(new Testcase(inputs[i].trim(), outputs[i].trim(), timeLimit));
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
