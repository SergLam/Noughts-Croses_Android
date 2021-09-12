package com.samurai.sergey.noughts_and_croses;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class StatsActivity extends Activity {

    public int noughts_wins =0;
    public int cross_wins=0;
    public int draws = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        set_stats();
    }



    private void set_stats() {
            SharedPreferences preferences = getSharedPreferences("scores", Context.MODE_PRIVATE);
            String X = preferences.getString("X","0");
            String O = preferences.getString("O","0");
            String D = preferences.getString("D","0");

            EditText noughts_text = (EditText)findViewById(R.id.noughts_text);
            noughts_text.setText(O);
            noughts_text.setEnabled(false);

            EditText croses_text = (EditText)findViewById(R.id.croses_text);
            croses_text.setText(X);
            croses_text.setEnabled(false);

            EditText draws_text = (EditText)findViewById(R.id.draws_text);
            draws_text.setText(D);
            draws_text.setEnabled(false);


            int height = getResources().getDisplayMetrics().heightPixels;
            Button reset = (Button)findViewById(R.id.reset_button);
            reset.setMinimumHeight(height/4);
            Button back = (Button)findViewById(R.id.button_back);
            back.setMinimumHeight(height/4);

    }

    public void ResetStat(View view)
    {
        if(view.getId() == R.id.reset_button){
            EditText noughts_text = (EditText)findViewById(R.id.noughts_text);
            noughts_text.setText("0");
            EditText croses_text = (EditText)findViewById(R.id.croses_text);
            croses_text.setText("0");
            EditText draws_text = (EditText)findViewById(R.id.draws_text);
            draws_text.setText("0");

            SharedPreferences preferences = getSharedPreferences("scores", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.apply();
        }
    }

    public void BackToMenu(View view)
    {
        if(view.getId() == R.id.button_back){
          onBackPressed();
        }
    }
}
