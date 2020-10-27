package structures.implementations;

import structures.interfaces.Land;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Natural implements Land {

    private final Map<String, BufferedImage> naturalMap = new HashMap<>();

    public Natural() {
        try {
            //water
            naturalMap.put("water", ImageIO.read(new File("natural_textures/water.png")));

            //ground
            naturalMap.put("bare_rock", ImageIO.read(new File("natural_textures/bare_rock.png")));
            naturalMap.put("grassland", ImageIO.read(new File("natural_textures/grassland.png")));
            naturalMap.put("scree", ImageIO.read(new File("natural_textures/scree.png")));
            naturalMap.put("sand", ImageIO.read(new File("natural_textures/sand.png")));

            //special
            naturalMap.put("tree", ImageIO.read(new File("natural_textures/tree.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public BufferedImage getTexture(String natural) { return naturalMap.get(natural); }
}
