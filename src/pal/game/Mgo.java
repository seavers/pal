package pal.game;

import pal.resource.Resource;

public class Mgo {
	
	private static int[][][][] data;
	public static void init() {
		//data = Resource.getMgo();
	}

	private Mgo(){}
	
	public static int[][][] getMgo(int id) {
		return data[id];
	}
	
	public static int[][] getImage(int id, int frame) {
		if (data[id] == null) {
			return null;
		}
		return data[id][frame];
	}
	
}




