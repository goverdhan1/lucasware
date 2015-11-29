package com.lucas.support;

import com.lucas.entity.reporting.Printable;

import java.util.List;

/**
 * @Author Adarsh
 * @Product Lucas.
 * Created By: Adarsh
 * Created On Date: 9/9/14  Time: 11:44 AM
 * This Class provide the implementation for the functionality of..
 */

public interface Gridable{

    public List<String> getHeaderRow();

    public List<Printable> getDataRows();
}
