import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class Main extends JFrame {
    
    //Double buffering
    Image dbImage;
    Graphics dbg;
    
    //Ball objects
    static Ball b = new Ball(193, 143);
    
    //Threads
    Thread ball = new Thread(b);
    Thread p1 = new Thread(b.p1);
    Thread p2 = new Thread(b.p2);
    
    //Checks whether game has started
    boolean gameStarted = false;
    
    //checks whether mouse is hovering over buttons
    boolean startHover;
    boolean difficultyHover;
    
    //checks difficulty
    boolean hardDifficulty = false;
    
    //Menu Buttons
    Rectangle startButton = new Rectangle(150, 100, 100, 25);
    Rectangle difficultyButton = new Rectangle(150, 150, 100, 25);
    //Variables for screen size
    int
    GWIDTH = 400,
    GHEIGHT = 300;
    //Dimension of GWIDTH*GHEIGHT
    Dimension screenSize = new Dimension(GWIDTH, GHEIGHT);
    
    //Create constructor to spawn window
    public Main(){
        this.setTitle("Pong Game");
        this.setSize(screenSize);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setBackground(Color.DARK_GRAY);
        this.addKeyListener(new KeyHandler());
        this.addMouseListener(new MouseHandler());
        this.addMouseMotionListener(new MouseHandler());
    }
    
    public void startGame(){
    	gameStarted = true;
    	ball.start();
    	p1.start();
    	p2.start();
    }
    
    public static void main(String[] args){
        Main m = new Main();
    }
    
    @Override
    public void paint(Graphics g){
        dbImage = createImage(getWidth(), getHeight());
        dbg = dbImage.getGraphics();
        draw(dbg);
        g.drawImage(dbImage, 0, 0, this);
    }
    public void draw(Graphics g){
    	
    	if(!gameStarted){
            g.setFont(new Font("Arial", Font.BOLD, 26));
            g.setColor(Color.WHITE);
            g.drawString("Pong Game!", 125, 75);
            if (!startHover){
            	g.setColor(Color.CYAN);
            }
            else{
            	g.setColor(Color.PINK);
            }
            	
            g.fillRect(startButton.x, startButton.y, startButton.width, startButton.height);
            g.setFont(new Font("Arial", Font.BOLD, 12));
            g.setColor(Color.GRAY);
            g.drawString("Start Game", startButton.x+20, startButton.y+17);
            if (!difficultyHover){
            	g.setColor(Color.CYAN);
            }
            else{
            	g.setColor(Color.PINK);
            }

            g.fillRect(difficultyButton.x, difficultyButton.y, difficultyButton.width, difficultyButton.height);
            g.setColor(Color.GRAY);
            g.drawString("Difficulty:",difficultyButton.x+5, difficultyButton.y+17);
            if(!hardDifficulty){
            	g.setColor(Color.BLUE);
            	g.drawString("Easy",difficultyButton.x+65,difficultyButton.y+17);
            }
            else{
            	g.setColor(Color.RED);
            	g.drawString("Hard",difficultyButton.x+65,difficultyButton.y+17);
            }
            	
            //Menu
    	}
    	else{
            //Game drawings
            b.draw(g);
            b.p1.draw(g);
            b.p2.draw(g);
            //Scores
            g.setColor(Color.WHITE);
            g.drawString(""+b.p1score, 15, 50);
            g.drawString(""+b.p2score, 370, 50);
    	}


            
        
        repaint();
    }
    
    ////////EVENT LISTENER CLASSES/////////
    public class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e){
            b.p1.keyPressed(e);
            b.p2.keyPressed(e);
        }
        @Override
        public void keyReleased(KeyEvent e){
            b.p1.keyReleased(e);
            b.p2.keyReleased(e);
        }
    }
    public class MouseHandler extends MouseAdapter {
        @Override
        public void mouseMoved(MouseEvent e){
            int mx = e.getX();
            int my = e.getY();
            if(startButton.contains(mx, my)){
            	startHover = true;
            }
            else{
            	startHover = false;
            }
            if(difficultyButton.contains(mx, my)){
            	difficultyHover = true;
            }
            else{
            	difficultyHover = false;
            }
        }
        @Override
        public void mousePressed(MouseEvent e){
            int mx = e.getX();
            int my = e.getY();
            if(startButton.contains(mx, my)){
            	startGame();
            }
            if(difficultyButton.contains(mx, my)){
            	if(!hardDifficulty){
            		b.setDifficulty(4);
            		hardDifficulty = true;
            	}
            	else{
            		b.setDifficulty(7);
            		hardDifficulty = false;
            	}
            }
        }
    }
    ///////END EVENT LISTENER CLASSES/////
    
}