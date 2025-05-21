package components.board;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@JsonPropertyOrder({ "tileType", "rotation", "coordinates" })
@JsonIgnoreType
@AllArgsConstructor
public class TerrainStrip extends Tile{
    @JsonIgnore
    private final List<List<Hexagon>> rows;
    @Setter
    int rotation;

    public TerrainStrip(List<List<Hexagon>> initialStrip, int rotation, int centerQOne, int centerROne,
                        int centerQTwo, int centerRTwo){
        this.rows = new ArrayList<>(initialStrip);
        this.rotation = rotation;
        this.rows.get(1).get(2).setCoordinates(centerQOne, centerROne);
        this.rows.get(1).get(3).setCoordinates(centerQTwo, centerRTwo);
        setNeighbours();
    }

    public TerrainStrip(List<List<Hexagon>> initialStrip){
        this.rows = new ArrayList<>(initialStrip);
    }

    private void setFirstElementCoordinates() {
        int[] coords = findFirstNonZeroCoordinates();
        int qCoord = coords[0];
        int rCoord = coords[1];
        int finalI = coords[2];
        int finalJ = coords[3];
        if(finalI != 1 && finalJ != 2){
            throw new NullPointerException("Center is not set!");
        }

        int[] firstCoords = getFirstCoordinates(qCoord, rCoord);
        rows.get(0).get(0).setCoordinates(firstCoords[0], firstCoords[1]);
    }

    private int[] findFirstNonZeroCoordinates() {
        for (int i = 0; i < rows.size(); i++) {
            for (int j = 0; j < rows.get(i).size(); j++) {
                if (rows.get(i).get(j).getQCoord() != 0 || rows.get(i).get(j).getRCoord() != 0) {
                    return new int[]{rows.get(i).get(j).getQCoord(), rows.get(i).get(j).getRCoord(), i, j};
                }
            }
        }
        return new int[]{0, 0, 0, 0}; // Default return if no non-zero coordinates found
    }

    private int[] getFirstCoordinates(int qCoord, int rCoord) {
        int inverseQ, inverseR;
        inverseR = switch (rotation) {
            case 60 -> {
                inverseQ = qCoord + 1;
                yield rCoord - 2;
            }
            case 120 -> {
                inverseQ = qCoord + 2;
                yield rCoord - 1;
            }
            case 180 -> {
                inverseQ = qCoord + 1;
                yield rCoord + 1;
            }
            case 240 -> {
                inverseQ = qCoord - 1;
                yield rCoord + 2;
            }
            case 300 -> {
                inverseQ = qCoord - 2;
                yield rCoord + 1;
            }
            default -> {
                inverseQ = qCoord - 1;
                yield rCoord - 1;
            }
        };
        return new int[]{inverseQ, inverseR};
    }


    public void setNeighbours() {
        if (rows.get(0).get(0).getQCoord() == 0 && rows.get(0).get(0).getRCoord() == 0) {
            setFirstElementCoordinates();
        }

        int[] rowSizes = {5, 6, 5};
        int centerRow = rowSizes.length / 2;
        int offset = (rotation % 360) / 60;

        for (int i = 0; i < rows.size(); i++) {
            for (int j = 0; j < rows.get(i).size(); j++) {
                Hexagon hex = rows.get(i).get(j);
                boolean beforeCenter = i <= centerRow;
                setNeighborsForHexagon(hex, i, j, rowSizes, beforeCenter, offset);
            }
        }
    }

    private void setNeighborsForHexagon(Hexagon hex, int i, int j, int[] rowSizes, boolean beforeCenter, int offset) {
        setTopRightNeighbor(hex, i, j, rowSizes, beforeCenter, offset);
        setRightNeighbor(hex, i, j, rowSizes, offset);
        setBottomRightNeighbor(hex, i, j, rowSizes, beforeCenter, offset);
        setBottomLeftNeighbor(hex, i, j, beforeCenter, offset);
        setLeftNeighbor(hex, i, j, offset);
        setTopLeftNeighbor(hex, i, j, beforeCenter, offset);
    }

