package pal.ui;

import java.awt.Graphics;

import javax.swing.JFrame;

import pal.game.Game;

public class GameUI extends JFrame {	

	public GameUI(Game game) {
		super("Pal");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setExtendedState(JFrame.MAXIMIZED_BOTH); // 最大化
		//setUndecorated(true); // 不要边框
		GamePanel panel = new GamePanel(game);		
		setContentPane(panel);
		setBounds(0, 0, 640, 400);
		addKeyListener(panel);
		setVisible(true);
	}
	
	/**
	 * 超类update()是先清屏后调用paint(), 这里重载是为了防止刷新, 以防闪烁.
	 * 
	 *  (non-Javadoc)
	 * @see javax.swing.JFrame#update(java.awt.Graphics)
	 */
	@Override
	public void update(Graphics g) {
		super.paint(g);		
	}
}







