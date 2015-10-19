package com.eebbk.studyos.viewanalysis.view;

import java.util.HashMap;

import com.eebbk.studyos.viewanalysis.view.engine.ParamValue;
import com.eebbk.studyos.viewanalysis.view.engine.YDResource;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;

/**
 * @author Codefarmer@sina.com
 */
public class VAImageView extends android.widget.ImageView {

	public VAImageView(Context context, AttributeSet attrs) {
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
				String viewId = attrs.getAttributeValue(i);
				if (viewId.startsWith("@+id/")) {
					setId(viewId.substring("@+id/".length()).hashCode());
				}
				break;
			case src:
				YDResource.getInstance().displayImage(attrs.getAttributeValue(i), this);

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
			case contentDescription:
				String content = attrs.getAttributeValue(i);
				this.setContentDescription(content);
				break;
			default:
				break;
			}
		}
	}
}
