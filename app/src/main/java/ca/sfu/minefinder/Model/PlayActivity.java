package ca.sfu.minefinder.Model;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Random;

import Logic.Scan;
import ca.sfu.minefinder.R;

public class PlayActivity extends AppCompatActivity {
    public int NUM_COLS = 6;
    public int NUM_ROWS = 4;
    public int ARRsz = 6;
    public int key = 0;
    public int scans = 0;
    public int mines = 0;
    public int highScore;
    public char aMine = 'N';
    public int minesinpath = 0;
    public int gameNum;
    Button [][]buttons;
    TextView txt;
    TextView mineTxt;
    TextView gamesPlayed;
    TextView high;
    int sound;
    private SoundPool soundPool;

    public static Intent makeIntent(Context c){
        return new Intent(c, PlayActivity.class);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        gameNum++;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        intentCatcher();
        buttons = new Button[NUM_ROWS][NUM_COLS];
        showHigh();
        populateButtons();
        updateScanCount();
        updateMineCount();
        updateGamesCount();
        makeSound();
        sound = soundPool.load(PlayActivity.this,R.raw.explosion,1);
    }

    private void showHigh() {
        high = findViewById(R.id.HighScore);
        high.setText("High score: "+highScore);
    }

    private void updateHighScore() {
        if(highScore <= scans)
            highScore = scans;
    }

    private void updateGamesCount() {
        gamesPlayed = findViewById(R.id.TimesPlayed);
        gamesPlayed.setText("Times played: "+gameNum);
    }

    private void updateMineCount() {
        mineTxt = findViewById(R.id.MineNum);
        mineTxt.setText("Found "+mines+" of "+ ARRsz+" mines");
    }

    private void updateScanCount() {
        txt = findViewById(R.id.ScanNum);
        txt.setText("Num of scans used: "+scans);
    }

    private void populateButtons() {
        final Scan gameLogic = new Scan(ARRsz,NUM_ROWS,NUM_COLS);
        gameLogic.generateRandomMines();


        TableLayout table = findViewById(R.id.Buttontable);
        for(int row = 0; row < NUM_ROWS; row++) {
                TableRow tableRow = new TableRow(this);
                tableRow.setLayoutParams(new TableLayout.LayoutParams(
                        TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.MATCH_PARENT,
                        1.0f
                ));
                table.addView(tableRow);

                for (int col = 0; col < NUM_COLS; col++) {
                    final int finalcol = col;
                    final int finalrow = row;
                    final Button button = new Button(this);
                    button.setLayoutParams(new TableRow.LayoutParams(
                            TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.MATCH_PARENT,
                            1.0f
                    ));
                    button.setPadding(0, 0, 0, 0);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            minesinpath = 0;
                            aMine = 'N';
                            minesinpath = gameLogic.FindMinesInPath(finalrow,finalcol);
                            gridButtonClicked(finalrow, finalcol, gameLogic);
                            if(aMine == 'N')
                                button.setText("" + minesinpath);

                        }
                    });

                    tableRow.addView(button);
                    buttons[row][col] = button;
                }
            }
    }

    private void gridButtonClicked(int row, int col, Scan gameLogic) {
        scans++;
        updateScanCount();
        for (int i = 0; i < ARRsz; i++) {
            Button button = buttons[row][col];
            if (row == gameLogic.getRow_arr_val(i) && col == gameLogic.getCol_arr_val(i)
                    && gameLogic.getMineFound(i) == 'N') {
                aMine = 'Y';
                gameLogic.setMineKey(row, col);
                mines++;
                updateMineCount();
                lockbuttonsizes();
                int newWidth = button.getWidth();
                int newHeight = button.getHeight();
                Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bomb);
                Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true);
                Resources resource = getResources();
                button.setBackground(new BitmapDrawable(resource, scaledBitmap));
                button.setText("");
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        soundPool.play(sound,1,1,1,0,1);
                    }
                });

                key++;
                gameLogic.setMineFound(i);
            }

        }
        if(key == ARRsz) {
            updateHighScore();
            Intent retIntent = new Intent();
            retIntent.putExtra("ca.sfu.minefinder.Model.PlayActivity.gameNum",gameNum);
            retIntent.putExtra("ca.sfu.minefinder.Model.PlayActivity.highScore",highScore);
            setResult(Activity.RESULT_OK, retIntent);
            setUpEnding();
        }
    }

    private void makeSound() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            soundPool = new SoundPool.Builder()
                    .setMaxStreams(6)
                    .setAudioAttributes(audioAttributes)
                    .build();
        }
        else{
            soundPool = new SoundPool(6, AudioManager.STREAM_MUSIC,0);
        }
    }

    private void setUpEnding() {
        FragmentManager manager = getSupportFragmentManager();
        MessageFragment dialog = new MessageFragment();
        dialog.show(manager,"MessageDialog");
    }


    private void lockbuttonsizes() {
        scans--;
        updateScanCount();
        for(int row = 0; row < NUM_ROWS; row++){
            for(int col = 0; col < NUM_COLS; col++){
                Button button = buttons[row][col];

                int width = button.getWidth();
                button.setMinWidth(width);
                button.setMaxWidth(width);

                int height = button.getHeight();
                button.setMinHeight(height);
                button.setMaxHeight(height);
            }
        }
    }

    private void intentCatcher() {
        Intent intent = getIntent();
        gameNum = intent.getIntExtra("ca.sfu.minefinder.Model.MainMenuActivity.gameCount",1);
        NUM_COLS = intent.getIntExtra("ca.sfu.minefinder.Model.MainMenuActivity.col",6);
        NUM_ROWS = intent.getIntExtra("ca.sfu.minefinder.Model.MainMenuActivity.row", 4);
        ARRsz = intent.getIntExtra("ca.sfu.minefinder.Model.MainMenuActivity.mine", 6);
        highScore = intent.getIntExtra("ca.sfu.minefinder.Model.MainMenuActivity.highScore", 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundPool.release();
        soundPool = null;
    }
}
