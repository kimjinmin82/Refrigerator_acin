package com.example.kimseolki.refrigerator_acin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Shopping {

@SerializedName("url")
@Expose
private String url;
@SerializedName("shopping_id")
@Expose
private Integer shoppingId;
@SerializedName("food_type")
@Expose
private String foodType;
@SerializedName("shopping_name")
@Expose
private String shoppingName;
@SerializedName("shopping_memo")
@Expose
private String shoppingMemo;

public String getUrl() {
return url;
}

public void setUrl(String url) {
this.url = url;
}

public Integer getShoppingId() {
return shoppingId;
}

public void setShoppingId(Integer shoppingId) {
this.shoppingId = shoppingId;
}

public String getFoodType() {
return foodType;
}

public void setFoodType(String foodType) {
this.foodType = foodType;
}

public String getShoppingName() {
return shoppingName;
}

public void setShoppingName(String shoppingName) {
this.shoppingName = shoppingName;
}

public String getShoppingMemo() {
return shoppingMemo;
}

public void setShoppingMemo(String shoppingMemo) {
this.shoppingMemo = shoppingMemo;
}

}