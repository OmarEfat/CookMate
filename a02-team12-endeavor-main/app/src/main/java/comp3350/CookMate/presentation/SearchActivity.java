package comp3350.CookMate.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import comp3350.CookMate.R;
import comp3350.CookMate.business.AccessRecipe;
import comp3350.CookMate.objects.Cook;
import comp3350.CookMate.objects.Recipe;

public class SearchActivity extends AppCompatActivity implements RecyclerViewInterface {

    private AccessRecipe accessRecipe;
    private Cook currentCook;
    private List<Recipe> recipeList;
    private List<Recipe> filteredList;
    private Adapter recipeAdapter;
    private RecyclerView recipeRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //initialize recyclerview
        recipeRecyclerView = findViewById(R.id.recipeRecyclerView);
        recipeRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //initialize accessRecipe and currentCook as needed
        accessRecipe = new AccessRecipe();
        Intent recipeIntent = getIntent();
        String cookJson = recipeIntent.getStringExtra("cook");
        Gson gson = new Gson();
        currentCook = gson.fromJson(cookJson, Cook.class);

        //initialize the recipe list and filtered list with all recipes for the current cook
        recipeList = accessRecipe.getAllRecipes(currentCook);
        filteredList = new ArrayList<>(recipeList);
        for (Recipe recipe : recipeList) {
            System.out.println("Recipe Name for search: " + recipe.getName());
        }

        //set up the recipe adapter
        recipeAdapter = new Adapter(recipeList, currentCook, accessRecipe, this);
        recipeRecyclerView.setAdapter(recipeAdapter);

        //set up the search view
        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("SearchActivity", "onQueryTextChange called with newText = " + newText);
                filteredList.clear();
                if (newText.isEmpty()) {
                    filteredList.addAll(recipeList);
                    Log.d("SearchActivity", "filteredList: " + filteredList);
                } else {
                    for (Recipe recipe : recipeList) {
                        if (recipe.getName().toLowerCase().contains(newText.toLowerCase())) {
                            filteredList.add(recipe);
                            Log.d("SearchActivity", "Added recipe: " + recipe.getName());
                        }
                    }
                }
                recipeAdapter.updateRecipeList(filteredList);
                recipeAdapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        Recipe recipe = filteredList.get(position);
        Intent intent = new Intent(SearchActivity.this, RecipePageActivity.class);
        Gson gson = new Gson();
        String recipeJson = gson.toJson(recipe);
        intent.putExtra("Recipe", recipeJson);
        startActivity(intent);
    }
}


