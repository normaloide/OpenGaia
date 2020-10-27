package structures.implementations;

import structures.interfaces.Land;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Landuse implements Land {

    private final Map<String, BufferedImage> landMap = new HashMap<>();

    public Landuse() {
        try {
            landMap.put("allotments", ImageIO.read(new File("landuse_textures/allotments.png")));
            landMap.put("basin", ImageIO.read(new File("landuse_textures/basin.png")));
            landMap.put("cemetery", ImageIO.read(new File("landuse_textures/cemetery.png")));
            landMap.put("commercial", ImageIO.read(new File("landuse_textures/commercial.png")));
            landMap.put("construction", ImageIO.read(new File("landuse_textures/construction.png")));
            landMap.put("farmland", ImageIO.read(new File("landuse_textures/farmland.png")));
            landMap.put("farmyard", ImageIO.read(new File("landuse_textures/farmyard.png")));
            landMap.put("garages", ImageIO.read(new File("landuse_textures/garages.png")));
            landMap.put("grass", ImageIO.read(new File("landuse_textures/grass.png")));
            landMap.put("industrial", ImageIO.read(new File("landuse_textures/industrial.png")));
            landMap.put("meadow", ImageIO.read(new File("landuse_textures/meadow.png")));
            landMap.put("military", ImageIO.read(new File("landuse_textures/military.png")));
            landMap.put("orchard", ImageIO.read(new File("landuse_textures/orchard.png")));
            landMap.put("recreation_ground", ImageIO.read(new File("landuse_textures/recreation_ground.png")));
            landMap.put("religious", ImageIO.read(new File("landuse_textures/religious.png")));
            landMap.put("reservoir", ImageIO.read(new File("landuse_textures/reservoir.png")));
            landMap.put("residential", ImageIO.read(new File("landuse_textures/residential.png")));
            landMap.put("retail", ImageIO.read(new File("landuse_textures/retail.png")));

            landMap.put("railway", landMap.get("industrial"));
            landMap.put("forest", landMap.get("meadow"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public BufferedImage getTexture(String land ) {
        return landMap.get(land);
    }
}
