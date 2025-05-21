package components.gameManager.ActionHandlers.TurnHandler;

import components.gameManager.ActionHandlers.MarketHandler;
import components.gameManager.ActionHandlers.MovementHandler;
import components.gameManager.ActionHandlers.PlayCardHandler;
import components.gameManager.IO.IOHandler;
import components.player.Player;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class TurnHandlerStrategy {

    @Setter
    public List<Player> players;

    public IOHandler ioHandler;
    @Getter
    public MarketHandler marketHandler;
    @Getter
    public MovementHandler movementHandler;
    public PlayCardHandler playCardHandler;
    protected boolean lastRound;
    protected boolean turnEnd;
    protected String infoString;
    protected int actionsNumber;

    public TurnHandlerStrategy(List<Player> players){
        ioHandler = IOHandler.getInstance();
        this.players = players;
        marketHandler = new MarketHandler();
        movementHandler = new MovementHandler();
        playCardHandler = new PlayCardHandler(movementHandler, marketHandler);
    }
    public boolean handleTurn(Player player){

        turnEnd = false;

        while(!turnEnd){

            player = getUnfinishedFigure(player);
            if(player == null){
                break;
            }

            int actionNumber = chooseAction(player);

            player = performAction(player, actionNumber);

            checkIfLastRound(player);

        }

        return lastRound;
    }

    private Player performAction(Player player, int actionNumber){
        switch (actionNumber){
            case 1:
                playCardHandler.handlePlayingCard(player);
                break;
            case 2:
                playCardHandler.handlePlayingToken(player);
                break;
            case 3:
                movementHandler.handleMovement(player);
                break;
            case 4:
                marketHandler.chooseCardsToBuyNewCards(player);
                break;
            case 5:
                handleTurnEnd(player);
                turnEnd = true;
                break;
            case 6:
                player = handleChangeFigure(player);
                break;
            default:
                ioHandler.printMessage("Wrong Action");
        }
        return player;
    }

    protected void handleTurnEnd(Player player){}
    protected Player handleChangeFigure(Player player){
        return player;
    }
    protected void checkIfLastRound(Player player){}

    protected Player getUnfinishedFigure(Player player){
        return player;
    }

    protected int chooseAction(Player player){
        ioHandler.printMessage(player.getColor() + " player turn");
        ioHandler.printMessage("Position: " + player.getHexagon().getQCoord() + ";" + player.getHexagon().getRCoord());

        ioHandler.printMessage("Draw pile size: " + player.getDeck().getDrawPile().size() +
                ",  Discard pile size: " + player.getDeck().getDiscardPile().size());
        ioHandler.printTable("Hand", player.handToString());

        return ioHandler.inputIntegerInRange(infoString, 0, actionsNumber);
    }

    public void placePlayersAtStart() {
        movementHandler.placePlayersAtStart(players);
    }

    public void setCaveRules(boolean caveRules){
        movementHandler.setCaveRules(caveRules);
    }

}
