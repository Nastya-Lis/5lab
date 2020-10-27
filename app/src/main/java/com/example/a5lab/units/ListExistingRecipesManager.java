package com.example.a5lab.units;

import android.content.Context;

import com.example.a5lab.educationManager.JsonManipulations;

import java.io.File;
import java.util.List;

public class ListExistingRecipesManager {

    private List<Recipe> recipeList;
    JsonManipulations jsonManipulations = new JsonManipulations();
    String fileName = "recipesJson.json";
    File file;
    Context context;


    public ListExistingRecipesManager(List<Recipe> recipeList, Context context){
        this.recipeList = recipeList;
        this.context = context;
    }

    public void removeElement(int position){
        recipeList.removeIf(recipe -> recipeList.indexOf(recipe) == position);
        updateJson();
    }

    private void updateJson(){
        file = new File(context.getFilesDir(),fileName);
        jsonManipulations.serializationToJsonForUpdateVersion(file,new RecipeForJson(recipeList));
    }
}
