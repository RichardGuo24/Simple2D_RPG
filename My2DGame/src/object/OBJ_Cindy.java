package object;

import javax.imageio.ImageIO;

public class OBJ_Cindy extends SuperObject{
    public OBJ_Cindy(){
        name = "Cindy";

        try{    
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/cindy.png"));
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
