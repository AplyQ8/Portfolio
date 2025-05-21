import components.board.Map;
import components.cards.*;
import components.gameManager.ActionHandlers.MarketHandler;
import components.gameManager.GameManager;
import components.gameManager.IO.IOHandler;
import components.player.Player;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static components.cards.enums.Color.*;
import static components.cards.enums.NameToken.*;
import static org.junit.jupiter.api.Assertions.*;

public class MarketHandlerTest extends ApplicationTest {
    protected Application application;
    private MarketHandler marketHandler;
    protected ExecutorService executorService;
    private IOHandler ioHandler;
    private Stage primaryStage;
    private FxRobot robot;

    @Start
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        new EldoradoGameForTesting().start(stage);  // Запуск основного приложения
    }

    @BeforeEach
    public void setUp() throws Exception {
        ApplicationTest.launch(EldoradoGameForTesting.class);
        executorService =  Executors.newSingleThreadExecutor();
        ioHandler = IOHandler.getInstance();

        marketHandler = GameManager.getInstance(ioHandler).turnHandlerStrategy.marketHandler;
        robot = new FxRobot();
    }
    @AfterEach
    public void tearDown() throws InterruptedException {
        executorService.shutdown();
        executorService.awaitTermination(5, TimeUnit.SECONDS); // Ждем завершения задач

        Platform.runLater(() -> {
            if (!Platform.isFxApplicationThread()) {
                Platform.exit();
            }
        });
    }

    @Test
    void testHandleBuyingCardV1(){
        Market market = marketHandler.market;
        Player player = new Player("Red");

        Card newCard = new ExpeditionCard(MILLIONAIRE, YELLOW, 5, false, 4);
        market.market.replace(0, newCard);
        executorService.submit(() -> {
            marketHandler.handleBuyingCard(player, 5);
        });
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);

        assertEquals(1, player.getDeck().getDiscardPile().size());
        assertTrue(player.getDeck().getDiscardPile().contains(newCard));

    }

    @Test
    void testHandleBuyingCardV2(){
        Market market = marketHandler.market;
        Player player = new Player("Red");
        setHandForBuyingCard(player);
        Card newCard = new ExpeditionCard(MILLIONAIRE, YELLOW, 5, false, 4);
        market.market.replace(0, newCard);

        executorService.submit(() -> {
            marketHandler.handleBuyingCard(player, 4.5f);
        });
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("0").type(KeyCode.ENTER);
        assertEquals(0, player.getDeck().getDiscardPile().size());
        assertFalse(player.getDeck().getDiscardPile().contains(newCard));
    }

    @Test
    void testHandleBuyingCardV3(){
        Market market = marketHandler.market;
        Player player = new Player("Red");
        setHandForBuyingCard(player);
        Card newCard = new ExpeditionCard(MILLIONAIRE, YELLOW, 5, false, 4);
        market.side.replace(7, newCard);

        executorService.submit(() -> {
            marketHandler.handleBuyingCard(player, 4.5f);
        });
        robot.clickOn("#consoleTextInput").write("7").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("0").type(KeyCode.ENTER);
        assertEquals(0, player.getDeck().getDiscardPile().size());
        assertFalse(player.getDeck().getDiscardPile().contains(newCard));
    }

    @Test
    void testHandleBuyingCardV4(){
        Market market = marketHandler.market;
        Player player = new Player("Red");
        setHandForBuyingCard(player);
        Card newCard = new ExpeditionCard(MILLIONAIRE, YELLOW, 5, false, 4);
        market.side.replace(6, newCard);
        market.market.replace(0, null);
        executorService.submit(() -> {
            marketHandler.handleBuyingCard(player, 5);
        });
        robot.clickOn("#consoleTextInput").write("7").type(KeyCode.ENTER);
        assertEquals(1, player.getDeck().getDiscardPile().size());
        assertTrue(player.getDeck().getDiscardPile().contains(newCard));
    }

    @Test
    void testChooseCardsToBuyNewCardsV1(){
        Market market = marketHandler.market;
        Player player = new Player("Red");


        setHandForBuyingCard(player);
        Card newCard = new ExpeditionCard(MILLIONAIRE, YELLOW, 5, false, 4);
        market.market.replace(0, newCard);
        executorService.submit(() -> {
            marketHandler.chooseCardsToBuyNewCards(player);
        });
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("0").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);
        assertEquals(5, player.getDeck().getDiscardPile().size());
        assertTrue(player.getDeck().getDiscardPile().contains(newCard));

    }
    @Test
    void testChooseCardsToBuyNewCardsV2(){
        Market market = marketHandler.market;
        Player player = new Player("Red");


        setHandForBuyingCard(player);
        Card newCard = new ExpeditionCard(MILLIONAIRE, YELLOW, 5, false, 4);
        market.market.replace(0, newCard);
        executorService.submit(() -> {
            marketHandler.chooseCardsToBuyNewCards(player);
        });
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("0").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("0").type(KeyCode.ENTER);
        assertEquals(3, player.getDeck().getDiscardPile().size());
        assertFalse(player.getDeck().getDiscardPile().contains(newCard));
    }
    @Test
    void testChooseCardsToBuyNewCardsV3(){
        Market market = marketHandler.market;
        Player player = new Player("Red");


        setHandForBuyingCard(player);
        Card newCard = new ExpeditionCard(MILLIONAIRE, YELLOW, 5, false, 4);
        market.market.replace(7, newCard);
        executorService.submit(() -> {
            marketHandler.chooseCardsToBuyNewCards(player);
        });
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("0").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("7").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("0").type(KeyCode.ENTER);
        assertEquals(4, player.getDeck().getDiscardPile().size());
        assertFalse(player.getDeck().getDiscardPile().contains(newCard));
    }
    @Test
    void testChooseCardsToBuyNewCardsV4(){
        Market market = marketHandler.market;
        Player player = new Player("Red");


        setHandForBuyingCard(player);
        Card newCard = new ExpeditionCard(MILLIONAIRE, YELLOW, 5, false, 4);
        market.side.replace(6, newCard);
        market.market.replace(0, null);
        executorService.submit(() -> {
            marketHandler.chooseCardsToBuyNewCards(player);
        });
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("0").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("7").type(KeyCode.ENTER);
        assertEquals(5, player.getDeck().getDiscardPile().size());
        assertTrue(player.getDeck().getDiscardPile().contains(newCard));
    }

    @Test
    void testPlayTransmitter(){
        Player player = new Player("Red");

        Deck deck = new Deck();
        List<Card> hand = new ArrayList<>();
        hand.add(new SpecialCard(TRANSMITTER, PURPLE, 4, true, 0, ""));
        deck.setHand(hand);
        player.setDeck(deck);

        Market market = marketHandler.market;
        Card newCard = new ExpeditionCard(MILLIONAIRE, YELLOW, 5, false, 4);
        market.market.replace(0, newCard);

        executorService.submit(() -> {
            marketHandler.transmitterAction(player);
        });
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);

        assertEquals(1, player.getDeck().getDiscardPile().size());
        assertTrue(player.getDeck().getDiscardPile().contains(newCard));

    }

    void setHandForBuyingCard(Player player){
        Deck deck = new Deck();
        List<Card> hand = new ArrayList<>();

        hand.add(new ExpeditionCard(PIONEER, GREEN, 5, false, 5));
        hand.add(new ExpeditionCard(PIONEER, GREEN, 5, false, 5));
        hand.add(new ExpeditionCard(JOURNALIST, YELLOW, 3, false, 3));
        hand.add(new ExpeditionCard(JACK_OF_ALL_TRADES, WHITE, 2, false, 1));

        deck.setHand(hand);
        player.setDeck(deck);
    }
}
