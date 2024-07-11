package h04.template;

import fopbot.World;
import h04.GameController;
import h04.chesspieces.Team;

import java.awt.Color;
import java.awt.Point;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import javax.swing.JLabel;
import java.beans.PropertyChangeEvent;

public class InputHandler {
    /**
     * The input queue.
     */
    private final BlockingDeque<Point> inputQueue = new LinkedBlockingDeque<>();

    public final GameController controller;

    private final JLabel statusLabel = new JLabel("");

    public InputHandler(final GameController controller) {
        this.controller = controller;
    }

    @SuppressWarnings("UnstableApiUsage")
    public void install() {
        final var guiPanel = World.getGlobalWorld().getGuiPanel();
        World.getGlobalWorld().getInputHandler().addFieldClickListener(e -> addInput(e.getField().getX(), e.getField().getY()));
        statusLabel.setFont(statusLabel.getFont().deriveFont(20.0f));
        guiPanel.add(statusLabel, JLabel.CENTER);
        guiPanel.addDarkModeChangeListener(this::onDarkModeChange);
        // trigger dark mode change to set the correct color
        guiPanel.setDarkMode(World.getGlobalWorld().getGuiPanel().isDarkMode());
    }

    public void onDarkModeChange(final PropertyChangeEvent e) {
        final var darkMode = (boolean) e.getNewValue();
        statusLabel.setForeground(darkMode ? Color.white : Color.black);
    }

    private void addInput(final int x, final int y) {
        inputQueue.add(new Point(x,y));
        System.out.println("Added input: "+x+","+y);
    }

    public Point getNextInput(final Team currentPlayer) {

        statusLabel.setText(currentPlayer.name()+" to move.");

        try {
            return inputQueue.take();
        } catch (final InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public JLabel getStatusLabel() {
        return statusLabel;
    }

    public void setStatusText(final String s) {
        statusLabel.setText(s);
    }
}
