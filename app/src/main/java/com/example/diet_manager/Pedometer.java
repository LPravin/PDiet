package com.example.diet_manager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class Pedometer extends AppCompatActivity implements SensorEventListener, View.OnClickListener {
    private TextView tv;
    private SensorManager sensorManager;
    private Sensor sensor;
    private boolean isCounterPresent;
    private int stepcount;
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedometer);
        tv=findViewById(R.id.tv);
        back=findViewById(R.id.back);
        back.setOnClickListener(this);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)!=null){
            sensor=sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            isCounterPresent=true;
        } else{
            tv.setText("Counter sensor is not present");
            isCounterPresent=false;
        }

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor==sensor){
            stepcount = (int) event.values[0];
            tv.setText(stepcount);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    @Override
    protected void onResume() {
        super.onResume();
        if(isCounterPresent){
            sensorManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(isCounterPresent){
            sensorManager.unregisterListener(this,sensor);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                startActivity(new Intent(Pedometer.this,Home.class));
                break;
        }
    }
}