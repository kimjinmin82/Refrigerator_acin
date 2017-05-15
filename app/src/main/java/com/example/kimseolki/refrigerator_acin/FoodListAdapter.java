package com.example.kimseolki.refrigerator_acin;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kimseolki.refrigerator_acin.model.Food_location;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by pc on 2017-05-03.
 */

public class FoodListAdapter extends ArrayAdapter<Food_location> {

    List<Food_location> foods;
    Context context;
    private LayoutInflater mInflater;

    public FoodListAdapter(@NonNull Context context, @NonNull List<Food_location> objects) {
        super(context,0 , objects);
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        foods = objects;
    }

    @Nullable
    @Override
    public Food_location getItem(int position) {
        return foods.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder vh;
        if (convertView == null) {
            View view = mInflater.inflate(R.layout.food_list_layout, parent, false);
            vh = ViewHolder.create((LinearLayout) view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        Food_location item = getItem(position);

        vh.tvFoodName.setText(item.getFood_Name());
        vh.tvFoodExdate.setText(item.getFood_Exdate());
        Picasso.with(context).load(item.getFood_Image()).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(vh.ivFood);

        return vh.rootView;
    }

    private static class ViewHolder {
        public final LinearLayout rootView;
        public final ImageView ivFood;
        public final TextView tvFoodName;
        public final TextView tvFoodExdate;

        public ViewHolder(LinearLayout rootView, ImageView ivFood, TextView tvFoodName, TextView tvFoodExdate) {
            this.rootView = rootView;
            this.ivFood = ivFood;
            this.tvFoodName = tvFoodName;
            this.tvFoodExdate = tvFoodExdate;
        }

        public static ViewHolder create(LinearLayout rootView) {
            ImageView ivFood = (ImageView) rootView.findViewById(R.id.ivFood);
            TextView tvFoodName = (TextView) rootView.findViewById(R.id.tvFoodname);
            TextView tvFoodExdate = (TextView) rootView.findViewById(R.id.tvFoodExdate);
            return new ViewHolder(rootView, ivFood, tvFoodName, tvFoodExdate);
        }
    }
}
