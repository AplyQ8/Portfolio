package components.gameManager.ActionHandlers.TurnHandler;

import components.gameManager.IO.InfoString;
import components.player.Player;

import java.util.List;

public class TurnHandlerOneFigureGame extends TurnHandlerStrategy{

    public TurnHandlerOneFigureGame(List<Player> players){
        super(players);
        infoString = ioHandler.getInfoString(InfoString.TurnActionsMenu);
        actionsNumber = 5;
    }

    @Override
    protected void handleTurnEnd(Player player){
        playCardHandler.discardCards(player, player.getHandSize());
        player.drawCardsTillFullHand();
        player.setOccupiedMoveToken(false);
    }

    @Override
    protected void checkIfLastRound(Player player){
        if(player.isReachedTheEnd()){
            lastRound = true;
        }
    }

    @Override
    protected Player getUnfinishedFigure(Player player){
        if(player.isReachedTheEnd()){
            return null;
        }

        return player;
    }


}
