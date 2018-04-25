package com.example.marcin.somethingapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by marcin on 24.04.2018.
 */

public class QuestionFragment extends Fragment {
    private static final int NUMBER_OF_WORDS = 8;
    private View view;
    private TextView textView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        inflateLayout(inflater, container);
        textView = view.findViewById(R.id.word_shown_tv);
        textView.setText("KaczkaBaczka");
        return view;
    }

    private void inflateLayout(LayoutInflater inflater, ViewGroup container) {
        if (view == null){
            view = inflater.inflate(R.layout.to_flip, container, false);
        }
        else{
            ViewGroup parent = (ViewGroup) view.getParent();
            parent.removeView(view);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
