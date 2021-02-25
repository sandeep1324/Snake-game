import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener {
	
	static final int SCREEN_WIDTH=600;
	static final int SCREEN_HEIGHT=600;
	static final int UNIT_SIZE=25;//how big we wants object in our game
	static final int GAME_UNITS=(SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;//total number of unit in our game 
	static final int DELAY=110;//higher the number slower the game.
	final int x[]=new int[GAME_UNITS];//it will hold all body parts of our snake in x axis
	final int[] y=new int[GAME_UNITS];//it will hold all body parts of our snake in y axis
	int bodyParts= 6;//initial body parts of snake
	int applesEaten=0;
	int appleX;//x axis of apple 
	int appleY;//y axis of apple
	char direction='R';
	boolean running=false;
	Timer timer;
	Random random;
	
   public GamePanel() {
		random=new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
   
   }
	
   public void startGame() {
		newApple();
		running=true;
		timer=new Timer(DELAY,this);
		timer.start();
	}
   
   public void paintComponent(Graphics g) {
	   super.paintComponent(g);
	   draw(g);
   }
   
   public void draw(Graphics g) {
	   
	if(running) {
	   /*this for loop help us to see grids in our game, uncomment it to see grid lines.
	    for(int i=0;i<SCREEN_HEIGHT/UNIT_SIZE;i++) { 
		   g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
		   g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
	   }*/
	   
	   g.setColor(Color.red);//sets color of appearing apple
	   g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);//apple appearance co-ordinate in size of one grid
   
       for(int i=0;i<bodyParts;i++) {
    	   if(i==0) {//for head
    		   g.setColor(Color.green);
    		   g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
    	   }
    	   else {//for rest of body parts
    		   //g.setColor(new Color(45,180,0)); //sets green colour
    		   g.setColor(new Color(random.nextInt(225),random.nextInt(225),random.nextInt(225))); //sets multi color
    		   g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
	   
    	   }
       }
       //Score appearance
       g.setColor(Color.RED);
	   g.setFont(new Font("MV Boli",Font.BOLD,40));
       FontMetrics metrics =getFontMetrics(g.getFont());
       g.drawString("Score: "+applesEaten, ( SCREEN_WIDTH-metrics.stringWidth("Score: "+applesEaten))/2,g.getFont().getSize());
	}
	else {
		gameOver(g);
	}
   }
   
   public void newApple() {
	   //co-ordinate's of new apple
	   appleX=random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
	   appleY=random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
	   
   }
   
   public void move() {
	   for(int i=bodyParts;i>0;i--) {
		   x[i]=x[i-1];
		   y[i]=y[i-1];
	   }
	   
	   switch(direction){
	   case 'U':
		   y[0]=y[0]-UNIT_SIZE;
		   break;
	   case 'D':
		   y[0]=y[0]+UNIT_SIZE;
		   break;
	   case 'L':
		   x[0]=x[0]-UNIT_SIZE;
		   break;
	   case 'R':
		   x[0]=x[0]+UNIT_SIZE;
		   break;
	   }
   }
   
   public void checkApple() {
	   
	   if((x[0]==appleX) && (y[0]==appleY)) {
		bodyParts++;
		applesEaten++;
		newApple();
	   }
   }
   
   public void checkCollision() {
	   //this check if head collides with body
	   for(int i=bodyParts;i>0;i--) {
		   if((x[0]==x[i])&&(y[0]==y[i])) {
			   running =false;
		   }
	   }
	   //this checks if head touches left border
	   if(x[0]<0) {
		   running=false;
	   }
	   //this checks if head touches right border
	   if(x[0]>SCREEN_WIDTH) {
		   running=false;
	   }
	   //this checks if head touches top border
	   if(y[0]<0) {
		   running=false;
	   }
	   //this checks if head touches bottom border
	   if(y[0]>SCREEN_HEIGHT) {
		   running=false;
	   }
	   if(!running) {
		   timer.stop();
	   }
   }
   
   public void gameOver(Graphics g) {
	   //Score
	   g.setColor(Color.RED);
	   g.setFont(new Font("MV Boli",Font.BOLD,40));
       FontMetrics metrics1 =getFontMetrics(g.getFont());
       g.drawString("Score: "+applesEaten, ( SCREEN_WIDTH-metrics1.stringWidth("Score: "+applesEaten))/2,g.getFont().getSize());
	
	  //game over text; 
	   g.setColor(Color.RED);
	   g.setFont(new Font("MV Boli",Font.BOLD,75));
       FontMetrics metrics =getFontMetrics(g.getFont());
       g.drawString("GAME  OVER", ( SCREEN_WIDTH-metrics.stringWidth("GAME  OVER"))/2,SCREEN_HEIGHT/2);
   
   }
	public void actionPerformed(ActionEvent arg0) {	
	
		if(running) {
			move();
			checkApple();
			checkCollision();
		}
		repaint();
	}
	
	public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(direction!='R') {
				direction='L';	
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(direction!='L') {
				direction='R';	
				}
				break;
			case KeyEvent.VK_UP:
				if(direction!='D') {
				direction='U';	
				}
				break;
			case KeyEvent.VK_DOWN:
					if(direction!='U') {
						direction='D';	
						}
						break;
			}
			
		}
	}
}
