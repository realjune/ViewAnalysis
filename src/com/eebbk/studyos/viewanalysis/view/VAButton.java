package com.eebbk.studyos.viewanalysis.view;

import java.util.HashMap;

import com.eebbk.studyos.viewanalysis.view.engine.ParamValue;
import com.eebbk.studyos.viewanalysis.view.engine.YDResource;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

/**
 * @author Codefarmer@sina.com
 */
public class VAButton extends android.widget.Button {

	public VAButton(Context context, AttributeSet attrs) {
		super(context);
		setAttributeSet(attrs);
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
			switch (key) {
			case id:
				this.setTag(attrs.getAttributeValue(i));
				break;
			case text:
				String value = YDResource.getInstance().getString(attrs.getAttributeValue(i));
				this.setText(value);
				break;
			case ellipsize:
				if (attrs.getAttributeBooleanValue(i, false)) {

					this.setFocusable(true);
					this.setFocusableInTouchMode(true);
					this.setSingleLine(true);
					this.setEllipsize(TruncateAt.MARQUEE);
					this.setMarqueeRepeatLimit(1000);
					this.setSingleLine();
					this.setHorizontallyScrolling(true);
					this.requestFocus();
				}
				break;
			case fadingEdge:
				this.setHorizontalFadingEdgeEnabled(attrs.getAttributeBooleanValue(i, false));
				break;
			case scrollHorizontally:
				this.setHorizontallyScrolling(attrs.getAttributeBooleanValue(i, false));
				break;
			case textColor:
				this.setTextColor(YDResource.getInstance().getIntColor(attrs.getAttributeValue(i)));
				break;
			case textSize:
				String val1 = attrs.getAttributeValue(i);
				if (!TextUtils.isEmpty(val1)) {
					this.setTextSize(YDResource.getInstance().calculateRealSize(val1));
				}
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
			case background:
				String backgroundString = attrs.getAttributeValue(i);
				if (backgroundString.startsWith("#")) {
					this.setBackgroundColor(YDResource.getInstance().getIntColor(attrs.getAttributeValue(i)));
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
			case textStyle:
				if ("bold".equalsIgnoreCase(attrs.getAttributeValue(i)))
					this.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
				break;
			case style:
				String style = attrs.getAttributeValue(i);
				style = style.substring(style.indexOf("/") + 1);

				Log.i("button", "设置属性值");
				int id = YDResource.getInstance().getIdentifier("R.style." + style);
				this.setTextAppearance(getContext(), id);
				break;
			case src:

				break;
			case contentDescription:
				String content = attrs.getAttributeValue(i);
				this.setContentDescription(content);
				break;
			case gravity:
				this.setGravity(Gravity.CENTER_HORIZONTAL);
				break;
			default:
				break;
			}
		}
	}

}
