package com.samurai.sergey.noughts_and_croses;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class GameActivity extends Activity {
    public int null_wins=0;
    public int cross_wins=0;
    public int draws = 0;

    // Saving result for future launch
    Map <String, String> scores = new HashMap<String, String>();
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public int[] board = new int[9];
    public boolean won  = false;
    public int currentPlayer = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        SharedPreferences  preferences1 = getSharedPreferences("scores", Context.MODE_PRIVATE);
        String X = preferences1.getString("X","0");
        String O = preferences1.getString("O","0");
        String D = preferences1.getString("D","0");

        if(X!=null&&O!=null&&D!=null){
            int d = Integer.parseInt(D);
            int o = Integer.parseInt(O);
            int x = Integer.parseInt(X);
            null_wins=o;
            cross_wins=x;
            draws = d;
        }
        set_sizes();
    }

    public void save_score(){
        scores.clear();
        scores.put("X",Integer.toString(cross_wins));
        scores.put("O",Integer.toString(null_wins));
        scores.put("D",Integer.toString(draws));
        preferences = getSharedPreferences("scores", Context.MODE_PRIVATE);
        editor = preferences.edit();
        for (String s : scores.keySet()) {
            editor.putString(s, scores.get(s));
        }
        // Apply the edits!
        editor.apply();
    }

    public void pressButton(View view){
        Object tag = view.getTag();
        String s = tag.toString();
        int num = Integer.parseInt(s);
        Button game_button = (Button)findViewById(view.getId());
            if(attepmtChange(board[num])){
                board[num]=currentPlayer;
                if(currentPlayer==1){
                    game_button.setBackgroundResource(R.drawable.x_image);
                }
                if(currentPlayer==2){
                    game_button.setBackgroundResource(R.drawable.o_image);
                }
                currentPlayer =(currentPlayer ==1) ? 2 : 1;
                update_status();
                checkWin();
            }
    }

    public void checkWin(){
        // Check horizontal lines
        if((board[0]==board[1]) && (board[1]==board[2]) && board[2]!=0){
            win(0);
        }
        if((board[3]==board[4]) && (board[4]==board[5]) && board[5]!=0){
            win(5);
        }
        if((board[6]==board[7]) && (board[7]==board[8]) && board[8]!=0){
            win(8);
        }
        // Check vertical lines
        if((board[0]==board[3]) && (board[3]==board[6]) && board[6]!=0){
            win(6);
        }
        if((board[1]==board[4]) && (board[4]==board[7]) && board[7]!=0){
            win(7);
        }
        if((board[2]==board[5]) && (board[5]==board[8]) && board[8]!=0){
            win(8);
        }
        // Check cross lines
        if((board[0]==board[4]) && (board[4]==board[8]) && board[8]!=0){
            win(8);
        }
        if((board[6]==board[4]) && (board[4]==board[2]) && board[2]!=0){
            win(2);
        }
        // Check draw condition
        for(int i =0;i<board.length;i++){
            if(board[i]==0){
                return;
            }
        }
        if (!won){
            draw();
        }
    }

    public void draw(){
        draws++;
        AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
        builder.setTitle(R.string.Draw_title)
                .setMessage(R.string.Draw_new_game)
                .setIcon(R.mipmap.ic_launcher)
                .setNegativeButton(R.string.No,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                save_score();
                                onBackPressed();
                            }
                        });
        builder.setPositiveButton(R.string.Yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        save_score();
                        new_game();
                    }
                });
        AlertDialog alert = builder.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();
    }

    public void win(int square){
        won = true;
        String winner = board[square]==1 ? " X " : " O ";
        if(board[square]==1){
            cross_wins++;
        } else null_wins++;
        //String message = R.string.Player
        AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
        builder.setTitle(R.string.Victory_title)
                .setMessage(getString(R.string.Player)+winner+getString(R.string.Victory_message))
                .setIcon(R.mipmap.ic_launcher)
                .setNegativeButton(R.string.No,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                save_score();
                                onBackPressed();
                            }
                        });
        builder.setPositiveButton(R.string.Yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        save_score();
                        new_game();
                    }
                });
        AlertDialog alert = builder.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();
    }

    public void update_status(){
        TextView status = (TextView)findViewById(R.id.status_bar);
        if(currentPlayer==1){
            status.setText(R.string.status_bar);
        }
        if(currentPlayer==2){
            status.setText(R.string.status_bar_O);
        }
    }

    public boolean attepmtChange(int square){
        if(square!=0){
            return false;
        }else return true;
    }

    public void  new_game(){
        won = false;
        currentPlayer=1;
        for(int i =0;i<9;i++){
            board[i] = 0;
        }
        // Status to default
        TextView status = (TextView)findViewById(R.id.status_bar);
        status.setText(R.string.status_bar);
        // Buttons to default
        Button game_button1 = (Button)findViewById(R.id.game_button1);
        game_button1.setBackgroundResource(android.R.drawable.btn_default);

        Button game_button2 = (Button)findViewById(R.id.game_button2);
        game_button2.setBackgroundResource(android.R.drawable.btn_default);

        Button game_button3 = (Button)findViewById(R.id.game_button3);
        game_button3.setBackgroundResource(android.R.drawable.btn_default);

        Button game_button4 = (Button)findViewById(R.id.game_button4);
        game_button4.setBackgroundResource(android.R.drawable.btn_default);

        Button game_button5 = (Button)findViewById(R.id.game_button5);
        game_button5.setBackgroundResource(android.R.drawable.btn_default);

        Button game_button6 = (Button)findViewById(R.id.game_button6);
        game_button6.setBackgroundResource(android.R.drawable.btn_default);

        Button game_button7 = (Button)findViewById(R.id.game_button7);
        game_button7.setBackgroundResource(android.R.drawable.btn_default);

        Button game_button8 = (Button)findViewById(R.id.game_button8);
        game_button8.setBackgroundResource(android.R.drawable.btn_default);

        Button game_button9 = (Button)findViewById(R.id.game_button9);
        game_button9.setBackgroundResource(android.R.drawable.btn_default);

        // Update count fields
        SharedPreferences preferences = getSharedPreferences("scores", Context.MODE_PRIVATE);
        String X = preferences.getString("X","0");
        int x = Integer.parseInt(X);
        String O = preferences.getString("O","0");
        int o = Integer.parseInt(O);
        String D = preferences.getString("D","0");
        int d = Integer.parseInt(D);

        TextView X_wins_count = (TextView) findViewById(R.id.X_wins_count);
        X_wins_count.setText(X);

        TextView O_wins_count = (TextView) findViewById(R.id.O_wins_count);
        O_wins_count.setText(O);

        TextView Draws_count = (TextView) findViewById(R.id.Draws_count);
        Draws_count.setText(D);
    }


    private void set_sizes() {
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        float txt_dpi = getResources().getDisplayMetrics().ydpi;

        TextView X_wins = (TextView) findViewById(R.id.X_wins);


        TextView X_wins_count = (TextView) findViewById(R.id.X_wins_count);
        X_wins_count.setMinimumWidth(width /3);
        X_wins_count.setMinimumHeight(height/9);
        X_wins_count.setTextSize(txt_dpi/5);

        TextView O_wins = (TextView) findViewById(R.id.O_wins);


        TextView O_wins_count = (TextView) findViewById(R.id.O_wins_count);
        O_wins_count.setMinimumWidth(width /3);
        O_wins_count.setMinimumHeight(height/9);
        O_wins_count.setTextSize(txt_dpi/5);

        TextView Draws = (TextView) findViewById(R.id.Draws);


        TextView Draws_count = (TextView) findViewById(R.id.Draws_count);
        Draws_count.setMinimumWidth(width /3);
        Draws_count.setMinimumHeight(height/9);
        Draws_count.setTextSize(txt_dpi/5);

        Button game_button1 = (Button)findViewById(R.id.game_button1);
        game_button1.setMinimumHeight(height/4);
        game_button1.setMinimumWidth(width /3);

        Button game_button2 = (Button)findViewById(R.id.game_button2);
        game_button2.setMinimumHeight(height/4);
        game_button2.setMinimumWidth(width /3);

        Button game_button3 = (Button)findViewById(R.id.game_button3);
        game_button3.setMinimumHeight(height/4);
        game_button3.setMinimumWidth(width /3);

        Button game_button4 = (Button)findViewById(R.id.game_button4);
        game_button4.setMinimumHeight(height/4);
        game_button4.setMinimumWidth(width /3);

        Button game_button5 = (Button)findViewById(R.id.game_button5);
        game_button5.setMinimumHeight(height/4);
        game_button5.setMinimumWidth(width /3);

        Button game_button6 = (Button)findViewById(R.id.game_button6);
        game_button6.setMinimumHeight(height/4);
        game_button6.setMinimumWidth(width /3);

        Button game_button7 = (Button)findViewById(R.id.game_button7);
        game_button7.setMinimumHeight(height/4);
        game_button7.setMinimumWidth(width /3);

        Button game_button8 = (Button)findViewById(R.id.game_button8);
        game_button8.setMinimumHeight(height/4);
        game_button8.setMinimumWidth(width /3);

        Button game_button9 = (Button)findViewById(R.id.game_button9);
        game_button9.setMinimumHeight(height/4);
        game_button9.setMinimumWidth(width /3);

        // set tags to buttons
        game_button1.setTag("0");
        game_button2.setTag("1");
        game_button3.setTag("2");
        game_button4.setTag("3");
        game_button5.setTag("4");
        game_button6.setTag("5");
        game_button7.setTag("6");
        game_button8.setTag("7");
        game_button9.setTag("8");

        // Set scores to fields
        preferences = getSharedPreferences("scores", Context.MODE_PRIVATE);
        String X = preferences.getString("X","0");
        X_wins_count.setText(X);

        String O = preferences.getString("O","0");
        O_wins_count.setText(O);

        String D = preferences.getString("D","0");
        Draws_count.setText(D);

    }

}