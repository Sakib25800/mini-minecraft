import java.util.List;
import java.util.Optional;

public class Recipes {
    private static final List<Recipe> RECIPES = List.of(
            new Recipe(Item.BLAZE_POWDER, Item.ENDER_PEARL, Item.EYE_OF_ENDER)
    );

    public static Optional<Recipe> findRecipe(Item item1, Item item2) {
        return RECIPES.stream()
                .filter(recipe -> recipe.matches(item1, item2))
                .findFirst();
    }
}