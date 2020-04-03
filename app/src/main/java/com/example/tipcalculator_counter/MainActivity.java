package com.example.tipcalculator_counter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
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
    SharedPreferences.Editor editor;
    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Access SharedPreferences
        final SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        //Assign View objects to corresponding IDs
        final TextView totalVal = findViewById(R.id.totalVal);
        totalVal.setText(sharedPref.getString("totalVal", getString(R.string.totalCostVal)));
        final TextView tipVal = findViewById(R.id.tipVal);
        tipVal.setText(sharedPref.getString("tipVal",getString(R.string.tipVal)));
        final TextView perPersonVal = findViewById(R.id.perPersonVal);
        perPersonVal.setText(sharedPref.getString("perPersonVal", getString(R.string.perPersonVal)));
        mealPrice = findViewById(R.id.mealPriceInput);
        mealPrice.setText(sharedPref.getString("mealPrice", null));
        mealPrice.setOnKeyListener(mKeyListener);
        numPeople = findViewById(R.id.numPeopleInput);
        numPeople.setText(sharedPref.getString("numPeople", null));
        numPeople.setOnKeyListener(mKeyListener);
        customTip = findViewById(R.id.customTipInput);
        customTip.setText(sharedPref.getString("customTip", null));
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

                    //Switch statement calculates based on tip percentage selected
                    switch (tipOptions.getCheckedRadioButtonId()){
                        case R.id.tip10:
                            tip = Double.parseDouble(mealPrice.getText().toString()) * .1;
                            total = Double.parseDouble(mealPrice.getText().toString()) + tip;
                            perPerson = total / Integer.parseInt(numPeople.getText().toString());
                            tipVal.setText(String.format("$%.2f",tip));
                            totalVal.setText(String.format("$%.2f",total));
                            perPersonVal.setText(String.format("$%.2f", perPerson));

                            //Add new values to sharedPref
                            editor.putString("tipVal", tipVal.getText().toString());
                            editor.putString("totalVal", totalVal.getText().toString());
                            editor.putString("perPersonVal", perPersonVal.getText().toString());
                            editor.commit();
                            break;

                        case R.id.tip15:
                            tip = Double.parseDouble(mealPrice.getText().toString()) * .15;
                            total = Double.parseDouble(mealPrice.getText().toString()) + tip;
                            perPerson = total / Integer.parseInt(numPeople.getText().toString());
                            tipVal.setText(String.format("$%.2f",tip));
                            totalVal.setText(String.format("$%.2f",total));
                            perPersonVal.setText(String.format("$%.2f", perPerson));

                            //Add new values to sharedPref
                            editor.putString("tipVal", tipVal.getText().toString());
                            editor.putString("totalVal", totalVal.getText().toString());
                            editor.putString("perPersonVal", perPersonVal.getText().toString());
                            editor.commit();
                            break;

                        case R.id.tip20:
                            tip = Double.parseDouble(mealPrice.getText().toString()) * .2;
                            total = Double.parseDouble(mealPrice.getText().toString()) + tip;
                            perPerson = total / Integer.parseInt(numPeople.getText().toString());
                            tipVal.setText(String.format("$%.2f",tip));
                            totalVal.setText(String.format("$%.2f",total));
                            perPersonVal.setText(String.format("$%.2f", perPerson));

                            //Add new values to sharedPref
                            editor.putString("tipVal", tipVal.getText().toString());
                            editor.putString("totalVal", totalVal.getText().toString());
                            editor.putString("perPersonVal", perPersonVal.getText().toString());
                            editor.commit();
                            break;

                        case R.id.customTip:
                            tip = Double.parseDouble(mealPrice.getText().toString()) *
                                    (Double.parseDouble(customTip.getText().toString()) / 100);
                            total = Double.parseDouble(mealPrice.getText().toString()) + tip;
                            perPerson = total / Integer.parseInt(numPeople.getText().toString());
                            tipVal.setText(String.format("$%.2f",tip));
                            totalVal.setText(String.format("$%.2f",total));
                            perPersonVal.setText(String.format("$%.2f", perPerson));

                            //Add new values to sharedPref
                            editor.putString("tipVal", tipVal.getText().toString());
                            editor.putString("totalVal", totalVal.getText().toString());
                            editor.putString("perPersonVal", perPersonVal.getText().toString());
                            editor.commit();
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
                editor.clear();
                editor.commit();
            }
        });



    }
    private View.OnKeyListener mKeyListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if(keyCode == KeyEvent.KEYCODE_ENTER){
                switch (v.getId()) {
                    case R.id.mealPriceInput:
                        if (!mealPrice.getText().toString().matches("") && Double.parseDouble(mealPrice.getText().toString()) < 1.00)
                            showErrorAlert("Meal cost must be $1.00 or greater", R.id.mealPriceInput);

                        break;
                    case R.id.numPeopleInput:
                        if(!numPeople.getText().toString().matches("") && Integer.parseInt(numPeople.getText().toString()) < 1)
                            showErrorAlert("Number of people must be greater than zero", R.id.numPeopleInput);

                        break;
                    case R.id.customTipInput:
                        if (!customTip.getText().toString().matches("") && Integer.parseInt(customTip.getText().toString()) < 1)
                            showErrorAlert("Custom tip must be 1% or greater", R.id.customTipInput);

                }

            }

            //Constantly stores current input values in sharedPref
            editor.putString("mealPrice", mealPrice.getText().toString());
            editor.putString("numPeople", numPeople.getText().toString());
            editor.putString("customTip", customTip.getText().toString());
            editor.commit();
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
