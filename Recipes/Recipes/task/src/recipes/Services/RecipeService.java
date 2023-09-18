package recipes.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import recipes.Entities.Recipe;
import recipes.Entities.User;
import recipes.Repositories.RecipeRepository;
import recipes.Requests.RecipeRequest;
import recipes.Responses.RecipeAddedResponse;
import recipes.Responses.RecipeResponse;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final UserService userService;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository, UserService userService) {
        this.recipeRepository = recipeRepository;
        this.userService = userService;
    }

    public ResponseEntity<RecipeResponse> getRecipe(long id) {
        if (!this.recipeRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(new RecipeResponse(this.recipeRepository.getById(id)));
    }

    public ResponseEntity<RecipeAddedResponse> addRecipe(RecipeRequest request) {
        Recipe recipe = new Recipe();

        setRecipeValues(recipe, request);

        this.recipeRepository.save(recipe);

        return ResponseEntity.ok(new RecipeAddedResponse(recipe.getId()));
    }

    public ResponseEntity<HttpStatus> deleteRecipe(long id) {
        Recipe recipe = recipeRepository.findById(id).orElse(null);

        if (recipe == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if (!recipe.getUser().equals(userService.getCurrentAuthenticatedUser())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        recipeRepository.deleteById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    public ResponseEntity<HttpStatus> updateRecipe(long id, RecipeRequest request) {
        Recipe recipe = recipeRepository.findById(id).orElse(null);

        if (recipe == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if (!recipe.getUser().equals(userService.getCurrentAuthenticatedUser())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        setRecipeValues(recipe, request);

        recipeRepository.save(recipe);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    public ResponseEntity<List<RecipeResponse>> searchRecipesByCategory(String category) {
        List<RecipeResponse> recipes = recipeRepository.findAllByCategoryIgnoreCaseOrderByDateDesc(category)
                .stream().map(recipe -> new RecipeResponse(recipe))
                .toList();

        return ResponseEntity.ok(recipes);
    }

    public ResponseEntity<List<RecipeResponse>> searchRecipesByName(String name) {
        List<RecipeResponse> recipes = recipeRepository.findAllByNameContainingIgnoreCaseOrderByDateDesc(name)
                .stream().map(recipe -> new RecipeResponse(recipe))
                .toList();

        return ResponseEntity.ok(recipes);
    }


    private void setRecipeValues(Recipe recipe, RecipeRequest request) {
        User user = userService.getCurrentAuthenticatedUser();

        recipe.setValues(user,
                request.getName(),
                request.getDescription(),
                request.getIngredients(),
                request.getDirections(),
                request.getCategory(),
                LocalDateTime.now());
    }
}