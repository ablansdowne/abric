import java.io.File;
import java.net.URL;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.image.Image;

/**
 * side scrolling action platforming game , base code version from mike
 * slattery's scroll program jump the platform and get to the amulet at the end,
 * dont fall or get hit by the guards! W- jump, A- move left, D- move right,
 * S-crouch, click- attack (for future versions :D)
 * 
 * 
 * @author Anna Bell Lansdowne, Ricardo Catinchi
 * @version mar 2016
 */
public class KingdomScroll extends Application {
	final String appName = "Kingdoms of Abric";
	final static int VWIDTH = 800;
	final static int VHEIGHT = 600;
	final static int BWIDTH = 1920;
	final int FPS = 25; // frames per second
	public static KingdomGrid grid;
	Knight knight;
	Guard[] guardArray;
	static boolean[] guardBooleanArray;
	int[] startingYArray;
	public Guard guard1;
	public static boolean guard1Alive = true;
	public Guard guard2;
	public static boolean guard2Alive = true;
	public Guard guard3;
	public static boolean guard3Alive = false;
	public Guard guard4;
	public static boolean guard4Alive = false;
	public Guard guard5;
	public static boolean guard5Alive = false;
	public Guard guard6;
	public static boolean guard6Alive = false;

	static Upgrade shieldUpgrade;
	public static int shieldState = 0;
	public static int vleft = 0; // Pixel coord of left edge of viewable area
	public static final int SCROLL = 200; // Set edge limit for scrolling
	public static boolean swing;
	Font Wonfont = Font.font("TimesRoman", FontPosture.ITALIC, 60.0);
	Font TitleFont = Font.font("TimesRoman", FontPosture.ITALIC, 40.0);
	Font SmallTitleFont = Font.font("TimesRoman", FontPosture.ITALIC, 20.0);
	Font StatusLineFont = Font.font("TimesRoman", FontPosture.ITALIC, 15.0);
	static int gameState = 2; 
	static int languageState = 0;
	static int genderState = 0;
	static int difficulty = 0;
	public static int lives = 3;
	public static int level = 1;
	
	public static Image boyKnight;
	public static Image girlKnight;
	public static Image sword;
	public static Image shield;
	public static Image badguy;
	public static Image badguyFull;
	public static Image ultraShield;
	public static Image background1;
	public static Image background2;
	public static Image background3;
	public static Image backgroundShield;
	
	int storyCounter = 0;
	int storyWidth = 10;
	int storyHeight = 20;
	String[] storyArray = {" ","Y","e","s","t","e","r","d","a","y"," ","i","n"," ","t","h","e"," ","a","n","n","u","a","l"," "
			+ "","k","n","i","g","h","t"," ","c","o","m","p","e","t","i","t","i","o","n",","," ","y","o","u","r"," ",
			"a","r","c","h"," ","n","e","m","e","s","i","s"," ","p","a","n","t","s","e","d"," ","y","o","u"," ","i","n"," ",
			" ","f","r","o","n","t"," ","o","f"," ","a","l","l"," ","y","o","u","r"," ","k","n","i","g","h","t"," ",
			"b","u","d","d","i","e","s","."," ","N","o","w",","," ","i","n"," ","a","n"," ","e","f","f","o","r","t"," ",
			"t","o"," ","r","e","g","a","i","n"," ","y","o","u","r"," ","d","i","g","n","i","t","y"," "," "," ","a","n","d"," ",
			"h","o","n","o","r"," ","y","o","u"," ","s","e","t"," ","o","u","t"," ","t","o"," ","f","i","n","d"," ",
			"t","h","e"," ","L","o","s","t"," ","A","m","u","l","e","t"," ","o","f"," ","t","h","e"," ","K","i","n","g","."," ",
			"R","e","t","u","r","n","i","n","g"," ","t","h","i","s"," ","v","e","r","y"," "," ","v","a","l","u","a","b","l","e"," ",
			"t","r","e","a","s","u","r","e",","," ","s","o","u","g","h","t"," ","o","u","t"," ","b","y"," ","a","l","l"," ",
			"t","h","e "," ","k","i","n","g","d","o","m","s"," ","i","n"," ","t","h","e"," ","l","a","n","d",","," ","w","i","l","l",
			" ","s","u","r","e","l","y"," "," ", " ", "r","e","s","t","o","r","e"," ","y","o","u","r"," ","h","o","n","o","r","…"," ",
			"o","n","l","y"," ","f","i","n","d","i","n","g"," ","i","t"," ","i","s"," ","n","o","t"," ","a","s"," ",
			"e","a","s","y",","," ","o","r"," ","s","a","f","e",","," ","a","s"," ","i","t"," ","s","e","e","m","s","."," ",
			" ", " ", " ", "D","u","r","i","n","g"," ","y","o","u","r"," ","a","d","v","e","n","t","u","r","e"," ","y","o","u"," ",
			"w","i","l","l"," ","e","n","c","o","u","n","t","e","r"," ","o","r","c","s",","," ","t","r","o","l","l","s",","," ",
			"o","g","r","e","s",","," ","a","n","d"," ","m","y","s","t","i","c","a","l"," "," ", " ", " ", "c","r","e","a","t","u","r","e","s"," "
					+ "","o","f"," ","a","l","l"," ","s","o","r","t","s",";"," ","b","u","t"," ","n","o","t","h","i","n","g"," ",
					"w","i","l","l"," ","s","t","a","n","d"," ","i","n"," ","t","h","e"," ","w","a","y"," ","o","f"," ",
					"y","o","u","r"," ","g","o","a","l","."};
	
