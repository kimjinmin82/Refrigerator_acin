package com.example.kimseolki.refrigerator_acin;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.kimseolki.refrigerator_acin.model.Food;
import com.example.kimseolki.refrigerator_acin.service.GetFoods;

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

public class ExpriationFragment extends Fragment {

    public static final String ENDPOINT_URLF = "http://172.30.46.206:8000/";
    private GetFoods getFoods;
    ListView lvFoods;
    ArrayList<String> food_list;
    ArrayAdapter<String> food_adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.activity_expriation,container,false);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(ENDPOINT_URLF)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        getFoods = retrofit.create(GetFoods.class);
        lvFoods = (ListView) rootview.findViewById(R.id.lvExpriation);
        food_list = new ArrayList<String>();
        food_adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, food_list);
        lvFoods.setAdapter(food_adapter);
        loadTodos();

        return rootview;
    }

    private void loadTodos() {
        Call<ArrayList<Food>> call = getFoods.all();
        call.enqueue(new Callback<ArrayList<Food>>() {
            @Override
            public void onResponse(Call<ArrayList<Food>> call, Response<ArrayList<Food>> response) {
                List<Food> foods = response.body();
                displayResult(foods);
            }

            @Override
            public void onFailure(Call<ArrayList<Food>> call, Throwable t) {

            }
        });
    }

    private void displayResult(List<Food> foods) {
        if (foods != null) {
            food_list.clear();
            for(int i=0; i<foods.size(); i++) {



                    String name = foods.get(i).getFood_number() + " | " + foods.get(i).getFood_name() + "\n";
                    food_list.add(name);
                food_adapter.notifyDataSetChanged();
            }
        } else {

        }
    }
}
