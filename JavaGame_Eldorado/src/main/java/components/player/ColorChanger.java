package components.player;

import java.util.HashMap;
import java.util.Map;

public class ColorChanger {

    private static volatile ColorChanger _instance;
    private Map<String, String> colorMap;

    private ColorChanger() {
        colorMap = new HashMap<>();
        colorMap.put("green", "\u001B[32m");
        colorMap.put("blue", "\u001B[34m");
        colorMap.put("purple", "\u001B[35m");
        colorMap.put("yellow", "\u001B[33m");
        colorMap.put("default", "\u001B[0m");
    }
    public static ColorChanger getInstance(){
        if (_instance == null) {
            synchronized (ColorChanger.class) {
                if (_instance == null) {
                    _instance = new ColorChanger();
                }
            }
        }
        return _instance;
    }

    public String colorCode(String cardColor){
        return colorMap.getOrDefault(cardColor.toLowerCase(), colorMap.get("default"));
    }
}