    private void setTopRightNeighbor(Hexagon hex, int i, int j, int[] rowSizes, boolean beforeCenter, int offset) {
        if (i > 0 && j < rowSizes[i - 1] - 1) {
            hex.setNeighbor(offset, rows.get(i - 1).get(beforeCenter ? j : j + 1));
        }
    }

    private void setRightNeighbor(Hexagon hex, int i, int j, int[] rowSizes, int offset) {
        if (j < rowSizes[i] - 1) {
            hex.setNeighbor((1 + offset) % 6, rows.get(i).get(j + 1));
        }
    }

    private void setBottomRightNeighbor(Hexagon hex, int i, int j, int[] rowSizes, boolean beforeCenter, int offset) {
        if (i < rows.size() - 1 && j < rowSizes[i + 1] - 1) {
            if (i == 1) {
                beforeCenter = false;
            }
            hex.setNeighbor((2 + offset) % 6, rows.get(i + 1).get(beforeCenter ? j + 1 : j));
        }
    }

    private void setBottomLeftNeighbor(Hexagon hex, int i, int j, boolean beforeCenter, int offset) {
        if (i < rows.size() - 1) {
            if (i == 1) {
                beforeCenter = false;
            }
            if (beforeCenter) {
                hex.setNeighbor((3 + offset) % 6, rows.get(i + 1).get(j));
            } else if (j > 0) {
                hex.setNeighbor((3 + offset) % 6, rows.get(i + 1).get(j - 1));
            }
        }
    }

    private void setLeftNeighbor(Hexagon hex, int i, int j, int offset) {
        if (j > 0) {
            hex.setNeighbor((4 + offset) % 6, rows.get(i).get(j - 1));
        }
    }

    private void setTopLeftNeighbor(Hexagon hex, int i, int j, boolean beforeCenter, int offset) {
        if (i > 0 && j > 0) {
            hex.setNeighbor((5 + offset) % 6, rows.get(i - 1).get(beforeCenter ? j - 1 : j));
        }
    }

    public void setCoordinates(int q1, int r1, int q2, int r2){
        this.rows.get(1).get(2).setCoordinates(q1, r1);
        this.rows.get(1).get(3).setCoordinates(q2, r2);
    }


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
//        if (connectingSideLeft >= 2 && connectingSideRight >= 3){
//            int temp = 0;
//            temp = connectingSideLeft;
//            connectingSideLeft = connectingSideRight;
//            connectingSideRight = temp;
//        }
//    }

//    public void connectToTerrainStrip(List<Hexagon> connectSide, boolean connectingTopSide){
//        if (connectSide.size() != 4){
//            throw new IllegalArgumentException("Connecting side should be of size 4!");
//        }
//        for (Hexagon hexagon : connectSide) {
//            verifyConnectingSide(hexagon);
//        }
//        int sideToConnect = 0;
//        if (!connectingTopSide){
//            sideToConnect = 2;
//        }
//        getConnectingSides(connectSide.get(1));
//        for (int i = 0; i < connectSide.size(); i++) {
//            connectSide.get(i).setNeighbor(connectingSideLeft, rows.get(sideToConnect).get(i));
//            connectSide.get(i).setNeighbor(connectingSideRight, rows.get(sideToConnect).get(i+1));
//        }
//        setNeighbours();
//    }

    public ArrayList<int[]> getCoordinates(){
        ArrayList<int[]> center = new ArrayList<>();
        int[] centerOne = new int[2];
        int[] centerTwo = new int[2];
        centerOne[0] = rows.get(1).get(2).getQCoord();
        centerOne[1] = rows.get(1).get(2).getRCoord();
        center.add(centerOne);
        centerTwo[0] = rows.get(1).get(3).getQCoord();
        centerTwo[1] = rows.get(1).get(3).getRCoord();
        center.add(centerTwo);
        return center;
    }
}
