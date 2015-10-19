package com.eebbk.studyos.viewanalysis.view.engine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.lang.reflect.Field;
import java.util.HashMap;

import org.xmlpull.v1.XmlPullParser;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Xml;
import android.view.Gravity;
import android.view.View;

import com.eebbk.studyos.viewanalysis.view.VAImageView;
import com.eebbk.studyos.viewanalysis.view.utils.DensityUtil;
import com.eebbk.studyos.viewanalysis.view.utils.Logger;

/**
 * @author Codefarmer@sina.com
 */
public class YDResource {

	private static final String TAG = "YDResource";
	private SoftReference<HashMap<String, ParamValue>> wkMap;
	private SoftReference<HashMap<String, String>> wkstrings;
	private SoftReference<HashMap<String, ParamValue>> wkViewMap;
	private String rootpath;
	private String vga;

	private Context mContext;

	private YDResource() {
	}

	private static YDResource resource;

	public static YDResource getInstance() {
		if (resource == null) {
			resource = new YDResource();
		}
		return resource;
	}

	public static void removeInstance() {
		resource = null;
	}

	public void initResourcePath(Context mContext, String path) {
		if (Logger.debug) {
			rootpath = mContext.getFilesDir().toString();
		} else {
			rootpath = path;
		}
		DisplayMetrics dm = DensityUtil.getDisplayMetrics(mContext);
		if (dm.heightPixels > 320) {
			if (Logger.debug) {
				vga = "/drawable-mdpi/";
			} else {
				vga = "/drawable-hdpi/";
			}
		} else {
			vga = "/drawable-mdpi/";
		}
		Logger.i("屏幕：" + vga);
		this.mContext = mContext;
	}

	public HashMap getLayoutMap() {

		if (wkMap == null || wkMap.get() == null) {
			HashMap<String, ParamValue> map = new HashMap<String, ParamValue>();
			map.put("layout_width", ParamValue.layout_width);
			map.put("layout_height", ParamValue.layout_height);
			map.put("orientation", ParamValue.orientation);
			map.put("layout_centerHorizontal", ParamValue.layout_centerHorizontal);
			map.put("layout_centerVertical", ParamValue.layout_centerVertical);
			map.put("layout_marginLeft", ParamValue.layout_marginLeft);
			map.put("layout_marginRight", ParamValue.layout_marginRight);
			map.put("layout_margin", ParamValue.layout_margin);
			map.put("layout_gravity", ParamValue.layout_gravity);
			map.put("layout_alignParentRight", ParamValue.layout_alignParentRight);
			map.put("layout_weight", ParamValue.layout_weight);
			map.put("contentDescription", ParamValue.contentDescription);
			map.put("gravity", ParamValue.gravity);
			/* 李通 */
			map.put("id", ParamValue.id);
			map.put("layout_below", ParamValue.layout_below);
			map.put("layout_above", ParamValue.layout_above);
			map.put("layout_toLeftOf", ParamValue.layout_toLeftOf);
			map.put("layout_toRightOf", ParamValue.layout_toRightOf);
			map.put("background", ParamValue.background);
			map.put("layout_marginTop", ParamValue.layout_marginTop);
			map.put("layout_marginBottom", ParamValue.layout_marginBottom);
			map.put("layout_marginLeft", ParamValue.layout_marginLeft);
			map.put("layout_marginRight", ParamValue.layout_marginRight);

			wkMap = new SoftReference<HashMap<String, ParamValue>>(map);
		}
		return wkMap.get();
	}

	public HashMap<String, ParamValue> getViewMap() {
		if (wkViewMap == null || wkViewMap.get() == null) {
			HashMap<String, ParamValue> map = new HashMap<String, ParamValue>();
			map.put("id", ParamValue.id);
			map.put("text", ParamValue.text);
			map.put("ellipsize", ParamValue.ellipsize);
			map.put("fadingEdge", ParamValue.fadingEdge);
			map.put("scrollHorizontally", ParamValue.scrollHorizontally);
			map.put("textColor", ParamValue.textColor);
			map.put("textSize", ParamValue.textSize);
			map.put("visibility", ParamValue.visibility);
			map.put("background", ParamValue.background);
			map.put("textStyle", ParamValue.textStyle);
			map.put("style", ParamValue.style);
			map.put("layout_width", ParamValue.layout_width);
			map.put("layout_height", ParamValue.layout_height);
			map.put("layout_below", ParamValue.layout_below);
			map.put("contentDescription", ParamValue.contentDescription);
			map.put("src", ParamValue.src);
			map.put("gravity", ParamValue.gravity);
			map.put("orientation", ParamValue.orientation);
			map.put("numColumns", ParamValue.numColumns);
			map.put("verticalSpacing", ParamValue.verticalSpacing);
			map.put("horizontalSpacing", ParamValue.horizontalSpacing);
			map.put("background", ParamValue.background);
			wkViewMap = new SoftReference<HashMap<String, ParamValue>>(map);
		}
		return wkViewMap.get();
	}

