package entities;

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
        landuseRelationCollector();
        landuseWayCollector();
    }

    private void landuseRelationCollector() {
        for (OsmRelation relation : this.relationCollection) {
            Map<String, String> tags = OsmModelUtil.getTagsAsMap(relation);
            String landuseType = tags.get(key);
            if (landuseType == null) {
                continue;
            }

            if (tags.containsKey(key)) {
                MultiPolygon area = this.geometry.getPolygon(relation);
                if (area != null) {
                    landuses.put(area, landuseType);
                }

                try {
                    this.entityFinder.findMemberWays(relation, this.relationSet);
                } catch (EntityNotFoundException ignored) { }
            }
        }
    }

    private void landuseWayCollector() {
        for (OsmWay way : this.wayCollection) {
            if (this.relationSet.contains(way)) {
                continue;
            }

            Map<String, String> tags = OsmModelUtil.getTagsAsMap(way);
            String landuseType = tags.get(key);
            if (landuseType == null) {
                continue;
            }

            if (!validLanduses.contains(landuseType)) {
                continue;
            }

            if (tags.containsKey(key)) {
                MultiPolygon area = this.geometry.getPolygon(way);
                if ( area != null ) {
                    landuses.put(area, landuseType );
                }
            }
        }
    }

    public void leisureCollector() {
        this.key = "leisure";
        leisureRelationCollector();
        specialLeisureNodeCollector();
        leisureWayCollector();
    }

    private void leisureRelationCollector() {
        for (OsmRelation relation : this.relationCollection) {
            Map<String, String> tags = OsmModelUtil.getTagsAsMap(relation);
            String leisureType = tags.get(key);
            if (leisureType == null) {
                continue;
            }

            if (tags.containsKey(key)) {
                MultiPolygon area = this.geometry.getPolygon(relation);
                if (area != null) {
                    leisures.put(area, leisureType);
                }

                try {
                    this.entityFinder.findMemberWays(relation, this.relationSet);
                } catch (EntityNotFoundException ignored) { }
            }
        }
    }

    private void specialLeisureNodeCollector() {
        for (OsmNode node : heapData.getNodes().valueCollection()) {
            Map<String, String> tags = OsmModelUtil.getTagsAsMap(node);
            String leisureType = tags.get(key);
            if (leisureType == null) {
                continue;
            }

            if (tags.containsKey(key)) {
                double[] place = {node.getLatitude(), node.getLongitude()};
                specialLeisures.put(place, leisureType);
            }
        }
    }

    private void leisureWayCollector() {
        for (OsmWay way : this.wayCollection) {
            if (this.relationSet.contains(way)) {
                continue;
            }

            Map<String, String> tags = OsmModelUtil.getTagsAsMap(way);
            String leisureType = tags.get(key);
            if (leisureType == null) {
                continue;
            }

            if (!validLeisures.contains(leisureType)) {
                continue;
            }

            if (tags.containsKey(key)) {
                MultiPolygon area = this.geometry.getPolygon(way);
                if (area != null) {
                    leisures.put(area, leisureType);
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
