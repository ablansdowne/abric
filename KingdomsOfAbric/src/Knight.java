import javafx.geometry.BoundingBox;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;


public class Knight {

	static public int locx;
	static public int locy;
	public KingdomGrid g;
	public Color color;
	public int state;
	static final int STAND = 0;
	static final int JUMP = 1;
	static final int WON = 2;
	static final int DEAD = 3;
	public int dir = 0;
	public int jump = 0;
	public int crouch = 0;
	public int dx = 0; public int dy = 0;
	public static int width = 20;
	public static int height = 20;
	static final int GRAVITY = 4;
	public int counter = 10;
	public int WonCounter = 100;
	public int swordY = 0; 
	public int shieldY = -3; 
	public int shieldState = 0; 
	public boolean swingBool = true;
	
	
	public Knight(KingdomGrid grid, int x, int y)
	{
		//We use locx, locy to store the top-left corner of the knight
		locx = x;
		locy = y;
		g = grid;
		color = Color.SILVER;
		state = STAND;
	}
	
	public void update()
	{
		//Handle movement inputs
		//Check direction flags set by the keyboard listener
		if (state != DEAD && state != WON) {

			if ((locx + width > 395 && locx < 440  && crouch != 1))
				dx = 0;
			else if (dir == 1)
				dx = -6;
			else if (dir == 2)
				dx = 6;
			else
				dx = 0;
			if ((state == STAND) && (jump == 1) && ((locx + width > 395) && (locx < 440)) )
			{
				jump = 0;
			}
			else if ((state == STAND) && (jump == 1) )
			{
				if (KingdomScroll.shieldState == 0)
					dy = -28;
				else if (KingdomScroll.shieldState == 1)
					dy = -38;
				state = JUMP;
				jump = 0;
			}
			

			// Then do the moving
			updatePosition();
		}
		else if(state == DEAD)
		{
			if (counter == 10) {
				KingdomScroll.lives--;
			}

			
			locy = -100;
			counter--;
			if (counter < 0) {
				locx = 100;
				locy = 499;
				color = Color.SILVER;
				state = STAND;
				counter = 10;
				if (KingdomScroll.level == 1)
				{
					KingdomScroll.guardBooleanArray[0] = true;
					KingdomScroll.guardBooleanArray[1] = true;
				}
				if (KingdomScroll.level == 2)
				{
					KingdomScroll.guardBooleanArray[2] = true;
					KingdomScroll.guardBooleanArray[3] = true;
					
				}
				if(KingdomScroll.level == 3)
				{
					KingdomScroll.guardBooleanArray[4] = true;
					KingdomScroll.guardBooleanArray[5] = true;
				}
				KingdomScroll.shieldState = 0;
				KingdomScroll.shieldUpgrade.obtained = false;
				
				if (KingdomScroll.lives == 0) {
					for (int i = 0; i < KingdomGrid.MWIDTH; i++)
					{
						for (int j = 0; j < KingdomGrid.MHEIGHT; j++)
						{
							KingdomGrid.map[i][j] = 0;
						}
					}
					KingdomScroll.guardBooleanArray[0] = true;
					KingdomScroll.guardBooleanArray[1] = true;
					KingdomScroll.guardBooleanArray[2] = false;
					KingdomScroll.guardBooleanArray[3] = false;
					KingdomScroll.guardBooleanArray[4] = false;
					KingdomScroll.guardBooleanArray[5] = false;
					
					KingdomScroll.changeLevel(1);
					KingdomScroll.lives = 3;
					KingdomScroll.shieldUpgrade.locx = 761;
				}
			}
		}
		else if (state == WON)
		{
			WonCounter--;
			if (WonCounter < 0) {
				
				locx = 100;
				locy = 499;
				color = Color.SILVER;
				state = STAND;
				WonCounter = 100;
				KingdomScroll.shieldState = 0;
				KingdomScroll.shieldUpgrade.obtained = false;
				
				if (KingdomScroll.level == 1)
				{	
					KingdomScroll.guardBooleanArray[0] = false;
					KingdomScroll.guardBooleanArray[1] = false;
					KingdomScroll.guardBooleanArray[2] = true;
					KingdomScroll.guardBooleanArray[3] = true;
					KingdomScroll.guardBooleanArray[4] = false;
					KingdomScroll.guardBooleanArray[5] = false;
					KingdomScroll.changeLevel(2);
					KingdomScroll.shieldUpgrade.locx = 350;
				}
				else if (KingdomScroll.level == 2)
				{
					
					KingdomScroll.guardBooleanArray[0] = false;
					KingdomScroll.guardBooleanArray[1] = false;
					KingdomScroll.guardBooleanArray[2] = false;
					KingdomScroll.guardBooleanArray[3] = false;
					KingdomScroll.guardBooleanArray[4] = true;
					KingdomScroll.guardBooleanArray[5] = true;
					KingdomScroll.changeLevel(3);
					KingdomScroll.shieldUpgrade.locx = 261;
				}
				else if (KingdomScroll.level == 3)
				{
					KingdomScroll.gameState = 3;
					KingdomScroll.guardBooleanArray[0] = true;
					KingdomScroll.guardBooleanArray[1] = true;
					KingdomScroll.guardBooleanArray[2] = false;
					KingdomScroll.guardBooleanArray[3] = false;
					KingdomScroll.guardBooleanArray[4] = false;
					KingdomScroll.guardBooleanArray[5] = false;
					KingdomScroll.changeLevel(1);
					KingdomScroll.shieldUpgrade.locx = 350;
				}
				KingdomScroll.lives = 3;
				for (int i = 0; i < KingdomGrid.MWIDTH; i++)
				{
					for (int j = 0; j < KingdomGrid.MHEIGHT; j++)
					{
						KingdomGrid.map[i][j] = 0;
					}
				}
				
				
			}
		}
	}
	
