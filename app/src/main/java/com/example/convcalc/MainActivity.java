package com.example.convcalc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnCalc = findViewById(R.id.btnCalc);
        Button btnClear = findViewById(R.id.btnClear);
        Button btnMode = findViewById(R.id.btnMode);
        EditText etFrom = findViewById(R.id.etFrom);
        EditText etTo = findViewById(R.id.etTo);

        btnCalc.setOnClickListener(v -> {
            hideKeybaord(v);
            String fromString = etFrom.getText().toString();
            String toString = etTo.getText().toString();
            double fromVal = 0;
            double toVal = 0;

            if(!fromString.equals("")){
                fromVal = Double.parseDouble(fromString);
                toVal = UnitsConverter.convert(fromVal, UnitsConverter.LengthUnits.Yards, UnitsConverter.LengthUnits.Meters);
            }
            else if(!toString.equals((""))){
                toVal = Double.parseDouble(toString);
                fromVal = UnitsConverter.convert(toVal, UnitsConverter.LengthUnits.Meters, UnitsConverter.LengthUnits.Yards);
            }

            if(fromVal != 0 && toVal != 0){
                etFrom.setText(Double.toString(fromVal));
                etTo.setText(Double.toString(toVal));
            }
        });

        btnClear.setOnClickListener(v -> {
            hideKeybaord(v);
            etFrom.setText("");
            etTo.setText("");
        });

        btnMode.setOnClickListener(v -> {
            hideKeybaord(v);

        });
    }

    private void hideKeybaord(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);
    }
}
