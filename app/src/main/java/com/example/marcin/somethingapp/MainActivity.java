package com.example.marcin.somethingapp;

import android.app.Fragment;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    boolean flipped = false;
    private ImageView imageView;
    private Fragment fragment;

    private SensorManager manager;
    private Sensor sensor;

    private boolean isFlipped = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragment = new QuestionFragment();


        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .replace(R.id.answer1_fragment, new AnswerFragment())
                .replace(R.id.answer2_fragment, new AnswerFragment())
                .commit();

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flipCard();
            }
        });

        manager = (SensorManager)getSystemService(SENSOR_SERVICE);
        sensor = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);



//        if (savedInstanceState == null) {
//            getFragmentManager()
//                    .beginTransaction()
//                    .add(R.id.container, new QuestionFragment())
//                    .commit();
//        }


    }

    private void flipCard() {
//        if (mShowingBack) {
//            getFragmentManager().popBackStack();
//            return;
//        }
        // Flip to the back.

       // mShowingBack = true;
        getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        R.animator.right_in, R.animator.right_out,
                        R.animator.left_in, R.animator.left_out)
                .replace(R.id.container, new QuestionFragment())
                .replace(R.id.answer1_fragment, new AnswerFragment())
                .replace(R.id.answer2_fragment, new AnswerFragment())
                // Add this transaction to the back stack, allowing users to press Back
                // to get to the front of the card.
                .addToBackStack(null)
                // Commit the transaction.
                .commit();

    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float y = sensorEvent.values[1];
        if (!isFlipped && y < -2.5f){
            flipCard();
            isFlipped = true;
        }
        else if (isFlipped && y>3){
            isFlipped = false;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        manager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //unregister Sensor listener
        manager.unregisterListener(this);
    }
}
