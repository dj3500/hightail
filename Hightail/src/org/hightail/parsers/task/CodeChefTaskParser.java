package org.hightail.parsers.task;

import org.hightail.Problem;
import org.hightail.SupportedSites;
import org.hightail.Testcase;
import org.hightail.TestcaseSet;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.beans.FilterBean;
import org.htmlparser.filters.CssSelectorNodeFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.util.ParserException;

/**
 *
 * @author krig
 */
public class CodeChefTaskParser implements TaskParser {

    @Override
    public Problem parse(String URL) throws ParserException, InterruptedException {
        URL = URL.trim();
        
        FilterBean fb = new FilterBean();
        Node node;
        
        // extract problem name
        fb.setFilters(new NodeFilter[] {
            new CssSelectorNodeFilter("span#problem-code")
        });
        fb.setURL(URL);
        String problemName = fb.getText(); // should be "Problem code: XXX"
        if(problemName.isEmpty()) {
            throw new ParserException("Problem name not extracted (probably incorrect url).");
        }
        
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        
        // extract time limit
        // TODO: this part should be better implemented
        int timeLimit = Testcase.DEFAULT_TIME_LIMIT;
        fb.setFilters(new NodeFilter[] {
            new HasAttributeFilter("class", "meta"),
        });
        node = fb.getNodes().elementAt(0);
        node = node.getNextSibling().getNextSibling();
        node = node.getNextSibling().getNextSibling().getNextSibling().getNextSibling();
        for(Node child : node.getChildren().toNodeArray()) {
            if(child.toString().contains("Time Limit:")) {
                String stringTimeLimit = child.getChildren().elementAt(3).getChildren().elementAt(0).getText(); // should be XX sec
                timeLimit = (int) (Double.valueOf(stringTimeLimit.split(" ")[0]) * 1000);
                break;
            }
        }
        
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        
        // extract inputs and outputs
        // TODO: this part should be better implemented
        fb.setFilters(new NodeFilter[] {
            new HasAttributeFilter("class", "meta"),
        });
        node = fb.getNodes().elementAt(0);
        node = node.getNextSibling().getNextSibling();
        String tmpInput = null, tmpOutput;
        TestcaseSet testcaseSet = new TestcaseSet();
        for(Node child : node.getChildren().toNodeArray()) {
            if(child.getText().contains("Input")) {
                tmpInput = child.getNextSibling().getNextSibling().getText();
            }
            if(child.getText().contains("Output")) {
                tmpOutput = child.getNextSibling().getNextSibling().getText();
                if(tmpInput == null) {
                    throw new ParserException("Error while parsing inputs/outputs.");
                }
                testcaseSet.add(new Testcase(tmpInput.trim(), tmpOutput.trim(), timeLimit));
                tmpInput = null;
            }
        }
        
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
//        if(testcaseSet.isEmpty()) {
//            throw new ParserException("No inputs/outputs extracted.");
//        }
        
        return new Problem(problemName, testcaseSet, SupportedSites.CodeChef);
    }

    @Override
    public boolean isCorrectURL(String URL) {
        return URL.contains("codechef.com");
    }
    
}
