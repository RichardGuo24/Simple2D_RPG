package object;

import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_Cindy extends SuperObject{

    GamePanel gp;

    public OBJ_Cindy(GamePanel gp){
        this.gp = gp;

        name = "Cindy";

        try{    
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/cindy.png"));
            uTool.scaledImage(image, gp.tileSize, gp.tileSize);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
