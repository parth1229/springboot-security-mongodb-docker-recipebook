package com.parth.recipebook.recipebookservice.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(value = "UserRecipe")
public class UserRecipeList {

    @Id
    private String userId;
    private List<Recipe> recipes;

    public UserRecipeList() {
    }

    public UserRecipeList(String userId, List<Recipe> recipes) {
        this.userId = userId;
        this.recipes = recipes;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }
}
