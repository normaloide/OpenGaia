package gui;

import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiPolygon;
import de.topobyte.osm4j.core.dataset.InMemoryMapDataSet;
import de.topobyte.osm4j.core.model.iface.OsmRelation;
import de.topobyte.osm4j.core.model.iface.OsmWay;
import de.topobyte.osm4j.core.resolve.EntityNotFoundException;
import de.topobyte.osm4j.geometry.RegionBuilder;
import de.topobyte.osm4j.geometry.RegionBuilderResult;
import de.topobyte.osm4j.geometry.WayBuilder;
import de.topobyte.osm4j.geometry.WayBuilderResult;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EntityGeometry {

    private final WayBuilder wayBuilder = new WayBuilder();
    private final RegionBuilder regionBuilder = new RegionBuilder();
    private final InMemoryMapDataSet heapData;

    public EntityGeometry(InMemoryMapDataSet heapData) {
        this.heapData = heapData;
    }

    public Collection<LineString> getLine(OsmWay way) {
        List<LineString> results = new ArrayList<>();
        try {
            WayBuilderResult lines = wayBuilder.build(way, heapData);
            results.addAll(lines.getLineStrings());
            if (lines.getLinearRing() != null) {
                results.add(lines.getLinearRing());
            }
        } catch (EntityNotFoundException ignored) { }

        return results;
    }

    public MultiPolygon getPolygon(OsmWay way) {
        try {
            RegionBuilderResult region = regionBuilder.build(way, heapData);
            return region.getMultiPolygon();
        } catch (EntityNotFoundException e) {
            return null;
        }
    }

    public MultiPolygon getPolygon(OsmRelation relation) {
        try {
            RegionBuilderResult region = regionBuilder.build(relation, heapData);
            return region.getMultiPolygon();
        } catch (EntityNotFoundException e) {
            return null;
        }
    }
}
