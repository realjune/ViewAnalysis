package com.eebbk.studyos.viewanalysis.view;

import java.util.HashMap;

import com.eebbk.studyos.viewanalysis.view.engine.ParamValue;
import com.eebbk.studyos.viewanalysis.view.engine.YDResource;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;

/**
 * @author Codefarmer@sina.com
 */
public class VAScrollView extends ScrollView {
	public VAScrollView(Context context, AttributeSet attrs) {
		super(context);
		setAttributeSet(attrs);
		setLayoutParams(generateLayoutParams(attrs));
	}

	@SuppressWarnings("deprecation")
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
			case contentDescription:
				String content = attrs.getAttributeValue(i);
				this.setContentDescription(content);
				break;
			}
		}
	}

	@Override
	public LayoutParams generateLayoutParams(AttributeSet attrs) {
		LayoutParams params = generateDefaultLayoutParams();
		HashMap<String, ParamValue> map = YDResource.getInstance().getViewMap();

		int count = attrs.getAttributeCount();
		for (int i = 0; i < count; i++) {
			ParamValue key = map.get(attrs.getAttributeName(i));
			if (key == null) {
				continue;
			}
			switch (key) {
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

}
