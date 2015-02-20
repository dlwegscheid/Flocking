package edu.msu.wegschei.flocking;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class ScoreActivity extends ActionBarActivity {

//    private int birdsPlaced;
//    private String winner;
//
//    private TextView textWinner;
//    private TextView textBirdsPlaced;

    private final static String SCORE = "ScoreActivity.score";
    private final static String WINNER = "ScoreActivity.winner";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int birdsPlaced;
        String winner;

        TextView textWinner;
        TextView textBirdsPlaced;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        Bundle extras = getIntent().getExtras();
        winner = extras.getString(WINNER);
        birdsPlaced = extras.getInt(SCORE);


        textWinner = (TextView)findViewById(R.id.textWinner);
        textWinner.setText(winner);

        textBirdsPlaced = (TextView)findViewById(R.id.textBirdsPlaced);
        textBirdsPlaced.setText("Birds placed: " + birdsPlaced);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_score, menu);
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

    public void onReset(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
