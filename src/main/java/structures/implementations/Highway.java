/*
Defines a HashMap that will associate each highway tag to two colors:
one color for the background and another for the foreground
 */

package structures.implementations;

import structures.interfaces.Way;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Highway implements Way {

    private final Map<String, int[]> highwayMap = new HashMap<>();

    public Highway() {
        final int background = 0xAFAFAF;

        highwayMap.put("motorway", new int[]{ background, 0xFF9D9D, 20, 18});
        highwayMap.put("motorway_link", new int[]{ background, 0xFF9D9D, 16, 14});

        highwayMap.put("trunk", new int[]{ background, 0xFFB59D, 20, 18});
        highwayMap.put("trunk_link", new int[]{ background, 0xFFB59D, 16, 14});

        highwayMap.put("primary", new int[]{ background, 0xFFD59D, 20, 18});
        highwayMap.put("primary_link", new int[]{background, 0xFFD59D, 16, 14});

        highwayMap.put("secondary", new int[]{ background, 0xFBFF9D, 16, 14});
        highwayMap.put("secondary_link", new int[]{ background, 0xFBFF9D, 12, 10});

        highwayMap.put("tertiary", new int[]{ background, 0xFAFAFA, 14, 12});
        highwayMap.put("tertiary_link", new int[]{ background, 0xFAFAFA, 12, 10});

        highwayMap.put("residential", new int[]{ background, 0xFAFAFA, 10, 8});
        highwayMap.put("unclassified", new int[]{ background, 0xFAFAFA, 10, 8});

        highwayMap.put("living_street", new int[]{ background, 0xDFFFFF, 4, 2});

        highwayMap.put("service", new int[]{ background, 0xFAFAFA, 4, 2});

        highwayMap.put("pedestrian", new int[]{ background, 0xD8D6FF, 3, 2});

        highwayMap.put("cycleway", new int[]{ background, 0xFF6262, 2, 1});

        //unknown types
        highwayMap.put("road", new int[]{ background, background, 2, 1});
        highwayMap.put("*", new int[]{ background, background, 2, 1});

        // dashed types
        highwayMap.put("track", new int[]{ 0xCD904A, 0xCD904A, 0, 1});
        highwayMap.put("bridleway", highwayMap.get("track"));
        highwayMap.put("path", highwayMap.get("track"));
        highwayMap.put("footway", new int[]{ 0xFF6666, 0xFF6666, 0, 1});
    }

    @Override
    public Color getBackgroundColor( String highway ) {
        return new Color(highwayMap.get(highway)[0]);
    }

    @Override
    public Color getForegroundColor( String highway ) {
        return new Color(highwayMap.get(highway)[1]);
    }

    @Override
    public int getBackgroundWidth( String highway ) {
        return highwayMap.get(highway)[2];
    }

    @Override
    public int getForegroundWidth( String highway ) { return highwayMap.get(highway)[3]; }
}
