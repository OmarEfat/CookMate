package comp3350.CookMate.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class Recipe {
    // instance variables
    private final String name;

    //private final String recipeID;
    private final int image;
    private final String category;
    private final String cookingLvl;
    private final int prepTime;
    private final int cookingTime;
    private final int numOfServing;
    private  List<Ingredient> ingredients;
    private  final List<Ingredient> keyIngredients;
    private final  String showSteps;

    // constructor
    public Recipe(String name, int image, String category, String cookingLvl, int prepTime, int cookingTime, int numOfServing, List<Ingredient> ingredients, List<Ingredient> keyIngredients, String showSteps) {
        this.name = name;
        this.image = image;
        this.category = category;
        this.cookingLvl = cookingLvl;
        this.prepTime = prepTime;
        this.cookingTime = cookingTime;
        this.numOfServing = numOfServing;
        this.ingredients = ingredients;
        this.keyIngredients = keyIngredients;
        this.showSteps = showSteps;
        //this.recipeID = "";

    }


    public Recipe(String name, int image, String category, String cookingLvl, int prepTime, int cookingTime, int numOfServing, String showSteps) {
        this.name = name;
        this.image = image;
        this.category = category;
        this.cookingLvl = cookingLvl;
        this.prepTime = prepTime;
        this.cookingTime = cookingTime;
        this.numOfServing = numOfServing;
        this.ingredients = new ArrayList<>();
        this.keyIngredients = new ArrayList<>();
        this.showSteps = showSteps;
        //this.recipeID = "";
    }
    public Recipe(String newName, String newCate, String newLevel, int prepTime, int cookTime, int serving, ArrayList<Ingredient> newIngred, ArrayList<Ingredient> newKeyIngred, String newInstruct){
        name = newName;
        category = newCate;
        cookingLvl = newLevel;
        ingredients = newIngred;
        this.showSteps = newInstruct;
        keyIngredients = newKeyIngred;
        this.prepTime = prepTime;
        this.cookingTime = cookTime;
        this.numOfServing = serving;
        image = -1;
    }
    // second constructor - just to make testing a bit easier
    public Recipe(String recipeName , String newCategory)
    {
        this(recipeName ,
                newCategory ,
                "easy" ,
                10 ,
                20 ,
                2 ,
                new ArrayList<Ingredient>(){{add(new Ingredient("eggs")); add(new Ingredient("water")); add(new Ingredient("salt")); }},
                new ArrayList<Ingredient>(){{add(new Ingredient("eggs")); }},
                "easy to cook" );
    }
    // get methods
    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getCookingLvl() {
        return cookingLvl;
    }

    public int getCookingTime() {
        return cookingTime;
    }

    public int getNumOfServing() {
        return numOfServing;
    }

    public int getPrepTime() {
        return prepTime;
    }

    public String getShowSteps(){return showSteps;}

    public int getImage()
    {
        return image;
    }

    public List<Ingredient> getIngredients() {
        return new ArrayList<>(ingredients);
    }

    public String getIngredientListString() {
        List<Ingredient> ingredients = getIngredients();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < ingredients.size(); i++) {
            if (i > 0) {
                builder.append(", ");
            }
            builder.append(ingredients.get(i).getName());
        }
        return builder.toString();
    }



    public void addIngredient(Ingredient newIngredient){ingredients.add(newIngredient);}

    public List<Ingredient> getKeyIngredients() {
        return new ArrayList<>(keyIngredients);
    }

    public void setIngredient(List<Ingredient> newIngredients){ingredients = newIngredients;}



    public int getRecipeImage() {
        return this.image;
    }

    public boolean isCompatibleWithCook(Cook cook) {
        List<DietaryRestriction> restrictions = cook.getDietaryRestrictions();
        for (Ingredient ingredient : ingredients) {
            for (DietaryRestriction restriction : restrictions) {
                for(int j = 0 ; j<ingredient.getIncompatibleRestrictions().size() ; j++)
                {
                    if(ingredient.getIncompatibleRestrictions().get(j).getName().equals(restriction.getName()))
                        return false;
                }
            }
        }
        // If all ingredients in the recipe are compatible with all restrictions
        // of the cook, return true.
        return true;
    }

}

