package com.example.tipcalculator_counter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText mealPrice;
    EditText numPeople;
    EditText customTip;
    RadioGroup tipOptions;
    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Assign View objects to corresponding IDs
        final TextView totalVal = findViewById(R.id.totalVal);
        final TextView tipVal = findViewById(R.id.tipVal);
        final TextView perPersonVal = findViewById(R.id.perPersonVal);
        mealPrice = findViewById(R.id.mealPriceAmount);
        mealPrice.setOnKeyListener(mKeyListener);
        numPeople = findViewById(R.id.numPeopleInput);
        numPeople.setOnKeyListener(mKeyListener);
        customTip = findViewById(R.id.customTipInput);
        customTip.setVisibility(View.INVISIBLE);
        customTip.setOnKeyListener(mKeyListener);

        //Checks for current tipOptions RadioGroup selection and enables customTipAmount EditText if customTip is selected
        tipOptions = findViewById(R.id.tipOptions);
        tipOptions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.customTip) customTip.setVisibility(View.VISIBLE);
                else customTip.setVisibility(View.INVISIBLE);
            }
        });

        //Invoke submitButton and assign onClickListener to calculate totals
        Button submitBtn = findViewById(R.id.submitButton);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double tip;
                double total;
                double perPerson;

                //if statement checks that all values have been entered
                if (!mealPrice.getText().toString().matches("") && !numPeople.getText().toString().matches("")
                    && (customTip.getVisibility() == View.INVISIBLE || !customTip.getText().toString().matches(""))){
                    switch (tipOptions.getCheckedRadioButtonId()){
                        case R.id.tip10:
                            tip = Double.parseDouble(mealPrice.getText().toString()) * .1;
                            total = Double.parseDouble(mealPrice.getText().toString()) + tip;
                            perPerson = total / Integer.parseInt(numPeople.getText().toString());
                            tipVal.setText(String.format("$%.2f",tip));
                            totalVal.setText(String.format("$%.2f",total));
                            perPersonVal.setText(String.format("$%.2f", perPerson));
                            break;

                        case R.id.tip15:
                            tip = Double.parseDouble(mealPrice.getText().toString()) * .15;
                            total = Double.parseDouble(mealPrice.getText().toString()) + tip;
                            perPerson = total / Integer.parseInt(numPeople.getText().toString());
                            tipVal.setText(String.format("$%.2f",tip));
                            totalVal.setText(String.format("$%.2f",total));
                            perPersonVal.setText(String.format("$%.2f", perPerson));
                            break;

                        case R.id.tip20:
                            tip = Double.parseDouble(mealPrice.getText().toString()) * .2;
                            total = Double.parseDouble(mealPrice.getText().toString()) + tip;
                            perPerson = total / Integer.parseInt(numPeople.getText().toString());
                            tipVal.setText(String.format("$%.2f",tip));
                            totalVal.setText(String.format("$%.2f",total));
                            perPersonVal.setText(String.format("$%.2f", perPerson));
                            break;

                        case R.id.customTip:
                            tip = Double.parseDouble(mealPrice.getText().toString()) *
                                    (Double.parseDouble(customTip.getText().toString()) / 100);
                            total = Double.parseDouble(mealPrice.getText().toString()) + tip;
                            perPerson = total / Integer.parseInt(numPeople.getText().toString());
                            tipVal.setText(String.format("$%.2f",tip));
                            totalVal.setText(String.format("$%.2f",total));
                            perPersonVal.setText(String.format("$%.2f", perPerson));
                            break;
                    }
                }
                else{
                    toast = Toast.makeText(getApplicationContext(), "All fields must contain a valid input",Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        Button resetBtn = findViewById(R.id.resetButton);
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipVal.setText("");
                totalVal.setText("");
                perPersonVal.setText("");
                tipOptions.clearCheck();
                mealPrice.setText("");
                numPeople.setText("");
                customTip.setText("");
            }
        });



    }
    private View.OnKeyListener mKeyListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if(keyCode == KeyEvent.KEYCODE_ENTER){
                switch (v.getId()) {
                    case R.id.mealPriceAmount:
                        if (!mealPrice.getText().toString().matches("") && Double.parseDouble(mealPrice.getText().toString()) < 1.00)
                            showErrorAlert("Meal cost must be $1.00 or greater", R.id.mealPriceAmount);
                        break;
                    case R.id.numPeopleInput:
                        if(!numPeople.getText().toString().matches("") && Integer.parseInt(numPeople.getText().toString()) < 1)
                            showErrorAlert("Number of people must be greater than zero", R.id.numPeopleInput);
                        break;
                    case R.id.customTipInput:
                }
            }
            return false;
        }

    };
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
