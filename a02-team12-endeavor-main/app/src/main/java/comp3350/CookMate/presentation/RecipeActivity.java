package comp3350.CookMate.presentation;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.PopupMenu;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import comp3350.CookMate.R;
import comp3350.CookMate.business.AccessRecipe;
import comp3350.CookMate.objects.Cook;
import comp3350.CookMate.objects.Ingredient;
import comp3350.CookMate.objects.Recipe;

public class RecipeActivity extends Activity implements RecyclerViewInterface {

    private AccessRecipe accessRecipe;
    private ArrayList<Recipe> RecipeArrayList;

    @SuppressLint({"SetTextI18n", "NonConstantResourceId"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        RecyclerView recipeView = findViewById(R.id.recipe_RV);

        accessRecipe = new AccessRecipe();

        Intent recipeIntent = getIntent();
        String cookJson = recipeIntent.getStringExtra("cook");
        Gson gson = new Gson();
        Cook currentCook = gson.fromJson(cookJson, Cook.class);

        String userId = currentCook.getUserName(); //currentCook.getUserName();

        Button welcomeTextView = findViewById(R.id.button_welcome);

        FloatingActionButton addRecipe = findViewById(R.id.button_add_recipe);

        // add the searching button
        Button searchRecipe = findViewById(R.id.button_search_recipe);

        addRecipe.setOnClickListener(v -> {
            Intent intent = new Intent(RecipeActivity.this, AddRecipeActivity.class);
            Gson gson1 = new Gson();
            String cookJson1 = gson1.toJson(currentCook);
            intent.putExtra("cook", cookJson1);
            startActivity(intent);
        });

        searchRecipe.setOnClickListener(v -> {
            Intent intent = new Intent(RecipeActivity.this, SearchActivity.class);
            Gson gson1 = new Gson();
            String cookJson1 = gson1.toJson(currentCook);
            intent.putExtra("cook", cookJson1);
            startActivity(intent);
        });

        welcomeTextView.setText("Welcome " + userId);

        RecipeArrayList = new ArrayList<>();
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        addCookRecipesToList(currentCook, RecipeArrayList);
        Adapter recipeAdapter = new Adapter(RecipeArrayList, currentCook, accessRecipe, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recipeView.setLayoutManager(linearLayoutManager);
        recipeView.setAdapter(recipeAdapter);

        welcomeTextView.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(RecipeActivity.this, welcomeTextView);
            popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
            popup.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.show_recipes:
                        // Here will go the page of new recipe

                        return true;
                    case R.id.update_account:
                        // Here will go a page for updating account info.
                        Intent updateIntent = new Intent(RecipeActivity.this, UpdateAccountActivity.class);
                        Gson gson1 = new Gson();
                        String cookJson1 = gson1.toJson(currentCook);
                        updateIntent.putExtra("cook", cookJson1);
                        startActivity(updateIntent);

                        return true;
                    case R.id.log_out:
                        Intent homeIntent = new Intent(RecipeActivity.this, HomeActivity.class);
                        startActivity(homeIntent);
                        return true;

                    default:
                        return false;
                }
            });
            popup.show();
        });
    }


    private void addCookRecipesToList(Cook currentCook, List<Recipe> recipeList) {
        List<Recipe> cookRecipes = accessRecipe.getAllRecipes(currentCook);
        System.out.println(cookRecipes.size());

        if(cookRecipes !=null)
        {

            recipeList.addAll(cookRecipes);

            for(int i = 0 ; i<cookRecipes.size() ; i++)
            {
                List<Ingredient>recipeIngredients = accessRecipe.getRecipeIngredients(cookRecipes.get(i));
                cookRecipes.get(i).setIngredient(recipeIngredients);
            }
        }
    }


    public void onItemClick(int position) {
        Intent recipePageIntent = new Intent(RecipeActivity.this, RecipePageActivity.class);
        Recipe currentRecipe = RecipeArrayList.get(position);
        Gson gson2 = new Gson();
        String recipeJson = gson2.toJson(currentRecipe);
        recipePageIntent.putExtra("Recipe", recipeJson);
        startActivity(recipePageIntent);
    }


}
