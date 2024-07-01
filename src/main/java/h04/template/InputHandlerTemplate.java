package h04.template;

import fopbot.World;
import h04.GameController;
import h04.chesspieces.Team;

import java.awt.Color;
import java.awt.Point;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class InputHandlerTemplate {
    /**
     * The input queue.
     */
    private final BlockingDeque<Integer> inputQueue = new LinkedBlockingDeque<>();

    public final GameController controller;

    public InputHandlerTemplate(GameController controller) {
        this.controller = controller;
    }

    public void setFieldColor(final Point field, final Color c) {
        World.getGlobalWorld().getField(field.x, field.y).setFieldColor(c);
    }
}
