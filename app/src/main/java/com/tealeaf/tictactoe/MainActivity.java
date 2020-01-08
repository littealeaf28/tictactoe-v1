package com.tealeaf.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

public class MainActivity extends AppCompatActivity {
    private TextView playerIndictX, playerIndictO, oSelect, xSelect;
    private Button startButton;
    public Game TTT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TTT = new Game();

        playerIndictX = (TextView) findViewById(R.id.player_indict_x);
        playerIndictO = (TextView) findViewById(R.id.player_indict_o);
        startButton = (Button) findViewById(R.id.start);
        oSelect = (TextView) findViewById(R.id.o_select);
        xSelect = (TextView) findViewById(R.id.x_select);
    }

    public void setPlayerTokens (View view) {
        //Sets player token based on their choice
        if (view.equals(xSelect)) {
            TTT.setPlayerTokens(xSelect.getText().toString());
            playerIndictX.setText(TTT.playerOne().toString());
            playerIndictO.setText(TTT.playerTwo().toString());
            xSelect.setTextColor(getResources().getColor(R.color.colorPrimary));
            oSelect.setTextColor(getResources().getColor(R.color.colorAccent));
        }
        else {
            TTT.setPlayerTokens(oSelect.getText().toString());
            playerIndictX.setText(TTT.playerTwo().toString());
            playerIndictO.setText(TTT.playerOne().toString());
            xSelect.setTextColor(getResources().getColor(R.color.colorAccent));
            oSelect.setTextColor(getResources().getColor(R.color.colorPrimary));
        }

        //Shows player indicators and start button if not shown before
        if (playerIndictX.getVisibility() == View.INVISIBLE) {
            playerIndictX.setVisibility(View.VISIBLE);
            playerIndictO.setVisibility(View.VISIBLE);
            startButton.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        playerIndictX.setVisibility(View.INVISIBLE);
        playerIndictO.setVisibility(View.INVISIBLE);
        startButton.setVisibility(View.INVISIBLE);
        xSelect.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        oSelect.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
    }

    public void startGame (View view) {
        TTT.setPlayerTurns();

        Intent playIntent = new Intent(this, GamePlayActivity.class);
        playIntent.putExtra("TTT", TTT);
        startActivity(playIntent);
    }
}
