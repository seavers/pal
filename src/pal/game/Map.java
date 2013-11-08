package pal.game;

import pal.resource.Resource;
import pal.resource.RleUtil;
import pal.util.ArraysUtil;
import pal.util.ImageUtil;

public class Map {
	byte[] data;
	private Gop gop;
	private int[][] image;
	
	public Map(int id) {
		//id = 80;
		data = Resource.getMap(id);
		gop = new Gop(id);
		initImage();
	}

	private void initImage() {
		image = new int[2048+32][2048+32];
		for (int i = 0; i < 128; i++) {
			for (int j = 0; j < 128; j++) {
				int n = j * 128 + i;
				int tileId0 = (data[4*n]&0xff) + ((data[4*n+1]&0x10)<<4);					//00.08  16.00 32.08 48.00 
				ImageUtil.draw(image, gop.getTile(tileId0), i*16, j*16+((i%2==0)?0:8));		//00.24  16.16 32.24 48.16
				int tileId1 = (data[4*n+2]&0xff) + ((data[4*n+3]&0x10)<<4) - 1;				//00.40  16.32 32.40 48.32
				if (tileId1 == -1) {continue;}
				ImageUtil.draw(image, gop.getTile(tileId1), i*16, j*16+((i%2==0)?0:8));
			}
		}		
	}

	public int[][] getImage(int x, int y, int width, int height) {
		if (x < 0 || y < 0 || x + width > 2048 || y + height > 2048) {
			//System.out.println("map outof arrays");
			return null;
		}
		return ArraysUtil.copyOfRange(image, x, y, width, height);
	}

	public int getInfo(int x, int y, boolean half) {
		if (half) {
			half = false;
		} else {
			x--; y--; half = true;
		}
		
		int ret =  data[(x*2+(half?1:0)+y*128)*4+1] & 0x20;
		//System.out.println(ret);
		return ret;
		
	}
	
	
}
