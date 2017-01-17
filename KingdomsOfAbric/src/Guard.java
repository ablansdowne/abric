import javafx.geometry.BoundingBox;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Guard {

	public int locx;
	public int locy;
	public int bound1;
	public int bound2;
	public KingdomGrid g;
	public Color color;
	public int state;
	int dx = 0;
	public int dy = 0;
	public int width = 23;
	public int height = 20;
	
	public int gState = 0;
	public final int RIGHT = 0;
	public final int LEFT = 1;
	
	
	

	public Guard(KingdomGrid grid, int x, int y, int z, int w) {
		// We use locx, locy to store the top-left corner of the knight
		locx = x;
		locy = y;
		g = grid;
		bound1 = z;
		bound2 = w;
	}

	

	public void update() 
	{


		switch(gState) {
		case RIGHT: 
					if (KingdomScroll.difficulty == 0)
					{
						locx += 2;
					}
					else if (KingdomScroll.difficulty == 1)
					{
						locx += 4;
					}
					if (locx >= bound2)
					{
						gState = LEFT;
					}
					break;
		case LEFT: 
					if (KingdomScroll.difficulty == 0)
					{
						locx -= 2;
					}
					else if (KingdomScroll.difficulty == 1)
					{
						locx -= 4;
					}
					if (locx <= bound1)
					{
						gState = RIGHT;
					}
					break;
		}
		
	}

	public BoundingBox collisionBox() {
		return new BoundingBox(locx, locy, width, height);
	}

	public int locx() {
		return locx;
	}

	public int width() {
		return width;
	}

	public void render(GraphicsContext gc) {
			
			//gc.setFill(Color.BLACK);
			//gc.fillOval(locx - KingdomScroll.vleft, locy, width, height);
			gc.drawImage(KingdomScroll.badguy, locx - KingdomScroll.vleft, locy);
	}

}
