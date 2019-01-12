package SpikesBird;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Menu {

	public static Rectangle play = new Rectangle(140, 440, 225, 40);
	public static Rectangle highScores = new Rectangle(140, 515, 225, 40);
	public static Rectangle difficulty = new Rectangle(140, 580, 225, 40);

	public void render(Graphics g) {		
		Font ft1 = new Font("Adobe Garamond Pro Bold", Font.BOLD, 40);
		g.setColor(Color.BLACK);
		g.setFont(ft1);
		g.drawString("BEWARE THE SPIKES", 50, 200);
	}
}
