package cz.skywall.circularnumberpicker.test;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import cz.skywall.circularnumberpicker.CircularNumberPickerView;
import cz.skywall.circularnumberpicker.OnNumberSelectedListener;

public class TestActivity extends Activity implements OnNumberSelectedListener {
	CircularNumberPickerView mCircle_1;
	CircularNumberPickerView mCircle_2;
	CircularNumberPickerView mCircle_3;
	CircularNumberPickerView mCircle_4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);

		mCircle_1 = (CircularNumberPickerView) findViewById(R.id.circle_1);
		mCircle_2 = (CircularNumberPickerView) findViewById(R.id.circle_2);
		mCircle_3 = (CircularNumberPickerView) findViewById(R.id.circle_3);
		mCircle_4 = (CircularNumberPickerView) findViewById(R.id.circle_4);

		mCircle_1.setFontSize(40);
		mCircle_1.setSnap(true);
		mCircle_1.angleCentering(true);
		mCircle_1.setCircleStrokeColor(Color.LTGRAY);
		mCircle_1.setSelectedItem(10);
		mCircle_1.setListener(this);

		mCircle_2.setFontSize(60);
		mCircle_2.setSnap(true);
		mCircle_2.angleCentering(false);
		mCircle_2.setSelectedItem(10);
		mCircle_2.setListener(this);

		mCircle_3.setFontSize(60);
		mCircle_3.setSnap(false);
		mCircle_3.angleCentering(true);
		mCircle_3.setPointerFillColor(Color.BLUE);
		mCircle_3.setPointerStrokeColor(Color.BLUE);
		mCircle_3.setFontColor(Color.BLUE);
		mCircle_3.setSelectedItem(10);
		mCircle_3.setListener(this);

		mCircle_4.setFontSize(60);
		mCircle_4.setSnap(false);
		mCircle_4.angleCentering(false);
		mCircle_4.setPointerStrokeColor(Color.RED);
		mCircle_4.setFontColor(Color.RED);
		mCircle_4.setSelectedItem(10);
		mCircle_4.setListener(this);
	}

	@Override
	public void onNumberSelected(View view, int value) {
		Log.d("TestActivity", String.valueOf(value));
	}
}
