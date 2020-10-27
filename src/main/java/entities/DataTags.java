package entities;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.LineString;

import java.util.*;

public interface DataTags {
    List<String> orderedLanduses = Arrays.asList("residential", "cemetery", "commercial",
            "construction", "industrial", "retail", "orchard", "meadow", "park", "grass",
            "farmyard", "farmland", "garages", "military", "forest", "allotments",
            "basin", "park", "sports_centre", "playground", "reservoir",
            "recreation_ground", "religious", "railway");
    Set<String> validLanduses = new HashSet<>(orderedLanduses);
    Map<Geometry, String> landuses = new HashMap<>();


    List<String> orderedLeisures = Arrays.asList("garden", "park", "pitch", "playground",
            "sports_centre");
    List<String> specialOrderedLeisures = Arrays.asList("tanning_salon", "picnic_table");
    Set<String> validLeisures = new HashSet<>(orderedLeisures);
    Map<Geometry, String> leisures = new HashMap<>();
    Map<double[], String> specialLeisures = new HashMap<>();

    List<String> orderedNaturals = Arrays.asList("water", "bare_rock", "grassland", "sand");
    List<String> orderedWaterNaturals = Arrays.asList("water");
    List<String> specialOrderedNaturals = Arrays.asList("tree");
    Map<Geometry, String> naturals = new HashMap<>();
    Set<String> validNaturals = new HashSet<>(orderedNaturals);
    Map<double[], String> specialNaturals = new HashMap<>();

    List<Geometry> buildings = new ArrayList<>();

    List<String> orderedWaterways = Arrays.asList("river", "riverbank", "canal", "ditch", "stream");
    List<String> orderedHighways = Arrays.asList("*", "path", "bridleway",
            "footway", "cycleway", "pedestrian", "road", "track", "service",
            "living_street", "unclassified", "residential", "tertiary_link",
            "tertiary", "secondary_link", "secondary", "primary_link",
            "primary", "trunk_link", "trunk", "motorway_link", "motorway");
    List<String> waterwayDashedTypes = Arrays.asList("ditch", "stream");
    List<String> highwayDashedTypes = Arrays.asList("track", "bridleway", "path", "footway");
    Set<String> validWaterways = new HashSet<>(orderedWaterways);
    Set<String> validHighways = new HashSet<>(orderedHighways);
    Map<LineString, String> ways = new HashMap<>();
    Map<LineString, String> wayNames = new HashMap<>();
}
