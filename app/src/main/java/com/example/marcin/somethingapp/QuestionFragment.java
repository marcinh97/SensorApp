package com.example.marcin.somethingapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class QuestionFragment extends Fragment {
    private View view;
    private TextView textView;
    private static final String QUESTION_PREFIX = "question";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        inflateLayout(inflater, container);
        initViews();
          Bundle bundle = getArguments();
        if (bundle != null) {
            initQuestion(bundle);
        }
        return view;
    }

    private void initQuestion(Bundle bundle) {
        String numberString = Integer.toString(bundle.getInt(MainActivity.QUESTION_NUMBER));
        String id = QUESTION_PREFIX+numberString;
        String question = getString(getResources().getIdentifier(id, "string", getActivity().getPackageName()));
        textView.setText(question);
    }

    private void initViews() {
        textView = view.findViewById(R.id.word_shown_tv);
        textView.setText(R.string.first_answer_text);
    }

    private void inflateLayout(LayoutInflater inflater, ViewGroup container) {
        if (view == null){
            view = inflater.inflate(R.layout.to_flip, container, false);
        }
        else{
            ViewGroup parent = (ViewGroup) view.getParent();
            if(parent != null)
                parent.removeView(view);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
