package pal.game;

import pal.resource.Resource;

public class Gop {

	private int[][][] data;
	
	public Gop(int id) {
		data = Resource.getGop(id);
	}

	public int[][] getTile(int index) {
		if (index < 0 || index > data.length) {
			System.out.println("error : gop.getTile() index = " + index);
			System.out.println("此错误发生, 说明地图1层数据中有TILE值为0");
			return null;
		}
		return data[index];
	}
}





