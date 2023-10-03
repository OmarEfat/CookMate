package comp3350.CookMate.presentation;

import static comp3350.CookMate.business.AccessCook.userInputLogin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import comp3350.CookMate.business.AccessCook;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import comp3350.CookMate.R;
import comp3350.CookMate.business.UserInputExceptions;
import comp3350.CookMate.objects.Cook;
import comp3350.CookMate.objects.NoCook;
import comp3350.CookMate.objects.YouShallNotPass;


public class LoginActivity extends Activity {


    //Variables store the userID and password from the login page
    private TextInputEditText userIdEditText;
    private TextInputEditText passwordEditText;

    private AccessCook accessCook;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        accessCook = new AccessCook();

        // Getting the information in the userID and password
        userIdEditText = findViewById(R.id.user_id_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);

        // Login button
        Button loginButton = findViewById(R.id.buttonLogin);

        // When login button is clicked, store text of username and password in string
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userId = userIdEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                try
                {

                    Cook currentAccount = accessCook.returnAccount( userId, password );
                    Cook c = new Cook(userId, currentAccount.getUserName(), password,
                    currentAccount.getPreferredName(), currentAccount.getDietaryRestrictions());
                    //validating currentAccount in logic layer....
                    userInputLogin(c);

                    if ( currentAccount instanceof NoCook )  // Cook not found.
                    {
                        Toast.makeText(getApplicationContext(), "Your User ID was not found, please try again!", Toast.LENGTH_SHORT).show();
                    }
                    else if ( currentAccount instanceof YouShallNotPass ) // Wrong password.
                    {
                        Toast.makeText(getApplicationContext(), "Your password is incorrect, please try again!", Toast.LENGTH_SHORT).show();
                    }
                    else // Correct user ID and password.
                    {
                        Toast.makeText(getApplicationContext(), "Welcome to your account! ", Toast.LENGTH_SHORT).show();
                        Intent recipeIntent = new Intent(LoginActivity.this, RecipeActivity.class);
                        Gson gson = new Gson();
                        String cookJson = gson.toJson(currentAccount);
                        recipeIntent.putExtra("cook", cookJson);
                        startActivity(recipeIntent);
                    }
                }
                catch (UserInputExceptions.UserLoginInfoEmptyException e)
                {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }catch (UserInputExceptions.UserIdPwdContainSpaceException e)
                {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }


            }
        });

    }
}
