package components.player;

import components.board.Blockade;
import components.board.Hexagon;
import components.board.Tile;
import components.cards.Card;
import components.cards.Deck;
import components.cards.enums.Color;
import components.tokens.Token;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class Player {
    @Getter
    private String color;
    @Getter
    private List<Token> tokens;
    @Getter
    private List<Blockade> blockades;

    @Getter @Setter
    private Deck deck;
    @Getter @Setter
    private Hexagon hexagon;
    @Getter @Setter
    private Tile currentTile;

    @Getter @Setter
    private Color playedCardColor;
    @Getter @Setter
    private int power;

    @Getter @Setter
    private boolean reachedTheEnd;

    @Getter @Setter
    private boolean saveNextItemToken;
    @Getter @Setter
    private boolean changeSymbolToken;
    @Getter @Setter
    private boolean occupiedMoveToken;

    public Player(){
        saveNextItemToken = false;
        changeSymbolToken = false;
        occupiedMoveToken = false;
        power = 0;
        playedCardColor = Color.GREEN;

        this.deck = new Deck();
        tokens = new ArrayList<Token>();
        blockades = new ArrayList<>();
    }

    public Player(String color){
        this();
        this.color = color;
    }

    public Card getCard(int cardNumber){
        return deck.returnHand().get(cardNumber);
    }
    public int getHandSize(){
        return deck.returnHand().size();
    }
    public void putNewCard(Card card){
        deck.AddToDiscardPile(card);
    }
    public void discardCard(int cardNumber) {
        /*
        Puts the card to discard pile.
        If the card is an item, removes it from the game.
        If the saveNextItemToken is active, does not remove the card, but saveNextItemToken is set to false.
         */
        if(!deck.returnHand().get(cardNumber).isRemovable()){
            discardCardFromHandAndPutInDiscardPile(cardNumber);
            return;
        }
        if(saveNextItemToken){
            discardCardFromHandAndPutInDiscardPile(cardNumber);
            saveNextItemToken = false;
            return;
        }
        removeCardFromGame(cardNumber);

    }

    private void discardCardFromHandAndPutInDiscardPile(int cardNumber) {
        deck.AddToDiscardPile(deck.returnHand().get(cardNumber));
        removeCardFromGame(cardNumber);
    }
    public void removeCardFromGame(int cardNumber) {
        deck.DeleteFromHand(deck.returnHand().get(cardNumber));
    }
    public void drawCard(){
        /*
        Draw card from draw pile to hand.
        If draw pile is empty, shuffle discard pile.
         */
        deck.drawCard();
    }
    public void drawCardsTillFullHand(){
        int cardsToDraw = 4 - deck.returnHand().size();
        if(cardsToDraw <= 0)
            return;
        for(int i = 0; i < cardsToDraw; i++){
            drawCard();
        }
    }
    public String handToString(){
        StringBuilder stringHand = new StringBuilder();
        ColorChanger colorChanger = ColorChanger.getInstance();
        for (int i = 0; i < deck.returnHand().size(); i++) {
            stringHand.append(i + 1).append(". ");
            stringHand.append("Name: ").append(deck.returnHand().get(i).getName().toString()).append(" ");

            String color = deck.returnHand().get(i).getColor().toString();
            String colorCode = colorChanger.colorCode(color);
            String resetCode = colorChanger.colorCode("default");

            stringHand.append("Color: ").append(colorCode).append(color).append(resetCode).append(" ");
            stringHand.append("Power: ").append(deck.returnHand().get(i).getPower()).append(" ");
            stringHand.append("Price: ").append(deck.returnHand().get(i).getPrice()).append(" ");
            stringHand.append("\n");
        }
        return stringHand.toString();
    }

    public Token getToken(int tokenNumber){
        return tokens.get(tokenNumber);
    }
    public int getTokensNumber(){
        return tokens.size();
    }
    public void putNewToken(Token token){
        tokens.add(token);
    }
    public void discardToken(int tokenNumber){
        tokens.remove(tokenNumber);
    }
    public String tokensToString(){
        /*
        Text representation of all tokens, like with cards.
         */
        StringBuilder returnString = new StringBuilder();
        for(int i = 0; i < tokens.size(); i++){
            returnString.append(i+1).append(tokens.get(i).getTokenAction()).append("\n");
        }
        return returnString.toString();
    }
    public int getBlockadesNumber(){
        return blockades.size();
    }
    public int getTotalBlockadesValue(){
        return blockades.stream().mapToInt(Blockade::getPower).sum();
    }
    public void putNewBlockade(Blockade blockade){
        blockades.add(blockade);
    }

    public void equalizeResources(Player player2){
        this.deck = player2.getDeck();
        this.tokens = player2.getTokens();
        this.blockades = player2.getBlockades();
    }
}
