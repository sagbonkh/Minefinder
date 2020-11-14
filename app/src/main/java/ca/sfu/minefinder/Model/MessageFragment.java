package ca.sfu.minefinder.Model;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatDialogFragment;

import ca.sfu.minefinder.R;

public class MessageFragment extends AppCompatDialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.layout_ending, null);

        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               getActivity().finish();
            }
        };

        return new AlertDialog.Builder(getActivity())
                .setTitle("Victory")
                .setView(v)
                .setPositiveButton(android.R.string.ok,listener)
                .create();
    }
}
