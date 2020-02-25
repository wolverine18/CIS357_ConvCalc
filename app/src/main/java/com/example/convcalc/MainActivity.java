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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private boolean isLength = true;
    private UnitsConverter.LengthUnits toLength = UnitsConverter.LengthUnits.Meters;
    private UnitsConverter.LengthUnits fromLength = UnitsConverter.LengthUnits.Yards;
    private UnitsConverter.VolumeUnits toVolume = UnitsConverter.VolumeUnits.Gallons;
    private UnitsConverter.VolumeUnits fromVolume = UnitsConverter.VolumeUnits.Liters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnCalc = findViewById(R.id.btnCalc);
        Button btnClear = findViewById(R.id.btnClear);
        Button btnMode = findViewById(R.id.btnMode);
        EditText etFrom = findViewById(R.id.etFrom);
        EditText etTo = findViewById(R.id.etTo);
        TextView lblConvert = findViewById(R.id.lblConverter);
        TextView lblFromUnit = findViewById(R.id.tvFrom);
        TextView lblToUnit = findViewById(R.id.tvTo);

        btnCalc.setOnClickListener(v -> {
            hideKeybaord(v);
            String fromString = etFrom.getText().toString();
            String toString = etTo.getText().toString();
            double fromVal = 0;
            double toVal = 0;

            if(!fromString.equals("")){
                fromVal = Double.parseDouble(fromString);
                toVal = UnitsConverter.convert(fromVal, fromLength, toLength);
            }
            else if(!toString.equals((""))){
                toVal = Double.parseDouble(toString);
                fromVal = UnitsConverter.convert(toVal, fromVolume, toVolume);
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
        });
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

            Intent intent = new Intent(MainActivity.this, Settings.class);
            intent.putExtra("isLength", isLength);
            startActivity(intent);
            return true;
        }
        return false;
    }
}
