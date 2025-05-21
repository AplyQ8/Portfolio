package components.gameManager.DecideWinner;

import components.player.Player;

import java.util.List;

public interface DecideWinnerStrategy {
    public Player decideWinner(List<Player> players);
}
