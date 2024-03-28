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

    GamePanel gp;
    Tile[] tile;


    //Array for mapTiles
    int[][] mapTileNum;

    public TileManager(GamePanel gp){
        this.gp = gp;

        tile = new Tile[10];
        mapTileNum = new int[gp.maxScreenRow][gp.maxScreenCol];

        getTileImage();
        loadMap("/res/maps/map01.txt");

    }

    public void getTileImage(){
        try {
            //grass
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/grass.png"));

            //wall
            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/wall.png"));


            //water
            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/water.png"));

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
    
            while(row < gp.maxScreenRow && col < gp.maxScreenCol){
                String line = br.readLine();
                
    
                while(col < gp.maxScreenCol){
                    String[] numbers = line.split(" ");
                    
                    //Seperate a number to get rid of any non number stuff in string so that parseInt works
                    String tileNum = numbers[col];
                    tileNum = tileNum.replaceAll("[^0-9]+", "");

                    int num = Integer.parseInt(tileNum);

                    mapTileNum[row][col] = num;

                    col++;

                }if(col == gp.maxScreenCol){
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

        
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while(col < gp.maxScreenCol && row < gp.maxScreenRow){

            int tileNum = mapTileNum[row][col];
            
            g2.drawImage(tile[tileNum].image,x,y,gp.tileSize,gp.tileSize, null);
            col++;
            x += 48;

            if(col == gp.maxScreenCol){
                col = 0;
                x = 0;
                y += 48;
                row++;
            }

        }

    }
}
