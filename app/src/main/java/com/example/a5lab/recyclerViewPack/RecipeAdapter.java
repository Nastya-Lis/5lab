package com.example.a5lab.recyclerViewPack;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a5lab.R;
import com.example.a5lab.activities.AddRecipeActivity;
import com.example.a5lab.activities.MainPageActivity;
import com.example.a5lab.activities.ShowCurrentRecipeActivity;
import com.example.a5lab.units.Recipe;
import com.example.a5lab.units.RecipeForJson;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeView> {

    RecipeForJson recipeForJson;
    Context context;

    public RecipeAdapter(RecipeForJson recipeForJson,Context context){
        this.context = context;
        this.recipeForJson = recipeForJson;
    }

    class RecipeView extends RecyclerView.ViewHolder{
        TextView name,ingredient,timeCooking,cookingRecipe;
        ImageView photo;
        public RecipeView(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.nameElementId);
            ingredient =(TextView) itemView.findViewById(R.id.ingredientElementId);
            timeCooking = (TextView) itemView.findViewById(R.id.timeElementId);
            cookingRecipe =(TextView) itemView.findViewById(R.id.cookingRecipeElementId);
            photo = (ImageView) itemView.findViewById(R.id.pictureElementId);
        }
    }


    @NonNull
    @Override
    public RecipeView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_example_template,
                parent,false);

        return new RecipeView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeView holder, int position) {
        ArrayList<Recipe> recipeArrayList = (ArrayList<Recipe>) recipeForJson.recipeList;
        Recipe recipe = recipeArrayList.get(position);

        holder.name.setText(recipe.getName());
        holder.cookingRecipe.setText(recipe.getCookingRecipe());
        holder.ingredient.setText(recipe.getIngredient());
        holder.timeCooking.setText(recipe.getTimeCooking());
        holder.photo.setImageURI(Uri.parse(recipe.getPhoto()));


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ShowCurrentRecipeActivity.class);
                Recipe recipeSend = recipeArrayList.get(position);
                intent.putExtra(Recipe.class.getSimpleName(),recipeSend);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return recipeForJson.recipeList.size();
    }

}
