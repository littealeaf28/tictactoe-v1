package com.tealeaf.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class GamePlayActivity extends AppCompatActivity {
    private Game TTT;
    private GridView TTTBoard;
    private TextView turnCountView, playerIndictView, gameOverMsg;
    //Single array of board items is required for use of the ArrayAdapter
    private String boardItems[] = new String[9];
    //0 - game still in play, 1 - game won by PlayerOne, 2 - game won by PlayerTwo, 3 - cat's game (tie)
    private int gameState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);

        Intent playIntent = getIntent();
        TTT = (Game) playIntent.getSerializableExtra("TTT");

        TTTBoard = (GridView) findViewById(R.id.board);
        turnCountView = (TextView) findViewById(R.id.turnCount);
        playerIndictView = (TextView) findViewById(R.id.playerIndict);

        boardItems = TTT.getSingleListBoard();
        turnCountView.setText("Turn: " + String.valueOf(TTT.getNumTurns()));
        if (TTT.getNumTurns() % 2 == TTT.playerOne().getStartTurn()) {
            playerIndictView.setText("It's " + TTT.playerOne().toString() + "'s turn!");
        }
        else {
            playerIndictView.setText("It's " + TTT.playerTwo().toString() + "'s turn!");
        }

        //Populates board with board item values
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.board_component, R.id.board_box, boardItems);
        TTTBoard.setAdapter(arrayAdapter);

        //Creates a user selection handler and links it to the GridView
        //Allows board to change based on user selection
        TTTBoard.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                turnPlay(position);
                arrayAdapter.notifyDataSetChanged();
            }
        });
    }

    //Executes processes for a single turn once one of the players make their move
    public void turnPlay (int position) {
        String selectItem = (String) TTTBoard.getItemAtPosition(position);
        String currentPlayerItem = "";
        if (TTT.getNumTurns() % 2 == TTT.playerOne().getStartTurn()) {
            currentPlayerItem = TTT.playerOne().getMoveToken();
        }
        else {
            currentPlayerItem = TTT.playerTwo().getMoveToken();
        }

        //If selected position is available, player's item will appear on board and turn count will update
        //Prevents player from playing if game already over
        if (gameState == 0 && selectItem.equals("")) {
            boardItems[position] = currentPlayerItem;
            int row = position / 3;
            int col = position % 3;
            TTT.setTokenOnBoard(row, col, currentPlayerItem);

            TTT.updateNumTurns();
            turnCountView.setText("Turn: " + String.valueOf(TTT.getNumTurns()));

            if (TTT.checkGameConditions(row, col) != 0) {
                gameState = TTT.checkGameConditions(row, col);
                showGameOver(TTT.checkGameConditions(row, col));
            }
        }
        else if (gameState == 0 && !selectItem.equals("")) {
            Toast.makeText(getApplicationContext(), "This move has already been made!", Toast.LENGTH_SHORT).show();
        }

        if (TTT.getNumTurns() % 2 == TTT.playerOne().getStartTurn()) {
            playerIndictView.setText("It's " + TTT.playerOne().toString() + "'s turn!");
        }
        else {
            playerIndictView.setText("It's " + TTT.playerTwo().toString() + "'s turn!");
        }

    }

    //Displays popup message announcing winner
    public void showGameOver(int gameState) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View gameOverMsgPopup = inflater.inflate(R.layout.game_over_popup, null);
        PopupWindow gameOverMsgWindow = new PopupWindow(gameOverMsgPopup, ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT, true);
        gameOverMsg = (TextView) gameOverMsgWindow.getContentView().findViewById(R.id.game_over_msg);

        String winnerNotif;
        switch(gameState){
            case 1:
                winnerNotif = TTT.playerOne().toString() + " won!!";
                gameOverMsg.setText(winnerNotif);
                break;
            case 2:
                winnerNotif = TTT.playerTwo().toString() + " won!!";
                gameOverMsg.setText(winnerNotif);
                break;
            case 3:
                winnerNotif = "Cat's game! What a close one.";
                gameOverMsg.setText(winnerNotif);
                break;
        }
        gameOverMsgWindow.showAtLocation(turnCountView, Gravity.CENTER, 0, 0);
    }

    public void closeOut(View v) {
        finish();
    }
}
