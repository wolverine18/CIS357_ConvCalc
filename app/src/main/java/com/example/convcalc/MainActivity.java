package com.example.convcalc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private boolean isLength = true;
    private UnitsConverter.LengthUnits toLength = UnitsConverter.LengthUnits.Meters;
    private UnitsConverter.LengthUnits fromLength = UnitsConverter.LengthUnits.Yards;
    private UnitsConverter.VolumeUnits toVolume = UnitsConverter.VolumeUnits.Gallons;
    private UnitsConverter.VolumeUnits fromVolume = UnitsConverter.VolumeUnits.Liters;
    private TextView lblFromUnit;
    private TextView lblToUnit;
    private EditText etFrom;
    private EditText etTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnCalc = findViewById(R.id.btnCalc);
        Button btnClear = findViewById(R.id.btnClear);
        Button btnMode = findViewById(R.id.btnMode);
        TextView lblConvert = findViewById(R.id.lblConverter);
        etFrom = findViewById(R.id.etFrom);
        etTo = findViewById(R.id.etTo);
        lblFromUnit = findViewById(R.id.tvFrom);
        lblToUnit = findViewById(R.id.tvTo);

        btnCalc.setOnClickListener(v -> {
            hideKeybaord(v);
            String fromString = etFrom.getText().toString();
            String toString = etTo.getText().toString();
            double fromVal = 0;
            double toVal = 0;

            if(!fromString.equals("")){
                fromVal = Double.parseDouble(fromString);
                toVal = convert(fromVal);
            }
            else if(!toString.equals((""))){
                toVal = Double.parseDouble(toString);
                fromVal = convert(toVal);
            }

            if(fromVal != 0 && toVal != 0){
                etFrom.setText(Double.toString(fromVal));
                etTo.setText(Double.toString(toVal));
            }
        });

        btnClear.setOnClickListener(v -> {
            hideKeybaord(v);
            clearTextFields();
        });

        btnMode.setOnClickListener(v -> {
            hideKeybaord(v);

            String convertText = isLength? "Volume Converter": "Length Converter";
            isLength = !isLength;
            if(isLength){
                lblFromUnit.setText(fromLength.name());
                lblToUnit.setText(toLength.name());
            } else {
                lblFromUnit.setText(fromVolume.name());
                lblToUnit.setText(toVolume.name());
            }

            lblConvert.setText(convertText);
            clearTextFields();
        });

        etFrom.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    etTo.setText("");
                }
            }
        });

        etTo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    etFrom.setText("");
                }
            }
        });
    }

    private void clearTextFields() {
        etFrom.setText("");
        etTo.setText("");
    }

    private void hideKeybaord(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menuSettings){
            String toUnit;
            String fromUnit;

            if(isLength) {
                toUnit = toLength.name();
                fromUnit = fromLength.name();
            }
            else {
                toUnit = toVolume.name();
                fromUnit = fromVolume.name();
            }

            Intent intent = new Intent(MainActivity.this, Settings.class);
            intent.putExtra("isLength", isLength);
            intent.putExtra("toUnit", toUnit);
            intent.putExtra("fromUnit", fromUnit);
            startActivityForResult(intent, 1);
            return true;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1) {
            if(resultCode == RESULT_OK) {
                String toUnit = data.getStringExtra("toUnit");
                String fromUnit = data.getStringExtra("fromUnit");

                if(isLength){
                    fromLength = UnitsConverter.LengthUnits.valueOf(fromUnit);
                    toLength = UnitsConverter.LengthUnits.valueOf(toUnit);
                } else {
                    fromVolume = UnitsConverter.VolumeUnits.valueOf(fromUnit);
                    toVolume = UnitsConverter.VolumeUnits.valueOf(toUnit);
                }
                lblFromUnit.setText(fromUnit);
                lblToUnit.setText(toUnit);
            }
        }
        clearTextFields();
    }

    private double convert(double fromVal) {
        double toVal;
        if(isLength) {
            toVal = UnitsConverter.convert(fromVal, fromLength, toLength);
        }
        else {
            toVal = UnitsConverter.convert(fromVal, fromVolume, toVolume);
        }
        return toVal;
    }
}
