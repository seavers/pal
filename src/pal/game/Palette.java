package pal.game;

import pal.resource.Resource;
import pal.util.Convert;

public class Palette {
	
	private int[] palette = new int[256];
	
	public Palette(int id) {
		byte[] data = Resource.getPalette(id);
		for (int i = 0; i < palette.length; i++) {
			palette[i] = 
				(data[3*i+2] << 2  &  0x000000ff) +
				(data[3*i+1] << 10 &  0x0000ff00) +
				(data[3*i+0] << 18 &  0x00ff0000) +
				(0xff000000) ;							//alpha, ²»Í¸Ã÷		
		}
	}
	
	public int[] getPalette() {
		return palette;
	}
}
