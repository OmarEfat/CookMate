package comp3350.CookMate.presentation;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import comp3350.CookMate.R;
import comp3350.CookMate.business.AccessCook;
import comp3350.CookMate.business.AccessRecipe;
import comp3350.CookMate.objects.Cook;
import comp3350.CookMate.objects.Ingredient;
import comp3350.CookMate.objects.Recipe;

public class AddRecipeActivity extends Activity {
    private EditText recipeNameEditText;
    private EditText categoryEditText;
    private EditText cookingLevelEditText;
    private EditText preparationTimeEditText;
    private EditText cookingTimeEditText;
    private EditText numberServing;
    private EditText stepsEditText;

    private AccessRecipe accessRecipe;

    private Button addRecipe;

    private List<Ingredient> recipeIngredients;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        accessRecipe = new AccessRecipe();

        Button addIngredient = findViewById(R.id.add_ingredient);

        addRecipe = findViewById(R.id.add_recipe);

        Intent recipeIntent = getIntent();
        String cookJson = recipeIntent.getStringExtra("cook");
        Gson gson = new Gson();
        Cook currentCook = gson.fromJson(cookJson, Cook.class);
        Button welcomeTextView = findViewById(R.id.button_welcome);
        welcomeTextView.setText("Welcome " + currentCook.getUserName());
        recipeIngredients = new ArrayList<>();

        addIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddRecipeActivity.this, AddIngredientActivity.class);
                startActivityForResult(intent, 1); // request code 1




            }
        });

        addRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Initialize EditText views
                recipeNameEditText = findViewById(R.id.recipe_name);
                categoryEditText = findViewById(R.id.category);
                cookingLevelEditText = findViewById(R.id.cooking_level);
                preparationTimeEditText = findViewById(R.id.preparation_time);
                cookingTimeEditText = findViewById(R.id.cooking_time);
                numberServing = findViewById(R.id.number_serving);
                stepsEditText = findViewById(R.id.steps);

                // Get the text entered in each EditText view
                String recipeName = recipeNameEditText.getText().toString().trim();
                String category = categoryEditText.getText().toString().trim();
                String cookingLevel = cookingLevelEditText.getText().toString().trim();
                int preparationTime;
                int cookingTime;
                int servingNumber;
                String steps = stepsEditText.getText().toString().trim();

                // Check if input is valid
                if (recipeName.isEmpty()) {
                    recipeNameEditText.setError("Recipe name is required.");
                    return;
                }
                if (category.isEmpty()) {
                    categoryEditText.setError("Category is required.");
                    return;
                }
                if (cookingLevel.isEmpty()) {
                    cookingLevelEditText.setError("Cooking level is required.");
                    return;
                }
                if (preparationTimeEditText.getText().toString().trim().isEmpty()) {
                    preparationTimeEditText.setError("Preparation time is required.");
                    return;
                } else {
                    preparationTime = Integer.parseInt(preparationTimeEditText.getText().toString().trim());

                    if (preparationTime <= 0) {
                        preparationTimeEditText.setError("Preparation time must be greater than zero.");
                        return;
                    }
                }
                if (cookingTimeEditText.getText().toString().trim().isEmpty()) {
                    cookingTimeEditText.setError("Cooking time is required.");
                    return;
                } else {
                    cookingTime = Integer.parseInt(cookingTimeEditText.getText().toString().trim());

                    if (cookingTime <= 0) {
                        cookingTimeEditText.setError("Cooking time must be greater than zero.");
                        return;
                    }
                }
                if (numberServing.getText().toString().trim().isEmpty()) {
                    numberServing.setError("Serving number is required.");
                    return;
                } else {
                    servingNumber = Integer.parseInt(numberServing.getText().toString().trim());

                    if (servingNumber <= 0) {
                        numberServing.setError("Serving number must be greater than zero.");
                        return;
                    }
                }
                if (steps.isEmpty()) {
                    stepsEditText.setError("Steps are required.");
                    return;
                }


                // If recipe is compatible add it, if not show message
                Recipe newRecipe = new Recipe(recipeName, 0, category, cookingLevel, preparationTime, cookingTime, servingNumber, steps);
                newRecipe.setIngredient(recipeIngredients);
                if (newRecipe.isCompatibleWithCook(currentCook)) {
                    accessRecipe.addRecipeCook(newRecipe, currentCook);
                    accessRecipe.addRecipeIngredients(recipeIngredients, newRecipe);
                    Intent newIntent = new Intent(AddRecipeActivity.this, RecipeActivity.class);

                    Gson gson = new Gson();
                    String cookJson = gson.toJson(currentCook);
                    newIntent.putExtra("cook", cookJson);

                    startActivity(newIntent);
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddRecipeActivity.this);
                    builder.setTitle("Warning");
                    builder.setMessage("This recipe is incompatible with your current ingredients. Do you still want to add it?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            accessRecipe.addRecipeCook(newRecipe,currentCook);
                            accessRecipe.addRecipeIngredients(recipeIngredients,newRecipe);
                            Intent newIntent = new Intent(AddRecipeActivity.this , RecipeActivity.class);
                            Gson gson = new Gson();
                            String cookJson = gson.toJson(currentCook);
                            newIntent.putExtra("cook", cookJson);

                            startActivity(newIntent);
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
            Ingredient ingredient = (Ingredient) data.getSerializableExtra("ingredient");
            accessRecipe.addIngredient(ingredient);
            // Do something with the ingredient object
            // For example, add it to a list of ingredients
            recipeIngredients.add(ingredient);




        }
    }

}
