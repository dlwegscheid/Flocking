package edu.msu.wegschei.flocking;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


public class SelectionActivity extends ActionBarActivity {

    private RadioGroup radioBirdGroup; //group of bird radio buttons
    private RadioButton radioBirdButton; //bird that was selected

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        addRadioButtonListener();
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

    public void addRadioButtonListener(){
        radioBirdGroup = (RadioGroup)findViewById(R.id.BirdRadioGroup);
        radioBirdGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            //Figured once the button gets clicked we get that element and load it up and pass it over
            //to the Game to be played.
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.RadioSelectBanana:
                        radioBirdButton = (RadioButton)findViewById(checkedId);
                        Toast.makeText(SelectionActivity.this, radioBirdButton.getText(),Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.RadioSelectOstrich:
                        radioBirdButton = (RadioButton)findViewById(checkedId);
                        Toast.makeText(SelectionActivity.this, radioBirdButton.getText(),Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.RadioSelectParrot:
                        radioBirdButton = (RadioButton)findViewById(checkedId);
                        Toast.makeText(SelectionActivity.this, radioBirdButton.getText(),Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.RadioSelectRobin:
                        radioBirdButton = (RadioButton)findViewById(checkedId);
                        Toast.makeText(SelectionActivity.this, radioBirdButton.getText(),Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.RadioSelectSwallow:
                        radioBirdButton = (RadioButton)findViewById(checkedId);
                        Toast.makeText(SelectionActivity.this, radioBirdButton.getText(),Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }
}
