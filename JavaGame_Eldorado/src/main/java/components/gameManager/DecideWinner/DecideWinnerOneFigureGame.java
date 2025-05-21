package components.gameManager.DecideWinner;

import components.player.Player;

import java.util.List;

public class DecideWinnerOneFigureGame implements DecideWinnerStrategy{

    @Override
    public Player decideWinner(List<Player> players) {
        Player winner = null;
        int maxBlockadesNumber = 0;
        int maxTotalBlockadesValue = 0;

        for (Player player : players) {
            if (winningCondition(player, maxBlockadesNumber, maxTotalBlockadesValue)) {
                winner = player;
                maxBlockadesNumber = player.getBlockadesNumber();
                maxTotalBlockadesValue = player.getTotalBlockadesValue();
            }
        }

        return winner;
    }

    private boolean winningCondition(Player player, int maxBlockadesNumber, int maxTotalBlockadesValue){
        return player.isReachedTheEnd() && (player.getBlockadesNumber() > maxBlockadesNumber ||
                player.getBlockadesNumber() == maxBlockadesNumber &&
                        player.getTotalBlockadesValue() >= maxTotalBlockadesValue);
    }
}
