import components.board.Blockade;
import components.board.HexagonType;
import components.board.Map;
import components.board.Tile;
import components.cards.Card;
import components.cards.Deck;
import components.cards.ExpeditionCard;
import components.cards.Market;
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
import static org.junit.jupiter.api.Assertions.*;


public class GameManagerTest extends ApplicationTest {
    protected Application application;
    private GameManager gameManager;
    protected ExecutorService executorService;
    private IOHandler ioHandler;
    private Stage primaryStage;
    private Map map;

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

        gameManager = new GameManager(ioHandler);
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

    @Test
    void testEnterPlayersDataV1(){
        ioHandler.setIOComponents(
                robot.lookup("#consoleTextInput").query(),
                robot.lookup("#consoleTextOutput").query());
        gameManager = new GameManager(ioHandler);

        executorService.submit(() -> {
            //GameManager.getInstance(ioHandler).enterTestMapA();
            //GameManager.getInstance(ioHandler).enterPlayersData();
            GameManager.getInstance(ioHandler).startGame();
        });

        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);

        robot.clickOn("#consoleTextInput").write("3").type(KeyCode.ENTER);

        assertEquals(3, GameManager.getInstance(ioHandler).players.size());
        assertFalse(GameManager.getInstance(ioHandler).twoPlayersGame);

        

    }
    @Test
    void testEnterPlayersDataV2() {
        ioHandler.setIOComponents(
                robot.lookup("#consoleTextInput").query(),
                robot.lookup("#consoleTextOutput").query());
        gameManager = new GameManager(ioHandler);

        executorService.submit(() -> {
            GameManager.getInstance(ioHandler).startGame();
        });
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);

        robot.write("2").type(KeyCode.ENTER);


        assertEquals(4, GameManager.getInstance(ioHandler).players.size());
        assertTrue(GameManager.getInstance(ioHandler).twoPlayersGame);
    }

    @Test
    void testDecideWinnerV1(){
        StartGame(robot);
        //robot.clickOn("#consoleTextInput").write("4").type(KeyCode.ENTER);
        robot.interact(() -> {
            robot.clickOn("#consoleTextInput").write("4").type(KeyCode.ENTER);
        });
        List<Player> players = GameManager.getInstance(ioHandler).players;
        players.get(0).setReachedTheEnd(true);
        Player winner = GameManager.getInstance(ioHandler).decideWinner();
        assertEquals("Red", winner.getColor());

    }

    @Test
    void testDecideWinnerV2(){
        StartGame(robot);
        //robot.clickOn("#consoleTextInput").write("4").type(KeyCode.ENTER);
        robot.interact(() -> {
            robot.clickOn("#consoleTextInput").write("4").type(KeyCode.ENTER);
        });
        List<Player> players = GameManager.getInstance(ioHandler).players;
        players.get(0).setReachedTheEnd(false);
        players.get(1).setReachedTheEnd(true);
        Player winner = GameManager.getInstance(ioHandler).decideWinner();
        assertEquals("White", winner.getColor());

    }

    @Test
    void testDecideWinnerV3(){
        StartGame(robot);
        //robot.clickOn("#consoleTextInput").write("4").type(KeyCode.ENTER);
        robot.interact(() -> {
            robot.clickOn("#consoleTextInput").write("4").type(KeyCode.ENTER);
        });
        List<Player> players = GameManager.getInstance(ioHandler).players;
        players.get(0).setReachedTheEnd(true);
        players.get(1).setReachedTheEnd(true);
        players.get(3).setReachedTheEnd(true);
        players.get(0).putNewBlockade(new Blockade(3, new Tile(), new Tile(), HexagonType.green, 1));
        players.get(0).putNewBlockade(new Blockade(1, new Tile(), new Tile(), HexagonType.green, 1));
        players.get(1).putNewBlockade(new Blockade(10, new Tile(), new Tile(), HexagonType.green, 1));
        players.get(3).putNewBlockade(new Blockade(4, new Tile(), new Tile(), HexagonType.green, 1));
        players.get(3).putNewBlockade(new Blockade(2, new Tile(), new Tile(), HexagonType.green, 1));
        Player winner = GameManager.getInstance(ioHandler).decideWinner();
        assertEquals("Yellow", winner.getColor());

    }
    @Test
    void testDecideWinnerV4(){
        StartGame(robot);
        //robot.clickOn("#consoleTextInput").write("2").type(KeyCode.ENTER);
        robot.interact(() -> {
            robot.clickOn("#consoleTextInput").write("2").type(KeyCode.ENTER);
        });
        List<Player> players = GameManager.getInstance(ioHandler).players;
        players.get(0).setReachedTheEnd(true);
        players.get(1).setReachedTheEnd(true);
        players.get(3).setReachedTheEnd(true);
        players.get(0).putNewBlockade(new Blockade(3, new Tile(), new Tile(), HexagonType.green, 1));
        players.get(0).putNewBlockade(new Blockade(1, new Tile(), new Tile(), HexagonType.green, 1));
        players.get(1).putNewBlockade(new Blockade(10, new Tile(), new Tile(), HexagonType.green, 1));
        players.get(3).putNewBlockade(new Blockade(4, new Tile(), new Tile(), HexagonType.green, 1));
        players.get(3).putNewBlockade(new Blockade(2, new Tile(), new Tile(), HexagonType.green, 1));
        Player winner = GameManager.getInstance(ioHandler).decideWinner();
        assertEquals("White", winner.getColor());
    }
    @Test
    void testDecideWinnerV5(){
        StartGame(robot);
        //robot.clickOn("#consoleTextInput").write("2").type(KeyCode.ENTER);
        robot.interact(() -> {
            robot.clickOn("#consoleTextInput").write("2").type(KeyCode.ENTER);
        });
        List<Player> players = GameManager.getInstance(ioHandler).players;
        players.get(0).setReachedTheEnd(true);
        players.get(1).setReachedTheEnd(true);
        players.get(2).setReachedTheEnd(true);
        players.get(3).setReachedTheEnd(true);
        players.get(0).putNewBlockade(new Blockade(3, new Tile(), new Tile(), HexagonType.green, 1));
        players.get(0).putNewBlockade(new Blockade(1, new Tile(), new Tile(), HexagonType.green, 1));
        players.get(2).putNewBlockade(new Blockade(1, new Tile(), new Tile(), HexagonType.green, 1));
        players.get(3).putNewBlockade(new Blockade(4, new Tile(), new Tile(), HexagonType.green, 1));
        players.get(3).putNewBlockade(new Blockade(10, new Tile(), new Tile(), HexagonType.green, 1));
        Player winner = GameManager.getInstance(ioHandler).decideWinner();
        assertEquals("Red", winner.getColor());
    }
    @Test
    void testDecideWinnerV6(){
        StartGame(robot);
        //robot.clickOn("#consoleTextInput").write("2").type(KeyCode.ENTER);
        robot.interact(() -> {
            robot.clickOn("#consoleTextInput").write("2").type(KeyCode.ENTER);
        });
        List<Player> players = GameManager.getInstance(ioHandler).players;
        players.get(0).setReachedTheEnd(true);
        players.get(1).setReachedTheEnd(true);
        players.get(2).setReachedTheEnd(true);
        players.get(3).setReachedTheEnd(true);
        players.get(0).putNewBlockade(new Blockade(3, new Tile(), new Tile(), HexagonType.green, 1));
        players.get(2).putNewBlockade(new Blockade(1, new Tile(), new Tile(), HexagonType.green, 1));
        players.get(3).putNewBlockade(new Blockade(4, new Tile(), new Tile(), HexagonType.green, 1));
        players.get(3).putNewBlockade(new Blockade(10, new Tile(), new Tile(), HexagonType.green, 1));
        Player winner = GameManager.getInstance(ioHandler).decideWinner();
        assertEquals("White", winner.getColor());
    }


    @Test
    void testBuyingCard(){
        StartGame(robot);
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);
        Market market = GameManager.getInstance(ioHandler).turnHandlerStrategy.getMarketHandler().market;
        Player player = GameManager.getInstance(ioHandler).players.get(0);
        setHandForBuyingCard(player);
        Card newCard = new ExpeditionCard(MILLIONAIRE, YELLOW, 5, false, 4);
        market.market.replace(0, newCard);
        robot.clickOn("#consoleTextInput").write("4").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("0").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("5").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("0").type(KeyCode.ENTER);

        assertEquals(5, player.getDeck().getDiscardPile().size());
        assertTrue(player.getDeck().getDiscardPile().contains(newCard));
    }

    @Test
    void testTurnEnd(){
        StartGame(robot);
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);
        Player player = GameManager.getInstance(ioHandler).players.get(0);

        robot.clickOn("#consoleTextInput").write("5").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("0").type(KeyCode.ENTER);


        assertEquals(3, player.getDeck().getDiscardPile().size());
        assertEquals(4, player.getDeck().getHand().size());
        assertEquals(1, player.getDeck().getDrawPile().size());
    }

    @Test
    void testPlayExpeditionToken(){
        StartGame(robot);
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);
        Player player = GameManager.getInstance(ioHandler).players.get(0);

        ExpeditionToken token = new ExpeditionToken(BLUE, 2);
        player.putNewToken(token);

        robot.clickOn("#consoleTextInput").write("2").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("5").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("0").type(KeyCode.ENTER);

        assertEquals(BLUE, player.getPlayedCardColor());
        assertEquals(2, player.getPower());
        assertEquals(0, player.getTokens().size());

    }
    @Test
    void testDrawCardToken(){
        StartGame(robot);
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);
        Player player = GameManager.getInstance(ioHandler).players.get(0);
        Token token = new Token(TokenAction.DrawCard);
        player.putNewToken(token);

        robot.clickOn("#consoleTextInput").write("2").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("5").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("0").type(KeyCode.ENTER);
        assertEquals(5, player.getDeck().getHand().size());

    }

    @Test
    void testRemoveCardToken(){
        StartGame(robot);
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);
        Player player = GameManager.getInstance(ioHandler).players.get(0);
        Token token = new Token(TokenAction.RemoveCard);
        player.putNewToken(token);

        robot.clickOn("#consoleTextInput").write("2").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("5").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("0").type(KeyCode.ENTER);

        assertEquals(7, player.getDeck().getDiscardPile().size() + player.getDeck().getHand().size() +
                player.getDeck().getDrawPile().size());

    }

    @Test
    void testReplaceHandToken(){
        StartGame(robot);
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);
        Player player = GameManager.getInstance(ioHandler).players.get(0);
        Token token = new Token(TokenAction.ReplaceHand);
        player.putNewToken(token);

        robot.clickOn("#consoleTextInput").write("2").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("0").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("5").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("0").type(KeyCode.ENTER);

        assertEquals(3, player.getDeck().getDiscardPile().size());
        assertEquals(1, player.getDeck().getDrawPile().size());

    }



    @Test
    void testChangePlayingFigure(){
        StartGame(robot);
        robot.clickOn("#consoleTextInput").write("2").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);
        Player player1 = GameManager.getInstance(ioHandler).players.get(0);
        Player player2 = GameManager.getInstance(ioHandler).players.get(2);
        Token token = new Token(TokenAction.ChangeSymbol);
        player1.putNewToken(token);

        assertEquals(1, player2.getTokens().size());

        robot.clickOn("#consoleTextInput").write("6").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("2").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("5").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("0").type(KeyCode.ENTER);

        assertEquals(0, player2.getTokens().size());
        assertEquals(0, player1.getTokens().size());
    }

    private void StartGame(FxRobot robot){
        ioHandler.setIOComponents(
                robot.lookup("#consoleTextInput").query(),
                robot.lookup("#consoleTextOutput").query());
        gameManager = new GameManager(ioHandler);

        executorService.submit(() -> {
            GameManager.getInstance(ioHandler).startGame();
        });
        robot.clickOn("#consoleTextInput").write("1").type(KeyCode.ENTER);
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
