package components.gameManager.ActionHandlers.TurnHandler;

import components.gameManager.IO.InfoString;
import components.player.Player;

import java.util.List;

public class TurnHandlerTwoFiguresGame extends TurnHandlerStrategy{

    public TurnHandlerTwoFiguresGame(List<Player> players){
        super(players);
        infoString = ioHandler.getInfoString(InfoString.TurnActionsMenu);
        infoString += "\n6. Change a game figure";
        actionsNumber = 6;
    }

    @Override
    protected void handleTurnEnd(Player player){
        playCardHandler.discardCards(player, player.getHandSize());
        player.drawCardsTillFullHand();
        resetOccupiedMoveToken(player);
    }

    private void resetOccupiedMoveToken(Player player){
        player.setOccupiedMoveToken(false);
        Player secondFigure = getSecondFigure(player);
        if(secondFigure != null){
            secondFigure.setOccupiedMoveToken(false);
        }
    }

    @Override
    protected void checkIfLastRound(Player player){
        if(player.isReachedTheEnd() && getSecondFigure(player) != null &&
                getSecondFigure(player).isReachedTheEnd()){
            lastRound = true;
        }
    }

    @Override
    protected Player handleChangeFigure(Player player){
        Player secondFigure = getSecondFigure(player);

        if(secondFigure.isReachedTheEnd()){
            ioHandler.printMessage("This figure has already reached the end");
            return player;
        }

        return secondFigure;
    }

    private Player getSecondFigure(Player player){
        Player secondFigure = null;

        for(int i = 0; i < players.size(); i++){
            if(players.get(i) == player){
                secondFigure = players.get((i+2)%4);
                break;
            }
        }
        return  secondFigure;
    }

    @Override
    protected Player getUnfinishedFigure(Player player){
        Player playingFigure = player;

        if(playingFigure.isReachedTheEnd()){
            playingFigure = getSecondFigure(player);
            if(playingFigure != null && playingFigure.isReachedTheEnd()){
                playingFigure = null;
            }
        }

        return playingFigure;
    }

}
