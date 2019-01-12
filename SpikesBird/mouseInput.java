package SpikesBird;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class mouseInput implements MouseListener {

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		int X = e.getX();
		int Y = e.getY();
		/**
		 * play = new Rectangle(140, 440, 225, 40); highScores = new
		 * Rectangle(140, 515, 225, 40); difficulty = new Rectangle(140, 580,
		 * 225, 40);
		 */
		// Start game on click
		if (X >= 140 && X <= 365) {
			if (Y >= 430 && Y <= 470) {
				Start.play = Start.GameState.Running;
			}
		}

		// Changing difficulty
		if (Y >= 580 && Y <= 620) {
			if (X >= 140 && X <= 270) 
				Start.difficulty = Start.Difficulty.Easy;
			if (X >= 270 && X <= 365) 
				{
				Start.difficulty = Start.Difficulty.Hard;
				}
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
