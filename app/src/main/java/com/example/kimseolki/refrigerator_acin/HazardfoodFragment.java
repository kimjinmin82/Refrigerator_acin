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

public class HazardfoodFragment extends Fragment {

    public HazardfoodFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.activity_hazardfood_main,container,false);

        return rootview;
    }
}
