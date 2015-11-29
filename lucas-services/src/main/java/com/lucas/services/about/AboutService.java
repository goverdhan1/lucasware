/**
 *
 *  Copyright (c)  Lucas Systems Inc.
 *
 **/
package com.lucas.services.about;

import com.lucas.entity.about.About;

public interface AboutService {

	//returns About bean containing build information etc.
    public About getAbout();
}