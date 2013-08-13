package net.eminor.accelerometertester;

import android.content.Context;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements SensorEventListener, View.OnClickListener {

    int accelerometerAccuracy = 0;
    private SensorManager mSensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(
                this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);

        Button resetButton = (Button)findViewById(R.id.buttonReset);
        resetButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        // TODO
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        accelerometerAccuracy = accuracy;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float x = sensorEvent.values[0];
        float y = sensorEvent.values[1];
        float z = sensorEvent.values[2];

        TextView textViewX = (TextView)findViewById(R.id.textViewX);
        textViewX.setText(String.format("%f", x));
        TextView textViewY = (TextView)findViewById(R.id.textViewY);
        textViewY.setText(String.format("%f", y));
        TextView textViewZ = (TextView)findViewById(R.id.textViewZ);
        textViewZ.setText(String.format("%f", z));

        //mAccelLast = mAccelCurrent;
        //mAccelCurrent = (float) Math.sqrt((double) (x*x + y*y + z*z));
        //float delta = mAccelCurrent - mAccelLast;
        //mAccel = mAccel * 0.9f + delta; // perform low-cut filter
    }

    @Override
    public void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }
    
}
