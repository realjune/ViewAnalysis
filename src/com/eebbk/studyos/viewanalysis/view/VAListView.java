package com.eebbk.studyos.viewanalysis.view;

import java.util.HashMap;

import com.eebbk.studyos.viewanalysis.view.engine.ParamValue;
import com.eebbk.studyos.viewanalysis.view.engine.YDResource;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

/**
 * @author Codefarmer@sina.com
 */
public class VAListView extends ListView {
	public VAListView(Context context, AttributeSet attrs) {
		super(context);
		setAttributeSet(attrs);
		setLayoutParams(generateLayoutParams(attrs));
	}

	@Override
	public LayoutParams generateLayoutParams(AttributeSet attrs) {
		LayoutParams params = (LayoutParams) generateDefaultLayoutParams();
		HashMap<String, ParamValue> map = YDResource.getInstance().getViewMap();
		int count = attrs.getAttributeCount();
		int leftValues = 0;
		int rightValues = 0;
		int topValues = 0;
		int bottomValues = 0;
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
			case layout_marginTop:
				topValues = YDResource.getInstance().calculateRealSize(attrs.getAttributeValue(i));
				break;
			case layout_marginLeft:
				leftValues = YDResource.getInstance().calculateRealSize(attrs.getAttributeValue(i));
				break;
			case layout_marginRight:
				rightValues = YDResource.getInstance().calculateRealSize(attrs.getAttributeValue(i));
				break;
			case layout_marginBottom:
				bottomValues = YDResource.getInstance().calculateRealSize(attrs.getAttributeValue(i));
				break;
			default:
				break;
			}
		}
		this.setPadding(leftValues, topValues, rightValues, bottomValues);
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
			case divider:
				int color = YDResource.getInstance().getIntColor(attrs.getAttributeValue(i));
				ColorDrawable cd = new ColorDrawable(color);
				this.setDivider(cd);
				break;
			case splitMotionEvents:
				String events = attrs.getAttributeValue(i);
				boolean ret = true;
				if (events.equals("false")) {
					ret = false;
				}
				setMotionEventSplittingEnabled(ret);
				break;
			default:
				break;
			}
		}
	}

}
