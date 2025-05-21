package components.board;

import com.fasterxml.jackson.annotation.JsonIgnore;
import components.tokens.Token;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
public class Hexagon extends GameBoardComponent {
    private final HexagonType type;
    private final int value;
    @Setter
    private int numberOfPlayers = 0;
    private boolean isFinal = false;
    private boolean isBlockade = false;
    private boolean isCoordinatesSet = false;
    private boolean isActive = false;
    private int qCoord;
    private int rCoord;
    @JsonIgnore
    private Hexagon[] neighbors = new Hexagon[6];
    public ArrayList<Token> tokens = new ArrayList<>();

    public Hexagon(){
        type = HexagonType.green;
        value = 1;
    }

    public Hexagon(HexagonType type, int value) {
        this.type = type;
        this.value = value;
    }

    public Hexagon(HexagonType type, int value, boolean isFinal) {
        this.type = type;
        this.value = value;
        this.isFinal = isFinal;
    }

    public Hexagon(HexagonType type, int value, boolean isFinal, int q, int r) {
        this.type = type;
        this.value = value;
        this.isFinal = isFinal;
        qCoord = q;
        rCoord = r;
    }

    // 0-topright 1-right 2-botright 3-botleft 4-left 5-topleft
    public void setNeighbor(int position, Hexagon neighbor) {
        if (neighbor == null){
            return;
        }
        if (position >= 0 && position < 6) {
            this.neighbors[position] = neighbor;
            // Automatically link back the current hexagon to the appropriate position of the neighbor
            int oppositePosition = (position + 3) % 6; // Calculate the opposite position
            neighbor.neighbors[oppositePosition] = this;
            if (position == 0)
                neighbor.setCoordinates(this.qCoord + 1, this.rCoord - 1);
            else if (position == 1)
                neighbor.setCoordinates(this.qCoord + 1, this.rCoord);
            else if (position == 2)
                neighbor.setCoordinates(this.qCoord, this.rCoord + 1);
            else if (position == 3)
                neighbor.setCoordinates(this.qCoord - 1, this.rCoord + 1);
            else if (position == 4)
                neighbor.setCoordinates(this.qCoord - 1, this.rCoord);
            else
                neighbor.setCoordinates(this.qCoord, this.rCoord - 1);

        } else {
            throw new IllegalArgumentException("Invalid position for neighbor");
        }
    }

    public void setCoordinates(int q, int r){
        if(!isCoordinatesSet) {
            qCoord = q;
            rCoord = r;
        }
        isCoordinatesSet = true;
    }

    public void setIsActive(){
        isActive = true;
    }

    public void setBlockade(){
        isBlockade = true;
    }

    public void unsetBlockade(){
        isBlockade = false;
     }

    @Override
    public String to_String() {
        return ("Hexagon Type: " + type + ", Move Value: " + value);
    }

    public void Occupy(){
        this.numberOfPlayers += 1;
    }

    public void Free(){
        this.numberOfPlayers = this.numberOfPlayers < 1 ? 0 : this.numberOfPlayers - 1;
    }

}
