package com.example.kimseolki.refrigerator_acin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by pc on 2017-05-04.
 */

public class Food_location {

    @SerializedName("food_name")
    @Expose
    private String food_Name;

    @SerializedName("food_exdate")
    @Expose
    private String food_Exdate;

    @SerializedName("food_image")
    @Expose
    private String food_Image;

    public Food_location(String food_Name, String food_Exdate, String food_Image) {
        this.food_Name = food_Name;
        this.food_Exdate = food_Exdate;
        this.food_Image = food_Image;
    }

    public String getFood_Name() {
        return food_Name;
    }

    public void setFood_Name(String food_Name) {
        this.food_Name = food_Name;
    }

    public String getFood_Exdate() {
        return food_Exdate;
    }

    public void setFood_Exdate(String food_Exdate) {
        this.food_Exdate = food_Exdate;
    }

    public String getFood_Image() {
        return food_Image;
    }

    public void setFood_Image(String food_Image) {
        this.food_Image = food_Image;
    }
}
