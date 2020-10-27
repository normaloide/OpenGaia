package gui;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.LineString;
import de.topobyte.jts2awt.Jts2Awt;
import de.topobyte.mercator.image.MercatorImage;
import entities.DataTags;
import structures.implementations.*;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

public class Painter implements DataTags {

    private final Graphics2D g2d;
    private final MercatorImage mercator;

    public Painter(Graphics2D g2d, MercatorImage mercator) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        this.g2d = g2d;
        this.mercator = mercator;
    }

    public void paintBackground() {
        this.g2d.setColor(new Color(0xAFE39E));
        this.g2d.fillRect(0, 0, this.mercator.getWidth(), this.mercator.getHeight());
    }

    public void paintLanduse() {
        Landuse landuse = new Landuse();
        for (String landType : orderedLanduses) {
            for (Geometry land : landuses.keySet()) {
                String type = landuses.get(land);
                if (landType.equals(type)) {
                    TexturePaint texture = new TexturePaint(landuse.getTexture(type), new Rectangle(0, 0, 125, 125));
                    this.g2d.setPaint(texture);

                    Shape polygon = Jts2Awt.toShape(land, this.mercator);
                    this.g2d.fill(polygon);
                }
            }
        }
    }

    public void paintLeisure() {
        Leisure leisure = new Leisure();
        for (String leisureType : orderedLeisures) {
            for (Geometry leis : leisures.keySet()) {
                String type = leisures.get(leis);
                if (leisureType.equals(type)) {
                    TexturePaint texture = new TexturePaint(leisure.getTexture(type), new Rectangle(0, 0, 125, 125));
                    this.g2d.setPaint(texture);

                    Shape polygon = Jts2Awt.toShape(leis, this.mercator);
                    this.g2d.fill(polygon);
                }
            }
        }

        for (String specialLeisureType : specialOrderedLeisures) {
            for (double[] specialLeis : specialLeisures.keySet()) {
                String type = specialLeisures.get(specialLeis);
                if (specialLeisureType.equals(type)) {
                    Coordinate coordinate = new Coordinate(specialLeis[1], specialLeis[0]);

                    double cx = this.mercator.getX(coordinate.x);
                    double cy = this.mercator.getY(coordinate.y);

                    AffineTransform backup = this.g2d.getTransform();

                    this.g2d.translate(cx, cy);
                    this.g2d.scale(1, 1);
                    this.g2d.drawImage(leisure.getTexture(type), backup, null);

                    this.g2d.setTransform(backup);
                }
            }
        }
    }

    public void paintGroundNatural() {
        Natural natural = new Natural();
        for (String landType : orderedNaturals) {
            for (Geometry land : naturals.keySet()) {
                String type = naturals.get(land);
                if (landType.equals(type) && !orderedWaterNaturals.contains(type)) {
                    TexturePaint texture = new TexturePaint(natural.getTexture(type), new Rectangle(0, 0, 125, 125));
                    this.g2d.setPaint(texture);

                    Shape polygon = Jts2Awt.toShape(land, this.mercator);
                    this.g2d.fill(polygon);
                }
            }
        }

        for (String specialLeisureType : specialOrderedNaturals) {
            for (double[] special : specialNaturals.keySet()) {
                String type = specialNaturals.get(special);
                if (specialLeisureType.equals(type)) {
                    Coordinate coordinate = new Coordinate(special[1], special[0]);

                    double cx = this.mercator.getX(coordinate.x);
                    double cy = this.mercator.getY(coordinate.y);

                    AffineTransform backup = this.g2d.getTransform();

                    this.g2d.translate(cx, cy);
                    this.g2d.scale(1, 1);
                    this.g2d.drawImage(natural.getTexture(type), backup, null);

                    this.g2d.setTransform(backup);
                }
            }
        }
    }

    public void paintWaterway() {
        Waterway waterway = new Waterway();
        for ( String waterwayType : orderedWaterways ) {
            for (LineString street : ways.keySet()) {
                String type = ways.get(street);
                if (waterwayDashedTypes.contains(type) && waterwayType.equals(type)) {
                    this.g2d.setColor(waterway.getForegroundColor(type));
                    this.g2d.setStroke(new BasicStroke(waterway.getForegroundWidth(type), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,0, new float[]{5}, 0));

                    Path2D path = Jts2Awt.getPath(street, this.mercator);
                    this.g2d.draw(path);
                }
            }
        }

        for ( String waterwayType : orderedWaterways ) {
            for (LineString street : ways.keySet()) {
                String type = ways.get(street);
                if (!waterwayDashedTypes.contains(type) && waterwayType.equals(type) ) {
                    this.g2d.setColor(waterway.getBackgroundColor(type));
                    this.g2d.setStroke(new BasicStroke(waterway.getBackgroundWidth(type), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

                    Path2D path = Jts2Awt.getPath(street, this.mercator);
                    this.g2d.draw(path);
                }
            }
        }

        for ( String waterwayType : orderedWaterways ) {
            for (LineString street : ways.keySet()) {
                String type = ways.get(street);
                if (!waterwayDashedTypes.contains(type) && waterwayType.equals(type)) {
                    this.g2d.setColor(waterway.getForegroundColor(type));
                    this.g2d.setStroke(new BasicStroke(waterway.getForegroundWidth(type), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

                    Path2D path = Jts2Awt.getPath(street, this.mercator);
                    this.g2d.draw(path);
                }
            }
        }
    }

    public void paintWaterNatural() {
        Natural natural = new Natural();
        for (String landType : orderedWaterNaturals) {
            for (Geometry land : naturals.keySet()) {
                String type = naturals.get(land);
                if (landType.equals(type) && orderedWaterNaturals.contains(type)) {
                    TexturePaint texture = new TexturePaint(natural.getTexture(type), new Rectangle(0, 0, 125, 125));
                    this.g2d.setPaint(texture);

                    Shape polygon = Jts2Awt.toShape(land, this.mercator);
                    this.g2d.fill(polygon);
                }
            }
        }
    }

    public void paintBuilding() {
        this.g2d.setColor(new Color(0x000000));
        this.g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        for (Geometry building : buildings) {
            Shape polygon = Jts2Awt.toShape(building, this.mercator);
            this.g2d.draw(polygon);
        }

        this.g2d.setColor(new Color(0xA89E9E));
        this.g2d.setStroke(new BasicStroke(11, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        for (Geometry building : buildings) {
            Shape polygon = Jts2Awt.toShape(building, this.mercator);
            this.g2d.fill(polygon);
        }
    }

    public void paintHighway() {
        Highway highway = new Highway();
        for ( String highwayType : orderedHighways ) {
            for (LineString street : ways.keySet()) {
                String type = ways.get(street);
                if (highwayDashedTypes.contains(type) && highwayType.equals(type)) {
                    this.g2d.setColor(highway.getForegroundColor(type));
                    this.g2d.setStroke(new BasicStroke(highway.getForegroundWidth(type), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,0, new float[]{3}, 0));

                    Path2D path = Jts2Awt.getPath(street, this.mercator);
                    this.g2d.draw(path);
                }
            }
        }

        for ( String highwayType : orderedHighways ) {
            for (LineString street : ways.keySet()) {
                String type = ways.get(street);
                if (!highwayDashedTypes.contains(type) && highwayType.equals(type) ) {
                    this.g2d.setColor(highway.getBackgroundColor(type));
                    this.g2d.setStroke(new BasicStroke(highway.getBackgroundWidth(type), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

                    Path2D path = Jts2Awt.getPath(street, this.mercator);
                    this.g2d.draw(path);
                }
            }
        }

        for ( String highwayType : orderedHighways ) {
            for (LineString street : ways.keySet()) {
                String type = ways.get(street);
                if (!highwayDashedTypes.contains(type) && highwayType.equals(type)) {
                    this.g2d.setColor(highway.getForegroundColor(type));
                    this.g2d.setStroke(new BasicStroke(highway.getForegroundWidth(type), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

                    Path2D path = Jts2Awt.getPath(street, this.mercator);
                    this.g2d.draw(path);
                }
            }
        }
    }

    public void paintWayLabel() {
        this.g2d.setFont(this.g2d.getFont().deriveFont(12f));
        this.g2d.setColor(Color.BLACK);

        for (LineString way : ways.keySet()) {
            String name = wayNames.get(way);
            if (name == null) {
                continue;
            }

            paintLabel(way, name);
        }
    }

    private void paintLabel(LineString way, String name) {
        FontMetrics metrics = this.g2d.getFontMetrics();
        int numPoints = way.getNumPoints();

        for (int i = 2; i < numPoints; i += 3) {
            boolean upsideDown = false;

            Coordinate from = way.getCoordinateN(i-1);
            Coordinate to = way.getCoordinateN(i);

            double fromX = this.mercator.getX(from.x);
            double fromY = this.mercator.getY(from.y);
            double toX = this.mercator.getX(to.x);
            double toY = this.mercator.getY(to.y);

            double screenLineLength = Math.sqrt(Math.pow((toX - fromX), 2) + Math.pow((toY - fromY), 2));
            int textLength = metrics.stringWidth(name);

            if (screenLineLength < textLength) {
                continue;
            }

            AffineTransform backup = this.g2d.getTransform();

            this.g2d.translate(fromX, fromY);
            double offset;
            double rotation = Math.atan2(toY - fromY, toX - fromX);
            if (Math.abs(rotation) > 2) {
                upsideDown = true;
                rotation = Math.atan2(fromY - toY, fromX - toX);
            }

            this.g2d.rotate(rotation);
            if (upsideDown) {
                offset = (textLength - screenLineLength) - textLength;
            } else {
                offset = (screenLineLength - textLength) / 2;
            }

            this.g2d.translate(offset, 4);
            this.g2d.drawString(name, 0, 0);

            this.g2d.setTransform(backup);
        }
    }
}
