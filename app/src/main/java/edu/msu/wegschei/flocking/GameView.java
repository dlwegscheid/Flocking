package edu.msu.wegschei.flocking;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Custom View class for the game board
 */
public class GameView extends View {

    /**
     * Paint for filling the area the game is in
     */
    private Paint fillPaint;

    /**
     * Paint for outlining the area the game is in
     */
    private Paint outlinePaint;

    /**
     * TEMP bird for drawing
     */
    private Bird bird;

    /**
     * Width of outline
     */
    final static int BRUSH_WIDTH = 6;

    /**
     * Percentage of the display width or height that is occupied by the game
     */
    final static float SCALE_IN_VIEW = 0.9f;

    public GameView(Context context) {
        super(context);
        init(null, 0);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public GameView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fillPaint.setColor(0xffcccccc);

        outlinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        outlinePaint.setColor(0xff008000);
        outlinePaint.setStrokeWidth(BRUSH_WIDTH);

        bird = new Bird(getContext(), R.drawable.ostrich);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int wid = canvas.getWidth();
        int hit = canvas.getHeight();

        // Determine the minimum of the two dimensions
        int minDim = wid < hit ? wid : hit;

        int gameSize = (int)(minDim * SCALE_IN_VIEW);

        // Compute the margins so we center the game
        int marginX = (wid - gameSize) / 2;
        int marginY = (hit - gameSize) / 2;

        canvas.drawRect(marginX, marginY, marginX + gameSize, marginY + gameSize, fillPaint);
        canvas.drawRect(marginX - BRUSH_WIDTH, marginY - BRUSH_WIDTH,
                marginX + gameSize + BRUSH_WIDTH, marginY + gameSize + BRUSH_WIDTH,
                outlinePaint);

        bird.draw(canvas, wid / 2, hit / 2);
    }
}
