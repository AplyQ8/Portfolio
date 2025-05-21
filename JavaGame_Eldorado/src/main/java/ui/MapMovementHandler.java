package ui;

import components.board.Hexagon;
import components.board.Map;
import components.player.Player;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.text.Text;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Slf4j
public class MapMovementHandler {

    private static Group root;
    private static final HashMap<Player, String[]> playerLastCoords = new HashMap<>();

    public static void updatePlayerInitialPosition(List<Player> playerList, Map map) {
        for (int i = 0; i < playerList.size(); i++) {
            String textId = map.findHexInElements(playerList.get(i).getHexagon()).getName().charAt(0) + "INIT" + (i + 1);
            Optional<Text> text = root.getChildren().stream()
                    .filter(node -> node instanceof Text)
                    .map(node -> (Text) node)
                    .filter(t -> t.getId() != null && t.getId().equals(textId))
                    .findFirst();
            if (text.isPresent()) {
                playerLastCoords.put(playerList.get(i), new String[]{
                        textId,
                        text.get().getText()
                });
                text.get().setText(playerList.get(i).getColor());
            } else {
                log.warn("Search for Text by ID failed.");
            }
        }
    }

    public static void updatePlayerPosition(Player p, Hexagon h, Map map) {
        updatePreviousHexagonToLastText(p);
        String textId = "";
        if (h.getValue() == 0) {
            textId = map.findHexInElements(p.getHexagon()).getName() + "INIT" +
                    (String.valueOf(Integer.parseInt(
                            playerLastCoords.get(p)[0].substring(playerLastCoords.get(p)[0].length() - 1)) + 1));
        } else {
            textId = map.findHexInElements(p.getHexagon()).getName() + h.getQCoord() + h.getRCoord();
        }
        String finalTextId = textId;
        Optional<Text> text = root.getChildren().stream()
                .filter(node -> node instanceof Text)
                .map(node -> (Text) node)
                .filter(t -> t.getId() != null)
                .filter(t -> t.getId().equals(finalTextId))
                .findFirst();
        if (text.isPresent()) {
            String[] idAndCoords = new String[2];
            idAndCoords[0] = textId;
            idAndCoords[1] = text.get().getText();
            playerLastCoords.put(p, idAndCoords);
            text.get().setText(p.getColor());
        } else {
            log.warn("Search for Text by ID failed.");
        }
    }

    public static void updatePreviousHexagonToLastText(Player p) {
        Optional<Text> text = root.getChildren().stream()
                .filter(node -> node instanceof Text)
                .map(node -> (Text) node)
                .filter(t -> t.getId() != null)
                .filter(t -> t.getId().equals(playerLastCoords.get(p)[0]))
                .findFirst();
        if (text.isPresent()) {
            text.get().setText(playerLastCoords.get(p)[1]);
        } else {
            log.warn("Search for Text by ID failed.");
        }
    }

    public static void setGroup(Group inp) {
        root = inp;
    }
}
