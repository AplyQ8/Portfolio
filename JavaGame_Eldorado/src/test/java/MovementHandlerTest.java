import components.board.Coordinate;
import components.board.Hexagon;
import components.board.HexagonType;
import components.board.Map;
import components.cards.Card;
import components.cards.Deck;
import components.cards.ExpeditionCard;
import components.cards.SpecialCard;
import components.cards.enums.Color;
import components.gameManager.ActionHandlers.MarketHandler;
import components.gameManager.ActionHandlers.MovementHandler;
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
import static components.cards.enums.NameToken.NATIVE;
import static components.cards.enums.NameToken.PIONEER;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class MovementHandlerTest {
    protected Application application;
    protected ExecutorService executorService;
    private MovementHandler movementHandler;
    private Map map;
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
        player.setPlayedCardColor(color);
        player.setPower(power);
    }

    @Test
    void testPlacePlayersAtStart(){
        String[] colors = {"Red", "White", "Blue", "Yellow"};

        List<Player> players = new ArrayList<Player>();
        for(int i = 0; i < 2; i++){
            Player player = new Player(colors[i]);
            players.add(player);
        }

        movementHandler.placePlayersAtStart(players);
        assertEquals(-3, players.get(0).getHexagon().getQCoord());
        assertEquals(3, players.get(0).getHexagon().getRCoord());
        assertEquals(-2, players.get(1).getHexagon().getQCoord());
        assertEquals(3, players.get(1).getHexagon().getRCoord());

        players = new ArrayList<Player>();
        for(int i = 0; i < 4; i++){
            Player player = new Player(colors[i]);
            players.add(player);
        }

        movementHandler.placePlayersAtStart(players);
        assertEquals(-3, players.get(0).getHexagon().getQCoord());
        assertEquals(3, players.get(0).getHexagon().getRCoord());
        assertEquals(-2, players.get(1).getHexagon().getQCoord());
        assertEquals(3, players.get(1).getHexagon().getRCoord());
        assertEquals(-1, players.get(2).getHexagon().getQCoord());
        assertEquals(3, players.get(2).getHexagon().getRCoord());
        assertEquals(0, players.get(3).getHexagon().getQCoord());
        assertEquals(3, players.get(3).getHexagon().getRCoord());
    }

    @Test
    void testNativeAction(){
        Player player = new Player("Red");

        Deck deck = new Deck();
        List<Card> hand = new ArrayList<>();
        hand.add(new SpecialCard(NATIVE, PURPLE, 4, false, 0, ""));
        deck.setHand(hand);
        player.setDeck(deck);
        player.setHexagon(map.getMap().get(new Coordinate(1, -2)));

        executorService.submit(() -> {
            movementHandler.nativeAction(player);
        });
        robot.clickOn("#consoleTextInput").write("2").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("5").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput").write("0").type(KeyCode.ENTER);

        assertEquals(2, player.getHexagon().getQCoord());
        assertEquals(-2, player.getHexagon().getRCoord());
    }

    @Test
    void testCompareColorAndHexagonType(){
        assertTrue(movementHandler.compareColorAndHexagonType(GREEN, HexagonType.green));
        assertFalse(movementHandler.compareColorAndHexagonType(GREEN, HexagonType.gray));
        assertFalse(movementHandler.compareColorAndHexagonType(BLUE, HexagonType.green));
        assertTrue(movementHandler.compareColorAndHexagonType(BLUE, HexagonType.blue));
        assertFalse(movementHandler.compareColorAndHexagonType(PURPLE, HexagonType.blue));
        assertFalse(movementHandler.compareColorAndHexagonType(PURPLE, HexagonType.empty));
        assertFalse(movementHandler.compareColorAndHexagonType(WHITE, HexagonType.empty));
    }
    @Test
    void testCheckIfHexagonIsBlockade(){
        Hexagon hexagon = new Hexagon();
        assertFalse(movementHandler.checkIfHexagonIsBlockade(hexagon));
        hexagon = new Hexagon();
        hexagon.setBlockade();
        assertTrue(movementHandler.checkIfHexagonIsBlockade(hexagon));
    }
    @Test
    void testDestroyBlockade(){
        Player player = new Player("Red");
        Hexagon hexagon = new Hexagon();
        hexagon.setBlockade();

        movementHandler.destroyBlockade(player, hexagon);
        assertEquals(1, player.getBlockades().size());
    }


}
