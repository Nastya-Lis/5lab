package com.example.a5lab.units;

import java.io.Serializable;
import java.util.List;

public class RecipeForJson implements Serializable {
  public List<Recipe> recipeList;
  public RecipeForJson(){

  }
  public RecipeForJson(List<Recipe> list){
    recipeList = list;
  }
}
