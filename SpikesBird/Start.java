package SpikesBird;

import java.applet.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;

public class Start extends Applet implements Runnable, KeyListener {

	public static enum GameState {
		Running, Dead, Menu
	}

	public static enum Difficulty {
		Easy, Hard
	}

	public static GameState play = GameState.Menu;
	public static Difficulty difficulty = Difficulty.Easy;

	public static Bird bird;
	public static SpikeMissile spikeM;
	public static Candy candyObject;

	public static int score = 0;
	public static int candies = 0;
	final public static Rectangle bottomRect = new Rectangle(0, 700, 500, 60);
	final public static Rectangle leftRect = new Rectangle(480, 20, 20, 740);
	final public static Rectangle rightRect = new Rectangle(0, 20, 20, 740);
	public static Rectangle candyRect = new Rectangle(0, 0, 0, 0);
	public static Rectangle[] Spikes;

	final public static Rectangle bottomSpikes = new Rectangle(53, 650, 400, 1);
	final public static Rectangle upSpikes = new Rectangle(50, 65, 400, 1);

	private boolean once = false;
	public static boolean oneCandy = false;
	private int currentScore;
	private int spikesX;
	private int theMissile;
	private int candyLocation;
	private int[] sideSpikes;
	private Image image, bottomSpike, upperSpike, leftSpikes, rightSpikes,
			spikesSprite, area, leftBird, rightBird, deadLeftBird,
			deadRightBird, candy, candyFigure, playButton, highScoresButton,
			difficultyButtonA, difficultyButtonB;
	private URL base;

	final private Menu menu = new Menu();

	private Graphics second;

	@Override
	public void init() {
		setSize(500, 780);
		setFocusable(true);

		Frame frame = (Frame) this.getParent().getParent();
		frame.setTitle("Beware the Spikes");
		addKeyListener(this);
		this.addMouseListener(new mouseInput());
		try {
			base = getDocumentBase();
		} catch (Exception e) {
			// TODO: handle exception
		}
		// Handling Images
		rightBird = getImage(base, "data/Birds/rightBird.png");
		leftBird = getImage(base, "data/Birds/leftBird.png");
		deadLeftBird = getImage(base, "data/Birds/deadLeftBird.png");
		deadRightBird = getImage(base, "data/Birds/deadRightBird.png");
		candy = getImage(base, "data/Other/Candy.png");
		candyFigure = getImage(base, "data/Other/candyFigure.png");
		playButton = getImage(base, "data/Buttons/play.png");
		highScoresButton = getImage(base, "data/Buttons/highScores.png");
		difficultyButtonA = getImage(base, "data/Buttons/difficultyEasy.png");
		difficultyButtonB = getImage(base, "data/Buttons/difficultyHard.png");

		super.init();
	}

	@Override
	public void start() {
		bird = new Bird();
		candyObject = new Candy();
		Thread thread = new Thread(this);
		thread.start();
		super.start();
	}

	public void update(Graphics g) {
		if (image == null) {
			image = createImage(this.getWidth(), this.getHeight());
			second = image.getGraphics();
		}

		second.setColor(getBackground());
		second.fillRect(0, 0, getWidth(), getHeight());
		second.setColor(getForeground());
		paint(second);

		g.drawImage(image, 0, 0, this);

	}

