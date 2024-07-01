package h04;

import h04.template.GameControllerTemplate;
import h04.chesspieces.King;

public class GameController extends GameControllerTemplate {
    public GameController() {
        super();
        setup();
    }

    @Override
    public boolean checkWinCondition() {
        King[] kings = getKings();
        return (kings[0].isTurnedOff() || kings[1].isTurnedOff());
    }
}
