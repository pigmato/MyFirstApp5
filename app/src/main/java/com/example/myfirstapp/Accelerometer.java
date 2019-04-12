package com.example.myfirstapp;


import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;

public class Accelerometer extends AppCompatActivity implements SensorEventListener {
    private SensorManager sm;
    private Sensor Sensor;
    private TextView value;
    private TextView position;
    private long update;
    private float X;
    private float Y;
    private float Z;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        value = (TextView) findViewById(R.id.accValuesView);
        position = (TextView) findViewById(R.id.accPosView);

        sm.registerListener(this, Sensor, SensorManager.SENSOR_DELAY_NORMAL);
        update = System.currentTimeMillis();
        setContentView(R.layout.activity_accelerometer);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            long time = System.currentTimeMillis();

            if ((time - update) > 200) {
                update = time;
                X = event.values[0];
                Y = event.values[1];
                Z = event.values[2];
                updateTextView(X, Y, Z);
                updatePosView(X, Y, Z);
            }
        }
    }


    public void updatePosView(float xValue, float yValue, float zValue){
        if (zValue > 0 && zValue < 9.8){
            if (xValue < -1) {
                position.setText("Right");
            } else if (xValue > 1){
                position.setText("Left");
            } else if (yValue > 1){
                position.setText("Up");
            } else if (yValue < -1){
                position.setText("Down");
            } else {
                position.setText("");
            }
        } else if (zValue < 0 && zValue > -9.8){
            if (xValue < -1) {
                position.setText("Left");
            } else if (xValue > 1){
                position.setText("Right");
            } else if (yValue > 1){
                position.setText("Down");
            } else if (yValue < -1){
                position.setText("Up");
            } else {
                position.setText("");
            }
        } else {
            position.setText("");
        }
    }

    public void updateTextView(float x, float y, float z){
        String xText = "X:    " + x;
        String yText = "Y:    " + y;
        String zText = "Z:    " + z;
        value.setText(xText + "\n" + yText + "\n" + zText);
    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}