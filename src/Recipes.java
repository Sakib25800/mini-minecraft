import java.util.List;
import java.util.Optional;

/**
 * Manages a collection of {@link Recipe} objects and provides methods for finding a recipe
 * based on the provided ingredients.
 */
public class Recipes {

    /**
     * A static list of predefined recipes.
     */
    private static final List<Recipe> RECIPES = List.of(
            new Recipe(Item.BLAZE_POWDER, Item.ENDER_PEARL, Item.EYE_OF_ENDER)
    );

    /**
     * Finds the recipe that matches the given ingredients.
     *
     * @param item1 The first ingredient to check.
     * @param item2 The second ingredient to check.
     * @return An {@link Optional} containing the matching {@link Recipe} if found, or an empty {@link Optional} if no match is found.
     */
    public static Optional<Recipe> findRecipe(Item item1, Item item2) {
        return RECIPES.stream()
                .filter(recipe -> recipe.matches(item1, item2))
                .findFirst();
    }
}