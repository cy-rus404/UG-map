package app;

import java.awt.*;

public class UgMap extends Canvas {

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.CYAN);
        Toolkit t = Toolkit.getDefaultToolkit();
        Image i = t.getImage("/Desktop/Group-12-DCIT-204-project/ug-map-ui-master/app/asset/map_info.png");
        g.drawImage(i, 10, 10, this);
    }

}
