package components.board;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@JsonIgnoreType
@NoArgsConstructor
public class Blockade extends Tile {
//    ArrayList<Hexagon> blockade = new ArrayList<>();

    int power;
    @JsonIgnore
    Tile neighbourA;
    @JsonIgnore
    Tile neighbourB;

    @Setter
    int number;

    @JsonIgnore
    public boolean isActive() {
        return isActive;
    }

    @JsonIgnore
    boolean isActive;
    @JsonIgnore
    Hexagon blockadeHex;
    HexagonType type;

    @JsonIgnore
    public Blockade(int power, Tile neighbourA, Tile neighbourB, HexagonType type, int number){
        this.power = power;
        this.neighbourA = neighbourA;
        this.neighbourB = neighbourB;
        isActive = true;
        blockadeHex = new Hexagon(type, power);
        blockadeHex.setBlockade();
        this.type = type;
        this.number = number;
    }

    @JsonIgnore
    public void deactivateBlockade(){
        isActive = false;
        blockadeHex.unsetBlockade();
    }

    @JsonProperty("between")
    public List<String> findBetweenNames(){
        return Arrays.asList(neighbourA.getName(), neighbourB.getName());
    }

    //    int connectingSideLeft;
//    int connectingSideRight;
//    public Blockade(int power, HexagonType stripType){
//        this.power = power;
//        makeBlockade(stripType);
//    }
//
//
//    private void makeBlockade(HexagonType BlockadeType){
//        if (BlockadeType != HexagonType.Blue && BlockadeType != HexagonType.Green && BlockadeType != HexagonType.Yellow
//        && BlockadeType != HexagonType.Gray){
//            throw new IllegalArgumentException("Incorrect type of terrain strip!");
//        }
//        for (int i = 0; i < 5; i++) {
//            blockade.add(new Hexagon(BlockadeType, 1));
//        }
//    }

//    private boolean verifyConnectingSide(Hexagon hex){
//        boolean empty = false;
//        for (int i = 0; i < hex.getNeighbors().length; i++) {
//            if (hex.getNeighbors()[i] == null && hex.getNeighbors()[(i+1)%6] == null){
//                empty = true;
//                break;
//            }
//        }
//        return empty;
//    }

//    private void getConnectingSides(Hexagon hex){
//        for (int i = 0; i < hex.getNeighbors().length; i++) {
//            if (hex.getNeighbors()[i] == null && hex.getNeighbors()[(i+1)%6] == null){
//                connectingSideLeft = i;
//                connectingSideRight = (i+1)%6;
//            }
//        }
//    }
//
//    private void setNeighbours(Hexagon hex, int index){
//        hex.setNeighbor(connectingSideLeft, blockade.get(index));
//        hex.setNeighbor(connectingSideRight, blockade.get(index+1));
//    }

//    public Blockade connectBlockade(ArrayList<Hexagon> connectingSide){
//        if (connectingSide.size() != 4){
//            throw new IllegalArgumentException("Can not connect terrain strip to a side of a length != 4");
//        }
//        for (Hexagon hexagon : connectingSide) {
//            verifyConnectingSide(hexagon);
//        }
//        getConnectingSides(connectingSide.get(1));
//        for (int i = 0; i < connectingSide.size(); i++) {
//            setNeighbours(connectingSide.get(i), i);
//        }
//        return this;
//    }
}
