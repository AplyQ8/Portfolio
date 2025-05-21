package Deck;
import components.cards.Card;
import components.cards.Deck;
import components.cards.ExpeditionCard;
import components.cards.enums.Color;
import components.cards.enums.NameToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
public class DeckTest {
    private Deck deck;
    private Card card1;
    private Card card2;
    private Card card3;
    private Card card4;

    @BeforeEach
    public void setUp() {
        deck = new Deck();

        // Initialization of test cards
        card1 = new ExpeditionCard(NameToken.TRAVELLER, Color.YELLOW, 5, false, 1);
        card2 = new ExpeditionCard(NameToken.SCOUT, Color.GREEN, 1, false, 1);
        card3 = new ExpeditionCard(NameToken.COMPASS, Color.PURPLE, 6, true, 2);
        card4 = new ExpeditionCard(NameToken.SAILOR, Color.BLUE, 3, false, 2);

        // Add cards into deck
        deck.getHand().clear();
        deck.getHand().add(card1);
        deck.getHand().add(card2);
        deck.getHand().add(card3);
        deck.getHand().add(card4);
    }

    @Test
    public void testDeckInit() {
        Deck testDeck = new Deck();
        assertEquals(4, deck.getHand().size());
        assertEquals(4, deck.getDrawPile().size());
        assertEquals(0, deck.getDiscardPile().size());
    }
    @Test
    public void testReturnHand() {
        List<Card> hand = deck.returnHand();
        assertEquals(4, hand.size());
        assertTrue(hand.contains(card1));
        assertTrue(hand.contains(card2));
        assertTrue(hand.contains(card3));
        assertTrue(hand.contains(card4));
    }

    @Test
    public void testAddToDiscardPile() {
        deck.AddToDiscardPile(card1);
        assertEquals(1, deck.getDiscardPile().size());
        assertTrue(deck.getDiscardPile().contains(card1));
    }
    @Test
    public void testAddToHand() {
        deck.AddToHand(card1);
        assertEquals(5, deck.getHand().size());
        assertTrue(deck.getHand().contains(card1));
    }
    @Test
    public void testDeleteFromHand() {
        boolean removed = deck.DeleteFromHand(card1);
        assertTrue(removed);
        assertEquals(3, deck.getHand().size());
        assertFalse(deck.getHand().contains(card1));
    }
    @Test
    public void testDrawCard() {
        deck.getDrawPile().clear();
        deck.getDrawPile().add(card1);
        deck.drawCard();
        assertEquals(5, deck.getHand().size());
        assertTrue(deck.getHand().contains(card1));
        assertEquals(0, deck.getDrawPile().size());
    }
    @Test
    public void testShuffleAndClearDiscardPile() {
        deck.getDrawPile().clear();
        deck.getDiscardPile().clear();
        deck.getDiscardPile().add(card1);
        deck.getDiscardPile().add(card2);
        deck.shuffleAndClearDiscardPile();
        assertEquals(2, deck.getDrawPile().size());
        assertTrue(deck.getDrawPile().contains(card1));
        assertTrue(deck.getDrawPile().contains(card2));
        assertEquals(0, deck.getDiscardPile().size());
    }
}
