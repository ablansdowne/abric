import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class Upgrade {
	int locx;
	int locy;
	KingdomGrid g;
	int width = 20;
	int height = 20;
	boolean obtained = false;

	public Upgrade(KingdomGrid grid, int x, int y) {
		// We use locx, locy to store the top-left corner of the knight
		locx = x;
		locy = y;
		g = grid;

	}
	
	public void update()
	{
		if (Knight.locx + Knight.width - 10 <= locx && locx + 20 < Knight.locx + Knight.width + 40 && KingdomScroll.swing)
		{
			
			KingdomScroll.shieldState = 1;
			obtained = true;
		}
	}
	
	public void render(GraphicsContext gc) {

		if (!obtained)
		{
			gc.setFill(Color.CYAN);
			gc.fillOval(locx - KingdomScroll.vleft, locy, width, height);
		}

		
	}
	
}
