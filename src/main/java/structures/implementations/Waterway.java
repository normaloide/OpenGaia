package structures.implementations;

import structures.interfaces.Way;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Waterway implements Way {

    private final Map<String, int[]> waterwayMap = new HashMap<>();

    public Waterway() {
        final int background = 0xE7F8FF;
        final int foreground = 0xA1E1FD;

        waterwayMap.put("river", new int[]{ background, foreground, 3, 2});
        waterwayMap.put("riverbank", new int[]{ background, foreground, 3, 2});
        waterwayMap.put("canal", new int[]{ background, foreground, 3, 2});

        // dashed types
        waterwayMap.put("ditch", new int[]{ background, foreground, 0, 1});
        waterwayMap.put("stream", new int[]{ background, foreground, 0, 1});
    }

    @Override
    public Color getBackgroundColor(String waterway ) {
        return new Color(waterwayMap.get(waterway)[0]);
    }

    @Override
    public Color getForegroundColor( String waterway ) {
        return new Color(waterwayMap.get(waterway)[1]);
    }

    @Override
    public int getBackgroundWidth( String waterway ) {
        return waterwayMap.get(waterway)[2];
    }

    @Override
    public int getForegroundWidth( String waterway ) {
        return waterwayMap.get(waterway)[3];
    }
}
