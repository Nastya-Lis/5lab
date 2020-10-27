package com.example.a5lab.educationManager;

import com.example.a5lab.units.Recipe;
import com.example.a5lab.units.RecipeForJson;


import java.io.File;

public class Manager{

    File file;
    public JsonManipulations jsonManipulations = new JsonManipulations();

    public void serialize(Recipe recipe){
        if(file!=null)
            jsonManipulations.serializationToJson(file,recipe);
    }

    public RecipeForJson deserialize(){
        RecipeForJson personReturn = jsonManipulations.deserializationFromJson(file);
        return personReturn;
    }

    public void takeFileFromActivity(File file){
        this.file = file;
    }

}
