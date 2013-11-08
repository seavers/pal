package pal.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.IndexColorModel;
import java.awt.image.MemoryImageSource;
import java.awt.image.Raster;
import java.awt.image.SampleModel;
import java.awt.image.SinglePixelPackedSampleModel;
import java.awt.image.WritableRaster;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JPanel;

import pal.game.EventObject;
import pal.game.Game;
import pal.game.Mgo;
import pal.game.Palette;
import pal.game.Role;
import pal.resource.Resource;
import pal.util.ArraysUtil;
import pal.util.Convert;
import pal.util.ImageUtil;

public class GamePanel extends JPanel implements KeyListener{
	
	//private static int WIDTH = 320, HEIGHT = 200;
	private int select = 0;
	private int roleX = 160, roleY = 112;			//显示的角度, 人物的屏幕坐标.
	private int mapX, mapY;
	private Image background = Toolkit.getDefaultToolkit().createImage("data\\31.jpg");	
	private static byte[][] words = ArraysUtil.getArrays(Resource.getWord(), 10);
	
	private Game game;
	public GamePanel(Game game) {
		super(true);		//内置双缓冲支持.
		this.game = game;
	}
	
	/**
	 * 绘制整个游戏画面.
	 * 
	 * 	1. 这里首先调用了Transform, 将游戏分辨率调整为320*200的.
	 *
	 */
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D graph = (Graphics2D) g;
		graph.setBackground(Color.BLACK);
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		graph.setTransform(AffineTransform.getScaleInstance(
				size.width / 640.0, size.height / 400.0));		
		
		switch (game.getState()) {
		case Game.STATE_INIT :
			break;
		case Game.STATE_LOAD :
			drawSelect(graph);
			break;
		case Game.STATE_REFRESH :
		case Game.STATE_SCENE :			
			drawScene(graph);
			break;
		case Game.STATE_FIGHT:
			drawFight(graph);
			break;
		case Game.STATE_MENU :
			drawScene(graph);
			drawMenu(graph);
			break;
		case Game.STATE_STOP :
			break;
		case Game.STATE_FAIL :
			break;
		case Game.STATE_TALK :
			drawScene(graph);
			drawTalk(graph);
			break;
		}
	}	

	private int[][] image = new int[200][320];
	private void drawScene(Graphics2D graph) {
		int x = game.getX();
		int y = game.getY();
		boolean half = game.isHalf();
		mapX = x * 32 - roleX + (half ? 16 : 0); 
		mapY = y * 16 - roleY + (half ? 8 : 0);			//层未处理, 全清0			
		int[][] map = game.getMap().getImage(mapX,mapY, 320, 200);
		for (int i = 0; i < map.length; i++) {
			System.arraycopy(map[i], 0, image[i], 0, map[i].length);
		}
		int start = game.getScene().getEventBeginId();
		int end = game.getScene().getEventEndId();
		for (int i = start+1; i < end; i++) {
			EventObject eo = game.getEventObject(i);			
			if (eo.getState() == 0 || eo.getRoleId() == 0) {
				continue;
			}
			int[][] data = eo.getImage();
			//System.out.println(i);
			int ex = eo.getX() - mapX;
			int ey = eo.getY() - mapY;
			int width = data[0].length;
			int height = data.length;
			//if (ex > 0 && ex < 320-width && ey > 0 && ey < 200-height) {
			ImageUtil.draw(image, data, ex-width/2 + 16, ey-height + 15);		
			//}				
		}
		
		int[][] role = game.getRole(0).getImage();
		if (role != null) {
			int width = role[0].length;
			int height = role.length;
			ImageUtil.draw(image, role, roleX-width/2, roleY-height+4);
		}
		ImageUtil.draw(graph, image, 0, 0, this);
	}
	

	private void drawTalk(Graphics2D graph) {
		String msg = game.getTalk().getWord();
		
		if (msg == null) {
			return ;
		}
		
		graph.setColor(Color.WHITE);
		graph.drawString(msg, 40, 20);
	}
	
	private void drawFight(Graphics2D graph) {
	}
	

	private void drawMenu(Graphics2D graph) {
	}



	private void drawSelect(Graphics2D graph) {		
		graph.drawImage(background, 0, 0, this);
		graph.setFont(new Font(Font.SANS_SERIF, Font.ROMAN_BASELINE, 16));
		if (select == 0) {
			graph.setColor(Color.YELLOW);
		} else {
			graph.setColor(Color.WHITE);
		}
		graph.drawString("新的故事", 130, 100);
		if (select == 1) {
			graph.setColor(Color.YELLOW);
		} else {
			graph.setColor(Color.WHITE);
		}
		graph.drawString("旧的回忆", 130, 115);
	
	}

	

	
	
	
	/* * * * * * * * * * * * * * * * *
	 *  下面是按键处理区.
	 */  
	
	
	public void keyPressed(KeyEvent e) {
		//System.out.println("user key Pressed " + e.getKeyCode());
	
		switch (game.getState()) {
		case Game.STATE_INIT :
			break;
		case Game.STATE_LOAD :
			onLoadKey(e);			
			break;
		case Game.STATE_SCENE :
			onRoleKey(e);
			break;
		case Game.STATE_FIGHT:
			onFightKey(e);
			break;
		case Game.STATE_MENU :
			onMenuKey(e);
			break;
		case Game.STATE_STOP :
			break;
		case Game.STATE_FAIL :
			break;
		case Game.STATE_TALK :
			onTalkKey(e);
			break;
		}
		repaint();
	}

	/**
	 * 角色判断分步骤, 依次为:
	 *    判断下一步位置. 检测该位置能否到达, 如果能到到达, 则改变位置, 并检测是否引起自动脚本. 
	 * 
	 * @param e
	 */
	private void onRoleKey(KeyEvent e) {
		//System.out.println("role key Pressed " + e.getKeyCode());
		Role role = game.getRole(0);
		int x = game.getX();
		int y = game.getY();
		boolean half = game.isHalf();
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT :
			if (half) {
				half = false;
			} else {
				x--;
				y--;
				half = true;
			}
			role.setDirection(Role.WEST);
			break;
		case KeyEvent.VK_RIGHT :
			if (!half) {
				half = true;
			} else {
				x++;
				y++;
				half = false;
			}							
			role.setDirection(Role.EAST);
			break;
		case KeyEvent.VK_UP :	
			if (!half) {
				y--;
				half = true;
			} else {
				x++;
				half = false;
			}		
			role.setDirection(Role.NORTH);
			break;
		case KeyEvent.VK_DOWN :	
			if (!half) {
				x--;
				half = true;
			} else {
				y++;
				half = false;
			}		
			role.setDirection(Role.SOUTH);
			break;
		case KeyEvent.VK_ESCAPE :
			//菜单
			break;
		case KeyEvent.VK_SPACE :
			//捡物品
			break;
		}
		if (game.getMap().getInfo(x, y, half) == 0 ) {
			game.setPos(x, y, half);
		}
	}

	private void onTalkKey(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_SPACE :
			game.clearState();
			break;
		}
	}

	private void onFightKey(KeyEvent e) {}
	
	private void onMenuKey(KeyEvent e) {}

	private void onLoadKey(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_ESCAPE:
			System.exit(0);
			break;
		case KeyEvent.VK_UP:
		case KeyEvent.VK_DOWN:
			select = (select + 1) % 2;
			break;
		case KeyEvent.VK_ENTER:
		case KeyEvent.VK_SPACE:
			game.load(select);
			break;
		}
	}

	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
	
}
