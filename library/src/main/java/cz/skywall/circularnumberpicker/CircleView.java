package cz.skywall.circularnumberpicker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by lukas on 11.11.14.
 * CircularNumberPicker
 */
public class CircleView extends View {

	private final Paint mPaint = new Paint();
	private boolean mDrawValuesReady;
	private int mXCenter;
	private int mYCenter;
	private int mCircleRadius;
	private float mCircleRadiusMultiplier = 0.82f;
	private int mCircleColor = Color.BLACK;

	public CircleView(Context context) {
		super(context);
	}

	public void setCircleColor(int circleColor) {
		this.mCircleColor = circleColor;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		int width = getWidth();
		if (width == 0) {
			return;
		}

		if (!mDrawValuesReady) {
			mXCenter = width / 2;
			mYCenter = getHeight() / 2;
			mCircleRadius = (int) (Math.min(mXCenter, mYCenter) * mCircleRadiusMultiplier);

			mPaint.setAntiAlias(true);
			mPaint.setColor(mCircleColor);

			mDrawValuesReady = true;
		}

		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeWidth(2.0f);

		canvas.drawCircle(mXCenter, mYCenter, mCircleRadius, mPaint);
	}
}
