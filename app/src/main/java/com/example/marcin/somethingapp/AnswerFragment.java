package com.example.marcin.somethingapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by marcin on 25.04.2018.
 */

public class AnswerFragment extends Fragment {

    private View view;
    private TextView textView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        inflateLayout(inflater, container);
        textView = view.findViewById(R.id.answer_tv);
        textView.setText("Odpowiedz");
        return view;
    }

    private void inflateLayout(LayoutInflater inflater, ViewGroup container) {
        if (view == null){
            view = inflater.inflate(R.layout.answers, container, false);
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
