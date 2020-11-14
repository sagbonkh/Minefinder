package Logic;

import java.util.Random;

import ca.sfu.minefinder.Model.PlayActivity;

/*
    This class is in charge of generating mines in randomised locations and keeping track of said
    mines.
 */

public class Scan extends PlayActivity {
    int []row_arr;
    int []col_arr;
    int rows;
    int cols;
    int ArrSz;
    char [][]MineKey;
    char []MineFound;

    public Scan(int arrSz, int rows, int cols) {
        ArrSz = arrSz;
        this.rows = rows;
        this.cols = cols;
        row_arr = new int [arrSz];
        col_arr = new int[arrSz];
        MineKey = new char[rows][cols];
        MineFound = new char[arrSz];
        for(int i = 0; i < rows;i++){
            for(int j = 0;j < cols;j++)
                MineKey[i][j] = 'N';
        }

    }


    public void generateRandomMines(){
        for(int i = 0;i < ARRsz;i++) {
            Random randomGenx = new Random();
            row_arr[i] = randomGenx.nextInt(NUM_ROWS);
            Random randomGeny = new Random();
            col_arr[i] = randomGeny.nextInt(NUM_COLS);
            MineFound[i] = 'N';
        }
    }

    public char getMineFound(int index) {
        return MineFound[index];
    }


    public int getRow_arr_val(int index) {
        return row_arr[index];
    }

    public int getCol_arr_val(int index) {
        return col_arr[index];
    }

    public void setMineFound(int mineFound) {
        MineFound[mineFound] = 'Y';
    }

    public int FindMinesInPath(int row, int col){
        int [][]arr = new int[NUM_ROWS][NUM_COLS];
        int mines = 0;
        for(int i = 0; i < NUM_ROWS;i++) {
            for (int j = 0; j < NUM_COLS; j++) {
                arr[i][j] = 0;
            }
        }

        for(int i = 0; i < ArrSz; i++) {
            arr[row_arr[i]][col_arr[i]] = 1;
        }

        for(int i = 0;i < rows; i++)
            if(arr[i][col] == 1 && MineKey[i][col] == 'N') {
                mines++;
            }

        for(int j = 0;j < cols;j++)
            if(arr[row][j] == 1 && MineKey[row][j] == 'N'){
                mines++;
            }
        return mines;
    }

    public void setMineKey(int row , int col) {
        MineKey[row][col] = 'Y';
    }
}

