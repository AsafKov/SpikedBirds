package SpikesBird;

import java.awt.Rectangle;

public class SpikeMissile {

	private int centerX, centerY;
	private int speedX;

	public static Rectangle missileRect = new Rectangle(0, 0, 0, 0);

	public void update() {
		if (Start.score % 2 == 0)
			{
			speedX = -14;
			missileRect.setRect(centerX + 52, centerY + 70, 10, 10);
			centerX += speedX;
			}
		else
			{
			speedX = 14;
			missileRect.setRect(centerX + 65, centerY + 68, 10, 10);
			centerX += speedX;
			}
	}

	public int getCenterX() {
		return centerX;
	}

	public int getCenterY() {
		return centerY;
	}

	public void setCenterX(int centerX) {
		this.centerX = centerX;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}

	public void setCenterY(int centerY) {
		this.centerY = centerY;
	}
}
