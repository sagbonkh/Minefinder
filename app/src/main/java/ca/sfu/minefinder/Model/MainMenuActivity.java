package ca.sfu.minefinder.Model;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ca.sfu.minefinder.R;

public class MainMenuActivity extends AppCompatActivity {
    public static final int REQUEST_CODE = 571;
    private Button playBtn, helpBtn, confBtn;
    int gameCount =0, row = 4, col = 6, mine = 6, highScore = 0;
    public static final int CONF_REQUEST_CODE = 3030;

    public static Intent makeIntent(Context c){
        return  new Intent(c, MainMenuActivity.class);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        gameCount = 0;
        setUpConfBtn();
        setUpPlayBtn();
        setUpHelpBtn();

        //setUpResetBtn();

    }

    private void setUpConfBtn() {
        confBtn = findViewById(R.id.configure);
        confBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ConfigureActivity.makeIntent(MainMenuActivity.this);
                intent.putExtra("ca.sfu.minefinder.Model.MainMenuActivity.highScore",highScore);
                intent.putExtra("ca.sfu.minefinder.Model.MainMenuActivity.gameCount",gameCount);
                startActivityForResult(intent, CONF_REQUEST_CODE);
            }
        });
    }

    private void setUpPlayBtn() {
        playBtn = findViewById(R.id.Play);
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameCount++;
                Intent intent = PlayActivity.makeIntent(MainMenuActivity.this);
                intent.putExtra("ca.sfu.minefinder.Model.MainMenuActivity.gameCount",gameCount);
                intent.putExtra("ca.sfu.minefinder.Model.MainMenuActivity.row", row);
                intent.putExtra("ca.sfu.minefinder.Model.MainMenuActivity.col", col);
                intent.putExtra("ca.sfu.minefinder.Model.MainMenuActivity.mine", mine);
                intent.putExtra("ca.sfu.minefinder.Model.MainMenuActivity.highScore", highScore);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    private void setUpHelpBtn() {
        helpBtn = findViewById(R.id.help);
        helpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = HelpScreenActivity.makeIntent(MainMenuActivity.this);
                startActivity(intent);
            }
        });
    }

//    private void setUpResetBtn() {
//        reset = findViewById(R.id.reset);
//        reset.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                gameCount = 0;
//            }
//        });
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        switch (requestCode){
            case REQUEST_CODE:
            if(resultCode == Activity.RESULT_OK){
                gameCount = data.getIntExtra("ca.sfu.minefinder.Model.PlayActivity.gameNum", 0);
                highScore = data.getIntExtra("ca.sfu.minefinder.Model.PlayActivity.highScore",0);
            }
            break;
            case CONF_REQUEST_CODE:
                if(resultCode == Activity.RESULT_OK){
                    gameCount = data.getIntExtra("ca.sfu.minefinder.Model.ConfigureActivity.gameNum", 0);
                    row = data.getIntExtra("ca.sfu.minefinder.Model.ConfigureActivity.row",4);
                    col = data.getIntExtra("ca.sfu.minefinder.Model.ConfigureActivity.col", 6);
                    mine = data.getIntExtra("ca.sfu.minefinder.Model.ConfigureActivity.mines", 6);
                    highScore = data.getIntExtra("ca.sfu.minefinder.Model.ConfigureActivity.high",0);
                    break;
                }
        }
    }
}
