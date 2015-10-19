package com.eebbk.studyos.viewanalysis.activity;

import com.eebbk.studyos.viewanalysis.view.utils.InflaterUtil;
import com.example.testviewtree.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class TestActivity extends Activity {

	private static final String filePath = "mnt/sdcard/aalayout_item.xml";
	private InflaterUtil mInflate;
	private ViewGroup mRootView;
	private String[] images = { "mnt/sdcard/12.png", "mnt/sdcard/13.png", "mnt/sdcard/14.png" };
	private int[] image2s = { R.drawable.image1, R.drawable.image1, R.drawable.image3, R.drawable.image2,
			R.drawable.image2, R.drawable.image3, R.drawable.image2, R.drawable.image2, R.drawable.image2,
			R.drawable.image3, R.drawable.image2, R.drawable.image2 };
	private String[] texts = { "火影", "海贼", "柯南", "死神", "七龙珠", "柯南", "死神", "七龙珠", "七龙珠", "柯南", "死神", "七龙珠" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mRootView = (ViewGroup) getLayoutInflater().inflate(R.layout.layout_main, null);
		setContentView(mRootView);
		mInflate = InflaterUtil.getInstance(this);
		addView();
	}

	private void addView() {
		View view = mInflate.getViewByInflate(filePath);
		if (view != null) {
			getView(view);
			mRootView.addView(view);
		}

	}

	private void getView(View view) {
		if (null == view) {
			return;
		}
		String imageFlag = "iv_";
		String textFlag = "tv_";

		View viewtemp;
		for (int i = 1; i < texts.length + 1; i++) {
			viewtemp = view.findViewById((textFlag + i).hashCode());

			if (viewtemp instanceof TextView) {
				((TextView) viewtemp).setText(texts[i - 1]);
			}
		}
		for (int i = 1; i < image2s.length + 1; i++) {
			viewtemp = view.findViewById((imageFlag + i).hashCode());
			if (viewtemp instanceof ImageView) {
				((ImageView) viewtemp).setImageResource(image2s[i - 1]);
				((ImageView) viewtemp).setTag(i);
				((ImageView) viewtemp).setOnClickListener(imageViewListener);
			}
		}

	}

	private OnClickListener imageViewListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Toast.makeText(getApplicationContext(), v.getTag() + "", 0).show();
		}
	};

	@Override
	protected void onDestroy() {
		if (mInflate != null) {
			mInflate.removeResource();
		}
		super.onDestroy();
	}

}
