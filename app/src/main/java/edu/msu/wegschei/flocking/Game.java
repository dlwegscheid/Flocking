package edu.msu.wegschei.flocking;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

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
    private float lastRelX;

    /**
     * Most recent relative Y touch when dragging
     */
    private float lastRelY;

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

    /**
     * The name of the bundle keys to save the puzzle
     */
    private final static String LOCATIONS = "Game.locations";
    private final static String IDS = "Game.ids";
    private final static String DRAGGING_INDEX = "Game.draggingIndex";


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

        dragging = new Bird(context, R.drawable.bananaquit);
        Bird bird = new Bird(context, R.drawable.bananaquit);
        bird.move(0.5f, 0.5f);
        birds.add(bird);
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
            bird.draw(canvas, marginX, marginY, gameSize, scaleFactor);
        }
    }

    /**
     * Handle a touch event from the view.
     * @param view The view that is the source of the touch
     * @param event The motion event describing the touch
     * @return true if the touch is handled.
     */
    public boolean onTouchEvent(View view, MotionEvent event) {
        //
        // Convert an x,y location to a relative location in the game.
        //

        float relX = (event.getX() - marginX) / gameSize;
        float relY = (event.getY() - marginY) / gameSize;

        switch(event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                lastRelX = relX;
                lastRelY = relY;
                return true;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                return true;

            case MotionEvent.ACTION_MOVE:
                // If we are dragging, move the piece and force a redraw
                if(dragging != null) {
                    dragging.move(relX - lastRelX, relY - lastRelY);
                    lastRelX = relX;
                    lastRelY = relY;
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
        lastRelX = x;
        lastRelY = y;

        return true;
    }

    public boolean canPlace() {
        boundary.set(marginX, marginY, marginX + gameSize, marginY + gameSize);
        if(boundary.contains(dragging.getRect())) {
            for(Bird bird : birds) {
                if(bird != dragging && dragging.collisionTest(bird)) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public void loadInstanceState(Bundle bundle, Context context) {
        float [] locations = bundle.getFloatArray(LOCATIONS);
        int [] ids = bundle.getIntArray(IDS);
        int draggingIndex = bundle.getInt(DRAGGING_INDEX);

        birds.clear();
        dragging = null;

        for(int i=0; i<ids.length; i++) {
            Bird bird = new Bird(context, ids[i]);
            bird.setX(locations[2*i]);
            bird.setY(locations[2*i+1]);
            birds.add(bird);

            if(i == draggingIndex) {
                dragging = bird;
            }
        }

        birds.remove(dragging);
        birds.add(dragging);
    }

    public void saveInstanceState(Bundle bundle) {
        float [] locations = new float[birds.size() * 2];
        int [] ids = new int[birds.size()];
        int draggingIndex = -1;

        for(int i=0;  i<birds.size(); i++) {
            Bird bird = birds.get(i);
            if(bird == dragging) {
                draggingIndex = i;
            }
            locations[i*2] = bird.getX();
            locations[i*2+1] = bird.getY();
            ids[i] = bird.getId();
        }

        bundle.putFloatArray(LOCATIONS, locations);
        bundle.putIntArray(IDS,  ids);
        bundle.putInt(DRAGGING_INDEX, draggingIndex);
    }
}
