package comp3350.CookMate.tests.objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;

import comp3350.CookMate.objects.Ingredient;
import comp3350.CookMate.objects.Recipe;

public class RecipeTest {
    Recipe recipe;
    @Before
    public void setUp() {
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        ArrayList<Ingredient> kIngredients = new ArrayList<>();
        ingredients.add(new Ingredient("Small pieces of paneer"));
        kIngredients.add(new Ingredient("Kofta"));
        recipe = new Recipe("Malai Kofta" , "Dinner" , "Medium" , 30 , 25 , 4 , ingredients , kIngredients , "Add Spices and grill it on medium flame");
    }
    @Test
    public void TestGetters() {
        assertEquals(recipe.getCategory() , "Dinner");
        assertEquals(recipe.getCookingLvl() , "Medium");
        assertEquals(recipe.getPrepTime() , 30);
        assertEquals(recipe.getCookingTime() , 25);
        assertEquals(recipe.getNumOfServing() , 4);
    }
    @Test
    public void addIngredientsTest() {
        recipe.addIngredient(new Ingredient("Black Pepper"));
        assertFalse(recipe.getIngredients().contains("Small pieces of paneer"));
    }
    @Test
    public void testRecipe(){
        Recipe recipe;
        ArrayList<Ingredient> keyIngredients = new ArrayList<Ingredient>() {{add(new Ingredient("tofu"));}};
        ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>() { {
            add(new Ingredient("broad bean sauce"));
            add(new Ingredient("chili oil"));
            add(new Ingredient("peppercorn"));}
        };

        String step = "Fry the Sichuan peppercorns in the oil to infuse the aroma\n" +
                "Cook the ground pork with doubanjiang\n" +
                "Once the pork is cooked, add the green onions and stir a few times\n" +
                "Add the broth and braise with the cover on\n" +
                "Drizzle in the cornstarch slurry to thicken the sauce\n";
        //found this on https://omnivorescookbook.com/authentic-mapo-tofu/

        System.out.println("\nStarting testRecipe");
        recipe = new Recipe("Mapo Tofu",2, "Main Dishes", "medium", 10, 15, 2, ingredients,keyIngredients,step);
        assertNotNull(recipe);
        assertEquals("Mapo Tofu", recipe.getName());
        assertEquals("Main Dishes", recipe.getCategory());
        assertEquals("medium", recipe.getCookingLvl());
        assertEquals(10, recipe.getPrepTime());
        assertEquals(15, recipe.getCookingTime());
        assertEquals(2, recipe.getNumOfServing());
        assertEquals(keyIngredients, recipe.getKeyIngredients());
        assertEquals(ingredients, recipe.getIngredients());
        System.out.println("\nEnding testRecipe");
    }
}

