package comp3350.CookMate.tests.persistence;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import comp3350.CookMate.business.AccessRecipe;
import comp3350.CookMate.objects.DietaryRestriction;
import comp3350.CookMate.objects.Ingredient;
import comp3350.CookMate.objects.Recipe;
import comp3350.CookMate.objects.Cook;
import comp3350.CookMate.persistence.AccountManagement;
import comp3350.CookMate.persistence.HSQLDB.AccountManagementHSQLDB;
import comp3350.CookMate.persistence.HSQLDB.RecipeManagementHSQLDB;
import comp3350.CookMate.persistence.RecipeManagement;
import comp3350.CookMate.presentation.HomeActivity;
import comp3350.CookMate.tests.CopyDB;

public class RecipeListIT {
    private RecipeManagement list;
    private File temp_Database;
    private AccessRecipe accessRecipe;
    private AccountManagement cookList;
    @Before
    public void setUp() throws IOException {
        this.temp_Database = CopyDB.copy_real_db();
        HomeActivity.setDBPathName(this.temp_Database.getAbsolutePath().replace(".script" , ""));
        list = new RecipeManagementHSQLDB(HomeActivity.getDBPathName());
        cookList = new AccountManagementHSQLDB(HomeActivity.getDBPathName());
        accessRecipe = new AccessRecipe(new RecipeManagementHSQLDB(HomeActivity.getDBPathName()));
    }
    @Test
    public void test_getRecipes() {
        List<DietaryRestriction> dietaryRestriction = new LinkedList<>();
        dietaryRestriction.add(new DietaryRestriction("Halal"));
        List<Recipe> all = list.getAllRecipes(cookList.getCook("tahao"));
        assertEquals(8,all.size());
    }
    @Test
    public void testAddRecipe() {
        // adding a valid recipe.
        Recipe r1 = new Recipe("recipe 1" , "Main Dishes");
        assertTrue(list.addRecipe(r1));
        assertEquals(8 , list.getAllRecipes(cookList.getCook("tahao")).size());
    }
    @Test
    public void testDeleteRecipe() {
        // adding a recipe to test delete
        Recipe r1 = new Recipe("recipe 1", "Main Dishes");
        assertTrue(list.addRecipe(r1));
        // delete the recipe
        Cook cook = cookList.getCook("tahao");
        list.removeRecipeCook(cook, r1);
        assertEquals(8, list.getAllRecipes(cookList.getCook("tahao")).size());
    }
    @After
    public void tearDown() throws IOException {
        this.temp_Database.delete();
    }
}
