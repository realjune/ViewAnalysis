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
import android.view.View;
import android.widget.AbsListView.LayoutParams;

/**
 * @author Codefarmer@sina.com
 */
public class VATextView extends android.widget.TextView {
	private boolean isMarquee = false;
	private static final String TAG = "TextView";

	public VATextView(Context context, AttributeSet attrs) {
		super(context);
		setAttributeSet(attrs);
		// setLayoutParams(generateLayoutParams(attrs));
	}

	@Override
	public boolean isFocused() {
		if (isMarquee) {
			return true;
		} else {
			return super.isFocused();
		}
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
					this.setId(viewId.substring("@+id/".length()).hashCode());
				}
				break;
			case text:
				String value = YDResource.getInstance().getString(attrs.getAttributeValue(i));
				this.setText(value);
				Log.i(TAG, value);
				break;
			case ellipsize:
				if (attrs.getAttributeBooleanValue(i, false)) {
					isMarquee = true;
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

				Log.i("textview", "设置属性值");
				int id = YDResource.getInstance().getIdentifier("R.style." + style);
				this.setTextAppearance(getContext(), id);
				break;

			case contentDescription:
				String content = attrs.getAttributeValue(i);
				this.setContentDescription(content);
				break;

			default:
				break;
			}
		}
	}

	public LayoutParams generateLayoutParams(AttributeSet attrs) {
		LayoutParams params = (LayoutParams) getLayoutParams();
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

}
