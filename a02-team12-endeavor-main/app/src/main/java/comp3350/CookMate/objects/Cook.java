package comp3350.CookMate.objects;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cook {
    private String userID;
    private String userName;
    private String password;
    private String preferredName;
    private List<DietaryRestriction> dietaryRestrictions;

    private List<Recipe> recipes;



    public Cook(String userID, String userName, String password, String preferredName,
                List<DietaryRestriction> dietaryRestrictions) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
        this.preferredName = preferredName;
        this.dietaryRestrictions = dietaryRestrictions;
        this.recipes = new ArrayList<>();

    }

    public Cook() {}


    public String getUserID(){
        return userID;
    }

    public String getUserName(){
        return userName;
    }

    public String getPassword(){
        return password;
    }

    public String getPreferredName() {
        return preferredName;
    }

    public List<DietaryRestriction>getDietaryRestrictions() {
        return dietaryRestrictions;
    }

    public void setUserID(String userID){
        this.userID = userID;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }

    public void setPassword(String password) { this.password = password; }

    public void setPreferredName(String preferredName) {
        this.preferredName = preferredName;
    }

    public void setDietaryRestrictions(List<DietaryRestriction> dietaryRestrictions){
        this.dietaryRestrictions = dietaryRestrictions;
    }
    public boolean equals(Object other){
        boolean equals = false;

        if (other instanceof Cook)
        {
            final Cook otherCook = (Cook) other;
            equals = Objects.equals(this.userID, otherCook.userID);
        }

        return equals;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void addRecipe(Recipe newRecipe)
    {

        System.out.println(" the recipe is called ");
        recipes.add(newRecipe);
    }

    public void deleteRecipe(Recipe deleteRecipe)
    {
        if(recipes.contains(deleteRecipe))
            recipes.remove(deleteRecipe);
    }

    public String getDietaryRestrictionString() {
        StringBuilder sb = new StringBuilder();
        for (DietaryRestriction restriction : dietaryRestrictions) {
            sb.append(restriction.getName()).append(", ");
        }
        // Remove the last comma and space
        if (sb.length() > 2) {
            sb.delete(sb.length() - 2, sb.length());
        }
        return sb.toString();
    }


}

