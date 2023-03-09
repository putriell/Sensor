package com.example.sensor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;

    private Sensor mSensorProximity;
    private Sensor mSensorLight;
    private Sensor mSensorTemperatur;
    private Sensor mSensorPressure;
    private Sensor mSensorMagnetic;
    private Sensor mSensorHumidity;

    private TextView mTextSensorLight;
    private TextView mTextSensorProximity;
    private TextView mTextSensorTemperatur;
    private TextView mTextSensorPressure;
    private TextView mTextSensorMagnetic;
    private TextView mTextSensorHumidity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        //variabel list untuk nampung sensornya
        List<Sensor> sensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        //karena belum tau sensor nya apa saja sehigga dipindah ke string
        StringBuilder sensorText = new StringBuilder();

        for (Sensor currentSensor : sensorList) {
            sensorText.append(currentSensor.getName()).append(System.getProperty("line.separator"));
        }

        TextView sensorTextView = findViewById(R.id.sensor_list);
        sensorTextView.setText(sensorText);

        mTextSensorLight = findViewById(R.id.label_light);
        mTextSensorProximity = findViewById(R.id.label_proximity);
        mTextSensorTemperatur = findViewById(R.id.label_temp);
        mTextSensorPressure = findViewById(R.id.label_pressure);
        mTextSensorMagnetic = findViewById(R.id.label_magnetic);
        mTextSensorHumidity = findViewById(R.id.label_humidity);

        mSensorProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        mSensorLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mSensorTemperatur = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        mSensorPressure = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        mSensorMagnetic = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mSensorHumidity = mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);

        String sensor_error = "No sensor";
        if (mSensorLight == null){
            mTextSensorLight.setText(sensor_error);
        }
        if (mSensorProximity == null) {
            mTextSensorProximity.setText(sensor_error);
        }
        if (mSensorTemperatur == null) {
            mTextSensorTemperatur.setText(sensor_error);
        }
        if (mSensorPressure == null) {
            mTextSensorPressure.setText(sensor_error);
        }
        if (mSensorMagnetic == null) {
            mTextSensorMagnetic.setText(sensor_error);
        }
        if (mSensorHumidity == null) {
            mTextSensorHumidity.setText(sensor_error);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mSensorProximity != null){
            mSensorManager.registerListener(this, mSensorProximity,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (mSensorLight != null){
            mSensorManager.registerListener(this, mSensorLight,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (mSensorTemperatur != null) {
            mSensorManager.registerListener(this, mSensorTemperatur,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (mSensorPressure != null) {
            mSensorManager.registerListener(this, mSensorPressure,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (mSensorMagnetic != null) {
            mSensorManager.registerListener(this, mSensorMagnetic,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (mSensorHumidity != null) {
            mSensorManager.registerListener(this, mSensorHumidity,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        //register menggunakan sensor manager
    }

    @Override
    protected void onStop() {
        super.onStop();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        //jika ada perubahan data maka akan diberitau lewat proximity
        //mengambil data dari sensor event
        //dapetim dl sensor typenya apa trus value nya
        int sensorType = sensorEvent.sensor.getType();
        float currentValue = sensorEvent.values[0];
        switch (sensorType) {
            case Sensor.TYPE_LIGHT:
                mTextSensorLight.setText(
                        String.format("light sensor: %1$.2f", currentValue));
                        changeBackgroundColor( currentValue);
                break;
            case Sensor.TYPE_PROXIMITY:
                mTextSensorProximity.setText(
                        String.format("Proximity sensor : %1$.2f", currentValue)
                );
                break;
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                mTextSensorTemperatur.setText(
                        String.format("Temperatur sensor:", currentValue)
                );
                break;
            case Sensor.TYPE_PRESSURE:
                mTextSensorPressure.setText(
                        String.format("Presure sensor:", currentValue)
                );
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                mTextSensorMagnetic.setText(
                        String.format("Magnetic sensor:", currentValue)
                );
                break;

            case Sensor.TYPE_RELATIVE_HUMIDITY:
                mTextSensorHumidity.setText(
                        String.format("Humadity sensor:", currentValue)
                );
                break;
            default:
        }
    }



    private void changeBackgroundColor (float currentValue) {
        ConstraintLayout layout = findViewById(R.id.layout_constraint);
        if (currentValue >= 100 && currentValue <= 40000) {
            layout.setBackgroundColor(Color.RED);
        } else if(currentValue >= 0 && currentValue < 100) {
            layout.setBackgroundColor(Color.BLUE);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}