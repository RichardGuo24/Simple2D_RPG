package object;

import javax.imageio.ImageIO;

public class OBJ_Lawrencemi extends SuperObject{
    public OBJ_Lawrencemi(){
        name = "Lawrence";

        try{
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/lawrence.png"));
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
