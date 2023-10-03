package comp3350.CookMate.persistence.HSQLDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import comp3350.CookMate.objects.Cook;
import comp3350.CookMate.objects.DietaryRestriction;
import comp3350.CookMate.objects.Ingredient;
import comp3350.CookMate.objects.Recipe;
import comp3350.CookMate.persistence.RecipeManagement;

// This class provides the interface for managing recipes in the HSQLDB database

public class RecipeManagementHSQLDB implements RecipeManagement {

    private final String dbPath;
    public RecipeManagementHSQLDB(final String dbPath)
    {
        this.dbPath = dbPath;
    }
    private Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }
// Returns a list of all recipes belonging to a given cook
    @Override
    public List<Recipe> getAllRecipes(Cook currentCook) {
        List<Recipe> recipes = new ArrayList<>();
        int count = 0;
        try (Connection c = connection()) {
            PreparedStatement statement = c.prepareStatement("SELECT * FROM Recipe WHERE name IN (SELECT recipeName FROM CookRecipe WHERE userId = ?)");
            statement.setString(1, currentCook.getUserID());
            System.out.println(currentCook.getUserID());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                int image = resultSet.getInt("image");
                String category = resultSet.getString("category");
                String cookingLvl = resultSet.getString("cookingLvl");
                int prepTime = resultSet.getInt("prepTime");
                int cookingTime = resultSet.getInt("cookingTime");
                int numOfServing = resultSet.getInt("numOfServing");
                String showSteps = resultSet.getString("showSteps");
                Recipe recipe = new Recipe(name, image, category, cookingLvl, prepTime, cookingTime, numOfServing, showSteps);
                recipes.add(recipe);
                currentCook.addRecipe(recipe);
                count++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(count);

        return recipes;
    }

    public void addIngredient(Recipe newRecipe , Ingredient newIngredient)
    {

    }
// Adds a recipe to the Recipe and CookRecipe tables in the database, as well as to the current cook's list of recipes

    @Override
    public void addRecipeCook(Recipe newRecipe, Cook currentCook) {
        try (Connection c = connection()) {
            // Add the recipe to the Recipe table
            addRecipe(newRecipe);
            // Associate the recipe with the current cook in the CookRecipe table
            PreparedStatement insertCookRecipe = c.prepareStatement("INSERT INTO CookRecipe (userId, recipeName) VALUES (?, ?)");
            insertCookRecipe.setString(1, currentCook.getUserID());
            insertCookRecipe.setString(2, newRecipe.getName());
            insertCookRecipe.executeUpdate();
            currentCook.addRecipe(newRecipe);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
// Removes a recipe from the CookRecipe table in the database and from the current cook's list of recipes

    public void removeRecipeCook(Cook currentCook, Recipe recipe) {
        try (Connection c = connection()) {
            // Remove the recipe from the CookRecipe table
            PreparedStatement deleteCookRecipe = c.prepareStatement("DELETE FROM CookRecipe WHERE userId = ? AND recipeName = ?");
            deleteCookRecipe.setString(1, currentCook.getUserID());
            deleteCookRecipe.setString(2, recipe.getName());
            deleteCookRecipe.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
// Searches for a recipe with the given name in the Recipe table of the database and returns it as a Recipe object

    public Recipe searchRecipe(String recipeName) {
        try (Connection c = connection()) {
            // Search for the recipe in the Recipe table
            PreparedStatement searchRecipe = c.prepareStatement("SELECT * FROM Recipe WHERE name = ?");
            searchRecipe.setString(1, recipeName);
            ResultSet recipeResult = searchRecipe.executeQuery();

            // If the recipe exists, create a Recipe object and return it
            if (recipeResult.next()) {
                String name = recipeResult.getString("name");
                int image = recipeResult.getInt("image");
                String category = recipeResult.getString("category");
                String cookingLvl = recipeResult.getString("cookingLvl");
                int prepTime = recipeResult.getInt("prepTime");
                int cookingTime = recipeResult.getInt("cookingTime");
                int numOfServing = recipeResult.getInt("numOfServing");
                String showSteps = recipeResult.getString("showSteps");
                Recipe recipe = new Recipe(name, image, category, cookingLvl, prepTime, cookingTime, numOfServing, showSteps);
                return recipe;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // If the recipe does not exist, return null
        return null;
    }

    public Recipe searchCookRecipes(Cook userCook , String recipeName) {
        Recipe recipe = null;
        try (Connection c = connection()) {
            // Search for the recipe in the CookRecipe table for the given cook
            PreparedStatement selectCookRecipe = c.prepareStatement("SELECT * FROM CookRecipe WHERE userId = ? AND recipeName = ?");
            selectCookRecipe.setString(1, userCook.getUserID());
            selectCookRecipe.setString(2, recipeName);
            ResultSet resultSet = selectCookRecipe.executeQuery();
            if (resultSet.next()) {
                // If the recipe is found, retrieve the details from the Recipe table
                String name = resultSet.getString("recipeName");
                int image = resultSet.getInt("image");
                String category = resultSet.getString("category");
                String cookingLvl = resultSet.getString("cookingLvl");
                int prepTime = resultSet.getInt("prepTime");
                int cookingTime = resultSet.getInt("cookingTime");
                int numOfServing = resultSet.getInt("numOfServing");
                String showSteps = resultSet.getString("showSteps");
                recipe = new Recipe(name, image, category, cookingLvl, prepTime, cookingTime, numOfServing, showSteps);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recipe;
    }

    public boolean addRecipe(Recipe newRecipe) {
        try (Connection c = connection()) {
            // Check if recipe name already exists
            PreparedStatement checkRecipe = c.prepareStatement("SELECT name FROM Recipe WHERE name = ?");
            checkRecipe.setString(1, newRecipe.getName());
            ResultSet rs = checkRecipe.executeQuery();

            if (rs.next()) {
                // Recipe name already exists
            } else {
                // Recipe name does not exist, insert new recipe
                PreparedStatement insertRecipe = c.prepareStatement("INSERT INTO Recipe (name, image, category, cookingLvl, prepTime, cookingTime, numOfServing, showSteps) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
                insertRecipe.setString(1, newRecipe.getName());
                insertRecipe.setInt(2, newRecipe.getImage());
                insertRecipe.setString(3, newRecipe.getCategory());
                insertRecipe.setString(4, newRecipe.getCookingLvl());
                insertRecipe.setInt(5, newRecipe.getPrepTime());
                insertRecipe.setInt(6, newRecipe.getCookingTime());
                insertRecipe.setInt(7, newRecipe.getNumOfServing());
                insertRecipe.setString(8, newRecipe.getShowSteps());
                insertRecipe.executeUpdate();
                System.out.println("Recipe added to database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }


// Adds an ingredient to the Ingredient and RecipeIngredient tables in the database

    public void addIngredient(Ingredient newIngredient) {
        try (Connection c = connection()) {
            PreparedStatement insertIngredient = c.prepareStatement("INSERT INTO Ingredient (name, incompatibleRestrictions) VALUES (?, ?)");
            insertIngredient.setString(1, newIngredient.getName());
            insertIngredient.setString(2, newIngredient.getIncompatibleRestrictionsString());
            insertIngredient.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void addRecipeIngredient(Recipe newRecipe, Ingredient newIngredient) {
        try (Connection c = connection()) {
            // Add the new ingredient to the list of ingredients of the specified recipe
            PreparedStatement addIngredient = c.prepareStatement("INSERT INTO RecipeIngredient (recipeName, ingredientName) VALUES (?, ?)");
            addIngredient.setString(1, newRecipe.getName());
            addIngredient.setString(2, newIngredient.getName());
            addIngredient.executeUpdate();
            // Update the Recipe object with the new ingredient
            newRecipe.addIngredient(newIngredient);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addRecipeIngredients(List<Ingredient> ingredients, Recipe recipe) {
        try (Connection c = connection()) {
            for (Ingredient ingredient : ingredients) {
                PreparedStatement insertIngredient = c.prepareStatement("INSERT INTO RecipeIngredient (recipeName, ingredientName) VALUES (?, ?)");
                insertIngredient.setString(1, recipe.getName());
                insertIngredient.setString(2, ingredient.getName());
                insertIngredient.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Ingredient> getRecipeIngredients(Recipe recipe) {
        List<Ingredient> ingredients = new ArrayList<>();
        try (Connection c = connection()) {
            PreparedStatement selectIngredients = c.prepareStatement("SELECT ingredientName, incompatibleRestrictions FROM RecipeIngredient INNER JOIN Ingredient ON RecipeIngredient.ingredientName = Ingredient.name WHERE recipeName = ?");
            selectIngredients.setString(1, recipe.getName());
            ResultSet rs = selectIngredients.executeQuery();
            while (rs.next()) {
                String name = rs.getString("ingredientName");
                String incompatibleRestrictionsStr = rs.getString("incompatibleRestrictions");
                List<DietaryRestriction> incompatibleRestrictions = new ArrayList<>();
                if (incompatibleRestrictionsStr != null && !incompatibleRestrictionsStr.isEmpty()) {
                    String[] restrictionsArr = incompatibleRestrictionsStr.split(",");
                    for (int i = 0; i < restrictionsArr.length; i++) {
                        DietaryRestriction newDietaryRestriction = new DietaryRestriction(restrictionsArr[i]);
                        incompatibleRestrictions.add(newDietaryRestriction);
                    }
                }
                Ingredient ingredient = new Ingredient(name);
                ingredient.setIncompatibleDietaryRestriction(incompatibleRestrictions);
                ingredients.add(ingredient);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ingredients;
    }

    public void addQuickRecipes(Cook currentCook) {
        try (Connection c = connection()) {
            PreparedStatement insertRecipe = c.prepareStatement("INSERT INTO CookRecipe VALUES (?, ?)");
            insertRecipe.setString(1, currentCook.getUserID());

            insertRecipe.setString(2, "Japanese Chicken");
            insertRecipe.executeUpdate();

            insertRecipe.setString(2, "Banana Bread");
            insertRecipe.executeUpdate();

            insertRecipe.setString(2, "Banana Cake");
            insertRecipe.executeUpdate();

            insertRecipe.setString(2, "Bourbon Chicken");
            insertRecipe.executeUpdate();

            insertRecipe.setString(2, "Chocolate Cake");
            insertRecipe.executeUpdate();

            insertRecipe.setString(2, "Oreo Balls");
            insertRecipe.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