	static MediaPlayer mP;
	static Media titleSong;
	static Media level1Song;
	static Media level2Song;
	static Media level3Song;
	
	void initialize() {
		guardArray = new Guard[6];
		guardBooleanArray = new boolean[6];
		startingYArray = new int[6];
		grid = new KingdomGrid();
		knight = new Knight(grid, 100, 499);
		
		guard1 = new Guard(grid, 751, 300, 750, 915);
		guardArray[0] = guard1;
		guardBooleanArray[0] = guard1Alive;
		startingYArray[0] = 300;
		
		guard2 = new Guard(grid, 1201, 460, 1200, 1300 );
		guardArray[1] = guard2;
		guardBooleanArray[1] = guard2Alive;
		startingYArray[1] = 460;
		
		guard3 = new Guard(grid, 285, 300, 284, 390 );
		guardArray[2] = guard3;
		guardBooleanArray[2] = guard3Alive;
		startingYArray[2] = 300;
		
		guard4 = new Guard(grid, 600, 500, 599, 640 );
		guardArray[3] = guard4;
		guardBooleanArray[3] = guard4Alive;
		startingYArray[3] = 500;
		
		guard5 = new Guard(grid, 751, 540, 750, 915 );
		guardArray[4] = guard5;
		guardBooleanArray[4] = guard5Alive;
		startingYArray[4] = 540;
		
		guard6 = new Guard(grid, 300, 300, 199, 299 );
		guardArray[5] = guard6;
		guardBooleanArray[5] = guard6Alive;
		startingYArray[5] = 300;
		
		
		shieldUpgrade = new Upgrade(grid, 761, 300);
		boyKnight = new Image("file:boyknight.png");
		girlKnight = new Image("file:girlknight.png");
		sword = new Image("file:sword.png");
		shield = new Image("file:shield.png");
		badguy = new Image("file:monster-06.png");
		badguyFull = new Image("file:monster.png");
		ultraShield = new Image("file:ultrashield.png");
		background1 = new Image("file:windmill-background.jpg");
		background2 = new Image("file:desert-background.jpg");
		background3 = new Image("file:volcano-background.jpg");
		backgroundShield = new Image("file:shield-background.jpg");
		
		titleSong = new Media(new File("Title.mp3").toURI().toString());
		level1Song =  new Media(new File("track1.mp3").toURI().toString());
		level2Song =  new Media(new File("track2.mp3").toURI().toString());
		level3Song = new Media(new File("track3.mp3").toURI().toString());
		mP = new MediaPlayer(titleSong);
		mP.setAutoPlay(true);
		mP.play();
		
	}
	
	
	public static void changeLevel(int l)
	{
		if (l == 2)
		{
			mP.stop();
			mP = new MediaPlayer(level2Song);
			mP.setAutoPlay(true);
			mP.setCycleCount(MediaPlayer.INDEFINITE);
			mP.play();
		}
		else if (l == 1 && gameState == 3)
		{
			mP.stop();
			mP = new MediaPlayer(titleSong);
			mP.setAutoPlay(true);
			mP.play();
		}
		else if (l == 1)
		{
			mP.stop();
			mP = new MediaPlayer(level1Song);
			mP.setAutoPlay(true);
			mP.setCycleCount(MediaPlayer.INDEFINITE);
			mP.play();
		}
		else if (l == 3)
		{
			mP.stop();
			mP = new MediaPlayer(level3Song);
			mP.setAutoPlay(true);
			mP.setCycleCount(MediaPlayer.INDEFINITE);
			mP.play();
		}

		level = l;
	}