	public void SwingUpdate(){		
		if ((locy + swordY < locy + 5) && swingBool == true){
			swordY += 1;
		}
		else if (locy + swordY == locy + 5 && swingBool == true)
		{
			swingBool = false;
		}
		else if (swingBool == false) { 
			swordY -= 1;
		}
		if ((locy + swordY == locy) && swingBool == false) {
			swingBool = true;
			KingdomScroll.swing = false;
		}
		
	}
	
	
	public void updatePosition()
	{
		//Sideways movement
		if (dx > 0)
		{
			dx = g.moveRight(collisionBox(), dx);
		}
		else if (dx < 0)
		{
			dx = -g.moveLeft(collisionBox(), -dx);
		}
		if (dx != 0)
			locx += dx;
		if (state == JUMP)
		{
			//Figure out how far we can move (at our current speed) 
			//without running into something
			if (dy > 0)
			{
				dy = g.moveDown(collisionBox(), dy);
			}
			else if (dy < 0)
			{
				dy = -g.moveUp(collisionBox(), -dy);
			}
			
			//Adjust position
			if (dy != 0)
				locy += dy;
			
			//Adjust dy to allow for the force of gravity
			dy += GRAVITY;
			
			//Impose terminal velocity
			if (dy > KingdomGrid.CELLSIZE - 1)
				dy = KingdomGrid.CELLSIZE - 1;
			
			if(g.fellDown(collisionBox()))
			{
				dy = 0;
				state = DEAD;
			}
			
			if(g.foundAmulet(collisionBox() ))
			{
				
				state = WON;
			}
			
			if(g.onGround(collisionBox()))
			{
				dy = 0;
				state = STAND;
			}
			else if (g.atTop(collisionBox()))
			{
				dy = 0;
			}
		
		}
		
		else
			if (!g.onGround(collisionBox()))
			{
				state = JUMP;
			}
	}
	
	public BoundingBox collisionBox()
	{
		return new BoundingBox(locx, locy, width, height);
	}
	
	public int locx()
	{
		return locx;
	}
	
	public int width()
	{
		return width;
	}
	
	public void render(GraphicsContext gc)
	{
		gc.setFill(color);
		gc.fillOval(locx-KingdomScroll.vleft, locy, width, height);
		

		if (crouch == 1)
		{
			
			//do not draw images
			
		}
		else {

			
			if (KingdomScroll.genderState == 0 && state != DEAD)
			{
				gc.drawImage(KingdomScroll.girlKnight,locx-KingdomScroll.vleft - 7, locy - 30 );
				
			}
			else if (KingdomScroll.genderState == 1 && state != DEAD)
			{
				gc.drawImage(KingdomScroll.boyKnight,locx-KingdomScroll.vleft - 17, locy - 30 );
			}
			
			if (state != DEAD)
			{
				
				gc.drawImage(KingdomScroll.sword,locx-KingdomScroll.vleft + 5, locy + swordY );
				if (KingdomScroll.shieldState == 0)
				{
					gc.drawImage(KingdomScroll.shield,locx-KingdomScroll.vleft - 10, locy + shieldY );
				}
				else if (KingdomScroll.shieldState == 1)
				{
					gc.drawImage(KingdomScroll.ultraShield, locx-KingdomScroll.vleft - 10, locy + shieldY);
					
				}
				
			}
		}
	}
	
}
