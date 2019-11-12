package com.lianxi.dingtu.newsnfc.mvp.ui.widget;


public class HelperManager {

	private HelperManager(){
		
	}

	public static AnimationsHelper getAnimationsHelper(){
		return AnimationsHelper.getSington();
	}

}