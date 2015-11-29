package com.lucas.alps.support.view;

import com.lucas.alps.viewtype.BaseView;

public interface DataView {
	boolean hasView();
	Class<? extends BaseView> getView();
	Object getData();
}
