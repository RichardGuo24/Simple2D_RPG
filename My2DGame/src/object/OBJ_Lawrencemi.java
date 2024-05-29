package object;

import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_Lawrencemi extends SuperObject{

    GamePanel gp;

    public OBJ_Lawrencemi(GamePanel gp){

        this.gp = gp;
        name = "Lawrence";

        try{
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/lawrence.png"));
            uTool.scaledImage(image, gp.tileSize, gp.tileSize);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
