package edu.msu.wegschei.flocking;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class GameActivity extends ActionBarActivity {

    /**
     * The game view in this activity's view
     */
    private GameView gameView;

    public final static String PLAYER_ONE = "GameActivity.playerOne";
    public final static String PLAYER_TWO = "GameActivity.playerTwo";

    private final static int BIRD_SELECTION = 1;

    private String playerNameOne;
    private String playerNameTwo;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_game);

        gameView = (GameView)this.findViewById(R.id.gameView);

        //Gets the players' names that came from MainActivity, stores them in variables here
        Bundle extras = getIntent().getExtras();
        playerNameOne = extras.getString(PLAYER_ONE);
        playerNameTwo = extras.getString(PLAYER_TWO);
        gameView.setNames(playerNameOne, playerNameTwo);

        if(bundle != null) {
            gameView.loadInstanceState(bundle);
        } else {
            gameView.advanceGame(-1);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onPlace(View view) {
        gameView.onPlace();
        gameView.invalidate();
    }

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);

        gameView.saveInstanceState(bundle);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == BIRD_SELECTION && resultCode == Activity.RESULT_OK){
            Bundle extras = data.getExtras();
            int birdID = extras.getInt("BirdImageID");
            gameView.advanceGame(birdID);
        }
    }

}