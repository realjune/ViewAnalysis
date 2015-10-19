package com.eebbk.studyos.viewanalysis.view;

import java.util.HashMap;

import com.eebbk.studyos.viewanalysis.view.engine.ParamValue;
import com.eebbk.studyos.viewanalysis.view.engine.YDResource;

import android.content.Context;
import android.util.AttributeSet;

/**
 * @author Codefarmer@sina.com
 */
public class VAFrameLayout extends android.widget.FrameLayout {

	public VAFrameLayout(Context context, AttributeSet attrs) {
		super(context);
		setLayoutParams(generateLayoutParams(attrs));
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public LayoutParams generateLayoutParams(AttributeSet attrs) {
		// TODO Auto-generated method stub
		LayoutParams params = this.generateDefaultLayoutParams();
		HashMap<String, ParamValue> map = YDResource.getInstance().getLayoutMap();
		params.width = -2;
		params.height = -2;
		int count = attrs.getAttributeCount();
		for (int i = 0; i < count; i++) {
			String name = attrs.getAttributeName(i);
			ParamValue key = map.get(name);
			if (key == null) {
				continue;
			}
			switch (key) {
			case layout_width:
				String width = attrs.getAttributeValue(i);
				if (width.startsWith("f") || width.startsWith("m")) {
					params.width = LayoutParams.FILL_PARENT;
					break;
				}
				if (width.startsWith("w")) {
					params.width = LayoutParams.WRAP_CONTENT;
					break;
				}
				params.width = YDResource.getInstance().calculateRealSize(width);
				break;
			case layout_height:
				String height = attrs.getAttributeValue(i);
				if (height.startsWith("f") || height.startsWith("m")) {
					params.width = -1;
					break;
				}
				if (height.startsWith("w")) {
					params.width = -2;
					break;
				}
				params.height = YDResource.getInstance().calculateRealSize(height);
				break;
			case layout_gravity:

				params.gravity = YDResource.getInstance().getGravity(attrs.getAttributeValue(i));

				break;
			case layout_marginLeft:
				params.leftMargin = YDResource.getInstance().calculateRealSize(attrs.getAttributeValue(i));
				break;
			case layout_margin:
				int tm = YDResource.getInstance().calculateRealSize(attrs.getAttributeValue(i));
				params.setMargins(tm, tm, tm, tm);
				break;

			default:
				break;
			}
		}
		return params;
	}

}
