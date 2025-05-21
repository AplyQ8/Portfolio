package components.cards;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class Deck {

    @Setter
    private List<Card> hand = new ArrayList<>();
    private final List<Card> discardPile = new ArrayList<>();
    private List<Card> drawPile = new ArrayList<>();

    public Deck() {
        AbstractCardFactory factory = new PlayerCardFactory();
        List<Card> allCards = new ArrayList<>(factory.loadCards());
        Collections.shuffle(allCards);
        hand.addAll(allCards.subList(0, 4));
        drawPile.addAll(allCards.subList(4, 8));
    }

    public List<Card> returnHand() {
        return hand;
    }

    public void shuffleAndClearDiscardPile(){
        Collections.shuffle(discardPile);
        drawPile.addAll(discardPile);
        discardPile.clear();
    }

    public void AddToDiscardPile(Card card){
        discardPile.add(card);
    }

    public void AddToHand(Card card){
        hand.add(card);
    }

    public boolean DeleteFromHand(Card card){
        return hand.remove(card);
    }

    public void drawCard(){
        if(drawPile.isEmpty()){
            shuffleAndClearDiscardPile();
            if(drawPile.isEmpty())
                return;
        }
        AddToHand(drawPile.get(0));
        drawPile.remove(0);
    }
}
