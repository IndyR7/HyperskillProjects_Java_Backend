package recipes.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import recipes.Requests.RecipeRequest;
import recipes.Responses.RecipeAddedResponse;
import recipes.Responses.RecipeResponse;
import recipes.Services.RecipeService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/recipe")
public class RecipeController {
    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeResponse> getRecipe(@PathVariable int id) {
        return recipeService.getRecipe(id);
    }

    @PostMapping("/new")
    public ResponseEntity<RecipeAddedResponse> addRecipe(@Valid @RequestBody RecipeRequest request) {
        return recipeService.addRecipe(request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteRecipe(@PathVariable long id) {
        return recipeService.deleteRecipe(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateRecipe(@PathVariable long id, @Valid @RequestBody RecipeRequest request) {
        return recipeService.updateRecipe(id, request);
    }

    @GetMapping("/search")
    public ResponseEntity<List<RecipeResponse>> searchRecipes(@RequestParam(required = false) String category,
                                                                   @RequestParam(required = false) String name) {
        if (category == null && name == null || category != null && name != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        if (category != null) {
            return recipeService.searchRecipesByCategory(category);
        }

        return recipeService.searchRecipesByName(name);
    }
}
