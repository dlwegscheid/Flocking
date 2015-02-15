package edu.msu.wegschei.flocking;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Class to describe a Bird object
 */
public class Bird {
    /**
     * The image for the actual bird.
     */
    private Bitmap bird;

    /**
     * Rectangle that is where our bird is.
     */
    private Rect rect;

    /**
     * Rectangle we will use for intersection testing
     */
    private Rect overlap = new Rect();

    /**
     * x location in pixels
     */
    private float x = 0;

    /**
     * y location in pixels
     */
    private float y = 0;

    /**
     * scale factor
     */
    private float scale;

    public Bird(Context context, int id) {
        bird = BitmapFactory.decodeResource(context.getResources(), id);
        rect = new Rect();
        setRect();
    }

    public void move(float dx, float dy) {
        x += dx;
        y += dy;
        setRect();
    }

    private void setRect() {
        rect.set((int)x, (int)y, (int)(x+bird.getWidth()*scale), (int)(y+bird.getHeight()*scale));
    }

    public Rect getRect() {
        return rect;
    }

    public boolean hit(float testX, float testY) {
        int pX = (int)((testX - x));
        int pY = (int)((testY - y));

        if(pX < 0 || pX >= bird.getWidth() ||
                pY < 0 || pY >= bird.getHeight()) {
            return false;
        }

        // We are within the rectangle of the piece.
        // Are we touching actual picture?
        return (bird.getPixel(pX, pY) & 0xff000000) != 0;
    }

    /**
     * Collision detection between two birds. This object is
     * compared to the one referenced by other
     * @param other Bird to compare to.
     * @return True if there is any overlap between the two birds.
     */
    public boolean collisionTest(Bird other) {
        // Do the rectangles overlap?
        if(!Rect.intersects(rect, other.rect)) {
            return false;
        }

        // Determine where the two images overlap
        overlap.set(rect);
        overlap.intersect(other.rect);

        // We have overlap. Now see if we have any pixels in common
        for(int r=overlap.top; r<overlap.bottom;  r++) {
            int aY = (int)((r - y));
            int bY = (int)((r - other.y));

            for(int c=overlap.left;  c<overlap.right;  c++) {

                int aX = (int)((c - x));
                int bX = (int)((c - other.x));

                if( (bird.getPixel(aX, aY) & 0x80000000) != 0 &&
                        (other.bird.getPixel(bX, bY) & 0x80000000) != 0) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Draw the puzzle piece
     * @param canvas Canvas we are drawing on
     * @param scaleFactor Amount we scale the game pieces when we draw them
     */
    public void draw(Canvas canvas, float scaleFactor) {
        scale = scaleFactor;
        canvas.save();

        canvas.translate(x, y);
        canvas.scale(scaleFactor, scaleFactor);
        canvas.translate(-bird.getWidth() / 2, -bird.getHeight() / 2);

        // Draw the bitmap
        canvas.drawBitmap(bird, 0, 0, null);
        canvas.restore();
    }
}
