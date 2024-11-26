/**
 * Represents a crafting recipe that consists of two ingredients and a result.
 * The recipe defines how two {@link Item} ingredients can be combined to create a resulting {@link Item}.
 */
public record Recipe(Item ingredient1, Item ingredient2, Item result) {

    /**
     * Checks if the provided ingredients match this recipe.
     * The order of the ingredients does not matter.
     *
     * @param item1 The first ingredient to check.
     * @param item2 The second ingredient to check.
     * @return {@code true} if the provided ingredients match the recipe, {@code false} otherwise.
     */
    public boolean matches(Item item1, Item item2) {
        return (item1 == ingredient1 && item2 == ingredient2) ||
                (item1 == ingredient2 && item2 == ingredient1);
    }
}

