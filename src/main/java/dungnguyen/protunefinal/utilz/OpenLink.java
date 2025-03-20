package dungnguyen.protunefinal.utilz;

import java.awt.*;
import java.net.URI;

public class OpenLink {
    public OpenLink(String link) {
        try {
            Desktop.getDesktop().browse(new URI(link));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
