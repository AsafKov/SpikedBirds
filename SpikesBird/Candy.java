package SpikesBird;

import java.awt.Rectangle;

public class Candy {

	private double centerX, centerY, centerYorigin;
	private double speedY = -0.35;

	public static Rectangle candyRect = new Rectangle(0, 0, 0, 0);

	public void update() {
		centerY += speedY;
		if (Start.candies % 2 == 0)
			candyRect.setRect(centerX + 23, centerY + 20, 30, 30);
		else
			candyRect.setRect(centerX + 25, centerY + 20, 30, 30);

		if (centerY < centerYorigin - 8 || centerY > centerYorigin + 8)
			speedY = speedY * -1;
			
	}

	public double getCenterX() {
		return centerX;
	}

	public double getCenterY() {
		return centerY;
	}

	public void setCenterX(int centerX) {
		this.centerX = centerX;
	}

	public void setCenterY(int centerY) {
		this.centerY = centerY;
		this.centerYorigin = centerY;

	}
}
