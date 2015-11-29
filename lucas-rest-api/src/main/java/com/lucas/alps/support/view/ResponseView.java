package com.lucas.alps.support.view;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.lucas.alps.viewtype.BaseView;

@Retention(RetentionPolicy.RUNTIME)
public @interface ResponseView {
	public Class<? extends BaseView> value();
}