	public int getIntColor(String val) {

		if (!TextUtils.isEmpty(val)) {
			if (val.startsWith("#")) {
				int length = val.length();
				if (length == 7) {
					long j = Long.decode(val.replace("#", "#FF"));

					return (int) j;
				} else if (length == 9) {
					long j = Long.decode(val);

					return (int) j;
				} else {
					Logger.i("返回白色背景");
					return 0xFFffffff;
				}
			}
		}
		return 0xFF000000;
	}

	public int calculateRealSize(String s) {
		int i = -2;
		try {
			i = Integer.parseInt(s);
			return i;
		} catch (Exception e) {
			int tmp = s.indexOf("d");
			int tmp2 = s.indexOf("s");
			int tmp3 = tmp != -1 ? tmp : tmp2;

			int j = Integer.parseInt(s.substring(0, tmp3));
			Log.i(TAG, "计算后的值为" + j);
			return j;
		}
	}

	public int getGravity(String gravity) {

		Log.i("YDResource gravity", gravity);
		String[] s = gravity.toUpperCase().split("\\|");
		int sum = Gravity.TOP;
		try {
			Class clazz = Class.forName("android.view.Gravity");
			for (int i = 0; i < s.length; i++) {
				Field f = clazz.getField(s[i]);
				sum |= f.getInt(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sum;
	}

	public int getIdentifier(String tid) {
		String packagename = "com.example.testviewtree";
		StringBuilder sb = new StringBuilder();
		sb.append(packagename);
		sb.append(".R$");
		int rid = 0;
		String[] classes = tid.split("\\.");
		System.out.println(classes.toString());
		sb.append(classes[1]);
		try {
			Class<?> innerClass = Class.forName(sb.toString());
			Object obj = innerClass.newInstance();

			Field field = innerClass.getDeclaredField(classes[2]);
			field.setAccessible(true);
			rid = (Integer) field.get(obj);
			Log.i(TAG, "id :" + rid);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rid;
	}

	public String getString(String s) {
		if (!s.startsWith("@")) {
			return s;
		}
		if (wkstrings == null || wkstrings.get() == null) {
			Logger.i("字符串变空了");
			wkstrings = new SoftReference<HashMap<String, String>>(readStringsXml());
		}
		s = s.substring(8);

		return wkstrings.get().get(s);
	}

	/**
	 * 加载String
	 * 
	 * @return
	 */
	private HashMap<String, String> readStringsXml() {

		InputStream is = null;
		try {

			// is = new FileInputStream(rootpath + "/strings.xml");
			is = mContext.getAssets().open("strings.xml");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		XmlPullParser parser = Xml.newPullParser();
		try {
			parser.setInput(is, "utf-8");

			HashMap<String, String> map = new HashMap<String, String>();
			for (int event = parser.getEventType(); event != XmlPullParser.END_DOCUMENT; event = parser.next()) {
				if (event == XmlPullParser.START_TAG) {
					if ("string".equals(parser.getName())) {
						String name = parser.getAttributeValue(0);
						String value = parser.nextText();
						// Log.i("string:",name+"="+value);
						map.put(name, value);
					}
				}
			}

			return map;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void displayImage(String imagename, VAImageView imageView) {
		if (imagename.startsWith("@drawable/")) {
			Resources res = mContext.getResources();
			int id = res.getIdentifier(imagename, "drawable", mContext.getPackageName());
			imageView.setImageResource(id);
		} else {
			StringBuilder sb = new StringBuilder(imagename);
			Bitmap bm = BitmapFactory.decodeFile(sb.toString());
			imageView.setImageBitmap(bm);
		}
	}

	public void displayImageByPath(String imgPath, VAImageView imageView) {
		if (imgPath.startsWith("@drawable/")) {
			imgPath = imgPath.substring(10);
		}
		StringBuilder sb = new StringBuilder(imgPath);
		Bitmap bm = BitmapFactory.decodeFile(sb.toString());
		imageView.setImageBitmap(bm);
	}

	/**
	 * 
	 * 方法的一句话概述
	 * 
	 * @author 林启明
	 * @日期 2014-11-9
	 * @param filePath
	 *            文件所在文件夹
	 * @param str
	 *            文件名 比如 expl.xml
	 * @return View
	 */
	public View getLayout(String filePath) {
		if (wkstrings == null || wkstrings.get() == null) {
			readStringsXml();
		}
		YDLayoutInflate inflate = new YDLayoutInflate(mContext);
		String sb = new String();
		sb = filePath;
		Logger.i(sb.toString());
		return inflate.inflate(sb.toString(), null);
	}
}
