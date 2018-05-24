package com.example.marcin.somethingapp;

import android.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    boolean isDark = false;
    private SensorManager manager;
    private Sensor sensor;

    private Sensor lightSensor;


    private boolean isFlipped = false;

    private static final int DAYLIGHT_ID = 0;
    private static final int NIGHTLIGHT_ID = 1;

    private static final int NUMBER_OF_QUESTIONS = 5;
    private int currentQuestion = 0;
    public static String QUESTION_NUMBER = "QUESTION_NUMBER";
    public static String ANSWER_NUMBER = "ANSWER_NUMBER";
    public static String IS_CORRECT = "IS_CORRECT";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        setTheme();

        initFragment();

        initSensors();
    }

    private void initSensors() {
        manager = (SensorManager)getSystemService(SENSOR_SERVICE);
        assert manager != null;
        sensor = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        lightSensor = manager.getDefaultSensor(Sensor.TYPE_LIGHT);
    }

    private void initFragment() {
        Fragment fragment = new QuestionFragment();
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .replace(R.id.answer1_fragment, new AnswerFragment())
                .replace(R.id.answer2_fragment, new AnswerFragment())
                .replace(R.id.answer3_fragment, new AnswerFragment())
                .replace(R.id.answer4_fragment, new AnswerFragment())
                .commit();
    }

    private void setTheme() {
        setTheme(R.style.AppTheme);
        (findViewById(R.id.mainLayout))
                .setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.seigaiha_repeating));
    }

    private void flipCard() {
        currentQuestion = ((currentQuestion+1)%NUMBER_OF_QUESTIONS);
        QuestionFragment questionFragment = new QuestionFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(QUESTION_NUMBER, currentQuestion);
        questionFragment.setArguments(bundle);
        AnswerFragment firstAnswer = new AnswerFragment();
        AnswerFragment secondAnswer = new AnswerFragment();
        AnswerFragment thirdAnswer = new AnswerFragment();
        AnswerFragment fourthAnswer = new AnswerFragment();

        setArguments(bundle, firstAnswer, secondAnswer, thirdAnswer, fourthAnswer);


        initFragmentOnFlipCard(questionFragment, firstAnswer, secondAnswer, thirdAnswer, fourthAnswer);
    }

    private void setArguments(Bundle bundle, AnswerFragment firstAnswer, AnswerFragment secondAnswer, AnswerFragment thirdAnswer, AnswerFragment fourthAnswer) {
        bundle.putInt(ANSWER_NUMBER, 1);
        bundle.putBoolean(IS_CORRECT, true);
        firstAnswer.setArguments(bundle);
        Bundle secondBundle = new Bundle();
        secondBundle.putInt(QUESTION_NUMBER, currentQuestion);
        secondBundle.putInt(ANSWER_NUMBER, 2);
        secondBundle.putBoolean(IS_CORRECT, false);
        secondAnswer.setArguments(secondBundle);
        Bundle thirdBundle = new Bundle();
        thirdBundle.putInt(QUESTION_NUMBER, currentQuestion);
        thirdBundle.putInt(ANSWER_NUMBER, 3);
        thirdBundle.putBoolean(IS_CORRECT, false);
        thirdAnswer.setArguments(thirdBundle);
        Bundle fourthBundle = new Bundle();
        fourthBundle.putInt(QUESTION_NUMBER, currentQuestion);
        fourthBundle.putInt(ANSWER_NUMBER, 4);
        fourthBundle.putBoolean(IS_CORRECT, false);
        fourthAnswer.setArguments(fourthBundle);
    }

    private void initFragmentOnFlipCard(QuestionFragment questionFragment, AnswerFragment firstAnswer, AnswerFragment secondAnswer, AnswerFragment thirdAnswer, AnswerFragment fourthAnswer) {
        getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        R.animator.right_in, R.animator.right_out,
                        R.animator.left_in, R.animator.left_out)
                .replace(R.id.container, questionFragment)
                .replace(R.id.answer1_fragment, firstAnswer)
                .replace(R.id.answer2_fragment, secondAnswer)
                .replace(R.id.answer3_fragment, thirdAnswer)
                .replace(R.id.answer4_fragment, fourthAnswer)
                .addToBackStack(null)
                .commit();
    }

    private void changeCardColor(){

        currentQuestion = ((currentQuestion)%NUMBER_OF_QUESTIONS);
        QuestionFragment questionFragment = new QuestionFragment();
        Bundle bundle = new Bundle();

        bundle.putInt(QUESTION_NUMBER, currentQuestion);
        questionFragment.setArguments(bundle);
        AnswerFragment firstAnswer = new AnswerFragment();
        AnswerFragment secondAnswer = new AnswerFragment();
        AnswerFragment thirdAnswer = new AnswerFragment();
        AnswerFragment fourthAnswer = new AnswerFragment();

        setArguments(bundle, firstAnswer, secondAnswer, thirdAnswer, fourthAnswer);


        initFragmentOnChangeColor(questionFragment, firstAnswer, secondAnswer, thirdAnswer, fourthAnswer);
    }

    private void initFragmentOnChangeColor(QuestionFragment questionFragment, AnswerFragment firstAnswer, AnswerFragment secondAnswer, AnswerFragment thirdAnswer, AnswerFragment fourthAnswer) {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, questionFragment)
                .replace(R.id.answer1_fragment, firstAnswer)
                .replace(R.id.answer2_fragment, secondAnswer)
                .replace(R.id.answer3_fragment, thirdAnswer)
                .replace(R.id.answer4_fragment, fourthAnswer)
                .addToBackStack(null)
                .commit();
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor currentSensor = sensorEvent.sensor;
        if (currentSensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float y = sensorEvent.values[1];
            if (!isFlipped && y < -2.5f) {
                flipCard();
                isFlipped = true;
            } else if (isFlipped && y > 3) {
                isFlipped = false;
            }
        }
        else if (currentSensor.getType() == Sensor.TYPE_LIGHT){
            float lightLevel = sensorEvent.values[0];
            if (!isDark && lightLevel < 1){
                setTheme(R.style.darkTheme);
                changeCardColor();
                isDark = true;
                changeColorsOfWidgetsDependingOnLight(NIGHTLIGHT_ID);

            }
            if (isDark && lightLevel > 1){
                setTheme(R.style.AppTheme);
                changeCardColor();
                isDark = false;
                changeColorsOfWidgetsDependingOnLight(DAYLIGHT_ID);
            }
        }
    }

    private void changeColorsOfWidgetsDependingOnLight(int id){
        if (id == NIGHTLIGHT_ID)
            (findViewById(R.id.mainLayout)).setBackground(new ColorDrawable(Color.BLACK));
        else
            (findViewById(R.id.mainLayout))
                    .setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.seigaiha_repeating));
        int textColor = id == NIGHTLIGHT_ID ? Color.WHITE : Color.BLACK;
        ((TextView)findViewById(R.id.textView)).setTextColor(textColor);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {}

    @Override
    protected void onResume() {
        super.onResume();
        manager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        manager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        manager.unregisterListener(this);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
