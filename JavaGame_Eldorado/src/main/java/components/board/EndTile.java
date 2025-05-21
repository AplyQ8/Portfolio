package components.board;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
public class EndTile extends Tile{
    @JsonIgnore
    ArrayList<Hexagon> end = new ArrayList<>();
    int rotation;
    HexagonType type;

    private void generateEndTile(boolean isGreen, int centerQ, int centerR){
        if (isGreen){
            type = HexagonType.green;
            for (int i = 0; i < 3; i++) {
                end.add(new Hexagon(HexagonType.green, 1, true));
            }
        }
        else{
            type = HexagonType.blue;
            for (int i = 0; i < 3; i++) {
                end.add(new Hexagon(HexagonType.blue, 1, true));
            }
        }
        end.get(1).setCoordinates(centerQ, centerR);
        int offset = (rotation % 360)/60;
        end.get(1).setNeighbor((4+offset)%6, end.get(0));
        end.get(1).setNeighbor((2+offset)%6, end.get(2));
    }


    /**
     * Connects the hex of a last tile to the end tile
     * @param isGreen - makes ending tile of type green hexes if true, else makes it of type blue
     */
    public EndTile(boolean isGreen, int rotation, int centerQ, int centerR){
        this.rotation = rotation;
        generateEndTile(isGreen, centerQ, centerR);
    }

    public int[] getCoordinates(){
        int[] center = new int[2];
        center[0] = end.get(1).getQCoord();
        center[1] = end.get(1).getRCoord();
        return center;
    }
}
