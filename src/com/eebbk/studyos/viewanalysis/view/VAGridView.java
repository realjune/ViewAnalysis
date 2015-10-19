package com.eebbk.studyos.viewanalysis.view;

import java.util.HashMap;

import com.eebbk.studyos.viewanalysis.view.engine.ParamValue;
import com.eebbk.studyos.viewanalysis.view.engine.YDResource;
import com.eebbk.studyos.viewanalysis.view.utils.DensityUtil;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.GridView;

/**
 * @author Codefarmer@sina.com
 */
public class VAGridView extends GridView {
	public VAGridView(Context context, AttributeSet attrs) {
		super(context);
		setAttributeSet(attrs);
		setLayoutParams(generateLayoutParams(attrs));
	}

	@Override
	public LayoutParams generateLayoutParams(AttributeSet attrs) {
		LayoutParams params = (LayoutParams) generateDefaultLayoutParams();
		HashMap<String, ParamValue> map = YDResource.getInstance().getViewMap();
		int count = attrs.getAttributeCount();
		for (int i = 0; i < count; i++) {
			ParamValue key = map.get(attrs.getAttributeName(i));
			if (key == null) {
				continue;
			}
			switch (key) {
			case layout_width:
				String width = attrs.getAttributeValue(i);

				if (width.startsWith("f") || width.startsWith("m")) {
					params.width = LayoutParams.MATCH_PARENT;
					break;
				}
				if (width.startsWith("wrap")) {
					params.width = LayoutParams.WRAP_CONTENT;
					break;
				}
				params.width = YDResource.getInstance().calculateRealSize(width);
				break;
			case layout_height:
				String height = attrs.getAttributeValue(i);
				if (height.startsWith("f") || height.startsWith("m")) {
					params.height = LayoutParams.MATCH_PARENT;
					break;
				}
				if (height.startsWith("wrap")) {
					params.height = LayoutParams.WRAP_CONTENT;
					break;
				}
				params.height = YDResource.getInstance().calculateRealSize(height);
				break;
			case fadingEdge:
				this.setHorizontalFadingEdgeEnabled(attrs.getAttributeBooleanValue(i, false));
				break;
			case visibility:
				String val2 = attrs.getAttributeValue(i);
				if (!TextUtils.isEmpty(val2)) {
					if (val2.equals("invisible")) {
						this.setVisibility(View.INVISIBLE);
					} else if (val2.equalsIgnoreCase("gone")) {
						this.setVisibility(View.GONE);
					}
				}
				break;
			default:
				break;
			}
		}

		return params;
	}

	public void setAttributeSet(AttributeSet attrs) {
		HashMap<String, ParamValue> maps = YDResource.getInstance().getViewMap();
		int count = attrs.getAttributeCount();
		for (int i = 0; i < count; i++) {
			String name = attrs.getAttributeName(i);
			ParamValue key = maps.get(name);
			if (key == null) {
				continue;
			}
			switch (key) {
			case id:
				String viewId = attrs.getAttributeValue(i);
				if (viewId.startsWith("@+id/")) {
					setId(viewId.substring("@+id/".length()).hashCode());
				}
				break;
			case verticalSpacing:
				this.setVerticalSpacing(DensityUtil.px2dip(getContext(),
						YDResource.getInstance().calculateRealSize(attrs.getAttributeValue(i))));
				break;
			case numColumns:
				Log.d("num", "aaaa");
				this.setNumColumns(attrs.getAttributeIntValue(i, 2));
				break;
			case splitMotionEvents:
				String events = attrs.getAttributeValue(i);
				boolean ret = true;
				if (events.equals("false")) {
					ret = false;
				}
				setMotionEventSplittingEnabled(ret);
				break;
			case cacheColorHint:
				this.setCacheColorHint(YDResource.getInstance().getIntColor(attrs.getAttributeValue(i)));
				break;
			case horizontalSpacing:
				this.setHorizontalSpacing(DensityUtil.px2dip(getContext(),
						YDResource.getInstance().calculateRealSize(attrs.getAttributeValue(i))));
				break;
			default:
				break;
			}
		}
	}

}
