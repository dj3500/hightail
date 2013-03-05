package org.hightail.parsers.contest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.hightail.util.StringPair;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.LinkRegexFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.TagFindingVisitor;

public class CodeForcesContestParser extends ContestParser {

    final static private String taskUrlRegExp = "/contest/(.*)/problem/(.*)";
    
    @Override
    public ArrayList<StringPair> parse(String URL) throws ParserException {
        
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
        
        ArrayList<String> names = new ArrayList<>();
        for (String link: links) {
            String[] splitted = link.split("/");
            String name = splitted[splitted.length - 1];
            names.add(name);
        }
        
        ArrayList<StringPair> result = new ArrayList<>();
        for (int i = 0; i < links.size(); ++i) {
            result.add(new StringPair(links.get(i), names.get(i)));
        }
        
        return result;        
    }
    
}
