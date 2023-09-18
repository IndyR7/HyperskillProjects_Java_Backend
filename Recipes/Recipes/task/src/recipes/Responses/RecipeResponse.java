package recipes.Responses;

import lombok.Data;
import recipes.Entities.Recipe;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class RecipeResponse {
    private String name;
    private String category;
    private String description;
    private List<String> ingredients;
    private List<String> directions;
    private LocalDateTime date;

    public RecipeResponse(Recipe recipe) {
        this.name = recipe.getName();
        this.category = recipe.getCategory();
        this.description = recipe.getDescription();
        this.ingredients = recipe.getIngredients();
        this.directions = recipe.getDirections();
        this.date = recipe.getDate();
    }
}
