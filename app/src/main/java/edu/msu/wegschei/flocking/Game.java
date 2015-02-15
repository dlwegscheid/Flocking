package edu.msu.wegschei.flocking;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * class to describe a Game
 */
public class Game {

    /**
     * Paint for filling the area the game is in
     */
    private Paint fillPaint;

    /**
     * Paint for outlining the area the game is in
     */
    private Paint outlinePaint;

    /**
     * Collection of birds
     */
    public ArrayList<Bird> birds = new ArrayList<>();

    /**
     * The size of the game in pixels
     */
    private int gameSize;

    /**
     * How much we scale the puzzle pieces
     */
    private float scaleFactor;

    /**
     * Left margin in pixels
     */
    private int marginX;

    /**
     * Top margin in pixels
     */
    private int marginY;

    /**
     * Most recent relative X touch when dragging
     */
    private float lastX;

    /**
     * Most recent relative Y touch when dragging
     */
    private float lastY;

    /**
     * This variable is set to a bird we are dragging. If
     * we are not dragging, the variable is null.
     */
    private Bird dragging = null;

    /**
     * The view of the game
     */
    private View parentView = null;

    /**
     * An ostrich bitmap for scaling
     */
    private Bitmap ostrich;

    /**
     * A rectangle of the boundary of the play area
     */
    private Rect boundary;

    /**
     * Width of outline
     */
    final static int BRUSH_WIDTH = 6;

    /**
     * Percentage of the display width or height that is occupied by the game
     */
    final static float SCALE_IN_VIEW = 0.9f;

    /**
     * Ratio of board height to ostrich height
     */
    final static float OSTRICH_RATIO = 1.5f;

    public Game(Context context, View parent) {

        parentView = parent;

        // Create paint for filling the area the puzzle will
        // be solved in.
        fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fillPaint.setColor(0xffcccccc);

        outlinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        outlinePaint.setColor(0xff008000);
        outlinePaint.setStrokeWidth(BRUSH_WIDTH);

        boundary = new Rect();

        // Load the solved puzzle image
        ostrich = BitmapFactory.decodeResource(context.getResources(), R.drawable.ostrich);

        dragging = new Bird(context, R.drawable.ostrich);
        birds.add(dragging);
    }

    public void draw(Canvas canvas) {

        int wid = canvas.getWidth();
        int hit = canvas.getHeight();

        // Determine the minimum of the two dimensions
        int minDim = wid < hit ? wid : hit;

        gameSize = (int)(minDim * SCALE_IN_VIEW);

        // Compute the margins so we center the puzzle
        marginX = (wid - gameSize) / 2;
        marginY = (hit - gameSize) / 2;

        // Draw the outline of the puzzle
        int startX = marginX - BRUSH_WIDTH;
        int startY = marginY - BRUSH_WIDTH;
        int endX = marginX + gameSize + BRUSH_WIDTH;
        int endY = marginY + gameSize + BRUSH_WIDTH;
        canvas.drawRect(startX, startY, endX, endY, outlinePaint);

        boundary.set(marginX, marginY, marginX + gameSize, marginY + gameSize);
        canvas.drawRect(boundary, fillPaint);

        scaleFactor = (float)gameSize / (float)ostrich.getHeight() / OSTRICH_RATIO;

        for(Bird bird : birds) {
            bird.draw(canvas, scaleFactor);
        }
    }

    /**
     * Handle a touch event from the view.
     * @param view The view that is the source of the touch
     * @param event The motion event describing the touch
     * @return true if the touch is handled.
     */
    public boolean onTouchEvent(View view, MotionEvent event) {

        float X = event.getX();
        float Y = event.getY();

        switch(event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                return onTouched(X, Y);

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                return true;

            case MotionEvent.ACTION_MOVE:
                // If we are dragging, move the piece and force a redraw
                if(dragging != null) {
                    dragging.move(X - lastX, Y - lastY);
                    lastX = X;
                    lastY = Y;
                    view.invalidate();
                    return true;
                }
        }

        return false;
    }

    /**
     * Handle a touch message. This is when we get an initial touch
     * @param x x location for the touch, relative to the game - 0 to 1 over the game
     * @param y y location for the touch, relative to the game - 0 to 1 over the game
     * @return true if the touch is handled
     */
    private boolean onTouched(float x, float y) {
        lastX = x;
        lastY = y;

        return true;
    }

    public void onPlace(Context context) {
        CharSequence text;

        boundary.set(marginX, marginY, marginX + gameSize, marginY + gameSize);
        if(!boundary.contains(dragging.getRect())) {
            text = "outside boundary";
        } else {
            text = "inside boundary";
        }

        Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        toast.show();
    }
}
