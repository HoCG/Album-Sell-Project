package Presentation;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class hmPanel extends JPanel {
	public void paintComponent(Graphics g) {
		ImageIcon icon = new ImageIcon("./IMG/AlbumShop.jpeg");
		g.drawImage(icon.getImage(), 0, 0, null);
		setOpaque(false);
		super.paintComponent(g);
	};
}
