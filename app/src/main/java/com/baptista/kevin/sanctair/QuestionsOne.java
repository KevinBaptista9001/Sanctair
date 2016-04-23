package com.baptista.kevin.sanctair;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class QuestionsOne extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions_one);
        CheckBox dustCheck, pollenCheck, smokeCheck, formCheck, danderCheck, moldCheck;

        dustCheck = (CheckBox)findViewById(R.id.dustcheck);
        dustCheck.setChecked(getFromSP("dustCheck"));
        dustCheck.setOnCheckedChangeListener(this);

        pollenCheck = (CheckBox)findViewById(R.id.pollencheck);
        pollenCheck.setChecked(getFromSP("pollenCheck"));
        pollenCheck.setOnCheckedChangeListener(this);

        smokeCheck = (CheckBox)findViewById(R.id.smokecheck);
        smokeCheck.setChecked(getFromSP("smokeCheck"));
        smokeCheck.setOnCheckedChangeListener(this);

        formCheck = (CheckBox)findViewById(R.id.formaldehydecheck);
        formCheck.setChecked(getFromSP("formCheck"));
        formCheck.setOnCheckedChangeListener(this);

        danderCheck = (CheckBox)findViewById(R.id.dandercheck);
        danderCheck.setChecked(getFromSP("danderCheck"));
        danderCheck.setOnCheckedChangeListener(this);

        moldCheck = (CheckBox)findViewById(R.id.moldcheck);
        moldCheck.setChecked(getFromSP("moldCheck"));
        moldCheck.setOnCheckedChangeListener(this);


        Button secondNext = (Button) findViewById(R.id.secondnext);
        secondNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuestionsOne.this, QuestionsTwo.class);
                startActivity(intent);
            }
        });
    }

    private boolean getFromSP(String key){
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("USER_HEALTH", android.content.Context.MODE_PRIVATE);
        return preferences.getBoolean(key, false);
    }
    private void saveInSp(String key,boolean value) {
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("USER_HEALTH", android.content.Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        switch (buttonView.getId()){
            case R.id.dustcheck:
                saveInSp("dustCheck",isChecked);
                break;
            case R.id.pollencheck:
                saveInSp("pollenCheck", isChecked);
                break;
            case R.id.smokecheck:
                saveInSp("smokeCheck", isChecked);
                break;
            case R.id.formaldehydecheck:
                saveInSp("formCheck", isChecked);
                break;
            case R.id.dandercheck:
                saveInSp("danderCheck", isChecked);
                break;
            case R.id.moldcheck:
                saveInSp("moldCheck", isChecked);
                break;
        }

    }
}
