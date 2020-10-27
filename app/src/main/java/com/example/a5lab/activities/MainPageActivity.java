package com.example.a5lab.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.a5lab.R;
import com.example.a5lab.educationManager.JsonManipulations;
import com.example.a5lab.recyclerViewPack.RecipeAdapter;
import com.example.a5lab.units.RecipeForJson;

import java.io.File;

public class MainPageActivity extends AppCompatActivity {

    String fileName = "recipesJson.json";
    File file;
    JsonManipulations jsonManipulations =  new JsonManipulations();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        //setContentView(R.layout.activity_add_new_recipe);

        file = new File(super.getFilesDir(),fileName);

        if(jsonManipulations.isFileExists(file) == true){

            RecipeForJson recipeForJson = jsonManipulations.deserializationFromJson(file);
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.myRecycler);
            recyclerView.setAdapter(new RecipeAdapter(recipeForJson,getApplicationContext()));
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
    }

}