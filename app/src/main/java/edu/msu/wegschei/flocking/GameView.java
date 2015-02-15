package edu.msu.wegschei.flocking;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Custom View class for the game board
 */
public class GameView extends View {

    /**
     * The game
     */
    private Game game;

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
        game = new Game(getContext(), this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        game.draw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return game.onTouchEvent(this, event);
    }

    public void onPlace() {
        game.onPlace(getContext());
    }
}
