package comp3350.CookMate.presentation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import comp3350.CookMate.R;
import comp3350.CookMate.business.AccessRecipe;
import comp3350.CookMate.objects.Cook;
import comp3350.CookMate.objects.Recipe;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private List<Recipe> recipeArrayList;
    private final RecyclerViewInterface recyclerViewInterface;

    private final Cook currentCook;
    private final AccessRecipe accessRecipe;

    // Constructor
    public Adapter(List<Recipe> recipeArrayList, Cook currentCook, AccessRecipe accessRecipe, RecyclerViewInterface recyclerViewInterface) {
        this.recipeArrayList = recipeArrayList;
        this.currentCook = currentCook;
        this.accessRecipe = accessRecipe;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        return new ViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // to set data to textview and imageview of each card layout
        Recipe model = recipeArrayList.get(position);
        holder.recipeTxtView.setText(model.getName());
        holder.recipeImgView.setImageResource(model.getRecipeImage());
        holder.deleteBtn.setOnClickListener(view -> {
            Recipe currentRecipe = recipeArrayList.get(holder.getAdapterPosition());
            accessRecipe.removeRecipeCook(currentCook, currentRecipe);
            recipeArrayList.remove(model);
            notifyItemRemoved(holder.getAdapterPosition());
        });
    }


    @Override
    public int getItemCount() {
        // this method is used for showing number of card items in recycler view
        return recipeArrayList.size();
    }

    // View holder class for initializing of your views such as TextView and Imageview
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView recipeImgView;
        private final TextView recipeTxtView;
        private final ImageButton deleteBtn;
        private final Button followButton;

        public ViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            recipeImgView = itemView.findViewById(R.id.imageViewRecipe);
            recipeTxtView = itemView.findViewById(R.id.textViewRecipeName);
            deleteBtn = itemView.findViewById(R.id.imageButtonDelete);
            followButton = itemView.findViewById(R.id.buttonFollow); // Add this line

            followButton.setOnClickListener(view -> {
                if (recyclerViewInterface != null) {
                    int position = getAdapterPosition();

                    if (position != RecyclerView.NO_POSITION) {
                        recyclerViewInterface.onItemClick(position);
                    }
                }
            });
        }
    }

    public void updateRecipeList(List<Recipe> recipeList) {
        recipeArrayList = recipeList;
        notifyDataSetChanged();
    }
}
