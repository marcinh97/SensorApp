package com.example.marcin.somethingapp;

import android.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class AnswerFragment extends Fragment {

    private View view;
    private TextView textView;

    private static String QUESTION_PREFIX = "question";
    private static String ANSWER_PREFIX = "_answer";

    public boolean isClicked;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        inflateLayout(inflater, container);

        initViews();

        Bundle bundle = getArguments();
        if (bundle != null) {
            initAllAnswers(bundle);
        }

        return view;
    }

    private void initAllAnswers(Bundle bundle) {
        String numberString = Integer.toString(bundle.getInt(MainActivity.QUESTION_NUMBER));
        String id = QUESTION_PREFIX+numberString;
        String answerNumber = Integer.toString(bundle.getInt(MainActivity.ANSWER_NUMBER));
        id += ANSWER_PREFIX+answerNumber;
        String question = getString(getResources().getIdentifier(id, "string", getActivity().getPackageName()));
        final boolean isCorrect = bundle.getBoolean(MainActivity.IS_CORRECT);
        textView.setText(question);


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int colorId = isCorrect ? Color.GREEN : Color.RED;
                String text = isCorrect ? getString(R.string.correct) : getString(R.string.incorrect);
                Toast.makeText(getActivity().getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                textView.setBackground(new ColorDrawable(colorId));
                isClicked = true;
            }
        });
    }

    private void initViews() {
        textView = view.findViewById(R.id.answer_tv);
        textView.setText(R.string.answer_text);
    }

    private void inflateLayout(LayoutInflater inflater, ViewGroup container) {
        if (view == null){
            view = inflater.inflate(R.layout.answers, container, false);
        }
        else{
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


}
