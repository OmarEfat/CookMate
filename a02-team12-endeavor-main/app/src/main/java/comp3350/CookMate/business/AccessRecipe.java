package comp3350.CookMate.business;

import java.util.List;

import comp3350.CookMate.application.Services;
import comp3350.CookMate.objects.Cook;
import comp3350.CookMate.objects.Ingredient;
import comp3350.CookMate.objects.Recipe;
import comp3350.CookMate.persistence.RecipeManagement;
/**
 * This class provides access to the recipe management system.
 */
public class AccessRecipe {

    private final RecipeManagement recipeManagement;

    public AccessRecipe()
    {
        recipeManagement = Services.getRecipeManagement();
    }

    public AccessRecipe(RecipeManagement recipeManagement){
        this.recipeManagement = recipeManagement;
    }

    public List<Recipe> getAllRecipes(Cook currentCook)
    {
        return recipeManagement.getAllRecipes(currentCook);
    }

    /**
     * Adds a new recipe to the recipe management system.
     *
     * @param newRecipe the recipe to be added.
     */
    public void addRecipe(Recipe newRecipe)
    {
        recipeManagement.addRecipe(newRecipe);
    }

    public void addRecipeCook(Recipe newRecipe , Cook currentCook)
    {
        recipeManagement.addRecipeCook(newRecipe , currentCook);
    }

    public void removeRecipeCook(Cook currentCook , Recipe currentRecipe)
    {
        recipeManagement.removeRecipeCook(currentCook,currentRecipe);
    }

    public void addIngredient(Ingredient ingredient)
    {
        recipeManagement.addIngredient(ingredient);
    }

    public List<Ingredient> getRecipeIngredients(Recipe recipe)
    {
        return recipeManagement.getRecipeIngredients(recipe);
    }

    public void addRecipeIngredients(List<Ingredient> ingredients, Recipe recipe){
        recipeManagement.addRecipeIngredients(ingredients,recipe);
    }

    public Recipe searchCookRecipes(Cook userCook , String recipeName){
        return recipeManagement.searchCookRecipes(userCook , recipeName);
    }

    public void addQuickRecipes(Cook currentCook){
        recipeManagement.addQuickRecipes(currentCook);
    }
}
