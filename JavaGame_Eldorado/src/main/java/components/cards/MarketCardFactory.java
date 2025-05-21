package components.cards;

import java.util.Arrays;
import java.util.List;

import static components.cards.enums.Color.*;
import static components.cards.enums.NameToken.*;

public class MarketCardFactory implements AbstractCardFactory {

    @Override
    public List<Card> loadCards() {
        return Arrays.asList(
                //YELLOW
                new ExpeditionCard(PHOTOGRAPHER, YELLOW, 1, false, 2),
                new ExpeditionCard(JOURNALIST, YELLOW, 3, false, 3),
                new ExpeditionCard(TREASURE_CHEST, YELLOW, 3, false, 4),
                new ExpeditionCard(MILLIONAIRE, YELLOW, 5, false, 4),

                //GREEN
                new ExpeditionCard(SCOUT, GREEN, 1, false, 2),
                new ExpeditionCard(TRAILBLAZER, GREEN, 3, false, 3),
                new ExpeditionCard(PIONEER, GREEN, 5, false, 5),
                new ExpeditionCard(GIANT_MACHETE, GREEN, 3, true, 6),

                //BLUE
                new ExpeditionCard(CAPTAIN, BLUE, 2, false, 3),

                //WHITE
                new ExpeditionCard(JACK_OF_ALL_TRADES, WHITE, 2, false, 1),
                new ExpeditionCard(ADVENTURER, WHITE, 4, false, 2),
                new ExpeditionCard(PROP_PLANE, WHITE, 4, true, 3),

                //ACTION
                new SpecialCard(TRANSMITTER, PURPLE, 4, true, 0, "Choose any one card from the market, (off board cards are allowed) and place it on your discard pile."),
                new SpecialCard(TRANSMITTER, PURPLE, 4, true, 0, "Choose any one card from the market, (off board cards are allowed) and place it on your discard pile."),
                new SpecialCard(TRANSMITTER, PURPLE, 4, true, 0, "Choose any one card from the market, (off board cards are allowed) and place it on your discard pile."),
                new SpecialCard(CARTOGRAPHER, PURPLE, 4, false, 0, "Draw 2 cards from your draw pile."),
                new SpecialCard(CARTOGRAPHER, PURPLE, 4, false, 0, "Draw 2 cards from your draw pile."),
                new SpecialCard(CARTOGRAPHER, PURPLE, 4, false, 0, "Draw 2 cards from your draw pile."),
                new SpecialCard(COMPASS, PURPLE, 2, true, 0, "Draw 3 cards from your draw pile."),
                new SpecialCard(COMPASS, PURPLE, 2, true, 0, "Draw 3 cards from your draw pile."),
                new SpecialCard(COMPASS, PURPLE, 2, true, 0, "Draw 3 cards from your draw pile."),
                new SpecialCard(SCIENTIST, PURPLE, 4, true, 0, "Draw 1 card from your draw pile. Then choose up to 1 card in your hand and remove it from the game."),
                new SpecialCard(SCIENTIST, PURPLE, 4, true, 0, "Draw 1 card from your draw pile. Then choose up to 1 card in your hand and remove it from the game."),
                new SpecialCard(SCIENTIST, PURPLE, 4, true, 0, "Draw 1 card from your draw pile. Then choose up to 1 card in your hand and remove it from the game."),
                new SpecialCard(TRAVEL_LOG, PURPLE, 3, true, 0, "Draw 2 card from your draw pile. Then choose up to 2 card in your hand and remove it from the game."),
                new SpecialCard(TRAVEL_LOG, PURPLE, 3, true, 0, "Draw 2 card from your draw pile. Then choose up to 2 card in your hand and remove it from the game."),
                new SpecialCard(TRAVEL_LOG, PURPLE, 3, true, 0, "Draw 2 card from your draw pile. Then choose up to 2 card in your hand and remove it from the game."),
                new SpecialCard(NATIVE, PURPLE, 5, false, 0, "Move your playing piece onto an adjacent space."),
                new SpecialCard(NATIVE, PURPLE, 5, false, 0, "Move your playing piece onto an adjacent space."),
                new SpecialCard(NATIVE, PURPLE, 5, false, 0, "Move your playing piece onto an adjacent space.")
        );
    }
}
