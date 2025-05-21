import components.board.Coordinate;
import components.board.Map;
import components.cards.*;
import components.cards.enums.Color;
import components.gameManager.ActionHandlers.MarketHandler;
import components.gameManager.ActionHandlers.MovementHandler;
import components.gameManager.ActionHandlers.PlayCardHandler;
import components.gameManager.GameManager;
import components.gameManager.IO.IOHandler;
import components.player.Player;
import components.tokens.ExpeditionToken;
import components.tokens.Token;
import components.tokens.TokenAction;
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
import static components.cards.enums.NameToken.MILLIONAIRE;
import static org.junit.jupiter.api.Assertions.*;

public class PlayCardHandlerTest {

    protected Application application;
    PlayCardHandler playCardHandler;
    MovementHandler movementHandler;
    MarketHandler marketHandler;
    private Map map;
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
        movementHandler = GameManager.getInstance(ioHandler).turnHandlerStrategy.movementHandler;
        marketHandler = GameManager.getInstance(ioHandler).turnHandlerStrategy.marketHandler;
        playCardHandler = GameManager.getInstance(ioHandler).turnHandlerStrategy.playCardHandler;
        map = movementHandler.map;
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

    void setPlayerHand(Player player, int handSize){
        Deck deck = new Deck();
        List<Card> hand = new ArrayList<>();

        for(int i = 0; i< handSize; i++){
            hand.add(new ExpeditionCard(PIONEER, GREEN, 5, false, 5));
        }

        deck.setHand(hand);
        player.setDeck(deck);
    }
    void setupPlayerPosAndPlayedCard(Player player, int q, int r, Color color, int power){
        player.setHexagon(map.getMap().get(new Coordinate(q, r)));
        setPlayerHand(player, 1);
        player.setPlayedCardColor(color);
        player.setPower(power);
    }

    @Test
    void testPlayExpeditionCard(){
        Player player = new Player("Red");

        setPlayerHand(player, 1);


        executorService.submit(() -> {
            playCardHandler.handlePlayingCard(player);
        });
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);

        assertEquals(GREEN, player.getPlayedCardColor());
        assertEquals(5, player.getPower());

        Deck deck = new Deck();
        List<Card> hand = new ArrayList<>();
        hand.add(new ExpeditionCard(ADVENTURER, WHITE, 4, false, 2));
        deck.setHand(hand);
        player.setDeck(deck);
        executorService.submit(() -> {
            playCardHandler.handlePlayingCard(player);
        });
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("3").type(KeyCode.ENTER);

