package com.example.feasthub;

public class recipeModel {

    private String recipe_name;
    private int imgid;

    public recipeModel(String recipe_name, int imgid) {
        this.recipe_name = recipe_name;
        this.imgid = imgid;
    }

    public String getRecipe_name() {
        return recipe_name;
    }

    public void setRecipe_name(String recipe_name) {
        this.recipe_name = recipe_name;
    }

    public int getImgid() {
        return imgid;
    }

    public void setImgid(int imgid) {
        this.imgid = imgid;
    }

    public Object getItem(int position){
        return null;
    }

}
