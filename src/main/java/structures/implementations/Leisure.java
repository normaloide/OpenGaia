package structures.implementations;

import structures.interfaces.Land;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Leisure implements Land {

    private final Map<String, BufferedImage> leisureMap = new HashMap<>();

    public Leisure() {
        try {
            leisureMap.put("garden", ImageIO.read(new File("leisure_textures/garden.png")));
            leisureMap.put("park", ImageIO.read(new File("leisure_textures/park.png")));
            leisureMap.put("pitch", ImageIO.read(new File("leisure_textures/pitch.png")));
            leisureMap.put("playground", leisureMap.get("park"));
            leisureMap.put("sports_centre", leisureMap.get("park"));

            //special
            leisureMap.put("picnic_table", ImageIO.read(new File("leisure_textures/picnic_table.png")));
            leisureMap.put("tanning_salon", ImageIO.read(new File("leisure_textures/tanning_salon.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public BufferedImage getTexture( String leisure ) {
        return leisureMap.get(leisure);
    }
}
