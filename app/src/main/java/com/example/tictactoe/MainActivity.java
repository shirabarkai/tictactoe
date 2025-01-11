package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    BoardCell[] gameBoard = new BoardCell[9];

// 0 => O 1=> X
   int currPlayer = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buildBoard();
    }

    private void buildBoard() {
        ImageView im;
        String imId;

        for( int i = 0; i <= 8 ; i ++) {
            final int index = i;

            imId = "mainactivity_" + (i + 1) + "_img";
            im = findViewById(getResources().getIdentifier(imId, "id", getPackageName()));

            im.setOnClickListener(view -> {
                if (gameBoard[index].getState() == 2) {
                    changeCellState(index);
                    changeCellPhoto(index);
                    changePlayer();
                }
            });

            gameBoard[i] = new BoardCell(im);
        }
    }

    private void changeCellState(final int place) {
        gameBoard[place].setState(currPlayer);
    }

    private void changePlayer() {
//      Switch players
        currPlayer = 1 - currPlayer;

//      Change player photo
        ImageView currPlayerImg = findViewById(R.id.mainactivity_player_img);

        if(currPlayer == 0) {
            currPlayerImg.setImageResource(R.drawable.oplay);
        } else {
            currPlayerImg.setImageResource(R.drawable.xplay);
        }
    }

    private void changeCellPhoto(final int place){
        if(currPlayer == 0) {
            gameBoard[place].getIm().setImageResource(R.drawable.o);
        } else {
            gameBoard[place].getIm().setImageResource(R.drawable.x);
        }
    }

    public class BoardCell {
//      state: 0 -> O, 1-> x, 2-> empty
        private int state = 2;
        private ImageView im;

        public BoardCell(ImageView im) {
            setIm(im);
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public ImageView getIm() {
            return im;
        }

        public void setIm(ImageView im) {
            this.im = im;
        }
    }
}