package components.tokens;

import components.cards.ExpeditionCard;
import components.cards.enums.Color;
import lombok.Getter;
import lombok.Setter;

import java.util.Random;

public class ExpeditionToken extends Token {
    @Getter @Setter
    private Color color;
    @Getter @Setter
    private int power;

    public ExpeditionToken(Color color, int power){
        tokenAction = TokenAction.Expedition;
        this.color = color;
        this.power = power;
    }

    public static ExpeditionToken getRandomExpToken(){
        Random random = new Random();
        int x = random.nextInt(3);
        Color tokenColor =  Color.class.getEnumConstants()[x];
        int maxPower = 2;
        if(tokenColor == Color.GREEN){
            maxPower = 3;
        }

        int tokenPower = random.nextInt(maxPower) + 1;

        return new ExpeditionToken(tokenColor, tokenPower);

    }
}
