package components.cards;

import components.cards.enums.Color;
import lombok.Getter;

import java.util.*;

@Getter
public class Market {

    private final List<Card> all = new ArrayList<>();
    public final Map<Integer, Card> market = new HashMap<>();
    public final Map<Integer, Card> side = new HashMap<>();
    private static Market instance;

    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";

    private Market() {
        AbstractCardFactory factory = new MarketCardFactory();
        all.addAll(factory.loadCards());
        Collections.shuffle(this.all);
        load(all);
    }

    public static Market getInstance() {
        return instance == null ? new Market() : null;
    }


    public void load(List<Card> inp) {
        int marketCount = 0;
        int sideCount = 6;
        for (int i = 0; i < inp.size(); i++) {
            if (market.size() < 6 && !market.containsValue(inp.get(i))) {
                market.put(marketCount, inp.get(i));
                marketCount++;
            } else {
                side.put(sideCount, inp.get(i));
                sideCount++;
            }
        }
    }

    private String coloring(Color color) {
        String res = "";
        switch (color) {
            case GREEN -> res = "\u001B[32m";
            case BLUE -> res = "\u001B[34m";
            case YELLOW -> res = "\u001B[33m";
            case PURPLE -> res = "\u001B[35m";
        }
        return res;
    }

    public String marketToString(){
        StringBuilder builder = new StringBuilder();
        builder.append(RED + "MARKET BOARD:" + RESET + "\n");
        print(builder, market);
        builder.append("\n" + RED + "NEXT TO THE MARKET BOARD:" + RESET + "\n");
        print(builder, side);
        return builder.toString();
    }

    private void print(StringBuilder builder, Map<Integer, Card> market) {
        market.forEach((k, v) -> {
            if (v != null) {
                builder.append(k + 1).append(". ").append(coloring(v.getColor())).append(v.getName())
                        .append(RESET).append(" AMOUNT: ").append(v.getAmount())
                        .append(" PRICE: ").append(v.getPrice()).append("\n");
            } else {
                builder.append(k + 1).append(". EMPTY STACK\n");
            }
        });
    }

    private int findEmptyStack() {
        for (Map.Entry<Integer, Card> entry : market.entrySet()) {
            if (entry.getValue() == null) {
                return entry.getKey();
            }
        }
        return -1;
    }

    private Card testOnNullBuy(int cardNumber, float gold) {
        int emptyStackId = findEmptyStack();
        if (market.containsValue(null) && emptyStackId != -1) {
            market.put(emptyStackId, side.get(cardNumber));
            side.put(cardNumber, null);
            return checkIfCanPay(emptyStackId, gold);
        } else {
            return null;
        }
    }

    private Card checkIfCanPay(int id, float gold) {
        return gold < market.get(id).getPrice() * 1.0 ? null : getCard(id);
    }

    public Card buyCard(int cardNumber, float gold){
        if (cardNumber < 6) {
            return market.get(cardNumber) != null ? checkIfCanPay(cardNumber, gold) : null;
        }
        return testOnNullBuy(cardNumber, gold);
    }

    private Card testOnNull(int cardNumber) {
        int emptyStackId = findEmptyStack();
        if (market.containsValue(null) && emptyStackId != -1) {
            market.put(emptyStackId, side.get(cardNumber));
            side.put(cardNumber, null);
            return market.get(emptyStackId);
        } else {
            return null;
        }
    }

    private Card getCard(int id) {
        Card res = market.get(id);
        market.get(id).setAmount(market.get(id).getAmount() - 1);
        if (market.get(id).getAmount() == 0) {
            market.put(id, null);
        }
        return res;
    }

    public Card getCardForFree(int cardNumber) {
        if (cardNumber < 6) {
            return market.get(cardNumber) != null ? getCard(cardNumber) : null;
        } else {
            return testOnNull(cardNumber);
        }
    }

    public int uniqueCardsNumber(){
        HashSet<Card> set = new HashSet<>();
        for (Map.Entry<Integer, Card> entry : market.entrySet()) {
            if (entry.getValue() != null) {
                set.add(entry.getValue());
            }
        }
        for (Map.Entry<Integer, Card> entry : side.entrySet()) {
            if (entry.getValue() != null) {
                set.add(entry.getValue());
            }
        }
        return set.size();
    }

    public int getSize(){
        return market.size() + side.size();
    }

    public boolean isEmpty(){
        return uniqueCardsNumber() == 0;
    }

}
