package com.app.virgo.imc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    float imc = 0.0f;

    Button compute = null;
    Button reset = null;

    EditText weight = null;
    EditText tall = null;

    RadioGroup unit = null;
    RadioGroup sex = null;

    TextView resultEl = null;

    CheckBox mega = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.compute = findViewById(R.id.computeIMC);
        this.reset = findViewById(R.id.reset);
        this.weight = findViewById(R.id.poids);
        this.tall = findViewById(R.id.taille);
        this.unit = findViewById(R.id.radioGroup);
        //this.sex = findViewById(R.id.sex);
        this.resultEl = findViewById(R.id.resultEl);
        this.mega = findViewById(R.id.megaFunction);

        this.compute.setOnClickListener(computeListener);
        this.reset.setOnClickListener(resetListener);
        this.weight.addTextChangedListener(textWatcher);
        this.tall.addTextChangedListener(textWatcher);
        this.mega.setOnClickListener(megaListener);
    };

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            resultEl.setText("");
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private void computeImc(){
        imc = 0.0f; // imc = weight/(tall*tall) ; kg/(m*m)
        String t = tall.getText().toString();
        String w = weight.getText().toString();
        if(t != null && !t.isEmpty() && w != null && !w.isEmpty()) {

            float tval = Float.valueOf(t);
            if (unit.getCheckedRadioButtonId() == R.id.cm) {
                tval /= 100;
            }

            float wval = Float.valueOf(w);

            if (tval > 1 && wval > 25) { // works if tall > 1 m and weight > 25 kg
                imc = wval / (tval * tval);
            }
        }
    }

    private void displayImc(){
        if (imc > 1) {
            if (mega.isChecked()) {
                /*
                if (sex.getCheckedRadioButtonId() == R.id.male) {
                    result.setText(R.string.niceBoy);
                } else {
                    result.setText(R.string.niceGirl);
                }
                */
                if (imc < 16){
                    resultEl.setText("Anorexie ou dénutrition");
                } else if (imc <= 18.5){
                    resultEl.setText("Maigreur");
                } else if (imc <= 25){
                    resultEl.setText("Corpulence normale");
                } else if (imc <= 30){
                    resultEl.setText("Surpoids");
                } else if (imc <= 35){
                    resultEl.setText("Obésité modérée (Classe 1)");
                } else if (imc <= 40){
                    resultEl.setText("Obésité élevée (Classe 2)");
                } else {
                    resultEl.setText("Obésite morbide ou massive");
                }
            } else {
                resultEl.setText(String.valueOf(imc));
            }
        } else {
            resultEl.setText("Veuillez verifier les valeurs.");
        }
    }

    private View.OnClickListener computeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            computeImc();
            displayImc();
        }
    };

    private View.OnClickListener megaListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            displayImc();
        }
    };

    private View.OnClickListener resetListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            weight.setText("");
            tall.setText("");
            resultEl.setText("");
            weight.requestFocus();
        }
    };
}
