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
     * The {@link Timer} that controls the game loop.
     */
    private final Timer gameLoopTimer = new Timer();

    /**
     * The {@link GameInputHandler} that handles the input of the user.
     */
    //private final GameInputHandler inputHandler = new GameInputHandler();

    /**
     * The total umber of coins in the map.
     */
    protected int totalCoins;

    /**
     * The {@link Robot}s that are controlled by the {@link GameControllerTemplate}.
     */
    protected final ArrayList<Robot> robots = new ArrayList<>();

    /**
     * Starts the game loop.
     */
    public void startGame() {
        System.out.println("Starting game...");
    }

    /**
     * Stops the game loop.
     */
    public void stopGame(boolean won) {
        endscreen(won?Color.GREEN:Color.RED);
    }

    public void endscreen(Color color) {
        World.getGlobalWorld().getGuiPanel().setColorProfile(
            ColorProfile.DEFAULT.toBuilder()
                .backgroundColorDark(Color.BLACK)
                .backgroundColorLight(Color.BLACK)
                .fieldColorDark(color)
                .fieldColorLight(color)
                .innerBorderColorLight(color)
                .InnerBorderColorDark(color)
                .wallColorDark(Color.BLUE)
                .wallColorLight(Color.BLUE)
                .outerBorderColorDark(Color.BLUE)
                .outerBorderColorLight(Color.BLUE)
                .coinColorDark(color)
                .coinColorLight(color)
                .build()
        );
        World.getGlobalWorld().getGuiPanel().updateGui();
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
        World.getGlobalWorld().getGuiPanel().setColorProfile(
            ColorProfile.DEFAULT.toBuilder()
                .backgroundColorDark(Color.BLACK)
                .backgroundColorLight(Color.BLACK)
                .fieldColorDark(Color.BLACK)
                .fieldColorLight(Color.BLACK)
                .innerBorderColorLight(Color.BLACK)
                .InnerBorderColorDark(Color.BLACK)
                .wallColorDark(Color.BLUE)
                .wallColorLight(Color.BLUE)
                .outerBorderColorDark(Color.BLUE)
                .outerBorderColorLight(Color.BLUE)
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
