package components.cards;

import static components.cards.enums.Color.*;
import static components.cards.enums.NameToken.*;

import java.util.Arrays;
import java.util.List;

public class PlayerCardFactory implements AbstractCardFactory {

    @Override
    public List<Card> loadCards() {
        return Arrays.asList(
                new ExpeditionCard(TRAVELLER, YELLOW, 0, false, 1),
                new ExpeditionCard(TRAVELLER, YELLOW, 0, false, 1),
                new ExpeditionCard(TRAVELLER, YELLOW, 0, false, 1),
                new ExpeditionCard(TRAVELLER, YELLOW, 0, false, 1),
                new ExpeditionCard(EXPLORER, GREEN, 0, false, 1),
                new ExpeditionCard(EXPLORER, GREEN, 0, false, 1),
                new ExpeditionCard(EXPLORER, GREEN, 0, false, 1),
                new ExpeditionCard(SAILOR, BLUE, 0, false, 1)

        );
    }
}
