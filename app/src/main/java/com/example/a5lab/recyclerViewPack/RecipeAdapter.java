package com.example.a5lab.recyclerViewPack;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a5lab.R;
import com.example.a5lab.activities.AddRecipeActivity;
import com.example.a5lab.activities.MainPageActivity;
import com.example.a5lab.activities.ShowCurrentRecipeActivity;
import com.example.a5lab.activities.UpdateRecipeActivity;
import com.example.a5lab.units.ListExistingRecipesManager;
import com.example.a5lab.units.Recipe;
import com.example.a5lab.units.RecipeForJson;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeView>
implements Filterable {


    String photoDefault = "content://media/external/images/media/66";


    RecipeForJson recipeForJson;
    List<Recipe> recipeListCopy;
    ListExistingRecipesManager listExistingRecipesManager;
    Context context;

    public RecipeAdapter(RecipeForJson recipeForJson,Context context){
        this.context = context;
        this.recipeForJson = recipeForJson;
        recipeListCopy = new ArrayList<>(recipeForJson.recipeList);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                List<Recipe> filteredListRecipes = new ArrayList<>();

                if(charSequence == null || charSequence.length() == 0){
                    filteredListRecipes.addAll(recipeListCopy);
                }
                else{
                    String enteringString = charSequence.toString().toLowerCase().trim();

                    for (Recipe recipe: recipeListCopy) {
                        if(recipe.getName().contains(enteringString)){
                            filteredListRecipes.add(recipe);
                        }
                    }
                }

                FilterResults filteredResult = new FilterResults();
                filteredResult.values = filteredListRecipes;
                return filteredResult;

            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                recipeForJson.recipeList.clear();
                recipeForJson.recipeList.addAll((List) filterResults.values);
                notifyDataSetChanged();
            }
        };
    }

    public class RecipeView extends RecyclerView.ViewHolder{
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

    public interface OnRecipeClickListener{
        void onRecipeClick(Recipe recipe);
    }

    public interface OnRecipeLongClickListener{
        boolean onRecipeLongClick(Recipe recipe,View view);
    }

    public OnRecipeClickListener onRecipeClickListener;
    public OnRecipeLongClickListener onRecipeLongClickListener;


    public void setOnRecipeClickListener(OnRecipeClickListener onRecipeClickListener){
        this.onRecipeClickListener = onRecipeClickListener;
    }

    public void setOnRecipeLongClickListener(OnRecipeLongClickListener onRecipeLongClickListener){
        this.onRecipeLongClickListener = onRecipeLongClickListener;
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

        if(recipe.getPhoto() == photoDefault)
            holder.photo.setImageResource(R.drawable.food);
        else {
            holder.photo.setImageResource(R.drawable.recipebook);
        }


        if(onRecipeClickListener!=null){
            holder.itemView.setOnClickListener(view -> onRecipeClickListener.onRecipeClick(recipe));
        }
        if(onRecipeLongClickListener != null){
            holder.itemView.setOnLongClickListener(view ->
                    onRecipeLongClickListener.onRecipeLongClick(recipe,view));
        }

    }

    @Override
    public int getItemCount() {
        return  recipeForJson.recipeList == null ? 0 : recipeForJson.recipeList.size();
    }

}
