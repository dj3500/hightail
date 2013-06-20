/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hightail.parsers.contest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.hightail.Problem;
import org.hightail.parsers.task.CodeChefTaskParser;
import org.hightail.parsers.task.TaskParser;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.LinkRegexFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.TagFindingVisitor;

/**
 *
 * @author krig
 */
public class CodeChefContestParser implements ContestParser {
    
    final static private String taskUrlRegExp = "/(.+)/problems/([A-Z0-9]+)$";
    final static private TaskParser taskParser = new CodeChefTaskParser();

    @Override
    public ArrayList<Problem> parse(String URL) throws ParserException, InterruptedException {
        URL = URL.trim();
        
        Parser parser = new Parser(URL);
        
        // get all <a> tags
        String[] tagsToVisit = {"a"};
        TagFindingVisitor visitor = new TagFindingVisitor(tagsToVisit);
        parser.visitAllNodesWith(visitor);
        Node[] aNodes = visitor.getTags(0);
        
        // filter link tags for those that link to problems
        // (we recognize that on the base of link itself, by using regexp)
        LinkRegexFilter filter = new LinkRegexFilter(taskUrlRegExp);
        ArrayList<String> links = new ArrayList<>();
        for (Node node: aNodes) {
            if (filter.accept(node)) {
                String linkUrl = ((LinkTag) node).extractLink();
                links.add(linkUrl);
            }
        }
        
        // remove link duplicates
        Set<String> s = new HashSet<>(links);
        links = new ArrayList<>(s);
        Collections.sort(links);
        
        if(links.isEmpty()) {
            throw new ParserException("No links to tasks found.");
        }
        
        ArrayList<Problem> problems = new ArrayList<>();
        for (String link : links) {
            problems.add(taskParser.parse(link));
        }
        
        return problems;
    }

    @Override
    public boolean isCorrectURL(String URL) {
        return URL.contains("codechef.com");
    }
    
}