        assertEquals(YELLOW, player.getPlayedCardColor());
        assertEquals(2, player.getPower());
    }

    @Test
    void testPlayTransmitter(){
        Player player = new Player("Red");

        Deck deck = new Deck();
        List<Card> hand = new ArrayList<>();
        hand.add(new SpecialCard(TRANSMITTER, PURPLE, 4, true, 0, ""));
        deck.setHand(hand);
        player.setDeck(deck);

        Market market = playCardHandler.marketHandler.market;
        Card newCard = new ExpeditionCard(MILLIONAIRE, YELLOW, 5, false, 4);
        market.market.replace(0, newCard);

        executorService.submit(() -> {
            playCardHandler.handlePlayingCard(player);
        });
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);

        assertEquals(1, player.getDeck().getDiscardPile().size());
        assertTrue(player.getDeck().getDiscardPile().contains(newCard));

    }
    @Test
    void testPlayCartographer(){
        Player player = new Player("Red");

        Deck deck = new Deck();
        List<Card> hand = new ArrayList<>();
        hand.add(new SpecialCard(CARTOGRAPHER, PURPLE, 4, false, 0, ""));
        deck.setHand(hand);
        player.setDeck(deck);

        executorService.submit(() -> {
            playCardHandler.handlePlayingCard(player);
        });
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);

        assertEquals(1, player.getDeck().getDiscardPile().size());
    }

    @Test
    void testPlayScientist(){
        Player player = new Player("Red");

        Deck deck = new Deck();
        List<Card> hand = new ArrayList<>();
        hand.add(new SpecialCard(SCIENTIST, PURPLE, 4, false, 0, ""));
        hand.add(new ExpeditionCard(ADVENTURER, WHITE, 4, false, 2));
        deck.setHand(hand);
        player.setDeck(deck);

        executorService.submit(() -> {
            playCardHandler.handlePlayingCard(player);
        });
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);

        assertEquals(5, player.getDeck().getDiscardPile().size() + player.getDeck().getHand().size() +
                player.getDeck().getDrawPile().size());
    }
    @Test
    void testPlayCompass() {
        Player player = new Player("Red");

        Deck deck = new Deck();
        List<Card> hand = new ArrayList<>();
        hand.add(new SpecialCard(COMPASS, PURPLE, 4, true, 0, ""));
        deck.setHand(hand);
        player.setDeck(deck);


        executorService.submit(() -> {
            playCardHandler.handlePlayingCard(player);
        });
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);

        assertEquals(4, player.getDeck().getDiscardPile().size() + player.getDeck().getHand().size() +
                player.getDeck().getDrawPile().size());
    }

    @Test
    void testPlayExpeditionToken(){
        Player player = new Player("Red");

        ExpeditionToken token = new ExpeditionToken(BLUE, 2);
        player.putNewToken(token);

        executorService.submit(() -> {
            playCardHandler.handlePlayingToken(player);
        });
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);

        assertEquals(BLUE, player.getPlayedCardColor());
        assertEquals(2, player.getPower());
        assertEquals(0, player.getTokens().size());
    }

    @Test
    void testDrawCardToken(){
        Player player = new Player("Red");

        Token token = new Token(TokenAction.DrawCard);
        player.putNewToken(token);

        executorService.submit(() -> {
            playCardHandler.handlePlayingToken(player);
        });
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);

        assertEquals(5, player.getDeck().getHand().size());

    }
    @Test
    void testRemoveCardToken(){
        Player player = new Player("Red");

        Token token = new Token(TokenAction.RemoveCard);
        player.putNewToken(token);

        executorService.submit(() -> {
            playCardHandler.handlePlayingToken(player);
        });
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);

        assertEquals(7, player.getDeck().getDiscardPile().size() + player.getDeck().getHand().size() +
                player.getDeck().getDrawPile().size());

    }
    @Test
    void testReplaceHandToken(){
        Player player = new Player("Red");

        Token token = new Token(TokenAction.ReplaceHand);
        player.putNewToken(token);

        executorService.submit(() -> {
            playCardHandler.handlePlayingToken(player);
        });
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("0").type(KeyCode.ENTER);

        assertEquals(3, player.getDeck().getDiscardPile().size());
        assertEquals(1, player.getDeck().getDrawPile().size());

    }

    @Test
    void testSaveItemToken(){
        Player player = new Player("Red");

        Token token = new Token(TokenAction.SaveItem);
        player.putNewToken(token);

        executorService.submit(() -> {
            playCardHandler.handlePlayingToken(player);
        });
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);
        assertTrue(player.isSaveNextItemToken());

        Deck deck = new Deck();
        List<Card> hand = new ArrayList<>();
        hand.add(new ExpeditionCard(PHOTOGRAPHER, YELLOW, 1, true, 2));
        deck.setHand(hand);
        player.setDeck(deck);

        executorService.submit(() -> {
            playCardHandler.handlePlayingCard(player);
        });
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);

        assertEquals(5, player.getDeck().getDiscardPile().size() + player.getDeck().getHand().size() +
                player.getDeck().getDrawPile().size());
        assertFalse(player.isSaveNextItemToken());

    }

    @Test
    void testMoveToOccupiedToken(){
        Player player = new Player("Red");

        Token token = new Token(TokenAction.MoveToOccupied);
        player.putNewToken(token);
        setupPlayerPosAndPlayedCard(player, 0, 2, GREEN, 10);
        map.getMap().get(new Coordinate(1, 2)).Occupy();

        executorService.submit(() -> {
            playCardHandler.handlePlayingToken(player);
        });
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);

        assertTrue(player.isOccupiedMoveToken());

    }

    @Test
    void testChangeSymbolToken(){
        Player player = new Player("Red");

        Token token = new Token(TokenAction.ChangeSymbol);
        player.putNewToken(token);

        executorService.submit(() -> {
            playCardHandler.handlePlayingToken(player);
        });
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);

        assertTrue(player.isChangeSymbolToken());

        Deck deck = new Deck();
        List<Card> hand = new ArrayList<>();
        hand.add(new ExpeditionCard(PHOTOGRAPHER, YELLOW, 1, false, 2));
        deck.setHand(hand);
        player.setDeck(deck);

        executorService.submit(() -> {
            playCardHandler.handlePlayingCard(player);
        });
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("2").type(KeyCode.ENTER);

        assertFalse(player.isChangeSymbolToken());
        assertEquals(BLUE, player.getPlayedCardColor());
        assertEquals(2, player.getPower());
    }

    @Test
    void testDiscardCards(){
        Player player = new Player("Red");


        executorService.submit(() -> {
            playCardHandler.discardCards(player, 4);
        });
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("3").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("0").type(KeyCode.ENTER);
        assertEquals(2, player.getDeck().getDiscardPile().size());


    }

}