	void setHandlers(Scene scene) {
		scene.setOnKeyPressed(e -> {
			KeyCode key = e.getCode();
			switch (key) {
			case A:
				knight.dir = 1;
				break;
			case D:
				knight.dir = 2;
				break;
			case W:
				if (knight.crouch != 1)
				knight.jump = 1;
				break;
			case S:
				if (knight.jump != 1)
				knight.crouch = 1;
				break;
			case SPACE:
				if (gameState == 2)
				{
					gameState = 0;
				}
				else if (gameState == 3)
				{
					gameState = 0;
					
				}
				else {
					gameState = 1;
					changeLevel(1);
				}
				break;
			case L:
				if (languageState == 1){
				languageState = 0;
				}else{
					languageState = 1;
				}
				break;
			case B:
				genderState = 1;
				break;
			case G:
				genderState = 0;
				break;
			case DOWN:
				difficulty = 1;
				break;
			case UP:
				difficulty = 0;
			
			default:
				break;
			}
		});

		scene.setOnMouseClicked(e -> {
			swing = true;
		});

		scene.setOnKeyReleased(e -> {
			KeyCode key = e.getCode();
			if ((key == KeyCode.A) || (key == KeyCode.D))
				knight.dir = 0;
			else if (key == KeyCode.S)
				knight.crouch = 0;
		});

	}

	public void update() {
		if (gameState == 0 || gameState == 3) {

		} 
		else if (gameState == 2) {
			storyCounter++;
	
			if (storyWidth > 775)
			{
				storyWidth = 10;
				storyHeight = storyHeight + 20;
			}
			else {
				storyWidth = storyWidth + 10;
			}
		}
		else {
			
			
			knight.update();
			
			shieldUpgrade.update();
			if (swing == true) {
				knight.SwingUpdate();
			}
			
			
			
			for (int i = 0; i < guardArray.length; i++)
			{
				checkDead(i);
			}
			
			for (int j = 0; j < guardArray.length; j++)
			{
				guardArray[j].update();
			}

		}

		checkScrolling();
	}
	
	void checkDead(int i)
	{
		if (knight.locx > guardArray[i].locx - 15 && (knight.locx + knight.width) < (guardArray[i].locx + guard1.width + 15) && (knight.locy + knight.height > guardArray[i].locy) && (knight.locy + knight.height < guardArray[i].locy + guardArray[i].height) && (knight.state != knight.DEAD) )
		{
			guardBooleanArray[i] = false;	
		}
		else {
			if (knight.locx - 20 < guardArray[i].locx && guardArray[i].locx < knight.locx + 20
				&& knight.locy - 20 < guardArray[i].locy
				&& guardArray[i].locy < knight.locy + 20
				&& guardBooleanArray[i]) {
			knight.state = 3;
			}
		}
		
		if (guardBooleanArray[i] == true) {
			guardArray[i].locy = startingYArray[i];
			
		}
		else
		{
			guardArray[i].locy = -100;
		}
		
	}

	void checkScrolling() {
		// Test if knight is at edge of view window and scroll appropriately
		if (knight.locx() < (vleft + SCROLL)) {
			vleft = knight.locx() - SCROLL;
			if (vleft < 0)
				vleft = 0;

		}
		if ((knight.locx() + knight.width()) > (vleft + VWIDTH - SCROLL)) {
			vleft = knight.locx() + knight.width() - VWIDTH + SCROLL;
			if (vleft > (grid.width() - VWIDTH))
				vleft = grid.width() - VWIDTH;
		}

	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage theStage) {
		
		theStage.setTitle(appName);
		Group root = new Group();
		Scene theScene = new Scene(root);
		theStage.setScene(theScene);

		Canvas canvas = new Canvas(VWIDTH, VHEIGHT);
		root.getChildren().add(canvas);

		GraphicsContext gc = canvas.getGraphicsContext2D();

		// Initial setup
		initialize();
		setHandlers(theScene);

		// Setup and start animation loop (Timeline)
		KeyFrame kf = new KeyFrame(Duration.millis(1000 / FPS), e -> {
			// update position
				update();
				// draw frame
				render(gc);
			});
		Timeline mainLoop = new Timeline(kf);
		mainLoop.setCycleCount(Animation.INDEFINITE);
		mainLoop.play();

		theStage.show();
	}

