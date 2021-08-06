package Presentation;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public 	class whPanel extends JPanel {
	public void paintComponent(Graphics g) {
		setBackground(Color.lightGray);
		setBorder(new TitledBorder(new LineBorder(Color.black)));
		super.paintComponent(g);
	};
}
