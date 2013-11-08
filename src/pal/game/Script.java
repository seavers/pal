package pal.game;

import java.util.Timer;
import java.util.TimerTask;

import pal.resource.Resource;
import pal.util.Convert;

public class Script {

	private byte[] data = Resource.getScript();
	private Game game;
	private int id;
	
	private int cmd;
	private int arg0;
	private int arg1;
	private int arg2;
	
	private boolean wait = false;
	
	public Script(Game game, int id) {
		this.game = game;
		this.id = id;
	}
	
	public boolean next() {
		return !wait && Convert.getInt2(data, id*8) != 0;
	}
	
	public void execute() {
		cmd = Convert.getInt2(data, id*8);
		arg0 = Convert.getInt2(data, id*8+2);
		arg1 = Convert.getInt2(data, id*8+4);
		arg2 = Convert.getInt2(data, id*8+6);
		//System.out.println("script : id = " + id + " " + cmd + " " + arg0 + " " + arg1 + " " + arg2);
		interpret();
		id++;
	}
	
	
	/*
	 * 脚本常数区
	 */
	public static final int TALK = 0xFFFF;					//TALK
	
	
	/**
	 * 这里的块位置是地图的块,由于仙剑地图图元是32x15的菱形，排列起来后每行都是锯齿形状的，
	 * 在MAP.MKF中的X应该等于(参数1*2+参数3),参数2为Y,参数3只有"0"和"1"两种情况，说明是否是右下那个菱形位置。
	 */
	public static final int SET_ROLE_POS = 0x46;		//设置队伍块坐标
	
	/**
	 *参数1是角色编号，"0"是李逍遥，"1"是赵灵儿..。
	 *参数2是MGO.MKF中的索引号，根据这个值去加载对应的MGO子文件，实际是多个RLE格式位图。
	 *参数3好像是说明是否暂缓变更形象的，false为立即更新。因为每个MGO的子文件中包含多个位图,
	 *更换后位图会指到第一帧上去，导致重画出错误的形象，所以改处设置true后，将暂缓更新，直到有0x0015的指令(设置队员方向/帧)时才更换。 
	 */
	public static final int SET_ROLE_TILE = 0x65;		//设置队员形象
	
	/**
	 * ;参数1为帧。参数2为方向。参数3为角色代号。
	 */
	public static final int SET_ROLE_INDEX = 0x15;		//设置队员方向或帧
	
	/**
	 * 仙剑中可控制的角色只有6个，游戏开始就应该初始化了这6个角色的空间。但是显示时只显示组队后的情况，
	 * 也就是这个命令的意义，队伍中最多只有3个角色，三个参数也就是说明队伍的成员了，"0"为空位置。
	 */
	public static final int SET_GROUP = 0x75;
	
	/**
	 * 
	 */
	public static final int UI_REPAINT = 0x05;
	
	public static final int TALK_POS_CENTER = 0x3B;
	public static final int TALK_POS_BOTTOM = 0x3D;
	public static final int SCENE_REFRESH = 0x09;
	public static final int SCENE_CHANGE = 0x59;
	public static final int ROLE_WALK = 0x70;
	public static final int SET_NPC_TILE = 0x16;
	public static final int SET_NPC_STATE = 0x49;
	public static final int EVENTOBJECT_WALK = 0x6C;
	/**
	 * 脚本核心. 
	 * 
	 * @param cmd
	 * @param x
	 * @param y
	 * @param z
	 */
	private void interpret() {
		System.out.println("script: " + id + ' ' + cmd + ' ' + arg0 + ' ' + arg1 + ' ' + arg2);
		
		int command = cmd;
		int x = arg0;
		int y = arg1;
		int z = arg2;
		switch (command) {
		case SET_ROLE_POS :
			game.setPos(x, y, z==0?true:false);
			refresh(500);
			break;
		case SET_ROLE_TILE :
			game.getRole(x).setTile(y);
			break;
		case SET_ROLE_INDEX :
			game.getRole(z).setDirection(x);
			game.getRole(z).setIndex(y);
			break;
		case SET_GROUP :
			game.setGroup(x, y, z);
			break;
		case UI_REPAINT :
			//refresh(100);
			break;
		case TALK_POS_CENTER :
			game.getTalk().setPos(Talk.POS_CENTER);
			break;
		case TALK_POS_BOTTOM :
			game.getTalk().setPos(Talk.POS_BOTTOM);
			break;
		case TALK :
			game.showTalk(x);
			break;
		case SCENE_REFRESH :
			//game.repaintUI();						//同一线程, 无法刷新了~~~
			refresh(z*200+200);
			break;
		case SCENE_CHANGE :
			game.changeScene(x);
			break;
		case ROLE_WALK :
			game.setPos(x, y, z==0?true:false);
			break;
		case SET_NPC_TILE :
			game.getEventObject(x).setFrame(z);			//y估计是roleId;
			break;
		case SET_NPC_STATE :
			game.getEventObject(x).setState(y);
			break;
		case EVENTOBJECT_WALK :
			EventObject e = game.getEventObject(x);
			y = (short)(y & 0xffff);
			z = (short)(z & 0xffff);
			e.setX(e.getX()+y);
			e.setY(e.getY()+z);			
		}
	}

	private void refresh(int delay) {
		game.refresh();
		new Timer().schedule(new TimerTask() {
		
			@Override
			public void run() {
				game.clearState();
			}
		
		}, delay);
	}
	
	
	
	
	
	
	

}