	void render(GraphicsContext gc) {
		if (gameState == 0) {
			gc.setFill(Color.WHITE);
			gc.fillRect(0.0, 0.0, VWIDTH, VHEIGHT);

			gc.setFill(Color.BLACK);
			gc.setFont(TitleFont);

			if (languageState == 0) {
				gc.drawImage(backgroundShield, 50, 50);
				gc.fillText("Select your difficulty and", 175, 100);
				gc.fillText("Press space for a new game", 175, 150);
				gc.setFont(SmallTitleFont);
				gc.fillText("TO PLAY: W - JUMP, A - MOVE LEFT", 150, 200);
				gc.fillText("D - MOVE RIGHT, S - CROUCH, CLICK - ATTACK", 150, 220);
				gc.fillText("Get to the Amulet! Jump on the guards to kill them!", 150,
						240);
				gc.fillText("Don't fall or get hit! Attack the blue circles to jump higher!", 150, 260);
				gc.setFont(SmallTitleFont);
				gc.fillText("Presiona L para Espanol", 225, 320);
				

				if (genderState == 1) {
					gc.fillText("You are playing as the male knight", 225, 375);
					gc.fillText("Press G to play as the female knight", 225,
							400);
				} else if (genderState == 0) {
					gc.fillText("You are playing as the female knight", 225,
							375);
					gc.fillText("Press B to play as the male knight", 225, 400);
				}
				
				
				if (difficulty == 0)
				{
					gc.setFill(Color.GREEN);
					gc.fillOval(340, 485, 10, 10);
				}
				gc.setFill(Color.BLACK);
				gc.fillText("Easy", 350, 500);
				if (difficulty == 1)
				{
					gc.setFill(Color.GREEN);
					gc.fillOval(340, 505, 10, 10);
				}
				gc.setFill(Color.BLACK);
				gc.fillText("Hard", 350, 520);
				
			} else {
				gc.fillText("Para un juego nuevo,", 175, 100);
				gc.fillText("presiona el espaciador", 175, 150);
				gc.setFont(SmallTitleFont);
				gc.fillText("PARA JUGAR: W- SALTO, A - MOVER A LA IZQUIERDA",
						150, 200);
				gc.fillText("D - MOVER A LA DERECHA, CLICK - ATAQUE", 150, 220);
				gc.fillText("Llega a la medalla sin caer o morir para ganar!",
						150, 240);

				gc.setFont(SmallTitleFont);
				gc.fillText("Press L to switch to English", 225, 320);

				if (genderState == 1) {
					gc.fillText("Estas jugando como el caballero (masculino)",
							225, 375);
					gc.fillText("Si deseas jugar como la hembra presiona G",
							225, 400);
				} else if (genderState == 0) {
					gc.fillText("Estas jugando como la caballera (hembra)",
							225, 375);
					gc.fillText("Si deseas jugar como el masculino presiona B",
							225, 400);
				}
			}

		}
		else if (gameState == 2)
		{
			gc.drawImage(badguyFull, 150, 300);
			gc.drawImage(badguyFull, 475, 300);
			if (storyCounter < 539)
			{
				gc.fillText("Press space to continue", 330, 400);
				gc.fillText(storyArray[storyCounter], storyWidth, storyHeight);
			}

		}
		
		else if (gameState == 3)
		{
			gc.setFill(Color.WHITE);
			gc.fillRect(0.0, 0.0, VWIDTH, VHEIGHT);
			gc.setFill(Color.BLACK);
			gc.setFont(TitleFont);
			gc.fillText("Thanks for playing!", 100, 100);
			gc.fillText("Game created by: Anna Bell Lansdowne", 50, 200);
			gc.fillText("and Ricky Catinchi", 200, 250);
			gc.fillText("Graphics created by: Natalie Ortego", 100, 300);
			gc.fillText("Music created by: Ricky Catinchi", 100, 350);
			gc.fillText("Press space to play again",100, 500);
		}
		
		else {
			gc.setFill(Color.WHITE);
			gc.fillRect(0.0, 0.0, VWIDTH, VHEIGHT);
			int cut = (vleft/2) % BWIDTH;
			if (level == 1)
			{
				gc.drawImage(background1, -cut, 0);
				gc.drawImage(background1, BWIDTH-cut, 0);
			}
			if (level == 2)
			{
				
				gc.drawImage(background2, -cut, 0);
				gc.drawImage(background2, BWIDTH-cut, 0);
			}
			if(level == 3)
			{
				gc.drawImage(background3, -cut, 0);
				gc.drawImage(background3, BWIDTH-cut, 0);
			}
			
			if (level == 1)
			{
				grid.renderLevel1();
			}

			if (level == 2)
			{
				grid.renderLevel2();
			}
			
			if(level == 3)
			{
				grid.renderlevel3();
			}
				
			grid.render(gc);
			knight.render(gc);
			guard1.render(gc);
			guard2.render(gc);
			guard3.render(gc);
			guard4.render(gc);
			guard5.render(gc);
			guard6.render(gc);
			
			shieldUpgrade.render(gc);

			if (knight.state == 2) {
				gc.setFill(Color.BLACK);
				gc.setFont(Wonfont);
				if (languageState == 0) {
					gc.fillText("You Won!", 200, 200);
				} else {
					gc.fillText("Ganaste!", 200, 200);
				}
			}

			// draw status line
			gc.setFill(Color.BLACK);
			gc.fillRect(0, 0, 800, 20);
			gc.setFill(Color.WHITE);
			gc.setFont(StatusLineFont);
			gc.fillText("Lives:" + lives, 10, 15);
		}

	}

}
