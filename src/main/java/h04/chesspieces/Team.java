package h04.chesspieces;

import java.awt.Color;

public enum Team {
    WHITE(Color.WHITE),
    BLACK(Color.BLACK);
    private final Color color;

    Team(final Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public boolean isOpponent(final Team other) {
        return this != other;
    }

    public Team getOpponent() {
        return this == WHITE ? BLACK : WHITE;
    }
}
