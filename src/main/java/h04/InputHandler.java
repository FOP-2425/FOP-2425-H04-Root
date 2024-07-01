package h04;

import h04.chesspieces.Team;
import h04.template.InputHandlerTemplate;

import java.awt.Color;
import java.awt.Point;

public class InputHandler extends InputHandlerTemplate {


    public InputHandler(final GameController controller) {
        super(controller);
    }

    private void colorMoveFields(Point[] fields, Team currentPlayer) {
        for (Point field: fields) {
            setFieldColor(field, controller.getPieceAt(field) != null ? Color.RED : Color.GREEN);
        }
    }
}
