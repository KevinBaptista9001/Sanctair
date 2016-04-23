package com.baptista.kevin.sanctair;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class QuestionsTwo extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions_two);
        Button thirdNext = (Button)findViewById(R.id.thirdnext);
        Button firstPrev = (Button)findViewById(R.id.firstprev);
        CheckBox asthmaCheck, copdCheck, pneumonitisCheck, cardiovascularCheck, anemiaCheck;

        thirdNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuestionsTwo.this,EndQuestions.class);
                startActivity(intent);
            }
        });

        firstPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuestionsTwo.this,QuestionsOne.class);
                startActivity(intent);
            }
        });

        asthmaCheck = (CheckBox)findViewById(R.id.asthmaCheck);
        asthmaCheck.setChecked(getFromSP("asthma"));
        asthmaCheck.setOnCheckedChangeListener(this);

        copdCheck = (CheckBox)findViewById(R.id.copd);
        copdCheck.setChecked(getFromSP("copd"));
        copdCheck.setOnCheckedChangeListener(this);

        pneumonitisCheck = (CheckBox)findViewById(R.id.pneumonitis);
        pneumonitisCheck.setChecked(getFromSP("pneumonitis"));
        pneumonitisCheck.setOnCheckedChangeListener(this);

        cardiovascularCheck = (CheckBox)findViewById(R.id.cardiovascular);
        cardiovascularCheck.setChecked(getFromSP("cardiovascular"));

        anemiaCheck = (CheckBox)findViewById(R.id.anemiaCheck);
        anemiaCheck.setChecked(getFromSP("anemia"));
        anemiaCheck.setOnCheckedChangeListener(this);
        
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
            case R.id.asthmaCheck:{
                saveInSp("asthma",isChecked);
                break;
            }
            case R.id.copd:{
                saveInSp("copd",isChecked);
                break;
            }
            case R.id.pneumonitis:{
                saveInSp("pneumonitis",isChecked);
                break;
            }
            case R.id.cardiovascular:{
                saveInSp("cardiovascular",isChecked);
                break;
            }
            case R.id.anemiaCheck:{
                saveInSp("anemia",isChecked);
                break;
            }

        }

    }

}
