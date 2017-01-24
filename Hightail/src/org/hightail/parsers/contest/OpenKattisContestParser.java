package org.hightail.parsers.contest;

import org.hightail.parsers.task.OpenKattisTaskParser;
import org.hightail.parsers.task.TaskParser;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.LinkRegexFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.TagFindingVisitor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class OpenKattisContestParser implements ContestParser {

    final static private String taskUrlRegExp = "/contests/.+?/problems/.+$";
    final static private TaskParser taskParser = new OpenKattisTaskParser();

    @Override
    public ArrayList<String> getProblemURLListFromURL(String URL) throws ParserException, InterruptedException {
        URL = URL.trim();

        if (URL.contains("/problems")) {
            URL = URL.replace("/problems", "");
        }

        if (URL.contains("/standings")) {
            URL = URL.replace("/standings", "");
        }

        Parser parser = new Parser(URL);

        String[] tagsToVisit = {"a"};
        TagFindingVisitor visitor = new TagFindingVisitor(tagsToVisit);
        parser.visitAllNodesWith(visitor);
        Node[] aNodes = visitor.getTags(0);

        LinkRegexFilter filter = new LinkRegexFilter(taskUrlRegExp);
        ArrayList<String> links = new ArrayList<>();
        Set<String> usedLinks = new HashSet<>();
        for (Node node : aNodes) {
            if (filter.accept(node)) {
                String linkUrl = ((LinkTag) node).extractLink();
                if (!usedLinks.contains(linkUrl)) {
                    usedLinks.add(linkUrl);
                    links.add(linkUrl);
                }
            }
        }

        return links;
    }

    @Override
    public boolean isCorrectURL(String URL) {
        return URL.contains(".kattis.com/");
    }

    @Override
    public TaskParser getTaskParser() {
        return taskParser;
    }
}
