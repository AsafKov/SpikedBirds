package SpikesBird;

import java.awt.Rectangle;
import SpikesBird.Start.GameState;

public class Bird {

	private double centerX = 80;
	private double centerY = 230;
	private double fallPoint;
	public static boolean falling = true;
	private int distance = 200;
	private double speedY = 1;
	private double speedX = 4.5;
	private boolean jump = false;
	public static Rectangle birdRect = new Rectangle(0, 0, 0, 0);

	public void update() {
		if (falling && speedY > 0) {
			fallPoint = centerY;
			distance = 100;
			falling = false;
		}
		if ((centerY - fallPoint) / distance > 0 && !falling) {
			speedY += 0.6;
			distance += 100;
		}
		checkCollosion();
		if (centerX > 410 && centerY > 630) {
			this.speedX = 0;
		}
		centerX += speedX;
		centerY += speedY;

		if (speedX < 0)
			birdRect.setRect(centerX, centerY + 5, 52, 30);
		else
			birdRect.setRect(centerX, centerY + 5, 60, 30);
	}

	private void checkCollosion() {
		if (birdRect.intersects(Start.leftRect)) {
			setSpeedX(-4.5);
			if (Start.play != GameState.Dead)
				Start.score++;
		}
		if (birdRect.intersects(Start.rightRect)) {
			setSpeedX(4.5);
			if (Start.play != GameState.Dead)
				Start.score++;
		}
		if (birdRect.intersects(Start.upSpikes)
				|| birdRect.intersects(Start.bottomSpikes)) {
			setSpeedY(10);
			Start.play = GameState.Dead;
		}
		if (birdRect.intersects(Start.bottomRect)) {
			setSpeedY(0);
			setSpeedX(1);
			Start.play = GameState.Dead;
		}
		if (birdRect.intersects(Candy.candyRect)) {
			Start.oneCandy = false;
			Start.candyRect.setRect(0, 0, 0, 0);
			Start.candies ++;
		}
		for (int i = 0; i < Start.Spikes.length; i++) {
			if (birdRect.intersects(Start.Spikes[i])) {
				Start.play = GameState.Dead;
				if (this.speedX < 0)
					this.speedX -= 4.5;
				else
					this.speedX += 4.5;

				if (this.speedY < 0)
					this.speedY -= 4.5;
				else
					this.speedY += 1.5;
			}
		}
		if (birdRect.intersects(SpikeMissile.missileRect)) {
			Start.play = GameState.Dead;
			if (this.speedX < 0)
				this.speedX = 40;
			else
				this.speedX = -40;

			if (this.speedY < 0)
				this.speedY -= 4.5;
			else
				this.speedY += 1.5;
		}
	}

	public double getCenterX() {
		return centerX;
	}

	public double getCenterY() {
		return centerY;
	}

	public double getSpeedY() {
		return speedY;
	}

	public void setSpeedY(double x) {
		this.speedY = x;
	}

	private void setSpeedX(double x) {
		this.speedX = x;

	}

	public double getSpeedX() {
		return speedX;
	}

	public void jump() {
		falling = false;
		distance = 100;
		if (!jump) {
			setSpeedY(-8.8);
			jump = true;
		}
	}

	public void setJump(boolean jump) {
		this.jump = jump;
	}
}
