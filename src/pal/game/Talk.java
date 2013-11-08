package pal.game;

import java.util.Arrays;

import pal.resource.Resource;
import pal.util.ArraysUtil;
import pal.util.Convert;

public class Talk {
	private static byte[] indexs = Resource.getTalk();
	private static byte[] data = Resource.getTalkContent();
	public static final int POS_CENTER = 0;
	public static final int POS_TOP = 1;
	public static final int POS_BOTTOM = 2;
	
	private int id;
	private int pos;
	private String msg;		//全部显示的内容
	private int speed;
	private boolean wait;
	private String word;	//当前显示的内容
	
	public Talk() {
		

	}

	public void load(int id) {
		this.id = id;
		
		int start = Convert.getInt4(indexs, id*4);
		int end = Convert.getInt4(indexs, id*4+4);
		byte[] talk = Arrays.copyOfRange(data, start, end);
		
		int msgStart = talk[0] == (byte)'$' ? 3 : 0;
		int msgEnd = talk[talk.length - 3]=='~' ? talk.length - 3 : talk.length;
		byte[] show = Arrays.copyOfRange(talk, msgStart, msgEnd);
		
		if (talk[0] == (byte)'$') {
			speed = Integer.parseInt(new String(talk, 1, 2));			
		} else {
			speed = 3;
		}
		msg = new String(show);
	}
	
	public int getPos() {
		return pos;
	}
	
	public void setPos(int pos) {
		this.pos = pos;
	}
	
	public void setWord(String word) {
		this.word = word;
	}

	public String getWord() {
		return word;
	}
	
	public int getSpeed() {
		return speed;
	}

	public String getMsg() {
		return msg;
	}
	

}