	@Override
	public void run() {
		while (true) {
			// Handling Spikes Color and map
			setImages();

			// Handling Spikes Locations
			if (!this.once) {
				if ((score / 5) + 2 < 8)
					sideSpikes = new int[(score / 5) + 2];
				else
					sideSpikes = new int[7];
				Spikes = new Rectangle[sideSpikes.length];
				currentScore = score;
				boolean identical;
				for (int i = 0; i < sideSpikes.length; i++) {
					identical = true;
					sideSpikes[i] = (int) ((Math.random() * 10) + 1) * 50;
					while (identical && i != 0) {
						identical = false;
						for (int j = 0; j < i; j++) {
							if (sideSpikes[i] == sideSpikes[j]) {
								sideSpikes[i] = (int) ((Math.random() * 10) + 1) * 50;
								identical = true;
							}
						}
					}
				}
				if (score % 2 == 0) {
					spikesX = 393;
					spikesSprite = leftSpikes;
					for (int i = 0; i < sideSpikes.length; i++)
						Spikes[i] = new Rectangle(445, sideSpikes[i] + 70, 10,
								10);
				} else {
					spikesX = -20;
					spikesSprite = rightSpikes;
					for (int i = 0; i < sideSpikes.length; i++)
						Spikes[i] = new Rectangle(45, sideSpikes[i] + 68, 10,
								10);
				}
				if (sideSpikes.length > 4 && difficulty == Difficulty.Hard) {
					spikeM = new SpikeMissile();
					theMissile = (int) Math.random() * (sideSpikes.length - 1);
					spikeM.setCenterY(sideSpikes[(int) Math.random()
							* (sideSpikes.length - 1)]);
					spikeM.setCenterX(spikesX);
					Spikes[theMissile].setRect(0, 0, 0, 0);
				}
				this.once = true;
			}

			// Handling Candy Location
			if (!oneCandy) {
				candyLocation = (int) ((Math.random() * 18) + 4) * 25;
				candyObject.setCenterY(candyLocation);
				if (candies % 2 == 0) {
					candyObject.setCenterX(372);
					Candy.candyRect.setRect(395, candyLocation + 20, 30, 30);
				} else {
					candyObject.setCenterX(40);
					Candy.candyRect.setRect(65, candyLocation + 20, 30, 30);
				}
				oneCandy = true;
			}
			if (play == GameState.Running || play == GameState.Dead)
				bird.update();
			if (sideSpikes.length > 4 && difficulty == Difficulty.Hard)
				spikeM.update();
			candyObject.update();
			repaint();
			try {
				Thread.sleep(17);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(area, 0, 0, this);

		if (currentScore != score)
			this.once = false;

		// Handling Score
		g.setColor(Color.WHITE);
		g.drawOval(160, 220, 200, 200);
		g.setFont(new Font("Adobe Garamond Pro Bold", Font.BOLD, 70));
		if (score < 10)
			g.drawString("0" + String.valueOf(score), 220, 330);
		else
			g.drawString(String.valueOf(score), 220, 330);
		g.setFont(new Font("Adobe Garamond Pro Bold", Font.BOLD, 20));
		if (candies < 10)
			g.drawString("X0" + String.valueOf(candies), 250, 365);
		else
			g.drawString("X" + String.valueOf(candies), 250, 365);
		g.setColor(Color.BLACK);

		// Drawing Candies
		g.drawImage(candyFigure, 200, 330, this);
		if (play == GameState.Running) {
			g.drawImage(candy, (int) candyObject.getCenterX(),
					(int) candyObject.getCenterY(), this);
		}

		// Handling Spikes
		for (int i = 0; i < 9; i++) {
			g.drawImage(bottomSpike, (i * 48) - 8, 600, this);
			g.drawImage(upperSpike, (i * 48) - 24, -46, this);
		}

		for (int i = 0; i < sideSpikes.length; i++) {
			
			if (sideSpikes.length > 4 && i == theMissile &&  difficulty == Difficulty.Hard)
				g.drawImage(spikesSprite, spikeM.getCenterX(), sideSpikes[i],
						this);
			else
				g.drawImage(spikesSprite, spikesX, sideSpikes[i], this);
		}

		// Drawing Bird Position
		if (score % 2 == 1) {
			if (play != GameState.Dead)
				g.drawImage(leftBird, (int) bird.getCenterX(),
						(int) bird.getCenterY(), this);
			else
				g.drawImage(deadLeftBird, (int) bird.getCenterX(),
						(int) bird.getCenterY(), this);
		} else {
			if (play != GameState.Dead)
				g.drawImage(rightBird, (int) bird.getCenterX(),
						(int) bird.getCenterY(), this);
			else
				g.drawImage(deadRightBird, (int) bird.getCenterX(),
						(int) bird.getCenterY(), this);
		}

		if (play == GameState.Menu) {
			menu.render(g);
			g.drawImage(playButton, (int) Menu.play.getX() - 65,
					(int) Menu.play.getY() - 65, this);
			g.drawImage(highScoresButton, (int) Menu.highScores.getX() - 65,
					(int) Menu.highScores.getY() - 65, this);
			if(difficulty==Difficulty.Easy)
			g.drawImage(difficultyButtonA, (int) Menu.difficulty.getX() - 65,
					(int) Menu.difficulty.getY() - 65, this);
			else
				g.drawImage(difficultyButtonB, (int) Menu.difficulty.getX() - 65,
						(int) Menu.difficulty.getY() - 65, this);
		}
		if (play == GameState.Dead) {
			g.setFont(new Font("Adobe Garamond Pro Bold", Font.BOLD, 70));
			g.drawString("GAME OVER", 50, 150);
		}
		super.paint(g);
	}

	public void setImages() {
		switch ((score / 5) % 10) {
		case 0:
			area = getImage(base, "data/Area/Yellow.png");
			bottomSpike = getImage(base,
					"data/BottomSpikes/yellowBottomSpike.png");
			upperSpike = getImage(base, "data/UpperSpikes/yellowUpperSpike.png");
			rightSpikes = getImage(base,
					"data/RightSpikes/yellowRightSpike.png");
			leftSpikes = getImage(base, "data/LeftSpikes/yellowLeftSpike.png");
			break;
		case 1:
			area = getImage(base, "data/Area/Blue.png");
			bottomSpike = getImage(base,
					"data/BottomSpikes/blueBottomSpike.png");
			upperSpike = getImage(base, "data/UpperSpikes/blueUpperSpike.png");
			rightSpikes = getImage(base, "data/RightSpikes/blueRightSpike.png");
			leftSpikes = getImage(base, "data/LeftSpikes/blueLeftSpike.png");
			break;
		case 2:
			area = getImage(base, "data/Area/Green.png");
			bottomSpike = getImage(base,
					"data/BottomSpikes/greenBottomSpike.png");
			upperSpike = getImage(base, "data/UpperSpikes/greenUpperSpike.png");
			rightSpikes = getImage(base, "data/RightSpikes/greenRightSpike.png");
			leftSpikes = getImage(base, "data/LeftSpikes/greenLeftSpike.png");
			break;
		case 3:
			area = getImage(base, "data/Area/PurpleBlue.png");
			bottomSpike = getImage(base,
					"data/BottomSpikes/purpleBlueBottomSpike.png");
			upperSpike = getImage(base,
					"data/UpperSpikes/purpleBlueUpperSpike.png");
			rightSpikes = getImage(base,
					"data/RightSpikes/purpleBlueRightSpike.png");
			leftSpikes = getImage(base,
					"data/LeftSpikes/purpleBlueLeftSpike.png");
			break;
		case 4:
			area = getImage(base, "data/Area/Gray.png");
			bottomSpike = getImage(base,
					"data/BottomSpikes/grayBottomSpike.png");
			upperSpike = getImage(base, "data/UpperSpikes/grayUpperSpike.png");
			rightSpikes = getImage(base, "data/RightSpikes/grayRightSpike.png");
			leftSpikes = getImage(base, "data/LeftSpikes/grayLeftSpike.png");
			break;
		case 5:
			area = getImage(base, "data/Area/Purple.png");
			bottomSpike = getImage(base,
					"data/BottomSpikes/purpleBottomSpike.png");
			upperSpike = getImage(base, "data/UpperSpikes/purpleUpperSpike.png");
			rightSpikes = getImage(base,
					"data/RightSpikes/purpleRightSpike.png");
			leftSpikes = getImage(base, "data/LeftSpikes/purpleLeftSpike.png");
			break;
		case 6:
			area = getImage(base, "data/Area/Orange.png");
			bottomSpike = getImage(base,
					"data/BottomSpikes/orangeBottomSpike.png");
			upperSpike = getImage(base, "data/UpperSpikes/orangeUpperSpike.png");
			rightSpikes = getImage(base,
					"data/RightSpikes/orangeRightSpike.png");
			leftSpikes = getImage(base, "data/LeftSpikes/orangeleftSpike.png");
			break;
		case 7:
			area = getImage(base, "data/Area/Red.png");
			bottomSpike = getImage(base, "data/BottomSpikes/redBottomSpike.png");
			upperSpike = getImage(base, "data/UpperSpikes/redUpperSpike.png");
			rightSpikes = getImage(base, "data/RightSpikes/redRightSpike.png");
			leftSpikes = getImage(base, "data/LeftSpikes/redLeftSpike.png");
			break;
		case 8:
			area = getImage(base, "data/Area/YellowGreen.png");
			bottomSpike = getImage(base,
					"data/BottomSpikes/yellowGreenBottomSpike.png");
			upperSpike = getImage(base,
					"data/UpperSpikes/yellowGreenUpperSpike.png");
			rightSpikes = getImage(base,
					"data/RightSpikes/yellowGreenRightSpike.png");
			leftSpikes = getImage(base,
					"data/LeftSpikes/yellowGreenLeftSpike.png");
			break;
		}
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		super.stop();
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_SPACE:
			if (play != GameState.Dead) {
				bird.jump();
			}
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_SPACE:
			Bird.falling = true;
			if (play != GameState.Dead) {
				bird.setSpeedY(0.83);
				bird.setJump(false);
			}
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
}