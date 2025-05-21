package cards;


import components.cards.Deck;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DeckTest {

    @Test
    public void testDeckInit() {
        Deck deck = new Deck();
        assertEquals(4, deck.getHand().size());
        assertEquals(4, deck.getDrawPile().size());
        assertEquals(0, deck.getDiscardPile().size());
    }
}
