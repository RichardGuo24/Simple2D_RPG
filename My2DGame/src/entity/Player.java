package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

public class Player extends Entity {

    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    public int hasKey = 0;

    public Player(GamePanel input_gP, KeyHandler input_keyH) {
        this.gp = input_gP;
        this.keyH = input_keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);



        //so it scales
        solidArea = new Rectangle((int) (1.0/4 * gp.tileSize), (int) (1.0/4 * gp.tileSize), (int) (2.0/4 * gp.tileSize), (int) (2.0/3 * gp.tileSize));
        
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        SetMainValues();
        getPlayerImage();
    }

    public void SetMainValues() {
        worldX = 23 * gp.tileSize;
        worldY = 21 * gp.tileSize;
        speed = 6;
        direction = "down";
    }

    public void getPlayerImage() {

        //up1 = setup("boy_up_1");
        //up2 = setup("boy_up_2");
        //down1 = setup("boy_down_1");
        //down2 = setup("boy_down_2");
        //right1 = setup("boy_right_1");
        //right2 = setup("boy_right_2");
        //left1 = setup("boy_left_1");
        //left2 = setup("boy_left_2");
        
        
        up1 = setup("up1");
        up2 = setup("up2");
        down1 = setup("down1");
        down2 = setup("down2");
        right1 = setup("right1");
        right2 = setup("right2");
        left1 = setup("left1");
        left2 = setup("left2");
        
        

    }

    public BufferedImage setup(String imageName){

        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try{
            image = ImageIO.read(getClass().getResourceAsStream("/res/player/" + imageName + ".png"));
            image = uTool.scaledImage(image, gp.tileSize, gp.tileSize);

        } catch (IOException e){
            e.printStackTrace();
        }

        return image;
    }

    public void update() {

        //So spriteCounter does not increase when not pressing anything (no image changes when still)
        if (keyH.downPressed || keyH.upPressed || keyH.rightPressed || keyH.leftPressed) {

            if (keyH.upPressed == true) {
                direction = "up";
            } else if (keyH.downPressed == true) {
                direction = "down";
            } else if (keyH.rightPressed == true) {
                direction = "right";
            } else if (keyH.leftPressed == true) {
                direction = "left";
            }

            collisionOn = false;
            gp.cChecker.CheckTile(this);

            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            if(collisionOn == false){

                switch(direction){
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                }
            }

            //might
            spriteCounter++;

            if (spriteCounter > 12) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }

            

        }
    }

    public void pickUpObject(int i){
        if(i != 2000000000){
            String objectName = gp.obj[i].name;

            switch(objectName){
                case "Key":
                    hasKey++;
                    gp.playSE(1);
                    gp.ui.showMessage("You found a key!");
                    gp.obj[i] = null;
                    break;
                case "Door":
                    if(hasKey > 0){
                        gp.ui.showMessage("You unlocked a door!");
                        gp.playSE(3);
                        gp.obj[i] = null;
                        hasKey--;
                    }else{
                        gp.ui.showMessage("You need more keys!");
                    }
                    break;

                case "Chest":
                    gp.ui.gameFinished = true;
                    gp.stopMusic();
                    gp.playSE(4);
                    
            }
        }
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;

        switch (direction) {

            case "up":
                if (spriteNum == 1) {
                    image = up1;
                } else if (spriteNum == 2) {
                    image = up2;
                }
                break;
            case "down":
                if (spriteNum == 1) {
                    image = down1;
                } else if (spriteNum == 2) {
                    image = down2;
                }
                break;
            case "right":
                if (spriteNum == 1) {
                    image = right1;
                } else if (spriteNum == 2) {
                    image = right2;
                }
                break;
            case "left":
                if (spriteNum == 1) {
                    image = left1;
                } else if (spriteNum == 2) {
                    image = left2;
                }
                break;
        }

        g2.drawImage(image, screenX, screenY,null);

        //JUST FOR SEEING THE HIT BOX
        /*
        g2.setColor(Color.RED); // Set the color to red or any other color you prefer
        g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
        */
    }
}
