package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity {

    GamePanel gP;
    KeyHandler keyH;
    int spriteCounter = 0;
    int spriteNum = 1;

    public final int screenX;
    public final int screenY;

    public Player(GamePanel input_gP, KeyHandler input_keyH) {
        this.gP = input_gP;
        this.keyH = input_keyH;

        screenX = gP.screenWidth / 2 - (gP.tileSize / 2);
        screenY = gP.screenHeight / 2 - (gP.tileSize / 2);

        SetMainValues();
        getPlayerImage();
    }

    public void SetMainValues() {
        worldX = 23 * gP.tileSize;
        worldY = 21 * gP.tileSize;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/res/player/boy_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/res/player/boy_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/res/player/boy_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/res/player/boy_down_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/res/player/boy_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/res/player/boy_right_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/res/player/boy_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/res/player/boy_left_2.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {

        //So spriteCounter does not increase when not pressing anything (no image changes when still)
        if (keyH.downPressed || keyH.upPressed || keyH.rightPressed || keyH.leftPressed) {

            if (keyH.upPressed == true) {
                direction = "up";
                worldY -= speed;
            } else if (keyH.downPressed == true) {
                direction = "down";
                worldY += speed;
            } else if (keyH.rightPressed == true) {
                direction = "right";
                worldX += speed;
            } else if (keyH.leftPressed == true) {
                direction = "left";
                worldX -= speed;
            }

            if (spriteCounter > 10) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }

            spriteCounter++;

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

        g2.drawImage(image, screenX, screenY, gP.tileSize, gP.tileSize, null);
    }
}
