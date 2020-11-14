package ca.sfu.minefinder.Model;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import ca.sfu.minefinder.R;

public class HelpScreenActivity extends AppCompatActivity {

    public static Intent makeIntent(Context c){
        return new Intent(c, HelpScreenActivity.class);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_screen);
    }
}
