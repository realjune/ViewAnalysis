package com.eebbk.studyos.viewanalysis.view.utils;

import java.io.File;

import android.content.Context;
import android.view.View;

import com.eebbk.studyos.viewanalysis.view.engine.YDResource;

public class InflaterUtil {

	private static InflaterUtil mInflaterUtil = null;
	private YDResource resource = null;

	public static InflaterUtil getInstance(Context context) {
		if (null == mInflaterUtil) {
			mInflaterUtil = new InflaterUtil(context);
		}
		return mInflaterUtil;
	}

	public InflaterUtil(Context context) {
		resource = YDResource.getInstance();
		resource.initResourcePath(context, "");
	}

	/**
	 * 根据保存路径解析出视图
	 * 
	 * @author 林启明
	 * @日期 2014-11-10
	 * @param filePath
	 * @return View
	 */
	public View getViewByInflate(String filePath) {
		View v = null;
		File file = new File(filePath);
		if (file.exists()) {
			v = resource.getLayout(filePath);
		}
		return v;
	}

	public void removeResource() {
		if (resource != null) {
			resource.removeInstance();
		}
	}
}
