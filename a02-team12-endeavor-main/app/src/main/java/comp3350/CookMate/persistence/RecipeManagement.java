package comp3350.CookMate.persistence;

import java.util.List;

import comp3350.CookMate.objects.Cook;
import comp3350.CookMate.objects.Ingredient;
import comp3350.CookMate.objects.Recipe;

public interface RecipeManagement {

    List<Recipe> getAllRecipes(Cook currentCook);
    void addRecipeCook(Recipe newRecipe , Cook currentCook);
    void removeRecipeCook(Cook currentCook, Recipe recipe);
    Recipe searchRecipe(String recipeName);

    Recipe searchCookRecipes(Cook userCook , String recipeName);

    boolean addRecipe(Recipe newRecipe);

    void addRecipeIngredients(List<Ingredient> ingredients, Recipe recipe);

    List<Ingredient> getRecipeIngredients(Recipe recipe);

    void addIngredient(Ingredient ingredient);

    void addQuickRecipes(Cook currentCook);


}
