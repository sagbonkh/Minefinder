package ca.sfu.minefinder.Model;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ca.sfu.minefinder.R;

public class ConfigureActivity extends AppCompatActivity {

    public static Intent makeIntent(Context c){
        return new Intent(c, ConfigureActivity.class);

    }
    Button reset;
    Spinner BoardSpinner;
    Spinner MineNumSpinner;
    Button Save;
    int row = 0;
    int col = 0;
    int mines = 0;
    int gameNum = 0;
    int high;
    String str1 = "4x6";
    String str2 = "5x10";
    String str3 = "6x15";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure);
        BoardSpinner = findViewById(R.id.BoardSzspinner);
        MineNumSpinner = findViewById(R.id.NumOfMinesSpinner);
        setUpBrd();
        setUpMine();
        setUpSaveBtn();
        catchIntent();
        setUpReset();
    }

    private void catchIntent() {
        Intent intent = getIntent();
        gameNum = intent.getIntExtra("ca.sfu.minefinder.Model.MainMenuActivity.gameCount",1);
        high = intent.getIntExtra("ca.sfu.minefinder.Model.MainMenuActivity.highScore",0 );
    }

    private void setUpReset() {
        reset = findViewById(R.id.resetBtn);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameNum = 0;
                high = 0;
            }
        });

    }

    private void setUpMine() {
        List<String> userList2 = new ArrayList<>();
        userList2.add("6");
        userList2.add("10");
        userList2.add("15");
        userList2.add("20");


        ArrayAdapter<String> myAdapter2 = new ArrayAdapter<String>(ConfigureActivity.this,
                android.R.layout.simple_list_item_1, userList2);
        myAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        MineNumSpinner.setAdapter(myAdapter2);
        MineNumSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        mines = 6;
                        break;
                    case 1:
                        mines = 10;
                        break;
                    case 2:
                        mines = 15;
                        break;
                    case 3:
                        mines = 20;
                        break;
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setUpBrd() {
        List<String> userList = new ArrayList<>();
        userList.add(str1);
        userList.add(str2);
        userList.add(str3);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(ConfigureActivity.this,
                android.R.layout.simple_list_item_1, userList);
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        BoardSpinner.setAdapter(myAdapter);
        BoardSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        row = 4;
                        col = 6;
                     //   Toast.makeText(ConfigureActivity.this,row +" "+ col,Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        row = 5;
                        col = 10;
                      //  Toast.makeText(ConfigureActivity.this,row +" "+ col,Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        row = 6;
                        col = 15;
                      //  Toast.makeText(ConfigureActivity.this,row +" "+ col,Toast.LENGTH_SHORT).show();
                        break;
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setUpSaveBtn() {
        Save = findViewById(R.id.Savebtn);
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("ca.sfu.minefinder.Model.ConfigureActivity.row",row);
                intent.putExtra("ca.sfu.minefinder.Model.ConfigureActivity.col",col);
                intent.putExtra("ca.sfu.minefinder.Model.ConfigureActivity.mines",mines);
                intent.putExtra("ca.sfu.minefinder.Model.ConfigureActivity.gameNum", gameNum);
                intent.putExtra("ca.sfu.minefinder.Model.ConfigureActivity.high", high);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }


}
