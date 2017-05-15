package com.example.kimseolki.refrigerator_acin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.kimseolki.refrigerator_acin.model.Food;
import com.example.kimseolki.refrigerator_acin.model.Food_location;
import com.example.kimseolki.refrigerator_acin.model.Food_modify;
import com.example.kimseolki.refrigerator_acin.service.GetFoods;

import java.io.Serializable;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kimseolki on 2017-05-02.
 */

public class Tab2 extends Fragment {
    public static final String ENDPOINT_URLF = "http://192.168.63.110:8000/";
    private static final String TAG ="Tap1" ;
    private GetFoods getFoods;
    ListView lvFoods;
    private ArrayList<Food> food_list;
    private FoodListAdapter food_adapter;
    ArrayList<Integer> fId = new ArrayList<Integer>();      //삭제의 food_number를 구분하기위한 ArrayList
    ArrayList<Food_location> foods_location;
    ArrayList<Food_modify> foods_modify;
    ArrayList<Food> foods;


    @Override
    public void onStart() {
        loadFoods();
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.tab1_refrigeration,container,false);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(ENDPOINT_URLF)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        getFoods = retrofit.create(GetFoods.class);
        lvFoods = (ListView) rootview.findViewById(R.id.lvFood1);
        food_list = new ArrayList<Food>();

        registerForContextMenu(lvFoods);
        loadFoods();

        return rootview;
    }
    private void loadFoods() {
        Call<ArrayList<Food>> call = getFoods.all();
        call.enqueue(new Callback<ArrayList<Food>>() {
            @Override
            public void onResponse(Call<ArrayList<Food>> call, Response<ArrayList<Food>> response) {
                foods = response.body();
                foods_location = new ArrayList<Food_location>();
                foods_modify = new ArrayList<Food_modify>();

                /* 냉장식품 분리*/
                for(int i=0; i < foods.size(); i++){
                    if(foods.get(i).getFood_location().equals("냉동")){
                        Food_location food_locations = new Food_location(foods.get(i).getFood_name(), foods.get(i).getFood_exdate(), foods.get(i).getFood_Image());
                        foods_location.add(food_locations);
                        /* 식재료 수정 코드 */
                        Food_modify food_modifys = new Food_modify(foods.get(i).getFood_number(), foods.get(i).getFood_type(),
                                foods.get(i).getFood_name(), foods.get(i).getFood_exdate(), foods.get(i).getFood_purchase(), foods.get(i).getFood_location());
                        foods_modify.add(food_modifys);

                        /* 식재료 삭제*/
                        fId.add(foods.get(i).getFood_number());         //삭제에 필요한 food_number를 arrayList에 저장해줌
                    }

                }
                displayResult(foods_location);
            }
            @Override
            public void onFailure(Call<ArrayList<Food>> call, Throwable t) {

            }
        });
    }

    private void displayResult(ArrayList<Food_location> foods) {
        if (foods != null) {
            String details = "";
            food_list.clear();
            food_adapter = new FoodListAdapter(getActivity(), foods);
            lvFoods.setAdapter(food_adapter);

        } else {

            // 에러 구문 삽입

        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.activity_lcilckmenu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo cmenuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.mDelete:
                cmenuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                foods_location.remove(cmenuInfo.position);
                food_adapter.notifyDataSetChanged();
                Integer f = fId.get(cmenuInfo.position);       //Click한 Item에 position값을 이용하여 fId에 저장되어있는 Click한 Item의 food_number값을 저장
                removeFood(f);                                  //DB에 삭제하는 함수
                fId.remove(cmenuInfo.position);                 //DB에 삭제했지만 f 라는 arraylist에는 삭제되지 않았기때문에 삭제해줌.
                return true;
            case R.id.mModify:
                cmenuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                Log.d(TAG, "Modify");
                Intent tIntent = new Intent(getContext(), RefrigeratorModify.class);
                Bundle bundle = new Bundle();
                //Integer food_position= fId.get(cmenuInfo.position);
                Integer mod1 = foods_modify.get(cmenuInfo.position).getFood_number();
                String mod2 = foods_modify.get(cmenuInfo.position).getFood_type();
                String mod3 = foods_modify.get(cmenuInfo.position).getFood_name();
                String mod4 = foods_modify.get(cmenuInfo.position).getFood_purchase();
                String mod5 = foods_modify.get(cmenuInfo.position).getFood_exdate();
                String mod6 = foods_modify.get(cmenuInfo.position).getFood_location();
                bundle.putSerializable("foods1", (Serializable) mod1);
                bundle.putSerializable("foods2", (Serializable) mod2);
                bundle.putSerializable("foods3", (Serializable) mod3);
                bundle.putSerializable("foods4", (Serializable) mod4);
                bundle.putSerializable("foods5", (Serializable) mod5);
                bundle.putSerializable("foods6", (Serializable) mod6);
                tIntent.putExtras(bundle);
                Log.d(TAG, "Modify4");
                startActivity(tIntent);
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void removeFood(Integer food_id) {
        getFoods.removeFood( food_id).enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }






}