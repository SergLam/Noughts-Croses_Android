package com.samurai.sergey.noughts_and_croses;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        set_sizes();
    }

    private void set_sizes() {
        int height = getResources().getDisplayMetrics().heightPixels;
        Button button = (Button)findViewById(R.id.button);
        button.setMinimumHeight(height/4);

        Button button2 = (Button)findViewById(R.id.button2);
        button2.setMinimumHeight(height/4);

        Button button3 = (Button)findViewById(R.id.button3);
        button3.setMinimumHeight(height/4);
    }

    public void onQuiteClick(View view)
    {
        finish();
        System.exit(0);
    }

    public void onStatsClick(View view)
    {
        if(view.getId() == R.id.button2){
            Intent myIntent = new Intent(this, StatsActivity.class);
            startActivity(myIntent);
        }
    }

    public void newGame(View view)
    {
        if(view.getId() == R.id.button){
            Intent myIntent = new Intent(this, GameActivity.class);
            startActivity(myIntent);
        }
    }

}
