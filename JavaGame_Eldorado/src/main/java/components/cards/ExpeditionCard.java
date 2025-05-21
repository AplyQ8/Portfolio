package components.cards;

import components.cards.enums.Color;
import components.cards.enums.NameToken;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpeditionCard extends Card {

    public ExpeditionCard(NameToken name, Color color, int price, boolean isRemovable, int power) {
        super(name, color, price, isRemovable, power);
    }

}
