package comp3350.CookMate.objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Ingredient  implements Serializable {
    // Declare instance variables
    private String name;
    private List<DietaryRestriction> incompatibleRestrictions;

    // Constructor to initialize the instance variables
    public Ingredient(String name) {
        this.name = name;
        this.incompatibleRestrictions = new ArrayList<>(); // Initialize the list of incompatible restrictions
    }

    // Getter method for name variable
    public String getName() {
        return name;
    }

    // Getter method for quantity variable

    // Setter method for quantity variable

    // Method to add a new incompatible restriction to the list
    public void addIncompatibleRestriction(DietaryRestriction restriction) {
        incompatibleRestrictions.add(restriction);
    }

    // Method to retrieve the list of incompatible restrictions
    public List<DietaryRestriction> getIncompatibleRestrictions() {
        return incompatibleRestrictions;
    }

    public String getIncompatibleRestrictionsString() {
        if (incompatibleRestrictions == null || incompatibleRestrictions.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < incompatibleRestrictions.size(); i++) {
            sb.append(incompatibleRestrictions.get(i).toString());
            if (i < incompatibleRestrictions.size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    public void setIncompatibleDietaryRestriction(List<DietaryRestriction> restrictions) {
        incompatibleRestrictions.clear();
        if (restrictions != null) {
            incompatibleRestrictions.addAll(restrictions);
        }
    }


}

