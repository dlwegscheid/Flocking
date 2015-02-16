package edu.msu.wegschei.flocking;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Custom View class for the game board
 */
public class GameView extends View {

    /**
     * The game
     */
    private Game game;
    /**
     * The bird ID from Selection view
     */
    private int birdID;

    public GameView(Context context) {
        super(context);
        init(null, 0);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Intent intent = ((Activity)getContext()).getIntent();
        Bundle bundle = intent.getExtras();
        birdID = bundle.getInt("BirdImageID");
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
        CharSequence text;

        if(game.canPlace()) {
            text = "GOOD";
        } else {
            text = "BAD";
        }
        Toast toast = Toast.makeText(getContext(), text, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void loadInstanceState(Bundle bundle) {
        game.loadInstanceState(bundle, getContext());
    }

    public void saveInstanceState(Bundle bundle) {
        game.saveInstanceState(bundle);
    }

}
