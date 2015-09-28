
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Ball implements Runnable {
	
	//Global Variables
	int x, y, xDirection, yDirection;
	
	int difficulty = 7;
	
	int p1score,p2score;
	
	Paddle p1 = new Paddle(15,140,1);
	Paddle p2 = new Paddle(370,140,2);
	
	Rectangle ball;
	
	public Ball(int x, int y){
		p1score = p2score = 0;
		this.x = x;
		this.y = y;
		
		//Set ball moving randomly
		Random r = new Random();
		int xrDir = r.nextInt(2);
		if (xrDir == 0)
			xrDir --;
		setXDirection(xrDir);
		
		int yrDir = r.nextInt(2);
		if (yrDir == 0)
			yrDir --;
		setYDirection(yrDir);
		
		//create 'ball'
		ball = new Rectangle(this.x, this.y, 7, 7);
	}
	
	public void setXDirection(int xdir){
		xDirection = xdir;
	}
	
	public void setYDirection(int ydir){
		yDirection = ydir;
	}
	
	public void draw(Graphics g){
		g.setColor(Color.WHITE);
		g.fillRect(ball.x, ball.y, ball.width, ball.height);
	}
	
	public void move(){
		collision();
		ball.x += xDirection;
		ball.y += yDirection;
		
		//Bounce the ball when edge is detected
		if(ball.x<=0){
			setXDirection(+1);
			p2score++;
		}
		if(ball.x>=385){
			setXDirection(-1);
			p1score++;
		}
		if(ball.y<=15)
			setYDirection(+1);
		if(ball.y>=285)
			setYDirection(-1);
	}
	
	public void collision(){
		if (ball.intersects(p1.paddle)){
			setXDirection(+1);
		}
		if (ball.intersects(p2.paddle)){
			setXDirection(-1);
		}
	}
	
	public void setDifficulty(int diff){
		difficulty = diff;
	}
    
    @Override
    public void run(){
        try{
        	while(true){
        		move();
        		Thread.sleep(difficulty);
        	}

        }catch(Exception e){System.err.println(e.getMessage());}
    }
    
}