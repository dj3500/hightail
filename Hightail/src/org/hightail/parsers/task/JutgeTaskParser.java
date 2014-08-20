package org.hightail.parsers.task;
import java.util.ArrayList;
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

/**
 * Task parser for www.jutge.org (UPC Online Judge).
 * @author Sergio Rodriguez Guasch
 */
public class JutgeTaskParser implements TaskParser {
    /*
        Boolean "isInput" is used to determine what we want to read.
        This is mainly due to the fact that inputs and outputs are contained in the very same structure, so there is
        no way to distinguish them by HTML properties.
    */
    private ArrayList<String> extractInputsOrOutputs (FilterBean fb, boolean isInput) throws ParserException {
        String cssSelector = "div.indent";
        NodeFilter filter = new CssSelectorNodeFilter(cssSelector);
        fb.setFilters(new NodeFilter[] {filter});
        NodeList nodeList = fb.getNodes();
        Node[] nodes = nodeList.toNodeArray();
        ArrayList<String> inputs = new ArrayList<>();
        for (Node n : nodes) {
            NodeList childrenNodeList = n.getChildren();
            if (childrenNodeList == null) {
                throw new ParserException("Parsing failed.");
            }
            Node[] children = childrenNodeList.toNodeArray();
            StringBuilder contents = new StringBuilder();
            /*
                The inputs and outputs are in a structure that looks like:
                <div class="indent">
                    <table>
                        <tr>
                            <td>
                                <pre class="mypre" ... > TEST STUFF </pre> ...
                I/O is identified by reading inmediate content of div.
                Inputs and outputs are matched by the order they appear.
                Also, sorry for poor implementation.
            */
            //Discriminate between I/O boxes and other stuff using the same div class
            if(children.length==0 ||
               (isInput&& !children[0].getText().equals("&nbsp;Input")) ||
               (!isInput && !children[0].getText().equals("&nbsp;Output"))) {
                continue;
            }
            if(children.length<4) {
                throw new ParserException("Parsing failed.");
            }
            NodeList table = children[3].getChildren();
            Node[] tableChildren = table.toNodeArray();
            if(tableChildren.length<2) {
                throw new ParserException("Parsing failed.");
            }
            NodeList tr = tableChildren[1].getChildren();
            Node[] trChildren = tr.toNodeArray();
            if(trChildren.length<2) {
                throw new ParserException("Parsing failed.");
            }
            NodeList td = trChildren[1].getChildren();
            Node[] tdChildren = td.toNodeArray();
            if(tdChildren.length<3) {
                throw new ParserException("Parsing failed.");
            }
            contents.append(Translate.decode(tdChildren[2].getText()));
            String contentsString = contents.toString();
            if (contentsString.isEmpty()) {
                throw new ParserException("Parsing failed.");
            } 
            inputs.add(contentsString);
        }
        // Unfortunately, I found no way to detect that there are no testcases before processing all nodes...
        if(inputs.isEmpty()) {
            throw new ParserException("Parsing failed - no testcases were extracted.");
        }
        /*
            Dirty hack. Some problems have null string as expected output. In this case, the algorithm above will return "/pre"
            as output. I hope that there is no problem which has "/pre" as output.
        */
        for(int i=0; i<inputs.size(); ++i) {
            if(inputs.get(i).equals("/pre")) {
                inputs.set(i,"");
            }
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
            new CssSelectorNodeFilter("head"),
            new CssSelectorNodeFilter("title")});
        String problemName = fb.getText();
        // remove extra info from problem name
        problemName = problemName.replaceAll("Jutge :: Problem P(.*)[_ca|_en|_es]: ","");
        if (problemName.isEmpty()) {
            throw new ParserException("Problem name not extracted (probably incorrect url).");
        }
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        // extract inputs
        ArrayList<String> inputs = extractInputsOrOutputs(fb, true);

        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        
        // extract outputs
        ArrayList<String> outputs = extractInputsOrOutputs(fb, false);
        
        if (inputs.size() != outputs.size()) {
            throw new ParserException("Number of inputs is not equal to number of outputs.");
        }
        
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        
        // as far as I know, Jutge sets time limit automatically as 2*official_solution_time and is not public
        int timeLimit = Testcase.DEFAULT_TIME_LIMIT;
        
        // add test cases
        TestcaseSet testcaseSet = new TestcaseSet();
        for (int i = 0; i < inputs.size(); ++i) {
            testcaseSet.add(new Testcase(inputs.get(i).trim(), outputs.get(i).trim(), timeLimit));
        }
        
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        
        Problem problem = new Problem(problemName, testcaseSet, SupportedSites.Jutge);
        return problem;
    }

    @Override
    public boolean isCorrectURL(String URL) {
        //Don't know if this is overkill, but jutge has lots of very similar links which lead to images or similar stuff.
        return URL.contains("jutge.org/problems/P(.*)[_ca|_en|_es]\b");
    }
    
}
