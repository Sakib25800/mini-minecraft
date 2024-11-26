public record Recipe(Item ingredient1, Item ingredient2, Item result) {
    public boolean matches(Item item1, Item item2) {
        return (item1 == ingredient1 && item2 == ingredient2) ||
                (item1 == ingredient2 && item2 == ingredient1);
    }
}

