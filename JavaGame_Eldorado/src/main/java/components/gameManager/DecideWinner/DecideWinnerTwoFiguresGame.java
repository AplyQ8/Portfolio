package components.gameManager.DecideWinner;

import components.player.Player;

import java.util.List;

public class DecideWinnerTwoFiguresGame implements DecideWinnerStrategy{
    List<Player> players;
    @Override
    public Player decideWinner(List<Player> players) {
        this.players = players;
        Player winner = null;

        if(playerReachedEnd(0) && playerReachedEnd(1)){
            if(playerBlockadesNumber(0) == playerBlockadesNumber(1)){
                winner = playerTotalBlockadesValue(0) > playerTotalBlockadesValue(1) ?
                        players.get(0) : players.get(1);
            }
            else{
                winner = playerBlockadesNumber(0) > playerBlockadesNumber(1) ?
                        players.get(0) : players.get(1);
            }
        }
        else{
            winner = playerReachedEnd(0) ? players.get(0) : players.get(1);
        }

        return winner;
    }

    private boolean playerReachedEnd(int playerNumber){
        playerNumber = playerNumber%2;
        return players.get(playerNumber).isReachedTheEnd() && players.get(playerNumber+2).isReachedTheEnd();
    }

    private int playerBlockadesNumber(int playerNumber){
        playerNumber = playerNumber%2;
        return players.get(playerNumber).getBlockadesNumber() + players.get(playerNumber+2).getBlockadesNumber();
    }
    private int playerTotalBlockadesValue(int playerNumber){
        playerNumber = playerNumber%2;
        return players.get(playerNumber).getTotalBlockadesValue() + players.get(playerNumber+2).getTotalBlockadesValue();
    }
}
