package edu.msu.wegschei.flocking;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class SelectionActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_selection, menu);
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

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        Intent intent = getIntent();
        //String STATE = intent.getStringExtra(GAME_STATE);
        int BIRD_COUNT = intent.getExtras().getInt("BIRD_COUNT");
        //savedInstanceState.putString(GAME_STATE, STATE);
        savedInstanceState.putInt("BIRD_COUNT", BIRD_COUNT);
        super.onSaveInstanceState(savedInstanceState);
    }



    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //BUNDLE_STATE = savedInstanceState.getString(GAME_STATE);
        //BIRD_COUNT = savedInstanceState.getInt("BIRD_COUNT");
    }



    public void imageClicked(View view){
        //Convert the view to an ImageView
        //TextView textView = (TextView)findViewById(R.id.Message);
        //textView.setText(getIntent().getExtras().getString("PLAYER"));

        ImageView imageView = (ImageView)view;
        //Get the contentDescription from the image
        String imageContentDescription = imageView.getContentDescription().toString();
        //Using that content description compare it to the strings.xml values
        //Create the intent
        Intent result = new Intent(this, GameActivity.class);
        if(imageContentDescription.equals(getString(R.string.robin))){
            result.putExtra("BirdImageID", R.drawable.robin);
        } else if(imageContentDescription.equals(getString(R.string.parrot))){
            result.putExtra("BirdImageID", R.drawable.parrot);
        } else if(imageContentDescription.equals(getString(R.string.swallow))){
            result.putExtra("BirdImageID", R.drawable.swallow);
        } else if(imageContentDescription.equals(getString(R.string.bananaquit))){
            result.putExtra("BirdImageID", R.drawable.bananaquit);
        } else if(imageContentDescription.equals(getString(R.string.ostrich))){
            result.putExtra("BirdImageID", R.drawable.ostrich);
        }
        //Fire it
        setResult(Activity.RESULT_OK, result);
        Toast.makeText(this, "Choice Received", Toast.LENGTH_SHORT).show();
        finish();
    }
}