package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{

    final int originalTileSize = 16; // 16x16
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; // 48x48 pixels per tile
    public final int maxScreenCol = 20;
    public final int maxScreenRow = 14;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    //World Settings

    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int maxWorldWidth = maxWorldCol * tileSize;
    public final int maxWorldHeight = maxWorldRow * tileSize;

    //System
    TileManager tileM = new TileManager(this);
    KeyHandler keyHandler = new KeyHandler();
    Sound music = new Sound();
    Sound se = new Sound();
    public UI ui = new UI(this);
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    Thread gameThread;

    //Entity and Object
    public Player player = new Player(this, keyHandler);
    public SuperObject obj[] = new SuperObject[10];

    //FPS
    int FPS = 60;
    

    public GamePanel(){
        //Figure out what this is in the grand scheme of things
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler); //Dont really know what this
        this.setFocusable(true); //Dont know what this is
    }

    public void setupGame(){

        aSetter.setObject();

        playMusic(0);

    }
    
    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run(){

        double drawInterval = 1000000000/FPS; //draw screen each .016667 seconds
        long lastTime = System.nanoTime();
        double delta = 0;
        long currentTime;
        
        while(gameThread != null){

            currentTime = System.nanoTime();
            
            //Basically calculates nano seconds between each from and divides it by number of nanoseconds I want in between each frame
            delta += (currentTime - lastTime) / drawInterval;

            lastTime = currentTime;

            if(delta >= 1){

                //Update information like character position
                update();
                //Update the screen with updated information
                repaint();

                delta = 0;

            }

        }

    }


    public void update(){
        
        player.update();

    }

    public void paintComponent(Graphics g){

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        //tiles
        tileM.draw(g2);

        //objects
        for(int i = 0; i < obj.length; i++){
            if(obj[i] != null){
                obj[i].draw(g2, this);
            }
        }

        //player
        player.draw(g2);

        ui.draw(g2);

        g2.dispose();
    }

    public void playMusic(int i){
        music.setFile(i);
        music.play();
        music.loop();
    }
    public void stopMusic(){
        music.stop();
    }

    public void playSE(int i){
        se.setFile(i);
        se.play();
    }

}
