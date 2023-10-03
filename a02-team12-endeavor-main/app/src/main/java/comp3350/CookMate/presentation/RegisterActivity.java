package comp3350.CookMate.presentation;

import static comp3350.CookMate.business.AccessCook.lengthCheck;
import static comp3350.CookMate.business.AccessCook.userInputRegister;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import comp3350.CookMate.R;
import comp3350.CookMate.business.AccessCook;
import comp3350.CookMate.business.AccessRecipe;
import comp3350.CookMate.business.UserInputExceptions;
import comp3350.CookMate.objects.Cook;
import comp3350.CookMate.objects.DietaryRestriction;
import comp3350.CookMate.persistence.HSQLDB.DuplicateAccountException;

public class RegisterActivity extends Activity {

    private TextInputEditText userIdEditText;
    private TextInputEditText passwordEditText;
    private TextInputEditText fullNameEditText;
    private AccessCook accessCook;

    private AccessRecipe accessRecipe;

    private TextView dietaryRestrictions;
    private boolean[] selectedDietaryRestrictions;
    private final LinkedList<Integer> dietaryRestrictionList = new LinkedList<>();
    private final LinkedList<String> selectedDietaryRestriction = new LinkedList<>();

    private  List<DietaryRestriction> selectedDietary = new ArrayList<>();
    private final String[] dietaryRestrictionArray = {"Vegetarian", "Vegan", "Halal", "Diabetic", "Lactose Intolerant"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        accessCook = new AccessCook();
        accessRecipe = new AccessRecipe();
        accessCook.getAccounts();
        selectedDietaryRestrictions = new boolean[dietaryRestrictionArray.length];

        userIdEditText = findViewById(R.id.user_name_edit_text);
        userIdEditText.addTextChangedListener( new TextWatcher()
        {
            @Override
            public void beforeTextChanged( CharSequence s, int start, int count, int after ) {}

            @Override
            public void onTextChanged( CharSequence s, int start, int before, int count )
            {
                if ( lengthCheck(s,20) )
                {
                    Toast.makeText( RegisterActivity.this, "Maximum length reached", Toast.LENGTH_SHORT ).show();
                }
            }

            @Override
            public void afterTextChanged( Editable s ) {}
        } );
        passwordEditText = findViewById(R.id.password_edit_text);
        fullNameEditText = findViewById(R.id.full_name_edit_text);
        fullNameEditText.addTextChangedListener( new TextWatcher()
        {
            @Override
            public void beforeTextChanged( CharSequence s, int start, int count, int after ) {}

            @Override
            public void onTextChanged( CharSequence s, int start, int before, int count )
            {
                if ( lengthCheck(s, 20) )
                {
                    Toast.makeText( RegisterActivity.this, "Maximum length reached", Toast.LENGTH_SHORT ).show();
                }
            }

            @Override
            public void afterTextChanged( Editable s ) {}
        } );
        //MaterialCardView card;
        RelativeLayout dietaryRestrictionsRelativeLayout = findViewById(R.id.user_dietary_restriction_relative_layout);
        dietaryRestrictions = findViewById(R.id.user_dietary_restriction_text_view);
        Button registerButton = findViewById(R.id.register);
        dietaryRestrictionsRelativeLayout.setOnClickListener(v -> dietaryRestrictionWindow());

        registerButton.setOnClickListener(v -> implementRegister());
    }

    private void implementRegister() {
        String userId = Objects.requireNonNull(userIdEditText.getText()).toString().toLowerCase();
        userId = userId.toLowerCase();
        String userPassword = Objects.requireNonNull(passwordEditText.getText()).toString();
        String fullName = Objects.requireNonNull(fullNameEditText.getText()).toString();

        try
        {
            selectedDietary = parseDietaryRestrictions(selectedDietaryRestriction);
            Cook newCook = new Cook( userId, fullName, userPassword, fullName, selectedDietary );
            //validating newCook in logic layer....
            userInputRegister(newCook);

            try
            {
                accessCook.addAccount(newCook);

                Toast.makeText( getApplicationContext(), userId + " account has been created!", Toast.LENGTH_SHORT ).show();
                Intent intent = new Intent( RegisterActivity.this, HomeActivity.class );

                accessRecipe.addQuickRecipes(accessCook.returnAccount(userId,userPassword));

                startActivity( intent );
            }
            catch ( DuplicateAccountException e )
            {
                System.out.println( "Error: " + e.getMessage() );
                Toast.makeText( getApplicationContext(), "User ID already exists, please try another user ID", Toast.LENGTH_SHORT ).show();
            }
        }
        catch ( UserInputExceptions.UserInfoIsEmptyException e )
        {
            Toast.makeText( getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT ).show();
        }catch ( UserInputExceptions.UserIdPwdContainSpaceException e )
        {
            Toast.makeText( getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT ).show();
        }catch ( UserInputExceptions.PwdLengthLessThanSixException e )
        {
            Toast.makeText( getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT ).show();
        }
    }


    private void dietaryRestrictionWindow() {

        AlertDialog.Builder builder = new AlertDialog.Builder((RegisterActivity.this));

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

    private List<DietaryRestriction> parseDietaryRestrictions(LinkedList<String> selectedDietaryRestriction) {
        List<DietaryRestriction> listDietaryRestriction = new ArrayList<>();
        for (String str : selectedDietaryRestriction) {
            listDietaryRestriction.add(new DietaryRestriction(str));
        }
        return listDietaryRestriction;
    }


}



