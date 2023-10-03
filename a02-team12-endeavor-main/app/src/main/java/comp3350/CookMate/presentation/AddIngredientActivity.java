package comp3350.CookMate.presentation;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import comp3350.CookMate.R;
import comp3350.CookMate.objects.DietaryRestriction;
import comp3350.CookMate.objects.Ingredient;

public class AddIngredientActivity extends Activity {
    private boolean[] selectedDietaryRestrictions;

    private TextView dietaryRestrictions;
    private final LinkedList<Integer> dietaryRestrictionList = new LinkedList<>();
    private final LinkedList<String> selectedDietaryRestriction = new LinkedList<>();

    private List<DietaryRestriction> selectedDietary = new ArrayList<>();
    private final String[] dietaryRestrictionArray = {"Vegetarian", "Vegan", "Halal", "Diabetic", "Lactose Intolerant"};

    private EditText ingredientName;
    private Button addIngredient;
    private Button cancel;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_ingredient_popup);
        selectedDietaryRestrictions = new boolean[dietaryRestrictionArray.length];
        dietaryRestrictions = findViewById(R.id.user_dietary_restriction_text_view);
        RelativeLayout dietaryRestrictionsRelativeLayout = findViewById(R.id.user_dietary_restriction_relative_layout);

        dietaryRestrictionsRelativeLayout.setOnClickListener(v -> dietaryRestrictionWindow());

        ingredientName = findViewById(R.id.editText_ingredient_name);
        addIngredient = findViewById(R.id.button_add_ingredient);
        cancel = findViewById(R.id.button_cancel);

        cancel.setOnClickListener(v -> {
            setResult(Activity.RESULT_CANCELED);
            finish();
        });


        addIngredient.setOnClickListener(v -> {
            String name = ingredientName.getText().toString();
            if (!name.isEmpty()) {
                Ingredient ingredient = new Ingredient(name);
                ingredient.setIncompatibleDietaryRestriction(selectedDietary);
                Intent intent = new Intent();
                intent.putExtra("ingredient", ingredient);
                setResult(Activity.RESULT_OK, intent); // set result
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Please enter an ingredient name", Toast.LENGTH_SHORT).show();
                setResult(Activity.RESULT_CANCELED); // set result as cancelled
                finish();
            }
        });






    }

    private void dietaryRestrictionWindow() {

        AlertDialog.Builder builder = new AlertDialog.Builder((AddIngredientActivity.this));

        builder.setTitle("Select Dietary Restrictions");
        builder.setCancelable(false);
        Log.d("debug", "dietaryRestrictionWindow method called");
        builder.setMultiChoiceItems(dietaryRestrictionArray, selectedDietaryRestrictions, (dialog, i, isChecked) -> {
            if (isChecked) {
                dietaryRestrictionList.add(i);
                selectedDietaryRestriction.add(dietaryRestrictionArray[i]);
            } else {
                if (!dietaryRestrictionList.isEmpty()) {
                    dietaryRestrictionList.remove(i);
                    selectedDietaryRestriction.remove(dietaryRestrictionArray[i]);
                }
            }
        }).setPositiveButton("Ok", (dialog, i) -> {
            StringBuilder string = new StringBuilder();
            for (int j = 0; j < dietaryRestrictionList.size(); j++) {

                string.append(dietaryRestrictionArray[dietaryRestrictionList.get(j)]);

                if (j != dietaryRestrictionList.size() - 1) {
                    string.append(", ");
                }

                dietaryRestrictions.setText(string.toString());
            }
        }).setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss()).setNeutralButton("Clear all", (dialog, which) -> {
            for (int i = 0; i < selectedDietaryRestrictions.length; i++) {

                selectedDietaryRestrictions[i] = false;

                dietaryRestrictionList.clear();
                dietaryRestrictions.setText("");
            }
        });

        builder.show();
    }


}

