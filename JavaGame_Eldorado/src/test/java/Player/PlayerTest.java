package Player;

import components.board.Blockade;
import components.board.HexagonType;
import components.board.Tile;
import components.cards.Card;
import components.cards.Deck;
import components.cards.ExpeditionCard;
import components.cards.enums.Color;
import components.cards.enums.NameToken;
import components.player.ColorChanger;
import components.player.Player;
import components.tokens.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    //Test commit
    private Player player;
    private Deck deck;
    private Card card1;
    private Card card2;
    private Token token;
    private Blockade blockade;

    @BeforeEach
    public void setUp() {
        // test card initialization
        card1 = new ExpeditionCard(NameToken.SCOUT, Color.GREEN, 5, false, 1);
        card2 = new ExpeditionCard(NameToken.COMPASS, Color.PURPLE, 10, true, 2);

        player = new Player();

        player.getDeck().getHand().clear();
        player.getDeck().getHand().add(card1);
        player.getDeck().getHand().add(card2);

        deck = player.getDeck();

        token = new Token();
        blockade = new Blockade(3, new Tile(), new Tile(), HexagonType.green, 1);
    }

    @Test
    public void testPlayerInit(){
        Player player = new Player();
        assertFalse(player.isSaveNextItemToken());
        assertFalse(player.isChangeSymbolToken());
        assertFalse(player.isOccupiedMoveToken());
        assertNotNull(player.getDeck());
        assertNotNull(player.getTokens());
        assertNotNull(player.getBlockades());
    }
    @Test
    public void testGetCard() {
        assertEquals(card1, player.getCard(0));
    }

    @Test
    public void testGetHandSize() {
        assertEquals(2, player.getHandSize());
    }

    @Test
    public void testPutNewCard() {
        player.putNewCard(card1);
        assertTrue(deck.getDiscardPile().contains(card1));
    }

    @Test
    public void testDiscardCard() throws Exception {
        player.discardCard(0);
        assertFalse(deck.getHand().contains(card1));
        assertTrue(deck.getDiscardPile().contains(card1));
    }

    @Test
    public void testRemoveCardFromGame() throws Exception {
        player.removeCardFromGame(0);
        assertFalse(deck.getHand().contains(card1));
    }

    @Test
    public void testDrawCard() {
        deck.getDrawPile().clear();
        deck.getDrawPile().add(card1);
        player.drawCard();
        assertTrue(deck.getHand().contains(card1));
        assertFalse(deck.getDrawPile().contains(card1));
    }

    @Test
    public void testDrawCardsTillFullHand() {
        /*
        Draw pile has more than 4 cards
        */
        deck.getHand().clear();
        deck.getDrawPile().add(card1);
        deck.getDrawPile().add(card2);
        player.drawCardsTillFullHand();
        assertEquals(4, deck.getHand().size());
    }

    @Test
    public void testDrawCardsTillFullHand_DrawPileHasTwo() {
        /*
        Draw pile has less than 4 cards
        */
        deck.getDrawPile().clear();
        deck.getHand().clear();
        deck.getDrawPile().add(card1);
        deck.getDrawPile().add(card2);
        player.drawCardsTillFullHand();
        assertEquals(2, deck.getHand().size());
    }

    @Test
    public void testHandToString() {
        ColorChanger colorChanger = ColorChanger.getInstance();

        // Ожидаемые строки с цветом
        String expectedGreen = colorChanger.colorCode("green") + "GREEN" + colorChanger.colorCode("default");
        String expectedPurple = colorChanger.colorCode("purple") + "PURPLE" + colorChanger.colorCode("default");

        // Ожидаемый результат
        String expected = "1. Name: SCOUT Color: " + expectedGreen + " Power: 1 Price: 5 \n" +
                "2. Name: COMPASS Color: " + expectedPurple + " Power: 2 Price: 10 \n";

        // Проверка
        assertEquals(expected, player.handToString());
    }

    @Test
    public void testPutNewToken() {
        player.putNewToken(token);
        assertEquals(1, player.getTokensNumber());
    }

    @Test
    public void testDiscardToken() {
        player.putNewToken(token);
        player.discardToken(0);
        assertEquals(0, player.getTokensNumber());
    }

    @Test
    public void testDiscardCardWithSaveNextItemToken() {
        Player player = new Player();
        player.setSaveNextItemToken(true);
        Card card = new ExpeditionCard(NameToken.ADVENTURER, Color.GREEN, 5, true, 1);
        player.getDeck().getHand().clear();
        player.getDeck().getHand().add(card);

        player.discardCard(0);

        assertTrue(player.getDeck().getDiscardPile().contains(card));
        assertFalse(player.isSaveNextItemToken());
    }

    @Test
    public void testDiscardCardWithoutSaveNextItemToken() {
        Player player = new Player();
        player.setSaveNextItemToken(false);
        Card card = new ExpeditionCard(NameToken.SCOUT, Color.GREEN, 5, false, 1);
        player.getDeck().getHand().clear();
        player.getDeck().getHand().add(card);

        player.discardCard(0);

        assertFalse(player.getDeck().getHand().contains(card));
        assertFalse(player.isSaveNextItemToken());
    }


    @Test
    public void testPutNewBlockade() {
        player.putNewBlockade(blockade);
        assertEquals(1, player.getBlockadesNumber());
    }

    @Test
    public void testGetTotalBlockadesValue() {
        player.putNewBlockade(blockade);
        assertEquals(3, player.getTotalBlockadesValue());
    }
}
