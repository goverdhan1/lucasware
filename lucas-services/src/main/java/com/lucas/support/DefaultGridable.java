package com.lucas.support;


import com.lucas.entity.reporting.Printable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @Author Adarsh
 * @Product Lucas.
 * Created By: Adarsh
 * Created On Date: 9/9/14  Time: 11:46 AM
 * This Class provide the implementation for the functionality of..
 */

public class DefaultGridable implements Gridable {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultGridable.class);

    private String title;
    private List<Printable> listOfPrintable;

    public DefaultGridable(List<Printable> listOfPrintable) {
        this.listOfPrintable = listOfPrintable;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public List<String> getHeaderRow() {
        return this.listOfPrintable.get(0).getHeaderElements();
    }

    @Override
    public List<Printable> getDataRows() {
        return this.listOfPrintable;
    }
}