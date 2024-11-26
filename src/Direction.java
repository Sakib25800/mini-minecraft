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

    public static Optional<Direction> fromString(String label) {
        return Optional.ofNullable(LABEL_MAP.get(label.toLowerCase()));
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return label;
    }
}