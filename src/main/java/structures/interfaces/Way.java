package structures.interfaces;

import java.awt.*;

public interface Way {
    Color getBackgroundColor(String way);
    Color getForegroundColor(String way);
    int getBackgroundWidth(String way);
    int getForegroundWidth(String way);
}
