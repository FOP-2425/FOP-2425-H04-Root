package h04.template;

import fopbot.*;
import fopbot.Robot;
import h04.*;

import java.awt.*;
import java.util.*;

/**
 * A {@link GameControllerTemplate} controls the game loop and the {@link Robot}s and checks the win condition.
 */
public abstract class GameControllerTemplate {
    /**
     * The {@link GameInputHandler} that handles the input of the user.
     */
    private final GameInputHandler inputHandler = new GameInputHandler();

    /**
     * The {@link Robot}s that are controlled by the {@link GameControllerTemplate}.
     */
    protected final ArrayList<Robot> allPieces = new ArrayList<>();



    /**
     * Starts the game loop.
     */
    public void startGame() {
        System.out.println("Starting game...");
    }

    /**
     * Stops the game loop.
     */
    public void stopGame(boolean winner) {
    }

    /**
     * Sets up the game.
     */
    protected void setup() {
        setupWorld();
        setupTheme();
        //this.inputHandler.install();
    }

    public void setupTheme() {
        //noinspection UnstableApiUsage
        World.getGlobalWorld().getGuiPanel().setColorProfile(
            ColorProfile.DEFAULT.toBuilder()
                .customFieldColorPattern(
                    (cp, p) -> (p.x + p.y) % 2 == 0 ? cp.fieldColorLight() : cp.fieldColorDark()
                )
                .build()
        );
        //noinspection UnstableApiUsage
        World.getGlobalWorld().getGuiPanel().setColorProfile(
            ColorProfile.DEFAULT.toBuilder()
                .backgroundColorDark(Color.BLACK)
                .backgroundColorLight(Color.BLACK)
                .innerBorderColorLight(Color.BLACK)
                .InnerBorderColorDark(Color.BLACK)
                .outerBorderColorDark(Color.BLACK)
                .outerBorderColorLight(Color.BLACK)
                .build()
        );
    }

    /**
     * Initializes the {@link World} and adds the {@link Robot}s to it.
     */
    public void setupWorld() {
        World.setSize(8, 8);

        World.setDelay(0);
        World.setVisible(true);
        World.getGlobalWorld().setDrawTurnedOffRobots(false);
    }
}
