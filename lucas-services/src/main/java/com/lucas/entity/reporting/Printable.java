package com.lucas.entity.reporting;

import java.util.List;

/**
 * @Author Adarsh
 * @Product Lucas.
 * Created By: Adarsh
 * Created On Date: 9/10/14  Time: 11:46 AM
 * This Class provide the implementation for the functionality of..
 */

public interface Printable<Printable> {

    public List<String> getHeaderElements();

    public List<String> getDataElements();
}
