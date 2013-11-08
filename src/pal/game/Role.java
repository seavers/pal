package pal.game;

import pal.resource.Resource;

public class Role {
	//private int[] mgoData;
	private int[][][] data;
	private String name;
	private int id;
	private int mgoId;
	private int frame;
	//private int direction;
	
	public Role(int id) {
		//data = Resource.getMgo(mgoData[id]);
	}
	
	public int[][] getImage() {
		if (data == null) {
			return null;
		}
		return data[frame];
	}
	
	public void setTile(int mgoId) {
		this.mgoId = mgoId;
		data = Resource.getMgo(mgoId);
	}
	
	public void setIndex(int frame) {
		this.frame = frame;
	}
	
	public static final int EAST = 1;
	public static final int WEST = 2;
	public static final int SOUTH = 3;
	public static final int NORTH = 4;
	
	
	private int count = 0;
	public void setDirection(int direction) {
		int[] next = {0,1,0,2,3,4,3,5,6,7,6,8,9,10,9,11};
		count = (count + 1)%4;
		switch (direction) {
		case EAST :
			count += 12;
			break;
		case WEST :
			count += 4;
			break;
		case SOUTH :
			count += 0;
			break;
		case NORTH :
			count += 8;
			break;
		}
		frame = next[count];
	}
	
	
}









