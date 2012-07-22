/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hightail.parsers.contest;

import java.util.ArrayList;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.LinkRegexFilter;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.TagFindingVisitor;

/**
 *
 * @author robertrosolek
 */
public class CodeForcesContestParser extends ContestParser {

    final static private String taskUrlRegExp = "/contest/211/problem/(.*)";
    final static private String contestDomain = "codeforces.com";
    
    @Override
    public ArrayList<String> parse(String URL) throws ParserException {
        
        URL = URL.trim();
        
        Parser parser = new Parser(URL);
                
        String[] tagsToVisit = {"a"};
        TagFindingVisitor visitor = new TagFindingVisitor(tagsToVisit);
        parser.visitAllNodesWith(visitor);
        Node[] aNodes = visitor.getTags(0);
        
        LinkRegexFilter filter = new LinkRegexFilter(taskUrlRegExp);
        
        ArrayList<String> result = new ArrayList<String>();
        
        for (Node node: aNodes)
            if (filter.accept(node)) {
                String linkUrl = ((LinkTag) node).extractLink();
                result.add(contestDomain + linkUrl);
            }
        
        return result;        
    }
    
}
