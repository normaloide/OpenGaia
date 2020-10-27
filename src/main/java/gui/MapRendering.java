package gui;

import de.topobyte.adt.geo.BBox;
import de.topobyte.mercator.image.MercatorImage;
import de.topobyte.osm4j.core.dataset.InMemoryMapDataSet;
import entities.DataTags;
import entities.EntityCollector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class MapRendering extends JPanel implements DataTags {

    private MercatorImage mercator;
    private final BBox box;
    private final InMemoryMapDataSet heapData;

    /**
     * Class constructor: inside, the addComponentListener to repaint the panel
     * when its size changes. The method collectData() is always executed.
     *
     * @param box a box indicating the rendering dimensions
     * @param mercator an image that shows data using Mercator projection
     * @param heapData the data kept in memory, containing all the needed entities
     */
    public MapRendering(BBox box, MercatorImage mercator, InMemoryMapDataSet heapData) {
        this.box = box;
        this.mercator = mercator;
        this.heapData = heapData;
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                refreshMercatorImage();
                repaint();
            }
        });

        collectData();
    }

    /**
     * The entities are collected using an EntityCollector. Thanks to public methods
     * in EntityCollector, we can choose which entities to collect. This is useful
     * when we render the map from far away and we don't want to collect every thing
     * such as leisures or not main roads (we can't for example keep 20GB of data in
     * our heap).
     */
    private void collectData() {
        EntityCollector collector = new EntityCollector(heapData);

        collector.landuseCollector();
        collector.leisureCollector();
        collector.naturalCollector();
        collector.buildingCollector();
        collector.wayCollector();
    }

    /**
     * Overriding the paintComponent from JComponent.java so we are able to
     * to create our polygons and modify the transform where needed. Also we
     * invoke the super's implementation so that we don't have to honor the
     * opaque property. Then we create a Graphics2D object and pass it to Painter.
     *
     * @param g a Graphics object to respect super's implementation
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        Painter painter = new Painter(g2d, mercator);

        painter.paintBackground();
        painter.paintLanduse();
        painter.paintLeisure();
        painter.paintGroundNatural();
        painter.paintWaterway();
        painter.paintWaterNatural();
        painter.paintBuilding();
        painter.paintHighway();
        painter.paintWayLabel();
    }

    /**
     * Creates a new MercatorImage. Used in the addComponentListener.
     */
    private void refreshMercatorImage() {
        this.mercator = new MercatorImage(box, getWidth(), getHeight());
    }

    /**
     * This is called from Main and starts all the MapRendering process.
     *
     * @param content the content that will be rendered
     * @param width the window width
     * @param height the window height
     */
    public void start(MapRendering content, int width, int height) {
        JFrame frame = new JFrame("OpenGaia");
        content.setPreferredSize(new Dimension(width, height));
        ImageIcon icon = new ImageIcon("favicon.png");

        frame.setContentPane(content);
        frame.setIconImage(icon.getImage());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.pack();

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
        frame.setAlwaysOnTop(true);
        frame.requestFocus();
    }
}