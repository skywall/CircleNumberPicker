package cz.skywall.circularnumberpicker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by lukas on 12.11.14.
 * CircularNumberPicker
 */
public class PointerView extends View {

	private final Paint mPaint = new Paint();
	float mDegrees;
	double mRadians;
	private boolean mDrawValuesReady = false;
	private int mXCenter;
	private int mYCenter;
	private float mCircleRadiusMultiplier = 0.82f;
	private float mPointerRadiusMultiplier = 0.16f;
	private int mFillColor = Color.WHITE;
	private int mStrokeColor = Color.BLACK;
	private float mPointRadius, mCircleRadius;

	public PointerView(Context context) {
		super(context);
	}

	private void init() {

	}

	public void setFillColor(int color) {
		mFillColor = color;
	}

	public void setStrokeColor(int color) {
		mStrokeColor = color;
	}

	public void setAngle(float angle) {
		mDegrees = angle;
		mRadians = angle * Math.PI / 180;
		invalidate();
	}

	@Override
	public boolean hasOverlappingRendering() {
		return false;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		int viewWidth = getWidth();
		if (viewWidth == 0) {
			return;
		}

		if (!mDrawValuesReady) {
			mXCenter = getWidth() / 2;
			mYCenter = getHeight() / 2;
			mCircleRadius = (int) (Math.min(mXCenter, mYCenter) * mCircleRadiusMultiplier);

			mPointRadius = (int) (mCircleRadius * mPointerRadiusMultiplier);

			mPaint.setAntiAlias(true);

			mDrawValuesReady = true;
		}

		int pointX = mXCenter + (int) (mCircleRadius * Math.sin(mRadians));
		int pointY = mYCenter - (int) (mCircleRadius * Math.cos(mRadians));

		mPaint.setColor(mStrokeColor);
		canvas.drawCircle(pointX, pointY, mPointRadius, mPaint);

		mPaint.setColor(mFillColor);
		canvas.drawCircle(pointX, pointY, mPointRadius - 3.0f, mPaint);
	}

}
