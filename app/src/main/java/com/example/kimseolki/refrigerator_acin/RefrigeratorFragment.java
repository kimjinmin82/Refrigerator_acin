package com.example.kimseolki.refrigerator_acin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by kimseolki on 2017-05-02.
 */

public class RefrigeratorFragment extends Fragment {

    public RefrigeratorFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.activity_refrigerator,container,false);

        return rootview;
    }
}
