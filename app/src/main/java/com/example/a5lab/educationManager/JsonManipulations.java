package com.example.a5lab.educationManager;

import android.util.Log;

import com.example.a5lab.units.Recipe;
import com.example.a5lab.units.RecipeForJson;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class JsonManipulations {
    ObjectMapper objectMapper;
    RecipeForJson recipes;

    public boolean isFileExists(File file) {
        if(file!=null) {
            if (!file.exists()) {
                try {
                    file.createNewFile();
                    Log.i("Log_json", "File has been just created");
                    return true;
                } catch (IOException e) {
                    Log.i("Log_json", "File is not created. Lox");
                    return false;
                }
            }
            else {
                //file.delete();
                Log.i("Log_json", "File exists");
                return true;
            }
        }
        else{
            Log.i("Log_json","File == null");
            return false;
        }

    }

    public void serializationToJson(File file, Recipe recipe){

      if(isFileExists(file)){
          objectMapper = new ObjectMapper();

          try {

              recipes = getFromFileList(file);
              if(recipes == null){
                  recipes = new RecipeForJson();
                  recipes.recipeList = new ArrayList<>();
              }
              recipes.recipeList.add(recipe);
              objectMapper.writerWithDefaultPrettyPrinter().writeValue(file,recipes);
          }
          catch (IOException e) {
             Log.i("Log_json","Oops, your serialization doesn't work");
          }
      }

    }


    public void serializationToJsonForRemove(File file, RecipeForJson recipeForJson){
        if(isFileExists(file)){
            objectMapper = new ObjectMapper();
            try{
                recipes = getFromFileList(file);
                if(recipes == null){
                    recipes = new RecipeForJson();
                }
                recipes.recipeList = recipeForJson.recipeList;
                objectMapper.writerWithDefaultPrettyPrinter().writeValue(file,recipes);
            }
            catch(IOException e){
                Log.i("Log_json","Oops, your serialization doesn't work");
            }
        }
    }


    public void serializationToJsonForUpdate(File file,RecipeForJson recipeForJson){
        if(isFileExists(file)){
            objectMapper = new ObjectMapper();
            try{
                recipes = getFromFileList(file);
                if(recipes == null){
                    recipes = new RecipeForJson();
                }
                recipes.recipeList = recipeForJson.recipeList;
                objectMapper.writerWithDefaultPrettyPrinter().writeValue(file,recipes);
            }
            catch(IOException e){
                Log.i("Log_json","Oops, your serialization doesn't work");
            }
        }
    }


    private RecipeForJson getFromFileList(File file){
        if(file!=null){
            try {
                objectMapper = new ObjectMapper();
                recipes = objectMapper.readValue(file,RecipeForJson.class);
                return recipes;
            } catch (IOException e) {
                Log.i("Log_json","Couldn't read file");
                return null;
            }
        }
        else{
            return null;
        }
    }

    public RecipeForJson deserializationFromJson(File file) {
      return getFromFileList(file);
    }
}
