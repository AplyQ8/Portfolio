package components.gameManager.ActionHandlers;

import components.cards.Card;
import components.cards.ExpeditionCard;
import components.cards.Market;
import components.cards.enums.Color;
import components.gameManager.IO.IOHandler;
import components.player.Player;

public class MarketHandler {
    public Market market;
    public IOHandler ioHandler;

    public MarketHandler(){
        market = Market.getInstance();
        ioHandler = IOHandler.getInstance();
    }

    public void chooseCardsToBuyNewCards(Player player){
        ioHandler.printTable("Market", market.marketToString());

        float gold = 0;
        while(true){

            int cardNumber = chooseCard(player, gold);
            if(cardNumber == -1){
                break;
            }

            Card card = player.getCard(cardNumber);
            player.discardCard(cardNumber);

            gold += getGoldForCard(player, card);
        }

        handleBuyingCard(player, gold);
    }

    public void handleBuyingCard(Player player, float gold){
        ioHandler.printTable("Market", market.marketToString());

        while(true){
            int cardNumber = ioHandler.inputIntegerInRange("Choose a card to buy. 0 to stop.",
                    -1, market.getSize()) - 1;
            if(cardNumber == -1){
                break;
            }

            Card card = market.buyCard(cardNumber, gold);
            if(card != null){
                player.putNewCard(card);
                break;
            }
            else{
                ioHandler.printMessage("You can't buy this card");
            }
        }

    }

    public void transmitterAction(Player player){
        ioHandler.printTable("Market", market.marketToString());

        while(true){
            if(market.isEmpty()){
                ioHandler.printMessage("No cards in the market.");
                break;
            }

            int cardNumber = ioHandler.inputIntegerInRange("Choose a card. 0 to stop.",
                    -1, market.getSize()) - 1;
            if(cardNumber == -1){
                break;
            }

            Card card = market.getCardForFree(cardNumber);

            if(card != null){
                player.putNewCard(card);
                break;
            } else {
                ioHandler.printMessage("You can't get this card");
            }
        }
    }

    private int chooseCard(Player player, float gold){
        ioHandler.printMessage("Gold received: " + gold);
        ioHandler.printTable("Hand", player.handToString());

        return ioHandler.inputIntegerInRange(
                "Choose a card to use in the market. 0 to stop",
                -1, player.getHandSize()) - 1;
    }

    private float getGoldForCard(Player player, Card card){
        float gold = 0;

        if(card.getColor() == Color.YELLOW || card.getColor() == Color.WHITE){
            gold += ((ExpeditionCard)card).getPower();
        } else if (player.isChangeSymbolToken()) {
            player.setChangeSymbolToken(false);
            gold += ((ExpeditionCard)card).getPower();
        } else{
            gold += 0.5F;
        }

        return gold;
    }
}
