package com.example.a5lab.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.a5lab.R;
import com.example.a5lab.educationManager.JsonManipulations;
import com.example.a5lab.recyclerViewPack.RecipeAdapter;
import com.example.a5lab.units.ListExistingRecipesManager;
import com.example.a5lab.units.Recipe;
import com.example.a5lab.units.RecipeForJson;

import java.io.File;
import java.util.List;

public class MainPageActivity extends AppCompatActivity {

    String fileName = "recipesJson.json";
    File file;
    JsonManipulations jsonManipulations = new JsonManipulations();
    RecipeForJson recipeForJson;
    RecipeAdapter recipeAdapter;
    RecyclerView recyclerView;
    PopupMenu popupMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        file = new File(super.getFilesDir(), fileName);

        if (jsonManipulations.isFileExists(file) == true) {

            recipeForJson = jsonManipulations.deserializationFromJson(file);
            recyclerView = (RecyclerView) findViewById(R.id.myRecycler);
            recipeAdapter = new RecipeAdapter(recipeForJson, getApplicationContext());
            recyclerView.setAdapter(recipeAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        updateRecycler();
        creationOfPopupMenu();
    }

    private void creationOfPopupMenu() {

        recipeAdapter.setOnRecipeClickListener(recipe -> {
            Intent intent = new Intent(this, ShowCurrentRecipeActivity.class);
            intent.putExtra(Recipe.class.getSimpleName(), recipe);
            startActivity(intent);
        });

        recipeAdapter.setOnRecipeLongClickListener((recipe, view) -> {
                    popupMenu = new PopupMenu(this, view);
                    popupMenu.inflate(R.menu.context_menu);
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            switch (menuItem.getItemId()) {
                                case R.id.editId:
                                    editRecipe(recipe);
                                    break;
                                case R.id.deleteId:
                                    deleteRecipe(recipe);
                                    break;
                            }
                            return true;
                        }
                    });
                    popupMenu.show();
                    return true;
                });

        recyclerView.setAdapter(recipeAdapter);

    }

    private void editRecipe(Recipe recipe){
        Intent intent = new Intent(this,UpdateRecipeActivity.class);
        intent.putExtra(Recipe.class.getSimpleName(),recipe);
        startActivity(intent);
    }

    private void deleteRecipe(Recipe recipe){
        AlertDialog.Builder alert = new AlertDialog.Builder(MainPageActivity.this);
        alert.setTitle("Warning!").
                setMessage("Are you really want to delete this element?").
                setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            ListExistingRecipesManager listExistingRecipesManager =
                                    new ListExistingRecipesManager(recipeForJson.recipeList,
                                            MainPageActivity.this);
                            listExistingRecipesManager.removeElementV2(recipe);
                            updateRecycler();
                        }
                        catch (Exception e){

                        }

                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog alertDialog = alert.create();
        alertDialog.show();

    }

    private void updateRecycler(){
        recipeForJson = jsonManipulations.deserializationFromJson(file);
        recipeAdapter = new RecipeAdapter(recipeForJson, getApplicationContext());
        recyclerView.setAdapter(recipeAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu_bar, menu);

        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                recipeAdapter.getFilter().filter(s);
                return false;
            }
        });

           return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){
            case R.id.addIcon:
                Intent intent = new Intent(this,AddRecipeActivity.class);
                startActivity(intent);
                break;

            case R.id.sorting_by_default:
                recipeForJson = jsonManipulations.deserializationFromJson(file);
                recipeAdapter = new RecipeAdapter(recipeForJson,this);
                recyclerView.setAdapter(recipeAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                break;

            case R.id.sorting_by_name:
                recipeForJson = jsonManipulations.deserializationFromJson(file);
               recipeForJson.recipeList.
                        sort((recipe1,recipe2) -> recipe1.getName().toUpperCase().
                                compareTo(recipe2.getName().toUpperCase()));

               RecipeAdapter recipeAdapterFoName = new RecipeAdapter(recipeForJson,this);
               recyclerView.setAdapter(recipeAdapterFoName);
               recyclerView.setLayoutManager(new LinearLayoutManager(this));
                break;

            case R.id.sorting_by_category:
                recipeForJson = jsonManipulations.deserializationFromJson(file);
                recipeForJson.recipeList.
                        sort((recipe1,recipe2) -> recipe1.getCategory().
                                compareTo(recipe2.getCategory()));

                RecipeAdapter recipeAdapterForCategory = new RecipeAdapter(recipeForJson,this);
                recyclerView.setAdapter(recipeAdapterForCategory);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}