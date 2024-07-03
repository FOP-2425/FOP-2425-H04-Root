package h04.chesspieces;

import fopbot.RobotFamily;
import fopbot.SvgBasedRobotFamily;

import java.awt.Color;

public class Families {
    public static RobotFamily PAWN_WHITE = new SvgBasedRobotFamily(
        "PAWN_WHITE",
        "/pieces/pawn_white.svg",
        "/pieces/pawn_white.svg",
        Color.WHITE
    );
    public static RobotFamily PAWN_BLACK = new SvgBasedRobotFamily(
        "PAWN_BLACK",
        "/pieces/pawn_black.svg",
        "/pieces/pawn_black.svg",
        Color.BLACK
    );
    public static RobotFamily ROOK_WHITE = new SvgBasedRobotFamily(
        "ROOK_WHITE",
        "/pieces/rook_white.svg",
        "/pieces/rook_white.svg",
        Color.WHITE
    );
    public static RobotFamily ROOK_BLACK = new SvgBasedRobotFamily(
        "ROOK_BLACK",
        "/pieces/rook_black.svg",
        "/pieces/rook_black.svg",
        Color.BLACK
    );
    public static RobotFamily KNIGHT_WHITE = new SvgBasedRobotFamily(
        "KNIGHT_WHITE",
        "/pieces/knight_white.svg",
        "/pieces/knight_white.svg",
        Color.WHITE
    );
    public static RobotFamily KNIGHT_BLACK = new SvgBasedRobotFamily(
        "KNIGHT_BLACK",
        "/pieces/knight_black.svg",
        "/pieces/knight_black.svg",
        Color.BLACK
    );
    public static RobotFamily BISHOP_WHITE = new SvgBasedRobotFamily(
        "BISHOP_WHITE",
        "/pieces/bishop_white.svg",
        "/pieces/bishop_white.svg",
        Color.WHITE
    );
    public static RobotFamily BISHOP_BLACK = new SvgBasedRobotFamily(
        "BISHOP_BLACK",
        "/pieces/bishop_black.svg",
        "/pieces/bishop_black.svg",
        Color.BLACK
    );
    public static RobotFamily QUEEN_WHITE = new SvgBasedRobotFamily(
        "QUEEN_WHITE",
        "/pieces/queen_white.svg",
        "/pieces/queen_white.svg",
        Color.WHITE
    );
    public static RobotFamily QUEEN_BLACK = new SvgBasedRobotFamily(
        "QUEEN_BLACK",
        "/pieces/queen_black.svg",
        "/pieces/queen_black.svg",
        Color.BLACK
    );
    public static RobotFamily KING_WHITE = new SvgBasedRobotFamily(
        "KING_WHITE",
        "/pieces/king_white.svg",
        "/pieces/king_white.svg",
        Color.WHITE
    );
    public static RobotFamily KING_BLACK = new SvgBasedRobotFamily(
        "KING_BLACK",
        "/pieces/king_black.svg",
        "/pieces/king_black.svg",
        Color.BLACK
    );
}
