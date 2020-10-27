package entities;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiPolygon;
import de.topobyte.osm4j.core.dataset.InMemoryMapDataSet;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.iface.OsmRelation;
import de.topobyte.osm4j.core.model.iface.OsmWay;
import de.topobyte.osm4j.core.model.util.OsmModelUtil;
import de.topobyte.osm4j.core.resolve.EntityFinder;
import de.topobyte.osm4j.core.resolve.EntityFinders;
import de.topobyte.osm4j.core.resolve.EntityNotFoundException;
import de.topobyte.osm4j.core.resolve.EntityNotFoundStrategy;
import gui.EntityGeometry;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EntityCollector implements DataTags {

    private final InMemoryMapDataSet heapData;
    private final EntityFinder entityFinder;
    private final Set<OsmWay> relationSet = new HashSet<>();
    private final Collection<OsmRelation> relationCollection;
    private final Collection<OsmWay> wayCollection;
    private final EntityGeometry geometry;
    private String key;

    /**
     * Class constructor: it passes heapData to class attributes and it
     * creates a new EntityFinders, used to find all way members of relations,
     * a new Collection<OsmRelation>, a Collection<OsmWay> and a Geometry.
     *
     * @param heapData entities kept in memory
     */
    public EntityCollector(InMemoryMapDataSet heapData) {
        this.heapData = heapData;
        this.entityFinder = EntityFinders.create(heapData, EntityNotFoundStrategy.IGNORE);
        this.relationCollection = heapData.getRelations().valueCollection();
        this.wayCollection = heapData.getWays().valueCollection();
        this.geometry = new EntityGeometry(heapData);
    }

    public void landuseCollector() {
        this.key = "landuse";
        relationCollector(landuses);
        wayCollector(landuses, validLanduses);
    }

    public void leisureCollector() {
        this.key = "leisure";
        relationCollector(leisures);
        specialNodeCollector(specialLeisures);
        wayCollector(leisures, validLeisures);
    }

    public void naturalCollector() {
        this.key = "natural";
        relationCollector(naturals);
        specialNodeCollector(specialNaturals);
        wayCollector(naturals, validNaturals);
    }

    private void relationCollector(Map<Geometry, String> map) {
        for (OsmRelation relation : this.relationCollection) {
            Map<String, String> tags = OsmModelUtil.getTagsAsMap(relation);
            String type = tags.get(key);
            if (type == null) {
                continue;
            }

            if (tags.containsKey(key)) {
                MultiPolygon area = this.geometry.getPolygon(relation);
                if (area != null) {
                    map.put(area, type);
                }

                try {
                    this.entityFinder.findMemberWays(relation, this.relationSet);
                } catch (EntityNotFoundException ignored) { }
            }
        }
    }

    private void specialNodeCollector(Map<double[], String> map) {
        for (OsmNode node : heapData.getNodes().valueCollection()) {
            Map<String, String> tags = OsmModelUtil.getTagsAsMap(node);
            String type = tags.get(key);
            if (type == null) {
                continue;
            }

            if (tags.containsKey(key)) {
                double[] place = {node.getLatitude(), node.getLongitude()};
                map.put(place, type);
            }
        }
    }

    private void wayCollector(Map<Geometry, String> map, Set<String> valid) {
        for (OsmWay way : this.wayCollection) {
            if (this.relationSet.contains(way)) {
                continue;
            }

            Map<String, String> tags = OsmModelUtil.getTagsAsMap(way);
            String type = tags.get(key);
            if (type == null) {
                continue;
            }

            if (!valid.contains(type)) {
                continue;
            }

            if (tags.containsKey(key)) {
                MultiPolygon area = this.geometry.getPolygon(way);
                if ( area != null ) {
                    map.put(area, type );
                }
            }
        }
    }

    public void buildingCollector() {
        this.key = "building";
        buildingRelationCollector();
        buildingWayCollector();
    }

    private void buildingRelationCollector() {
        for (OsmRelation relation : this.relationCollection) {
            Map<String, String> tags = OsmModelUtil.getTagsAsMap(relation);
            if (tags.containsKey(key)) {
                MultiPolygon area = this.geometry.getPolygon(relation);
                if (area != null) {
                    buildings.add(area);
                }

                try {
                    this.entityFinder.findMemberWays(relation, this.relationSet);
                } catch (EntityNotFoundException ignored) { }
            }
        }
    }

    private void buildingWayCollector() {
        for (OsmWay way : this.wayCollection) {
            if (this.relationSet.contains(way)) {
                continue;
            }

            Map<String, String> tags = OsmModelUtil.getTagsAsMap(way);
            if (tags.containsKey(key)) {
                MultiPolygon area = this.geometry.getPolygon(way);
                if (area != null) {
                    buildings.add(area);
                }
            }
        }
    }

    public void wayCollector() {
        for (OsmWay way : this.wayCollection) {
            Map<String, String> tags = OsmModelUtil.getTagsAsMap(way);
            this.key = "highway";
            String wayType = tags.get(key);

            if (wayType == null) {
                wayType = tags.get("waterway");
                if ( wayType == null ) {
                    continue;
                }
            }

            if (!validHighways.contains(wayType) && !validWaterways.contains(wayType)) {
                continue;
            }

            Collection<LineString> paths = this.geometry.getLine(way);
            for (LineString path : paths) {
                ways.put(path, wayType);
            }

            String name = tags.get("name");
            if (name == null) {
                continue;
            }

            for (LineString path : paths) {
                wayNames.put(path, name);
            }
        }
    }
}
