package components.board;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreType
public class HexagonalGrid extends Tile{
    @JsonIgnore
    private List<List<Hexagon>> rows;
    private int rotation;

    public HexagonalGrid(List<List<Hexagon>> initialGrid, int centerQ, int centerR) {
        this.rows = new ArrayList<>(initialGrid);
        rows.get(3).get(3).setCoordinates(centerQ, centerR);
        rotation = 0;
        setNeighbours();
    }

    public HexagonalGrid(List<List<Hexagon>> initialGrid){
        this.rows = new ArrayList<>(initialGrid);
    }

    public HexagonalGrid(List<List<Hexagon>> initialGrid, int centerQ, int centerR, int rotation) {
        this.rows = new ArrayList<>(initialGrid);
        rows.get(3).get(3).setCoordinates(centerQ, centerR);
        this.rotation = rotation;
        int numOfRotations = (rotation % 360)/60;
        for (int i = 0; i < numOfRotations; i++) {
            rotateGrid();
        }
        setNeighbours();
    }

    public void setRotation(int rotation){
        this.rotation = rotation;
        int numOfRotations = (rotation % 360)/60;
        for (int i = 0; i < numOfRotations; i++) {
            rotateGrid();
        }
    }

    public void setNeighbours(){
        rows.get(0).get(0).setCoordinates(rows.get(3).get(3).getQCoord(), rows.get(3).get(3).getRCoord() - 3);
        int[] rowSizes = {4, 5, 6, 7, 6, 5, 4};
        int centerRow = rowSizes.length / 2;

        for (int i = 0; i < rows.size(); i++) {
            // Determine if the row index is before, at, or after the central row
            boolean beforeCenter = i <= centerRow;
            for (int j = 0; j < rows.get(i).size(); j++) {
                Hexagon hex = rows.get(i).get(j);

                // Set top-right neighbor
                if (i > 0 && j < rowSizes[i - 1] - 1) {
                    hex.setNeighbor(0, rows.get(i - 1).get(beforeCenter ? j : j + 1));
                }

                // Set right neighbor
                if (j < rowSizes[i] - 1) {
                    hex.setNeighbor(1, rows.get(i).get(j + 1));
                }

                // Set bottom-right neighbor
                if (i < rows.size() - 1 && j < rowSizes[i + 1] - 1) {
                    if ( i == 3)
                        beforeCenter = false;
                    hex.setNeighbor(2, rows.get(i + 1).get(beforeCenter ? j + 1 : j));
                    beforeCenter = i <= centerRow;
                }

                // Set bottom-left neighbor
                if (i < rows.size() - 1) {
                    if ( i == 3)
                        beforeCenter = false;
                    if (beforeCenter){
                        hex.setNeighbor(3, rows.get(i + 1).get(j));
                    }
                    else if (j > 0){
                        hex.setNeighbor(3, rows.get(i + 1).get(j - 1));
                    }
                    beforeCenter = i <= centerRow;
                }

                // Set left neighbor
                if (j > 0) {
                    hex.setNeighbor(4, rows.get(i).get(j - 1));
                }

                // Set top-left neighbor
                if (i > 0 && j > 0) {
                    hex.setNeighbor(5, rows.get(i - 1).get(beforeCenter ? j - 1 : j));
                }
            }
        }
    }

    /**
     * Only works for Grid with the structure 4-5-6-7-6-5-4
     */
    private void rotateGrid() {

        List<List<Hexagon>> rings = new ArrayList<>();

        int[][] ringCoordinates = {
                {3, 3},
                {2, 2, 2, 3, 3, 4, 4, 3, 4, 2, 3, 2},
                {1, 1, 1, 2, 1, 3, 2, 4, 3, 5, 4, 4, 5, 3, 5, 2, 5, 1, 4, 1, 3, 1, 2, 1},
                {0, 0, 0, 1, 0, 2, 0, 3, 1, 4, 2, 5, 3, 6, 4, 5, 5, 4, 6, 3, 6, 2, 6, 1, 6, 0, 5, 0, 4, 0, 3, 0, 2, 0, 1, 0}
        };

        for (int[] coordinates : ringCoordinates) {
            List<Hexagon> ring = new ArrayList<>();
            for (int i = 0; i < coordinates.length; i += 2) {
                ring.add(rows.get(coordinates[i]).get(coordinates[i + 1]));
            }
            rings.add(ring);
        }


        // Rotate each ring
        for (int i = 0; i < rings.size(); i++) {
            rings.set(i, rotateRing(rings.get(i), i));
        }

        for (int i = 0; i < ringCoordinates.length; i++) {
            for (int j = 0; j < ringCoordinates[i].length; j+=2) {
                rows.get(ringCoordinates[i][j]).set(ringCoordinates[i][j+1], rings.get(i)
                        .get(j/2));
            }
        }

        //setNeighbours();
    }

    private List<Hexagon> rotateRing(List<Hexagon> ring, int times){
        int n = ring.size();
        int p = 1;
        while (p <= times) {
            Hexagon last = ring.get(n-1);
            for (int i = n-1; i > 0; i--) {
                ring.set(i, ring.get(i - 1));
            }
            ring.set(0, last);
            p++;
        }
        return ring;
    }

