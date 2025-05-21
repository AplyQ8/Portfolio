package components.tokens;

import lombok.Getter;
import lombok.Setter;

import java.util.Random;


public class Token {
    @Getter @Setter
    protected TokenAction tokenAction;

    public Token(TokenAction action){
        this.tokenAction = action;
    }

    public Token(){
        this.tokenAction = TokenAction.Native;
    }

    public static TokenAction getRandomAction(){
        Random random = new Random();
        int x = random.nextInt(TokenAction.class.getEnumConstants().length);
        return TokenAction.class.getEnumConstants()[x];
    }

}
