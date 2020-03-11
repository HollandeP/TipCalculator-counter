package com.example.tipcalculator_counter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        
    }

    private void showErrorAlert(String errorMessage, final int fieldId) {
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(errorMessage)
                .setNeutralButton("Close",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                findViewById(fieldId).requestFocus();
                            }
                        }).show();
    }
}
