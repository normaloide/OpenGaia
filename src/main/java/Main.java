import de.topobyte.adt.geo.BBox;
import de.topobyte.mercator.image.MercatorImage;
import de.topobyte.osm4j.core.access.OsmInputException;
import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.core.access.OsmReader;
import de.topobyte.osm4j.core.dataset.InMemoryMapDataSet;
import de.topobyte.osm4j.core.dataset.MapDataSetLoader;
import de.topobyte.osm4j.xml.dynsax.OsmXmlIterator;
import de.topobyte.osm4j.xml.dynsax.OsmXmlReader;
import gui.MapRendering;

import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, OsmInputException {
        Scanner in = new Scanner(System.in);
        System.out.print("Which map? ");
        String map = "map_" + in.nextLine() + ".osm";

        long startTime = System.currentTimeMillis();

        OsmIterator iterator = new OsmXmlIterator(map, true);
        double[] coords = {
                iterator.getBounds().getLeft(),
                iterator.getBounds().getBottom(),
                iterator.getBounds().getRight(),
                iterator.getBounds().getTop()
        };
        BBox box = new BBox(coords[0], coords[1], coords[2], coords[3]);

        InputStream stream = new FileInputStream(map);
        OsmReader reader = new OsmXmlReader(stream, true);
        InMemoryMapDataSet heapData = MapDataSetLoader.read(reader, true, true, true);

        Rectangle windowSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        MercatorImage mercator = new MercatorImage(box, windowSize.width, windowSize.height);
        MapRendering content = new MapRendering(box, mercator, heapData);
        content.start(content, windowSize.width, windowSize.height);

        long stopTime = System.currentTimeMillis();
        System.out.println("total execution time: " + (stopTime - startTime) / 1000.0);
    }
}
