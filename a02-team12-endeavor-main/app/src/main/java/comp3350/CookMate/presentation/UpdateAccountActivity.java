package comp3350.CookMate.presentation;

import static comp3350.CookMate.business.AccessCook.lengthCheck;
import static comp3350.CookMate.business.AccessCook.userInputUpdate;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import comp3350.CookMate.R;
import comp3350.CookMate.business.AccessCook;
import comp3350.CookMate.business.UserInputExceptions;
import comp3350.CookMate.objects.Cook;
import comp3350.CookMate.objects.DietaryRestriction;

//editTextUsername

public class UpdateAccountActivity extends Activity {

    private TextInputEditText fullNameEditText;
    private TextInputEditText passwordEditText;

    private TextView dietaryRestrictions;

    private AccessCook accessCook;

    private Button deleteButton;

    final String[] dietaryRestrictionArray = {"Vegetarian", "Vegan", "Halal", "Diabetic", "Lactose Intolerant"};
    boolean[] selectedDietaryRestrictions;
    final LinkedList<Integer> dietaryRestrictionList = new LinkedList<>();
    final LinkedList<String> selectedDietaryRestriction = new LinkedList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_info);

        Intent recipeIntent = getIntent();
        String cookJson = recipeIntent.getStringExtra("cook");
        Gson gson = new Gson();
        Cook currentCook = gson.fromJson(cookJson, Cook.class);

        accessCook = new AccessCook();
        selectedDietaryRestrictions = new boolean[dietaryRestrictionArray.length];



        fullNameEditText = findViewById(R.id.full_name_edit_text);
        // if user enter a char sequence > 20, give them a text
        fullNameEditText.addTextChangedListener( new TextWatcher()
        {
            @Override
            public void beforeTextChanged( CharSequence s, int start, int count, int after ) {}

            @Override
            public void onTextChanged( CharSequence s, int start, int before, int count )
            {
                if ( lengthCheck(s,20) )
                {
                    Toast.makeText( UpdateAccountActivity.this, "Maximum length reached", Toast.LENGTH_SHORT ).show();
                }
            }

            @Override
            public void afterTextChanged( Editable s ) {}
        } );
        passwordEditText = findViewById(R.id.password_edit_text);
        RelativeLayout dietaryRestrictionsRelativeLayout = findViewById(R.id.user_dietary_restriction_relative_layout);
        dietaryRestrictions = findViewById(R.id.user_dietary_restriction_text_view);
        dietaryRestrictionsRelativeLayout.setOnClickListener(v -> dietaryRestrictionWindow());
        deleteButton = findViewById(R.id.buttonDeleteAccount);

        Button loginButton = findViewById(R.id.buttonSubmit);


        loginButton.setOnClickListener(view -> {
            String userName = Objects.requireNonNull(fullNameEditText.getText()).toString();
            String password = Objects.requireNonNull(passwordEditText.getText()).toString();

            try {
                Cook c = new Cook(currentCook.getUserID(), userName, password,
                        currentCook.getPreferredName(), parseDietaryRestrictions(selectedDietaryRestriction));
                //validating currentCook in logic layer...
                userInputUpdate(c);

                accessCook.updateUserName(currentCook, userName);
                if(!password.isEmpty()){
                accessCook.updatePassword(currentCook, password);}
                accessCook.updateDietaryRestrictions(currentCook, parseDietaryRestrictions(selectedDietaryRestriction));

            }catch (UserInputExceptions.NothingToUpdateException e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }catch (UserInputExceptions.PwdContainSpaceException e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }catch (UserInputExceptions.PwdLengthLessThanSixException e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }


            Intent homePage = new Intent(UpdateAccountActivity.this, HomeActivity.class);
            startActivity(homePage);
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAccount(currentCook);

            }
        });
    }


    private void dietaryRestrictionWindow() {

        AlertDialog.Builder builder = new AlertDialog.Builder((UpdateAccountActivity.this));

        builder.setTitle("Select Dietary Restrictions");
        builder.setCancelable(false);
        Log.d("debug", "dietaryRestrictionWindow method called");


        builder.setMultiChoiceItems(dietaryRestrictionArray, selectedDietaryRestrictions, (dialog, i, isChecked) -> {
            if (isChecked) {
                dietaryRestrictionList.add(i);
                selectedDietaryRestriction.add(dietaryRestrictionArray[i]);
            } else {
                dietaryRestrictionList.remove(i);
                selectedDietaryRestriction.remove(dietaryRestrictionArray[i]);
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

    public void deleteAccount(Cook deletedCook) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Account")
                .setMessage("Are you sure you want to delete your account?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        accessCook.deleteAccount(deletedCook);
                        Intent homePage = new Intent(UpdateAccountActivity.this, HomeActivity.class);
                        startActivity(homePage);
                        Toast.makeText(getApplicationContext(), "Your account has been deleted. ", Toast.LENGTH_SHORT).show();

                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public List<DietaryRestriction> parseDietaryRestrictions(List<String> dietaryRestrictions) {
        List<DietaryRestriction> restrictions = new ArrayList<>();
        for (String restriction : dietaryRestrictions) {
            DietaryRestriction restrictionObject = new DietaryRestriction(restriction);
            restrictions.add(restrictionObject);
        }
        return restrictions;
    }

}
