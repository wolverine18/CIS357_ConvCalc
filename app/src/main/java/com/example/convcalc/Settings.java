package com.example.convcalc;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        boolean isLength = true;
        String toUnit = "Yards";
        String fromUnit = "Meters";

        Intent payload = getIntent();
        if (payload.hasExtra("isLength")) {
            isLength = payload.getBooleanExtra("isLength", true);
            toUnit = payload.getStringExtra("toUnit");
            fromUnit = payload.getStringExtra("fromUnit");
        }

        Spinner spinnerTo = findViewById(R.id.spinnerTo);
        Spinner spinnerFrom = findViewById(R.id.spinnerFrom);

        ArrayAdapter<CharSequence> adapterLength = ArrayAdapter.createFromResource(this, R.array.lengthUnits, android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> adapterVolume = ArrayAdapter.createFromResource(this, R.array.volumeUnits, android.R.layout.simple_spinner_dropdown_item);

        if(isLength){
            spinnerTo.setAdapter(adapterLength);
            spinnerFrom.setAdapter(adapterLength);
            int toPos = adapterLength.getPosition(toUnit);
            int fromPos = adapterLength.getPosition(fromUnit);

            spinnerTo.setSelection(toPos);
            spinnerFrom.setSelection(fromPos);
        }
        else {
            spinnerTo.setAdapter(adapterVolume);
            spinnerFrom.setAdapter(adapterVolume);
            int toPos = adapterVolume.getPosition(toUnit);
            int fromPos = adapterVolume.getPosition(fromUnit);

            spinnerTo.setSelection(toPos);
            spinnerFrom.setSelection(fromPos);
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra("toUnit", spinnerTo.getSelectedItem().toString());
            intent.putExtra("fromUnit", spinnerFrom.getSelectedItem().toString());
            setResult(RESULT_OK, intent);
            finish();
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
