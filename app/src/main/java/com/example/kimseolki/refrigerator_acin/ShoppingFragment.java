package com.example.kimseolki.refrigerator_acin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.kimseolki.refrigerator_acin.model.Shopping;
import com.example.kimseolki.refrigerator_acin.service.GetShopping;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kimseolki on 2017-05-02.
 */

public class ShoppingFragment extends Fragment {
    private static final String TAG = "ShoppingFragment";

    public static final String ENDPOINT_URLF = "http://172.30.46.206:8000/";
    private GetShopping getShopping;
    ListView lvShopping;
    ArrayList<String> shopping_list;
    ArrayAdapter<String> shopping_adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.activity_shopping,container,false);

        FloatingActionButton fab1 = (FloatingActionButton) rootview.findViewById(R.id.fab);
        Log.d("아","아");
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), ShoppingRegister.class);
                startActivity(in);
            }
        });

        Log.d("젠장","젠장");

        Retrofit retrofit = new Retrofit.Builder().baseUrl(ENDPOINT_URLF)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        getShopping = retrofit.create(GetShopping.class);
        lvShopping = (ListView) rootview.findViewById(R.id.lvShowpingList);
        shopping_list = new ArrayList<String>();
        shopping_adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, shopping_list);
        lvShopping.setAdapter(shopping_adapter);
        loadTodos();

        return rootview;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG, "onDetach");
    }

    private void loadTodos() {
        Call<List<Shopping>> call = getShopping.all();
        call.enqueue(new Callback<List<Shopping>>() {
            @Override
            public void onResponse(Call<List<Shopping>> call, Response<List<Shopping>> response) {
                List<Shopping> shopping = response.body();
                displayResult(shopping);
            }
            @Override
            public void onFailure(Call<List<Shopping>> call, Throwable t) {
            }
        });
    }

    private void displayResult(List<Shopping> shopping) {
        if (shopping != null) {
            shopping_list.clear();
            for(int i=0; i<shopping.size(); i++) {
                    String name = shopping.get(i).getShoppingName() + " | " + shopping.get(i).getShoppingMemo() + "\n";
                shopping_list.add(name);
                shopping_adapter.notifyDataSetChanged();
            }
        } else {

        }
    }

}
