package org.hightail.parsers.task;

import org.hightail.TestcaseSet;

/**
 *
 * @author jtarnawski
 */
public abstract class TaskParser {
    abstract public TestcaseSet parse(String URL);
}
