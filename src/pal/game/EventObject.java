package pal.game;

import pal.resource.Resource;
import pal.util.Convert;

public class EventObject {
	private static byte[] data = Resource.getEvent();
	public static int getMax() {
		return data.length / 32 - 1;
	}
	
	private int x;
	private int y;
	private int layer;
	private int trigScript;
	private int autoScript;
	private int state;
	private int trigMode;
	private int roleId;
	private int frame;
	private int dir;
	//private int unknown1;
	//private int unknown2;
	private int monstRef;
	//private int unknown3;
	//private int unknown4;	
	
	private int[][][] tiles;
	private boolean hasTiles = true;
	
	public EventObject(int id) {
		x = Convert.getInt2(data, id*32+2);
		y = Convert.getInt2(data, id*32+4);
		layer = Convert.getInt2(data, id*32+6);
		trigScript = Convert.getInt2(data, id*32+8);
		autoScript = Convert.getInt2(data, id*32+10);
		state = Convert.getInt2(data, id*32+12);
		trigMode = Convert.getInt2(data, id*32+14);
		roleId = Convert.getInt2(data, id*32+16);
		frame = Convert.getInt2(data, id*32+18);
		dir = Convert.getInt2(data, id*32+20);
		monstRef = Convert.getInt2(data, id*32+26);
	}
	
	public int[][] getImage() {
		if (state == 0) {
			return null;
		}
		if (tiles == null) {
			tiles = Resource.getMgo(roleId);
		}
		return tiles[frame];
	}
	
	public int getAutoScript() {
		return autoScript;
	}

	public void setAutoScript(int autoScript) {
		this.autoScript = autoScript;
	}

	public int getDir() {
		return dir;
	}

	public void setDir(int dir) {
		this.dir = dir;
	}

	public int getFrame() {
		return frame;
	}

	public void setFrame(int frame) {
		this.frame = frame;
	}

	public int getLayer() {
		return layer;
	}

	public void setLayer(int layer) {
		this.layer = layer;
	}

	public int getMonstRef() {
		return monstRef;
	}

	public void setMonstRef(int monstRef) {
		this.monstRef = monstRef;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getTrigMode() {
		return trigMode;
	}

	public void setTrigMode(int trigMode) {
		this.trigMode = trigMode;
	}

	public int getTrigScript() {
		return trigScript;
	}

	public void setTrigScript(int trigScript) {
		this.trigScript = trigScript;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	
}
