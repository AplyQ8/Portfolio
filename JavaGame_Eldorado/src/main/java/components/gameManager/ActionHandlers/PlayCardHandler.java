package components.gameManager.ActionHandlers;

import components.cards.Card;
import components.cards.ExpeditionCard;
import components.cards.enums.Color;
import components.cards.enums.NameToken;
import components.gameManager.IO.IOHandler;
import components.gameManager.IO.InfoString;
import components.player.Player;
import components.tokens.ExpeditionToken;
import components.tokens.Token;
import components.tokens.TokenAction;

public class PlayCardHandler {
    public IOHandler ioHandler;
    public MovementHandler movementHandler;
    public MarketHandler marketHandler;

    public PlayCardHandler(MovementHandler movementHandler, MarketHandler marketHandler){
        this.movementHandler = movementHandler;
        this.marketHandler = marketHandler;
        ioHandler = IOHandler.getInstance();
    }

    public void handlePlayingCard(Player player){
        ioHandler.printTable("Hand", player.handToString());
        int cardNumber = ioHandler.inputIntegerInRange("Choose a card to play:",
                -1, player.getHandSize()) - 1;
        if(cardNumber == -1){
            return;
        }

        Card card = player.getCard(cardNumber);
        player.discardCard(cardNumber);

        if (card.getColor() == Color.PURPLE){
            handleCardAction(player, card.getName());
        }
        else {
            playExpeditionCard(player, card);
        }
    }

    public void playExpeditionCard(Player player, Card card){
        Color color = card.getColor();
        int power = ((ExpeditionCard)card).getPower();
        if(canChangeCardColor(player, color)){
            String infoString = ioHandler.getInfoString(InfoString.ChooseCardColor);
            int colorNumber = ioHandler.inputIntegerInRange(infoString, 0, 3);

            color = switch (colorNumber) {
                case 1 -> Color.GREEN;
                case 2 -> Color.BLUE;
                case 3 -> Color.YELLOW;
                default -> color;
            };

            player.setChangeSymbolToken(false);

        }

        player.setPlayedCardColor(color);
        player.setPower(power);
    }

    private boolean canChangeCardColor(Player player, Color color){
        return color == Color.WHITE || player.isChangeSymbolToken();
    }


    public void handlePlayingToken(Player player){
        ioHandler.printTable("Tokens", player.tokensToString());
        int tokenNumber = ioHandler.inputIntegerInRange("Choose a token to play. 0 to cancel",
                -1, player.getTokensNumber()) - 1;

        if(tokenNumber == -1){
            return;
        }

        Token token = player.getToken(tokenNumber);
        player.discardToken(tokenNumber);

        handleTokenAction(player, token);
    }

    private void handleCardAction(Player player, NameToken action){
        switch (action){
            case TRANSMITTER:
                marketHandler.transmitterAction(player);
                break;
            case CARTOGRAPHER:
                drawCards(player, 2);
                break;
            case SCIENTIST:
                drawCards(player, 1);
                removeCardAction(player, 1);
                break;
            case COMPASS:
                drawCards(player, 3);
                break;
            case TRAVEL_LOG:
                drawCards(player, 2);
                removeCardAction(player, 2);
                break;
            case NATIVE:
                movementHandler.nativeAction(player);
                break;
        }
    }

    private void drawCards(Player player, int cardsNumber){
        for(int i = 0; i < cardsNumber; i++){
            player.drawCard();
        }
    }

    private void handleTokenAction(Player player, Token token){

        TokenAction tokenAction = token.getTokenAction();

        switch (tokenAction){
            case Expedition:
                expeditionTokenAction(player, token);
                break;
            case DrawCard:
                player.drawCard();
                break;
            case RemoveCard:
                removeCardAction(player, 1);
                break;
            case ReplaceHand:
                replaceHandTokenAction(player);
                break;
            case Native:
                movementHandler.nativeAction(player);
                break;
            case SaveItem:
                player.setSaveNextItemToken(true);
                break;
            case ChangeSymbol:
                player.setChangeSymbolToken(true);
                break;
            case MoveToOccupied:
                player.setOccupiedMoveToken(true);
                break;
        }
    }

    private void expeditionTokenAction(Player player, Token token){
        Color color = ((ExpeditionToken)token).getColor();
        int power = ((ExpeditionToken)token).getPower();
        player.setPlayedCardColor(color);
        player.setPower(power);
    }

    private void replaceHandTokenAction(Player player){
        int maxCardsToDiscard = Math.min(player.getHandSize(), 4);
        int discardedCardsNumber = discardCards(player, maxCardsToDiscard);
        for(int i = 0; i < discardedCardsNumber; i++){
            player.drawCard();
        }
    }

    private void removeCardAction(Player player, int cardsNumberToRemove){
        while(cardsNumberToRemove > 0){
            ioHandler.printTable("Hand", player.handToString());

            int cardNumber = ioHandler.inputIntegerInRange("Choose a card to remove. 0 to stop.",
                    -1, player.getHandSize()) - 1;

            if(cardNumber == -1){
                break;
            }

            player.removeCardFromGame(cardNumber);
            cardsNumberToRemove -= 1;
        }
    }

    public int discardCards(Player player, int discardCardsLimit){
        int discardedCardsNumber = 0;
        while(discardCardsLimit > 0){
            ioHandler.printTable("Hand", player.handToString());
            int cardNumber = ioHandler.inputIntegerInRange("Choose a card to discard. 0 to stop",
                    -1, player.getHandSize()) - 1;

            if(cardNumber == -1){
                break;
            }

            player.discardCard(cardNumber);
            discardCardsLimit -= 1;
            discardedCardsNumber += 1;
        }

        return discardedCardsNumber;
    }
}
