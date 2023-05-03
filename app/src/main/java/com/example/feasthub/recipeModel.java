package com.example.feasthub;

/**
 * The recipeModel class represents a recipe model that includes the name and image of the recipe.
 */
public class recipeModel {

    private String recipe_name;
    private int imgid;

    /**
     *Constructs a recipeModel object with the given recipe name and image ID.
     * @param recipe_name The name of the recipe.
     * @param imgid The image ID of the recipe.
     */
    public recipeModel(String recipe_name, int imgid) {
        this.recipe_name = recipe_name;
        this.imgid = imgid;
    }

    /**
     * Returns the name of the recipe.
     * @return The name of the recipe.
     */
    public String getRecipe_name() {
        return recipe_name;
    }

    /**
     * Sets the name of the recipe.
     * @param recipe_name The new name of the recipe.
     */
    public void setRecipe_name(String recipe_name) {
        this.recipe_name = recipe_name;
    }

    /**
     * Returns the image ID of the recipe.
     * @return The image ID of the recipe.
     */
    public int getImgid() {
        return imgid;
    }


    /**
     * Sets the image ID of the recipe.
     * @param imgid The new image ID of the recipe.
     */
    public void setImgid(int imgid) {
        this.imgid = imgid;
    }


    /**
     * Returns the object at the specified position in this recipe model.
     * @param position The position of the object to return.
     * @return The object at the specified position.
     */
    public Object getItem(int position){
        return null;
    }

}
