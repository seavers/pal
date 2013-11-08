package pal.game;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

import pal.ui.GameUI;

public class Game {	
	public static final int STATE_INIT = 0;
	public static final int STATE_LOAD = 1;
	public static final int STATE_SCENE = 2;
	public static final int STATE_FIGHT = 3;
	public static final int STATE_STOP = 4;
	public static final int STATE_MENU = 5;
	public static final int STATE_FAIL = 6;
	public static final int STATE_TALK = 7;
	public static final int STATE_REFRESH = 8;

	private int state = STATE_LOAD;
	private int x = 50, y = 50;
	private boolean half = false;
	
	
	private int sceneId;
	private Scene[] scenes;	
	private EventObject[] events;
	private Item[] items;
	private Role[] roles;
	private int[] group = new int[3];
	
	private Script mainScript;				//主脚本.
	
	private GameUI ui;
	private Map map;
	private Talk talk;	
	

	public Game() {
		scenes = new Scene[Scene.getMax()];
		for (int i = 1; i < scenes.length; i++) {				//场景ID是从1开始计数.
			scenes[i] = new Scene(i-1);			
		}
		events = new EventObject[EventObject.getMax()];
		for (int i = 1; i < events.length; i++) {
			events[i] = new EventObject(i-1);
		}
		items = new Item[Item.getMax()];
		for (int i = 0; i < items.length; i++) {
			items[i] = new Item(i);
		}
		roles = new Role[6];
		for (int i = 0; i < roles.length; i++) {
			roles[i] = new Role(i);
		}
		talk = new Talk();
		ui = new GameUI(this);
	}
	
	
	/////////////////////////////////////////////////////////////////////
	/**主程序. 主循环, 主脚本执行区. 
	 * 在载入场景, 按键响应, 或定时器到来时所执行的函数
	 * 此函数的功能 : 
	 * 	1. 首先检查场景, 根据场景状态, 执行场景进入, 场景进行, 场景退出, 还是场景转换, 调用相应功能.
	 * 	2. 如果
	 */
	public void run() {
		int sceneOld = sceneId;
		while (state == STATE_SCENE && mainScript.next()) {
			mainScript.execute();
		}
		if (sceneOld != sceneId) {
			exitScene();
			enterScene();
		}
		
	}
	
	
	/////////////////////////////////////////////////////////////////////
	//游戏逻辑功能. 开始, 保存, 载入, 退出,  战斗,  菜单.
	
	public void init() {
		
	}
	
	public void load(int archiveId) {
		state = STATE_SCENE;
		sceneId = 1;
		enterScene();
	}
	
	
	/**
	 * 进入场景, 首先载入地图, 再载入事件对象, 最后执行场景进入脚本循环. 
	 * 
	 * 
	 * 
	 */
	public void enterScene() {
		state = STATE_SCENE;
		map = new Map(getScene().getMapId());
		mainScript = new Script(this, getScene().getEnterScriptId());
		run();
	}

	/**
	 * 退出场景, 应该执行场景退出脚本.
	 */
	public void exitScene() {
		state = STATE_SCENE;
		mainScript = new Script(this, getScene().getExitScriptId());
		run();
	}
	

	public void changeScene(int id) {
		sceneId = id;
	}
	
	//////////////////////////////////////////////////////////////////////
	
	public void showTalk(int id) {
		state = STATE_TALK;
		talk.load(id);
		
		if (talk.getSpeed() == 0) {			
			talk.setWord(talk.getMsg());
		} else {
			
			final String msg = talk.getMsg();
			
			final AtomicInteger ai = new AtomicInteger();
			new Timer().schedule(new TimerTask() {
				
				@Override
				public void run() {
					int n = ai.addAndGet(1);
					if (n > msg.length()) {
						
					} else {
						talk.setWord(msg.substring(0, n));
					}
				}
			}, 0, talk.getSpeed() * 10);
		}
		ui.repaint();
	}

	public void repaint() {
		ui.repaint();
	}

	public void refresh() {
		state = STATE_REFRESH;
		ui.repaint();
	}
	
	public void clearState() {
		state = STATE_SCENE;
		run();
	}	
	
	///////////////////////////////////////////////////////////////////
	//下面是GET/SET方法.
	
	public int getState() {
		return state;		
	}
	
	private void setState(int state) {
		this.state = state;
	}

	public void setPos(int x, int y, boolean half) {
		this.x = x;
		this.y = y;
		this.half = half;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public boolean isHalf() {
		return half;
	}
	
	public Scene getScene() {
		return scenes[sceneId];
	}

	public Map getMap() {
		return map;
		
	}

	public Role getRole(int id) {
		return roles[id];		
	}

	public int[] getGroup() {
		return group;
	}

	public void setGroup(int roleId1, int roleId2, int roleId3) {
		group[0] = roleId1;
		group[1] = roleId2;
		group[2] = roleId3;
	}

	public Talk getTalk() {
		return talk;
	}


	public EventObject getEventObject(int id) {
		return events[id];		
	}

}
