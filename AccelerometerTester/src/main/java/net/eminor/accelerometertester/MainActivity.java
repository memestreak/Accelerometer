// memetron was here.

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

    private int accelerometerAccuracy = 0;
    private SensorManager mSensorManager;
    private float maxX = 0.0f;
    private float maxY = 0.0f;
    private float maxZ = 0.0f;
    private double vector = 0.0;
    private double maxVector = 0.0;
    private double minVector = 999.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        Button resetButton = (Button)findViewById(R.id.buttonReset);
        resetButton.setOnClickListener(this);

    }

    @Override
    public void onResume() {
        super.onResume();
        mSensorManager.registerListener(
                this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onClick(View view) {
        maxX = 0;
        maxY = 0;
        maxZ = 0;
        vector = 0;
        maxVector = 0;
        minVector = 999;
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
        double vector = Math.sqrt(x*x + y*y + z*z);

        if (x > maxX) {
            maxX = x;
        }
        if (y > maxY) {
            maxY = y;
        }
        if (z > maxZ) {
            maxZ = z;
        }
        if (vector > maxVector) {
            maxVector = vector;
        }
        if (vector < minVector) {
            minVector = vector;
        }

        TextView textViewX = (TextView)findViewById(R.id.textViewX);
        textViewX.setText(String.format("%f", x));
        TextView textViewY = (TextView)findViewById(R.id.textViewY);
        textViewY.setText(String.format("%f", y));
        TextView textViewZ = (TextView)findViewById(R.id.textViewZ);
        textViewZ.setText(String.format("%f", z));
        TextView textViewVector = (TextView)findViewById(R.id.textViewVector);
        textViewVector.setText(String.format("%f", (float)vector));

        TextView textViewMaxX = (TextView)findViewById(R.id.textViewMaxX);
        textViewMaxX.setText(String.format("%f", maxX));
        TextView textViewMaxY = (TextView)findViewById(R.id.textViewMaxY);
        textViewMaxY.setText(String.format("%f", maxY));
        TextView textViewMaxZ = (TextView)findViewById(R.id.textViewMaxZ);
        textViewMaxZ.setText(String.format("%f", maxZ));

        TextView textViewMaxVector = (TextView)findViewById(R.id.textViewVectorMax);
        textViewMaxVector.setText(String.format("%f", (float)maxVector));
        TextView textViewMinVector = (TextView)findViewById(R.id.textViewVectorMin);
        textViewMinVector.setText(String.format("%f", (float)minVector));

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
