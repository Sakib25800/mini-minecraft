import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public enum Direction {
    NORTH("north"),
    EAST("east"),
    SOUTH("south"),
    WEST("west");

    private static final Map<String, Direction> LABEL_MAP =
            Arrays.stream(values())
                    .collect(Collectors.toMap(
                            direction -> direction.label.toLowerCase(),
                            direction -> direction
                    ));
    private final String label;


    Direction(String label) {
        this.label = label;
    }

    /**
     * Converts a string label to its corresponding {@link Direction} enum.
     *
     * @param label the string label representing a direction (e.g., "north", "south")
     * @return an {@link Optional} containing the corresponding {@link Direction} if found,
     * or an empty {@link Optional} if no match is found
     */
    public static Optional<Direction> fromString(String label) {
        return Optional.ofNullable(LABEL_MAP.get(label.toLowerCase()));
    }

    @Override
    public String toString() {
        return label;
    }
}