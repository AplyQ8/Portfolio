package components.board.serialization;

import components.board.Map;
import components.board.presets.MapA;
import components.board.presets.MapB;

public class JarMain {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java -jar eldorado.jar <parameter>");
            return;
        }

        String parameter = args[0];
        Map map = new Map();
        map = makeMap(parameter, map);
        SerializeJSON serializeJSON = new SerializeJSON();
        serializeJSON.serializeIntoJsonFile(map);
    }

    public static Map makeMap(String param, Map map){
        if ("A".equals(param)) {
            MapA mapA = new MapA();
            map = mapA.loadMap();
            return map;
        } else if ("B".equals(param)) {
            MapB mapB = new MapB();
            map = mapB.loadMap();
            return map;
        } else {
            System.out.println("Invalid parameter. Please use A or B.");
            return null;
        }
    }
}
