package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {

    //Have tiles and display them somehow

    private GamePanel gp;
    public Tile[] tile;


    //Array for mapTiles
    public int[][] mapTileNum;

    public TileManager(GamePanel gp){
        this.gp = gp;

        tile = new Tile[10];
        mapTileNum = new int[gp.maxWorldRow][gp.maxWorldCol];

        getTileImage();
        loadMap("/res/maps/world02.txt");

    }

    public void getTileImage(){
        try {
            //grass
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/grass.png"));

            //wall
            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/wall.png"));
            tile[1].collision = true;

            //water
            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/water.png"));
            tile[2].collision = true;

            //earth
            tile[3] = new Tile();
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/earth.png"));

            //tree
            tile[4] = new Tile();
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/tree.png"));
            tile[4].collision = true;

            //sand
            tile[5] = new Tile();
            tile[5].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/sand.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    public void loadMap(String filePath){

        try{
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
    
            int col = 0;
            int row = 0;
    
            while(row < gp.maxWorldRow && col < gp.maxWorldCol){
                String line = br.readLine();
                
    
                while(col < gp.maxWorldCol){
                    String[] numbers = line.split(" ");
                    
                    //Seperate a number to get rid of any non number stuff in string so that parseInt works
                    String tileNum = numbers[col];
                    tileNum = tileNum.replaceAll("[^0-9]+", "");

                    int num = Integer.parseInt(tileNum);

                    mapTileNum[row][col] = num;

                    col++;

                }if(col == gp.maxWorldCol){
                    col = 0;
                    row++;
                }
    
            }
            br.close();

        }catch(Exception e){
            e.printStackTrace();
        }

    }
    

    public void draw(Graphics2D g2){

        
        int worldCol = 0;
        int worldRow = 0;

        while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow){

            int tileNum = mapTileNum[worldRow][worldCol];
            
            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;


            if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && 
            worldX - gp.tileSize < gp.player.worldX + gp.player.screenX && 
            worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
            worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){
                g2.drawImage(tile[tileNum].image,screenX,screenY,gp.tileSize,gp.tileSize, null);
            }


            worldCol++;


            if(worldCol == gp.maxWorldCol){
                worldCol = 0;
                worldRow++;
            }

        }

    }
}
