package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    // 0 => O 1=> X
    public static int currPlayer;
    public static int turnCounter;
    public static BoardCell[][] gameBoard = new BoardCell[3][3];
    public static Boolean isGameOver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initBoardVar();
        buildBoard();
    }

    private void initBoardVar() {
       turnCounter = 0;
       isGameOver = false;
       currPlayer = 1;
       ImageView currPlayerImg = findViewById(R.id.mainactivity_MessagesArea_img);
       currPlayerImg.setImageResource(R.drawable.xplay);
    }

    private void buildBoard() {
        int nImgId = 0;

        for( int i = 0; i < 3 ; i ++) {
            for( int j = 0; j < 3 ; j ++) {
                final int rowIndex = i;
                final int colIndex = j;

                clickOnRestartButton();

                String sImgId = "mainactivity_" + (nImgId + 1) + "_img";
                ImageView imgView = findViewById(getResources().getIdentifier(sImgId, "id", getPackageName()));
                imgView.setOnClickListener(view -> {
                    turnFlow(rowIndex,colIndex);
                });

                gameBoard[rowIndex][colIndex] = new BoardCell(imgView);
                nImgId++;
            }
        }
    }

    private void clickOnRestartButton() {
        Button restartButton = findViewById(R.id.restartbutton);
        restartButton.setOnClickListener(View -> {
            cleanBoard();
        });
    }

    private void turnFlow(int rowIndex, int colIndex) {
        if(!isGameOver) {
            if (gameBoard[rowIndex][colIndex].getState() == 2) {
                changeCellState(rowIndex, colIndex);
                changeCellImg(rowIndex, colIndex, currPlayer);
                turnCounter++;
            }
            if(iSWinnig(rowIndex, colIndex)) {
                if(currPlayer == 0) {
                    changeWinnerImg(R.drawable.owin);
                } else {
                    changeWinnerImg(R.drawable.xwin);
                }
            } else if(turnCounter == 9) {
                changeWinnerImg(R.drawable.nowin);
            } else {
                changePlayer();
            }
        }
    }

    private void changeCellState( int i,  int j) {
        gameBoard[i][j].setState(currPlayer);
    }

    private void cleanBoard() {
        for( int i = 0; i < 3 ; i ++) {
            for( int j = 0; j < 3 ; j ++) {
                gameBoard[i][j].setState(2);
                changeCellImg(i,j,2);
                initBoardVar();
            }
        }
    }

    private void changePlayer() {
//      Switch players
        currPlayer = 1 - currPlayer;

//      Change player photo
        ImageView currPlayerImg = findViewById(R.id.mainactivity_MessagesArea_img);

        if(currPlayer == 0) {
            currPlayerImg.setImageResource(R.drawable.oplay);
        } else {
            currPlayerImg.setImageResource(R.drawable.xplay);
        }
    }

    private void changeCellImg( int i,  int j, int cellState) {
        if(cellState == 0) {
            gameBoard[i][j].getIm().setImageResource(R.drawable.o);
        } else if(cellState == 1){
            gameBoard[i][j].getIm().setImageResource(R.drawable.x);
        } else {
            gameBoard[i][j].getIm().setImageResource(R.drawable.empty);
        }
    }

    private void changeWinnerImg(int img) {
        ImageView winnerImg = findViewById(R.id.mainactivity_MessagesArea_img);
        winnerImg.setImageResource(img);
    }

        private boolean iSWinnig(int rowIndex, int colIndex){
        isGameOver = rowWin(rowIndex) ||
                colWin(colIndex) ||
                mainDiagonalWin(rowIndex, colIndex) ||
                secondDiagonalWin(rowIndex, colIndex);;
        return isGameOver;
    }

    private boolean rowWin(int rowIndex) {
        if(gameBoard[rowIndex][0].getState() != 2 && gameBoard[rowIndex][0].getState() == gameBoard[rowIndex][1].getState() && gameBoard[rowIndex][2].getState() == gameBoard[rowIndex][1].getState()) {
            return true;
        }
        return false;
    }

    private boolean colWin(int colIndex) {
        if(gameBoard[0][colIndex].getState() != 2 && gameBoard[0][colIndex].getState() == gameBoard[1][colIndex].getState() && gameBoard[1][colIndex].getState() == gameBoard[2][colIndex].getState()) {
            return true;
        }
        return false;
    }

    private boolean mainDiagonalWin(int rowIndex, int colIndex) {
        if(gameBoard[0][0].getState() != 2 && gameBoard[0][0].getState() == gameBoard[1][1].getState() && gameBoard[1][1].getState() == gameBoard[2][2].getState()) {
            return true;
        }
        return false;
    }

    private boolean secondDiagonalWin(int rowIndex, int colIndex) {
        if(gameBoard[0][2].getState() != 2 && gameBoard[0][2].getState() == gameBoard[1][1].getState() && gameBoard[1][1].getState() == gameBoard[2][0].getState()) {
            return true;
        }
        return false;
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