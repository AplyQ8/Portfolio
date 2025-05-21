package components.cards;

import components.cards.enums.Color;
import components.cards.enums.NameToken;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpecialCard extends Card {

    private String description;

    public SpecialCard(NameToken name, Color color, int price, boolean isRemovable, int power, String description) {
        super(name, color, price, isRemovable, power);
        this.description = description;
    }


}
