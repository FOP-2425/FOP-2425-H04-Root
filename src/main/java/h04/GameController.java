package h04;

import h04.template.ChessUtils;
import h04.template.GameControllerTemplate;
import h04.chesspieces.King;

public class GameController extends GameControllerTemplate {
    public GameController() {
        super();
        setup();
    }

    @Override
    public boolean checkWinCondition() {
        final King[] kings = ChessUtils.getKings();
        return (kings[0].isTurnedOff() || kings[1].isTurnedOff());
    }
}
