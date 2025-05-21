package cards;


import components.cards.Card;
import components.cards.Market;
import components.cards.MarketCardFactory;
import static components.cards.enums.NameToken.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class MarketTest {

    private Market market;

    @BeforeEach
    public void create() {
        market = Market.getInstance();
    }

    @Test
    public void testSide() {
        assertEquals(24, market.getSide().size());
    }

    @Test
    public void testMarket() {
        assertEquals(6, market.getMarket().size());
        assertTrue(testUniqueness());
    }

    private boolean testUniqueness() {
        Set<Card> testSet = new HashSet<>(market.getMarket().values());
        return testSet.size() == 6;
    }

    @Test
    public void testToString() {
        System.out.println(market.marketToString());
        market.getMarket().put(2, null);
        market.getSide().put(8, null);
        System.out.println(market.marketToString());
    }


    @Test
    public void testBuyCard() {
        testLoader();
        assertNull(market.buyCard(2, 2));
        assertNotNull(market.buyCard(2, 3));
        market.getMarket().put(1, null);
        assertNull(market.buyCard(1, 3));
        assertNull(market.buyCard(8, 2));
        assertNotNull(market.getMarket().get(1));
        market.getMarket().put(1, null);
        assertNotNull(market.buyCard(9, 4));
        assertNull(market.buyCard(9, 4));
    }

    @Test
    public void testUniqueCardNumber() {
        testLoader();
        assertEquals(18, market.uniqueCardsNumber());
        market.getSide().put(13, null);
        assertEquals(18, market.uniqueCardsNumber());
        market.getSide().put(14, null);
        assertEquals(18, market.uniqueCardsNumber());
        market.getMarket().put(1, null);
        assertEquals(17, market.uniqueCardsNumber());
    }

    @Test
    public void testGetCardForFree() {
        testLoader();
        assertEquals(PHOTOGRAPHER, market.getCardForFree(1).getName());
        market.getMarket().put(1, null);
        assertNull(market.getCardForFree(1));
        assertEquals(GIANT_MACHETE, market.getCardForFree(8).getName());
        assertEquals(GIANT_MACHETE, market.getMarket().get(1).getName());
        assertNull(market.getMarket().get(8));
    }

    private void testLoader() {
        market.getMarket().clear();
        List<Card> all = new MarketCardFactory().loadCards();
        int i = 1;
        for (Card c : all) {
            if (market.getMarket().size() < 6) {
                market.getMarket().put(i, c);
            } else {
                market.getSide().put(i, c);
            }
            i++;
        }
    }
}
