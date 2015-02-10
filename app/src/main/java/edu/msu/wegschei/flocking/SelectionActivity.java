package edu.msu.wegschei.flocking;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
//import android.widget.Toast;


public class SelectionActivity extends ActionBarActivity {

    private Bitmap birdChoice;

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

    private void onImageClick(Bitmap selectedBird){
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("BitmapSelectedBird", selectedBird);
        startActivity(intent);
    }

    public void imageClicked(View view){
        //Convert the view to an ImageView
        ImageView imageView = (ImageView)view;
        //Get the contentDescription from the image
        String imageContentDescription = imageView.getContentDescription().toString();
        //Using that content description compare it to the strings.xml values
        if(imageContentDescription.equals(getString(R.string.robin))){
            //Toast.makeText(this, "Robin1", Toast.LENGTH_SHORT).show();
            birdChoice = BitmapFactory.decodeResource(getResources(), R.drawable.robin);
        } else if(imageContentDescription.equals(getString(R.string.parrot))){
            //Toast.makeText(this, "Parrot2", Toast.LENGTH_SHORT).show();
            birdChoice = BitmapFactory.decodeResource(getResources(), R.drawable.parrot);
        } else if(imageContentDescription.equals(getString(R.string.swallow))){
            //Toast.makeText(this, "Swallow3", Toast.LENGTH_SHORT).show();
            birdChoice = BitmapFactory.decodeResource(getResources(), R.drawable.swallow);
        } else if(imageContentDescription.equals(getString(R.string.bananaquit))){
            //Toast.makeText(this, "Bananaquit4", Toast.LENGTH_SHORT).show();
            birdChoice = BitmapFactory.decodeResource(getResources(), R.drawable.bananaquit);
        } else if(imageContentDescription.equals(getString(R.string.ostrich))){
            //Toast.makeText(this, "Ostrich5", Toast.LENGTH_SHORT).show();
            birdChoice = BitmapFactory.decodeResource(getResources(), R.drawable.ostrich);
        }
        //send the bird it's hopeful home through the intent
        onImageClick(birdChoice);
    }
}

/**
 * Need this in the GameActivity
 * Intent intent = getIntent();
 * Bitmap bitmap = (Bitmap)intent.getParcelableExtra("BitmapSelectedBird");
 */
