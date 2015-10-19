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
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * @author Codefarmer@sina.com
 */
public class VARelativeLayout extends android.widget.RelativeLayout {

	// private static final String TAG = "YDRelativeLayout";

	public VARelativeLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public VARelativeLayout(Context context, AttributeSet attrs) {
		super(context);
		setAttributeSet(attrs);
		setLayoutParams(generateLayoutParams(attrs));

	}

	public VARelativeLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	/* 设置自身属性 */
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
			case gravity:
				this.setGravity(YDResource.getInstance().getGravity(attrs.getAttributeValue(i)));
				break;
			}
		}
	}

	@Override
	public LayoutParams generateLayoutParams(AttributeSet attrs) {
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(0, 0);

		HashMap<String, ParamValue> map = YDResource.getInstance().getLayoutMap();
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
				if (width.startsWith("fill") || width.startsWith("match")) {
					params.width = -1;
					break;
				}
				if (width.startsWith("w")) {
					params.width = -2;
					break;
				}
				params.width = YDResource.getInstance().calculateRealSize(width);
				break;
			case layout_height:
				String height = attrs.getAttributeValue(i);
				if (height.startsWith("fill") || height.startsWith("match")) {
					params.height = -1;
					break;
				}
				if (height.startsWith("wrap")) {
					params.height = -2;
					break;
				}
				params.height = YDResource.getInstance().calculateRealSize(height);
				break;
			case layout_centerHorizontal:
				if (attrs.getAttributeBooleanValue(i, false)) {
					params.addRule(this.CENTER_HORIZONTAL, this.TRUE);
				}
				break;
			case layout_centerVertical:
				if (attrs.getAttributeBooleanValue(i, false)) {
					params.addRule(this.CENTER_VERTICAL, this.TRUE);
				}
				break;
			case layout_below:
				String belowId = attrs.getAttributeValue(i);
				if (belowId.startsWith("@id")) {
					params.addRule(BELOW, belowId.substring("@id/".length()).hashCode());
				}
				break;
			case layout_above:
				String aboveId = attrs.getAttributeValue(i);
				if (aboveId.startsWith("@id")) {
					params.addRule(ABOVE, aboveId.substring("@id/".length()).hashCode());
				}
				break;
			case layout_toLeftOf:
				String leftId = attrs.getAttributeValue(i);
				if (leftId.startsWith("@id")) {
					params.addRule(LEFT_OF, leftId.substring("@id/".length()).hashCode());
				}
				break;
			case layout_toRightOf:
				String rightId = attrs.getAttributeValue(i);
				if (rightId.startsWith("@id")) {
					params.addRule(RIGHT_OF, rightId.substring("@id/".length()).hashCode());
				}
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
			case layout_alignParentRight:
				if (attrs.getAttributeBooleanValue(i, false)) {
					params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, this.TRUE);
				}
				break;
			case contentDescription:
				String content = attrs.getAttributeValue(i);
				this.setContentDescription(content);
				break;
			default:
				break;
			}

		}
		return params;
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		for (int i = 0; i < getChildCount(); i++) {
			View child = getChildAt(i);
			RelativeLayout.LayoutParams rlp = (LayoutParams) child.getLayoutParams();
			if (child instanceof VALinearLayout) {
				VALinearLayout groupLayout = (VALinearLayout) child;
				for (int j = 0; j < groupLayout.getChildCount(); j++) {
					View c = groupLayout.getChildAt(j);
					LinearLayout.LayoutParams llp = (android.widget.LinearLayout.LayoutParams) c.getLayoutParams();
				}
			}
		}

		super.onLayout(changed, l, t, r, b);
	}
}
