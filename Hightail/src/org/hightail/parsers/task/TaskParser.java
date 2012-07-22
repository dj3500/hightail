package org.hightail.parsers.task;

import java.util.ArrayList;
import org.hightail.Testcase;

/**
 *
 * @author jtarnawski
 */
public abstract class TaskParser {
    abstract public ArrayList<Testcase> parse(String URL);
}
