package cz.skywall.circularnumberpicker;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by lukas on 11.11.14.
 * CircularNumberPicker
 */
public class CircularNumberPickerView extends FrameLayout implements View.OnTouchListener {

	int mSelectedItem;
	boolean mSnap = true;
	boolean mCentering = true;
	private ArrayList<Integer> mValues;
	private OnNumberSelectedListener mListener;
	private int mMin = 1;
	private int mMax = 10;
	private int mStep = 1;
	private int mDegreeStepSize;
	private CircleView mCircleView;
	private TextView mTextView;
	private PointerView mPointerView;

	public CircularNumberPickerView(Context context) {
		super(context);
		init();
	}

	public CircularNumberPickerView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public CircularNumberPickerView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int measuredWidth = MeasureSpec.getSize(widthMeasureSpec);
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int measuredHeight = MeasureSpec.getSize(heightMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int minDimension = Math.min(measuredWidth, measuredHeight);

		super.onMeasure(MeasureSpec.makeMeasureSpec(minDimension, widthMode),
				MeasureSpec.makeMeasureSpec(minDimension, heightMode));
	}

	public void setListener(OnNumberSelectedListener listener) {
		mListener = listener;
	}

	public void setLimits(int minValue, int maxValue) {
		mMin = minValue;
		mMax = maxValue;
		init();
	}

	public void setLimits(int minValue, int maxValue, int step) {
		mMin = minValue;
		mMax = maxValue;
		mStep = step;
		init();
	}

	public void setSnap(boolean enabled) {
		mSnap = enabled;
	}

	public void angleCentering(boolean enabled) {
		mCentering = enabled;
	}

	public void setFontColor(int color) {
		mTextView.setTextColor(color);
	}

	public void setFontSize(float size) {
		mTextView.setTextSize(size);
	}

	public void setTypeface(Typeface tf) {
		mTextView.setTypeface(tf);
	}

	public void setPointerStrokeColor(int color) {
		mPointerView.setStrokeColor(color);
	}

	/**
	 * Set pointer circle fill color
	 * Cannot be transparent!
	 *
	 * @param color fill color
	 */
	public void setPointerFillColor(int color) {
		mPointerView.setFillColor(color);
	}

	public void setCircleStrokeColor(int color) {
		mCircleView.setCircleColor(color);
	}


	private void init() {
		createValuesArray();

		mDegreeStepSize = 360 / mValues.size();

		mCircleView = new CircleView(getContext());
		addView(mCircleView);

		mTextView = new TextView(getContext());
		mTextView.setGravity(Gravity.CENTER);
		addView(mTextView);

		mPointerView = new PointerView(getContext());
		addView(mPointerView);

		setSelectedItem(mMin);

		setOnTouchListener(this);
	}

	private void createValuesArray() {
		if (mMin > mMax) throw new RuntimeException("minValue is bigger than maxValue");

		mValues = new ArrayList<Integer>();
		mValues.add(mMin);

		int val = mMin;
		while (val != mMax) {
			val += mStep;
			mValues.add(val);
		}
	}

	public float getAngle(float x, float y) {
		float centerX = getWidth() / 2;
		float centerY = getHeight() / 2;
		float angle = (float) Math.toDegrees(Math.atan2(y - centerY, x - centerX) + Math.PI / 2.0);

		if (angle < 0) {
			angle += 360;
		}

		return angle;
	}

	private int getSelectedItem(float angle) {
		if (mCentering) {
			angle = ((mDegreeStepSize / 2) + angle) % 360;
		}

		int selected = (int) (angle / mDegreeStepSize);
		return mValues.get(selected);
	}

	private void itemPicked(int item) {
		if (mSelectedItem != item && mListener != null) {
			mListener.onNumberSelected(this, item);
		}

		setSelectedItem(item);
	}

	public void setSelectedItem(int selectedItem) {
		int itemPos = mValues.indexOf(selectedItem);
		if (itemPos == -1)
			throw new RuntimeException("Cannot select item: " + String.valueOf(selectedItem));

		mSelectedItem = selectedItem;

		int angle = itemPos * mDegreeStepSize;
		angle = ((mDegreeStepSize / 2) + angle) % 360; // move pointer to the angle center

		if (mCentering) {
			angle = (angle - (mDegreeStepSize / 2) + 360) % 360;
		}

		mPointerView.setAngle(angle);
		mTextView.setText(String.valueOf(selectedItem));
	}

	public int getSelectedItem() {
		return mSelectedItem;
	}

	private int getSelectedPos(float angle) {
		if (mCentering) {
			angle = ((mDegreeStepSize / 2) + angle) % 360;
		}
		return (int) (angle / mDegreeStepSize);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		float angle;
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				return true;
			case MotionEvent.ACTION_MOVE:
				angle = getAngle(event.getX(), event.getY());

				mTextView.setText(String.valueOf(getSelectedItem(angle)));
				mPointerView.setAngle(angle);
				return true;
			case MotionEvent.ACTION_UP:
				angle = getAngle(event.getX(), event.getY());
				itemPicked(getSelectedItem(angle));

				return true;
		}
		return false;
	}
}
