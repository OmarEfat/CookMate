package comp3350.CookMate.presentation;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import comp3350.CookMate.R;
import comp3350.CookMate.objects.Recipe;

public class RecipePageActivity extends Activity {
    @SuppressLint("SetTextI18n")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_page);

        // Get the Recipe object from the intent extras
        Intent intent = getIntent();
        String recipeJson = intent.getStringExtra("Recipe");
        Gson gson = new Gson();
        Recipe recipe = gson.fromJson(recipeJson, Recipe.class);

        // Populate the views with recipe data
        ImageView recipeImageView = findViewById(R.id.recipe_image);
        TextView recipeNameTextView = findViewById(R.id.recipe_name);
        TextView recipeCategoryTextView = findViewById(R.id.recipe_category);
        TextView recipeCookingLvlTextView = findViewById(R.id.recipe_difficulty);
        TextView recipePrepTimeTextView = findViewById(R.id.recipe_prep_time);
        TextView recipeCookingTimeTextView = findViewById(R.id.recipe_cooking_time);
        TextView recipeServingTextView = findViewById(R.id.recipe_servings);
        TextView recipeIngredientsTextView = findViewById(R.id.recipe_ingredients);
        TextView recipeInstructionsTextView = findViewById(R.id.recipe_steps);

        recipeImageView.setImageResource(recipe.getRecipeImage());
        recipeNameTextView.setText("Name: " + recipe.getName());
        recipeCategoryTextView.setText("Category: " + recipe.getCategory());
        recipeCookingLvlTextView.setText("Difficulty: " + recipe.getCookingLvl());
        recipePrepTimeTextView.setText("Preparation time: " + recipe.getPrepTime() + " minutes");
        recipeCookingTimeTextView.setText("Cooking time: " + recipe.getCookingTime() + " minutes");
        recipeServingTextView.setText("Servings: " + recipe.getNumOfServing());
        recipeIngredientsTextView.setText("List of ingredients:\n" + recipe.getIngredientListString());
        // Needs to be updated when getInstruction() method is implemented.
        recipeInstructionsTextView.setText("List of steps:\n" + recipe.getShowSteps());

    }
}