package edu.msu.wegschei.flocking;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;


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

    public void imageClicked(View view){
        //Convert the view to an ImageView
        ImageView imageView = (ImageView)view;
        //Get the contentDescription from the image
        String imageContentDescription = imageView.getContentDescription().toString();
        //Using that content description compare it to the strings.xml values

        //Create the intent
        Intent intent = new Intent(this, GameActivity.class);

        if(imageContentDescription.equals(getString(R.string.robin))){
            intent.putExtra("BirdImageID", R.drawable.robin);
        } else if(imageContentDescription.equals(getString(R.string.parrot))){
            intent.putExtra("BirdImageID", R.drawable.parrot);
        } else if(imageContentDescription.equals(getString(R.string.swallow))){
            intent.putExtra("BirdImageID", R.drawable.swallow);
        } else if(imageContentDescription.equals(getString(R.string.bananaquit))){
            intent.putExtra("BirdImageID", R.drawable.bananaquit);
        } else if(imageContentDescription.equals(getString(R.string.ostrich))){
            intent.putExtra("BirdImageID", R.drawable.ostrich);
        }
        //Fire it
        startActivity(intent);
    }
}