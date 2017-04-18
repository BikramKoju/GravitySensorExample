package com.example.bikramkoju.gravitysensorexample;

import android.graphics.Canvas;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    SensorManager sensorManager;
    Sensor acclerometer;

    AnimatedView animatedView;

    ShapeDrawable shapeDrawable=new ShapeDrawable();
    static int x,y;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sensorManager=(SensorManager)getSystemService(SENSOR_SERVICE);
        acclerometer=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        animatedView=new AnimatedView(this);
        setContentView(animatedView);


    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
            x=x-(int)event.values[0];
            y=y+(int)event.values[1];
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this,acclerometer,SensorManager.SENSOR_DELAY_GAME);
    }

    private class AnimatedView extends ImageView {
        int width =50;
        int height =50;
        public AnimatedView(MainActivity mainActivity) {
            super(mainActivity);
            shapeDrawable=new ShapeDrawable(new OvalShape());

            shapeDrawable.getPaint().setColor(getResources().getColor(R.color.colorAccent));
            shapeDrawable.setBounds(x, y, x + width, y + height);

        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            shapeDrawable.setBounds(x, y, x + width, y + height);
            shapeDrawable.draw(canvas);
            invalidate();
        }
    }
}
