import javafx.geometry.BoundingBox;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class KingdomGrid {

	public static final int MWIDTH = 40;
	public static final int MHEIGHT = 15;
	public static int map[][] = new int[MWIDTH][MHEIGHT];
	static final int CELLSIZE = 40; //Number of pixels per map cell

	
	public void setBlock(int x, int y)
	{
		map[x][y] = 1;
	}
	
	public void setAmulet(int x, int y)
	{
		map[x][y] = 2;
	}
	
	public void setCrouchBlock(int x, int y)
	{
		map[x][y] = 3;
	}

	public int moveRight(BoundingBox b, int d)
	{
		//Return the number of pixels (not exceeding d) that the rectangle
		//b can move to the right without hitting a block
		//Assume d is less than CELLSIZE
		int right = (int)b.getMaxX();
		int col = right/CELLSIZE;
		int row1 = ((int)b.getMinY())/CELLSIZE;
		int row2 = ((int)b.getMaxY())/CELLSIZE;
		if (row2 >= MHEIGHT)
			row2 = MHEIGHT - 1;
		int edge = CELLSIZE*(col+1);
		if ((right+d) < edge)
			return d;
		if (col == (MWIDTH-1))
			return width()-right-1;
		for (int row = row1; row <= row2; row++)
			if (map[col+1][row] == 1)
				return edge-right-1;
		return d;
	}
	
	public int width()
	{
		return MWIDTH*CELLSIZE;
	}
	
	public int moveLeft(BoundingBox b, int d)
	{
		//Return the number of pixels (not exceeding d) that the rectangle
		//b can move to the left without hitting a block
		//Assume d is less than CELLSIZE
		int left = (int)b.getMinX();
		int col = left/CELLSIZE;
		int row1 = ((int)b.getMinY())/CELLSIZE;
		int row2 = ((int)b.getMaxY())/CELLSIZE;
		if (row2 >= MHEIGHT)
			row2 = MHEIGHT-1;
		int edge = CELLSIZE*col;
		if ((left-d) >=edge)
			return d;
		if (col ==0)
			return left;
		for (int row = row1; row <= row2; row++)
			if (map[col-1][row] == 1)
				return left-edge;
		return d;
	}
	
	public int moveDown(BoundingBox b, int d)
	{
		//Return the number of pixels (not exceeding d) that the rectangle
		//b can move down without hitting a block
		//Assume d is less than CELLSIZE
		int rbottom = (int)b.getMaxY();
		int row = rbottom/CELLSIZE;
		int col1 = ((int)b.getMinX())/CELLSIZE;
		int col2 = ((int)b.getMaxX())/CELLSIZE;
		int edge = CELLSIZE*(row+1);
		if (rbottom+d < edge)
			return d;
		for (int col = col1; col <= col2; col++)
			if (map[col][row+1] == 1)
				return edge - rbottom - 1;
		return d;
	}
	
	public int moveUp(BoundingBox b, int d)
	{
		return d;
	}
	
	public boolean onGround(BoundingBox b)
	{
		//Return true if Rectangle b is resting on the ground (or a block)
		int rbottom = (int)b.getMaxY();
		int row = rbottom/CELLSIZE;
		int edge = CELLSIZE*(row+1);
		if ((rbottom+1) !=edge)
			return false;
		int col1 = ((int)b.getMinX())/CELLSIZE;
		int col2 = ((int)b.getMaxX())/CELLSIZE;
		for (int i = col1; i <= col2; i++)
			if (map[i][row+1] == 1)
				return true;
		return false;
	}
	
	public boolean foundAmulet(BoundingBox b)
	{
		//check if found amulet and return true if not return false
		int rbottom = (int)b.getMaxY();
		int row = rbottom/CELLSIZE;

		int col1 = ((int)b.getMinX())/CELLSIZE;
		int col2 = ((int)b.getMaxX())/CELLSIZE;
		for (int i = col1; i <= col2; i++)
			if (map[i][row] == 2)
			{
				return true;
			}
		return false;
	}
	
	

	
	public boolean fellDown(BoundingBox b)
	{
		if (b.getMaxY() > 570)
			return true;
		return false;
	}
	
	public boolean atTop(BoundingBox b)
	{
		return false;
	}
	
	public void renderLevel1()
	{
		setBlock(0, 14);
		setBlock(1, 14);
		setBlock(2, 14);
		setBlock(3, 14);
		setBlock(4, 14);
		setBlock(5, 14);
		setBlock(8, 14);
		setBlock(9, 14);
		setCrouchBlock(10, 13);
		setBlock(10, 12);
		setBlock(10, 11);
		setBlock(10, 10);
		setBlock(10, 9);
		setBlock(10, 14);
		setBlock(11, 14);
		setBlock(12, 14);
		setBlock(13, 14);
		setBlock(15, 12);
		setBlock(16, 12);
		setBlock(18, 10);
		setBlock(16, 9);
		setBlock(18, 8);
		setBlock(19, 8);
		setBlock(20, 8);
		setBlock(21, 8);
		setBlock(22, 8);
		setBlock(23, 8);
		setBlock(24, 8);
		setBlock(26, 14);
		setBlock(27, 14);
		setBlock(29, 12);
		setBlock(30, 12);
		setBlock(31, 12);
		setBlock(32, 12);
		setBlock(33, 12);
		setBlock(34, 12);
		setBlock(35, 12);
		setBlock(36, 12);
		setBlock(37, 12);
		setBlock(38, 12);
		setBlock(39, 12);
		setAmulet(33, 10);
	}
	
	public void renderLevel2()
	{
		setBlock(0, 14);
		setBlock(1, 14);
		setBlock(2, 14);
		setBlock(3, 14);
		setBlock(4, 14);
		setBlock(6, 12);
		setBlock(5, 11);
		setBlock(4, 10);
		setBlock(7, 8);
		setBlock(8, 8);
		setBlock(9, 8);
		setBlock(10, 8);
		setCrouchBlock(10, 7);
		setBlock(10, 6);
		setBlock(10, 5);
		setBlock(10, 4);
		setBlock(10, 3);
		setBlock(11, 8);
		setBlock(13, 10);
		setBlock(11, 12);
		setBlock(10, 12);
		setBlock(15,13);
		setBlock(16, 13);
		setBlock(18, 12);
		setBlock(19, 12);
		setBlock(21, 10);
		setBlock(23, 8);
		setBlock(25, 6);
		setBlock(28, 4);
		setBlock(29, 4);
		setBlock(33,14);
		setBlock(34, 14);
		setBlock(35, 14);
		setAmulet(34, 12);
	}
	
	public void renderlevel3(){
		setBlock(0, 14);
		setBlock(1, 14);
		setBlock(2, 14);
		setBlock(3, 14);
		setBlock(4, 14);
		setBlock(4, 12);
		setBlock(2, 10);
		setBlock(2, 8);
		setBlock(5, 8);
		setBlock(6, 8);
		setBlock(7, 8);
		setBlock(8, 12);
		setBlock(9, 14);
		setBlock(10, 14);
		setCrouchBlock(10, 13);
		setBlock(10, 12);
		setBlock(10, 11);
		setBlock(10, 10);
		setBlock(10, 9);
		setBlock(10, 8);
		setBlock(10, 7);
		setBlock(10, 6);
		setBlock(10, 5);
		setBlock(10, 4);
		setBlock(11, 14);
		setBlock(13, 12);
		setBlock(15, 14);
		setBlock(17, 12);
		setBlock(19, 14);
		setBlock(20, 14);
		setBlock(21, 14);
		setBlock(22, 14);
		setBlock(23, 14);
		setBlock(23, 12);
		setBlock(23, 10);
		setBlock(23, 8);
		setBlock(25, 6);
		setBlock(27, 6);
		setBlock(30, 10);
		setBlock(32, 10);
		setBlock(35, 12);
		setBlock(36, 10);
		setBlock(37, 8);
		setBlock(38, 8);
		setBlock(39, 8);
		setAmulet(38, 6);
	}
	
	public void render(GraphicsContext gc)
	{
		gc.setFill(Color.BROWN);
		int col1 = (KingdomScroll.vleft)/CELLSIZE;
		int col2 = (KingdomScroll.vleft + KingdomScroll.VWIDTH)/CELLSIZE;
		if(col2 >= MWIDTH)
			col2 = MWIDTH-1;
		for (int row = 0; row < MHEIGHT; row++)
			for (int col = col1; col <= col2; col++)
				if(map[col][row] == 1)
				{
					gc.setFill(Color.BROWN);
					gc.fillRect(col*CELLSIZE-KingdomScroll.vleft, row*CELLSIZE, CELLSIZE, CELLSIZE);
				}
				else if(map[col][row] == 2)
				{
					gc.setFill(Color.YELLOW);
					gc.fillOval(col*CELLSIZE-KingdomScroll.vleft, row*CELLSIZE, CELLSIZE, CELLSIZE);
				}
				else if(map[col][row] == 3)
				{
					gc.setFill(Color.BROWN);
					gc.fillRect(col*CELLSIZE-KingdomScroll.vleft, row*CELLSIZE, CELLSIZE, CELLSIZE/2 - 5);
				}

	}
	
}
