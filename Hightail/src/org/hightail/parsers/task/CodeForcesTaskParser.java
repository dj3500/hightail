/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hightail.parsers.task;

import org.hightail.Testcase;
import org.hightail.TestcaseSet;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.beans.FilterBean;
import org.htmlparser.filters.CssSelectorNodeFilter;
import org.htmlparser.filters.HasParentFilter;
import org.htmlparser.util.ParserException;

/**
 *
 * @author robertrosolek
 */
public class CodeForcesTaskParser extends TaskParser {

    @Override
    public TestcaseSet parse(String URL) throws ParserException {
        
        URL = URL.trim();
        
        FilterBean fb = new FilterBean();
        
        // extract inputs
        CssSelectorNodeFilter inputFilter = new CssSelectorNodeFilter("div.input");
        HasParentFilter preInputFilter = new HasParentFilter(inputFilter);
        fb.setFilters(new NodeFilter[] {preInputFilter});
        fb.setURL(URL);
        String[] inputs = fb.getText().split("Input");
        
        // extract outputs
        CssSelectorNodeFilter outputFilter = new CssSelectorNodeFilter("div.output");
        HasParentFilter preOutputFilter = new HasParentFilter(outputFilter);
        fb.setFilters(new NodeFilter[] {preOutputFilter});
        fb.setURL(URL);
        String[] outputs = fb.getText().split("Output");
        
                
        if (inputs.length != outputs.length)
                throw new ParserException();
        
        TestcaseSet result = new TestcaseSet();
        for (int i = 1; i < inputs.length; ++i)
            result.add(new Testcase(inputs[i], outputs[i]));
        
        return result;
    }
    
}