    private ArrayList<Hexagon> getTopSide(){
        return (ArrayList<Hexagon>) rows.get(0);
    }

    private ArrayList<Hexagon> getTopRightSide(){
        ArrayList<Hexagon> topRight = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            topRight.add(rows.get(i).get(rows.get(i).size()-1));
        }
        return topRight;
    }

    private ArrayList<Hexagon> getBottomRightSide(){
        ArrayList<Hexagon> bottomRight = new ArrayList<>();
        for (int i = 3; i < 7; i++) {
            bottomRight.add(rows.get(i).get(rows.get(i).size()-1));
        }
        return bottomRight;
    }

    private ArrayList<Hexagon> getBottomSide(){
        return new ArrayList<>(rows.get(rows.size() - 1));
    }

    private ArrayList<Hexagon> getBottomLeftSide(){
        ArrayList<Hexagon> bottomLeft = new ArrayList<>();
        for (int i = 3; i < 7; i++) {
            bottomLeft.add(rows.get(i).get(0));
        }
        return bottomLeft;
    }

    private ArrayList<Hexagon> getTopLeftSide(){
        ArrayList<Hexagon> topLeft = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            topLeft.add(rows.get(i).get(0));
        }
        return topLeft;
    }

    /**
     * Hexagon sides are marked like this
     * 1-top, 2-top right, 3-bottom right
     * 4-bottom, 5-bottom left, 6-top left
     * @param sideNum - number of hexagonal side
     * @return arraylist of hexagons for this side
     */
    public ArrayList<Hexagon> getHexagonSide(int sideNum){
        return switch (sideNum) {
            case 1 -> getTopSide();
            case 2 -> getTopRightSide();
            case 3 -> getBottomRightSide();
            case 4 -> getBottomSide();
            case 5 -> getBottomLeftSide();
            case 6 -> getTopLeftSide();
            default -> throw new IllegalArgumentException("Incorrect side of hexagon!");
        };
    }

    public int[] getCoordinates(){
        int[] center = new int[2];
        center[0] = rows.get(3).get(3).getQCoord();
        center[1] = rows.get(3).get(3).getRCoord();
        return center;
    }

    public void setCoordinates(int q, int r){
        rows.get(3).get(3).setCoordinates(q, r);
    }

    //
//    /**
//     * Hexagon sides are marked like this
//     * 1-top, 2-top right, 3-bottom right
//     * 4-bottom, 5-bottom left, 6-top left
//     * @param lastHexInConnectingSideHasTwoNeighbours - defines if the most left element in connecting side will
//     *                                                have 2 neighbours or 1. Affects the position of the connecting Hexagon.
//     */
//    public void connectToHexagonalGrid(int sideNumber, ArrayList<Hexagon> connectingSide, boolean lastHexInConnectingSideHasTwoNeighbours){
//        ArrayList<Hexagon> currentSide = getHexagonSide(sideNumber);
//        int currentSideLeft = -1;
//        int currentSideRight = -1;
//        for (int i = 0; i < connectingSide.get(1).getNeighbors().length; i++) {
//            if (currentSide.get(1).getNeighbors()[i] == null && currentSide.get(1).getNeighbors()[(i+1)%6] == null){
//                currentSideLeft = i;
//                currentSideRight = (i+1)%6;
//            }
//        }
//        if (currentSideLeft >= 2 && currentSideRight >= 3){
//            int temp = 0;
//            temp = currentSideLeft;
//            currentSideLeft = currentSideRight;
//            currentSideRight = temp;
//        }
//        if (lastHexInConnectingSideHasTwoNeighbours){
//            for (int i = 0; i < currentSide.size()-1; i++) {
//                currentSide.get(i).setNeighbor(currentSideLeft, connectingSide.get(i));
//                currentSide.get(i).setNeighbor(currentSideRight, connectingSide.get(i+1));
//            }
//            currentSide.get(currentSide.size() - 1).setNeighbor(currentSideLeft, connectingSide.get(3));
//        }
//        else{
//            currentSide.get(0).setNeighbor(currentSideRight, connectingSide.get(0));
//            for (int i = 1; i < currentSide.size(); i++) {
//                currentSide.get(i).setNeighbor(currentSideLeft, connectingSide.get(i-1));
//                currentSide.get(i).setNeighbor(currentSideRight, connectingSide.get(i));
//            }
//        }
//    }

//    public void activateBlockade(int sideNum){
//        ArrayList<Hexagon> side = getHexagonSide(sideNum);
//        for (Hexagon hex : side){
//            hex.setConnectedToBlockade();
//        }
//    }

//    public void deactivateBlockade(int sideNum, Blockade blockade){
//        ArrayList<Hexagon> side = getHexagonSide(sideNum);
//        for (Hexagon hex : side){
//            hex.unsetBlockade();
//        }
//        blockade.deactivateBlockade();
//    }
}
