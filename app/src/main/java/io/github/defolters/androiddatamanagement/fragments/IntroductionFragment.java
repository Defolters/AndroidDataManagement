package io.github.defolters.androiddatamanagement.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.github.defolters.androiddatamanagement.R;
import ru.noties.markwon.Markwon;


/**
 * A simple {@link Fragment} subclass.
 */
public class IntroductionFragment extends Fragment {


    public IntroductionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_introduction, container, false);
    }

}
