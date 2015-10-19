package com.eebbk.studyos.viewanalysis.view;

import java.util.HashMap;

import com.eebbk.studyos.viewanalysis.view.engine.ParamValue;
import com.eebbk.studyos.viewanalysis.view.engine.YDResource;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;

/**
 * @author Codefarmer@sina.com
 */
public class VALinearLayout extends android.widget.LinearLayout {

	private static final String TAG = "YDLinearLayout";

	public VALinearLayout(Context context, AttributeSet attrs) {
		super(context);
		// setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
		// LayoutParams.MATCH_PARENT));
		setAttributeSet(attrs);
		setLayoutParams(generateLayoutParams(attrs));
	}

	public void setAttributeSet(AttributeSet attrs) {
		HashMap<String, ParamValue> map = YDResource.getInstance().getViewMap();

		int count = attrs.getAttributeCount();
		for (int i = 0; i < count; i++) {
			ParamValue key = map.get(attrs.getAttributeName(i));
			if (key == null) {
				continue;
			}
			Log.d("setupview", key.toString());
			switch (key) {
			case id:
				String viewId = attrs.getAttributeValue(i);
				if (viewId.startsWith("@+id/")) {
					setId(viewId.substring("@+id/".length()).hashCode());
				}
				break;
			case background:
				Log.i("litongtest", "设置背景颜色");

				String backgroundString = attrs.getAttributeValue(i);
				if (backgroundString.startsWith("#")) {
					int color = YDResource.getInstance().getIntColor(attrs.getAttributeValue(i));
					this.setBackgroundColor(color);
				} else {
					if (backgroundString.startsWith("@drawable/")) {
						backgroundString = backgroundString.substring(10);
					}
					String rootpath = getContext().getFilesDir().toString();
					StringBuilder sb = new StringBuilder();
					sb.append(rootpath).append("/").append(backgroundString).append(".png");

					Bitmap bm = BitmapFactory.decodeFile(sb.toString());
					setBackgroundDrawable(new BitmapDrawable(bm));
				}

				break;
			case orientation:
				String orientation = attrs.getAttributeValue(i);
				if ("horizontal".equalsIgnoreCase(orientation)) {
					this.setOrientation(HORIZONTAL);

					Log.i(TAG, "设置了水平的布局");
				} else {
					this.setOrientation(VERTICAL);
				}
				break;
			}
		}
	}

	@Override
	public LayoutParams generateLayoutParams(AttributeSet attrs) {
		LayoutParams params = generateDefaultLayoutParams();
		HashMap<String, ParamValue> map = YDResource.getInstance().getLayoutMap();
		// params.weight=0;
		// params.gravity=-1;

		int count = attrs.getAttributeCount();
		for (int i = 0; i < count; i++) {
			String name = attrs.getAttributeName(i);
			ParamValue key = map.get(name);
			if (key == null) {
				continue;
			}
			String val;
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
				Log.i(TAG, "设置宽度" + params.width);
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
			case layout_centerHorizontal:
				params.gravity = Gravity.CENTER_HORIZONTAL;
				break;
			case layout_centerVertical:
				params.gravity = Gravity.CENTER_VERTICAL;
				break;
			case layout_weight:
				params.weight = attrs.getAttributeFloatValue(i, 0);
				break;
			case gravity:
				this.setGravity(YDResource.getInstance().getGravity(attrs.getAttributeValue(i)));
				break;
			case contentDescription:
				String content = attrs.getAttributeValue(i);
				this.setContentDescription(content);
				break;
			case layout_margin:
				int values = YDResource.getInstance().calculateRealSize(attrs.getAttributeValue(i));
				params.bottomMargin = values;
				params.leftMargin = values;
				params.rightMargin = values;
				params.topMargin = values;
				break;
			case layout_marginLeft:
				params.leftMargin = YDResource.getInstance().calculateRealSize(attrs.getAttributeValue(i));
				break;
			case layout_marginRight:
				params.rightMargin = YDResource.getInstance().calculateRealSize(attrs.getAttributeValue(i));
				break;
			case layout_marginTop:
				params.topMargin = YDResource.getInstance().calculateRealSize(attrs.getAttributeValue(i));
				break;
			case layout_marginBottom:
				params.bottomMargin = YDResource.getInstance().calculateRealSize(attrs.getAttributeValue(i));
				break;
			case layout_gravity:
				params.gravity = YDResource.getInstance().getGravity(attrs.getAttributeValue(i));
				break;
			default:
				Log.e(TAG, "未处理的属性:" + name);
				break;
			}
		}
		return params;
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
	}
}
