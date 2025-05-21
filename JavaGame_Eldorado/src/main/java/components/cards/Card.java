package components.cards;

import components.cards.enums.Color;
import components.cards.enums.NameToken;
import lombok.Data;
import lombok.Getter;

@Data
public abstract class Card {

    private NameToken name;
    private Color color;
    private int price;
    private boolean isRemovable;
    private int power;
    private int amount;


    public Card(NameToken name, Color color, int price, boolean isRemovable, int power) {
        this.name = name;
        this.color = color;
        this.price = price;
        this.isRemovable = isRemovable;
        this.amount = 3;
        this.power = power;
    }

}
