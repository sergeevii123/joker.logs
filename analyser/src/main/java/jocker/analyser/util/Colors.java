package jocker.analyser.util;

/**
 * Created by ilyasergeev on 19/09/16.
 */
public enum Colors {
    RED("\u001B[31m"),
    BLUE("\u001B[34m"),
    PURPLE("\u001B[35m"),
    YELLOW("\u001B[33m"),
    BLACK("\u001B[30m");

    public String color;

    Colors(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return color;
    }
}
