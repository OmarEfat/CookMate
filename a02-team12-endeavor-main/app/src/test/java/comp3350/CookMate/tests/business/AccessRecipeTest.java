package comp3350.CookMate.tests.business;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import comp3350.CookMate.business.AccessCook;
import comp3350.CookMate.objects.Cook;
import comp3350.CookMate.application.Services;
import comp3350.CookMate.objects.DietaryRestriction;
import comp3350.CookMate.objects.Ingredient;
import comp3350.CookMate.objects.Recipe;
import comp3350.CookMate.business.AccessRecipe;
import comp3350.CookMate.persistence.AccountManagement;
import comp3350.CookMate.persistence.HSQLDB.AccountManagementHSQLDB;
import comp3350.CookMate.persistence.HSQLDB.RecipeManagementHSQLDB;
import comp3350.CookMate.persistence.RecipeManagement;
import comp3350.CookMate.presentation.HomeActivity;
import comp3350.CookMate.tests.CopyDB;

public class AccessRecipeTest {
    private AccessRecipe accessRecipe;
    private AccessCook accessCook;
    private AccountManagement accountManagement;
    private RecipeManagement list;
    private File temp_Database;

    @Before
    public void setUp() throws IOException {
        // Use the RecipePersistenceStub for testing purposes
        this.temp_Database = CopyDB.copy_real_db();
        HomeActivity.setDBPathName(this.temp_Database.getAbsolutePath().replace(".script" , ""));
        list = new RecipeManagementHSQLDB(HomeActivity.getDBPathName());
        accessRecipe = new AccessRecipe(new RecipeManagementHSQLDB(HomeActivity.getDBPathName()));
    }

    @Test
    public void testGetAllRecipes() {
        List<DietaryRestriction> dietaryRestriction = new LinkedList<>();
        dietaryRestriction.add(new DietaryRestriction("Halal"));

        // Test adding a recipe to a cook's collection
        Cook testCook = new Cook("tahao","Omar Taha","1023" , "Mero Mero" , dietaryRestriction);
        Recipe testRecipe = new Recipe("Chicken Alfredo", "Dinner");
        testCook.addRecipe(testRecipe);
        accessRecipe.addRecipeCook(testRecipe, testCook);

        // Retrieve the recipe from the database and check if its details match the original recipe
        List<Recipe> recipes = accessRecipe.getAllRecipes(testCook);
        boolean found = false;
        for (Recipe recipe : recipes) {
            if (recipe.getName().equals(testRecipe.getName())) {
                found = true;
                break;
            }
        }
        assertTrue(found);
    }
    @Test
    public void testAddRecipe() {
        List<DietaryRestriction> dietaryRestriction = new LinkedList<>();
        dietaryRestriction.add(new DietaryRestriction("Halal"));

        // Test adding a recipe to a cook's collection
        Cook testCook = new Cook("tahao","Omar Taha","1023" , "Mero Mero" , dietaryRestriction);
        Recipe newRecipe = new Recipe("New Recipe", "New Description");
        accessRecipe.addRecipe(newRecipe);
        List<Recipe> recipeList = accessRecipe.getAllRecipes(testCook);
        assertFalse(recipeList.contains(newRecipe));
    }
    @Test
    public void testAddRecipeCook() {
        List<DietaryRestriction> dietaryRestriction = new LinkedList<>();
        dietaryRestriction.add(new DietaryRestriction("Halal"));

        // Test adding a recipe to a cook's collection
        Cook testCook = new Cook("tahao","Omar Taha","1023" , "Mero Mero" , dietaryRestriction);
        Recipe testRecipe = new Recipe("Chicken Alfredo", "Dinner");
        testCook.addRecipe(testRecipe);
        accessRecipe.addRecipeCook(testRecipe, testCook);
        List retrievedRecipe = accessRecipe.getAllRecipes(testCook);
        assertNotNull(retrievedRecipe);
    }
    @Test
    public void testRemoveRecipeCook() {
        List<DietaryRestriction> dietaryRestriction = new LinkedList<>();
        dietaryRestriction.add(new DietaryRestriction("Halal"));

        // create a test cook and recipe
        Cook testCook = new Cook("tahao","Omar Taha","1023" , "Mero Mero" , dietaryRestriction );
        Recipe testRecipe = new Recipe("Pasta", 1, "Italian", "Beginner", 10, 20, 4, "Boil water and cook pasta.");

        // add the recipe to the cook
        testCook.addRecipe(testRecipe);

        // remove the recipe
        testCook.deleteRecipe(testRecipe);

        // assert that the recipe is no longer in the cook's recipe list
        assertFalse(testCook.getRecipes().contains(testRecipe));
    }
    @Test
    public void testGetRecipeIngredients() {
        // Test getting the ingredients of a recipe
        // Create a new recipe object with ingredients
        Recipe recipe = new Recipe("Spaghetti Carbonara", 1, "Italian", "Intermediate", 20, 30, 4, "Steps go here");
        recipe.addIngredient(new Ingredient("Spaghetti"));
        recipe.addIngredient(new Ingredient("Bacon"));
        recipe.addIngredient(new Ingredient("Eggs"));
        recipe.addIngredient(new Ingredient("Parmesan cheese"));

        // Call the getRecipeIngredients method
        List<Ingredient> ingredientsList = recipe.getIngredients();

        // Check if the returned list size matches the number of ingredients added
        assertEquals(ingredientsList.size(), 4);
    }
}
