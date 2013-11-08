package pal.resource;

import java.util.Arrays;

import pal.game.Palette;
import pal.util.ArraysUtil;
import pal.util.Convert;

/**
 * 获得此src 最多可解压多少个rle图片.
 * 
 * 如逍遥图片, 12张, 第一个short为0D,即13.  max = s1 - 1;
 * [0,max)
 * 
 * 
 * @author lhj
 * @time   2007-2-11 - 上午02:48:58
 * 
 */
public class RleUtil {
	
	/**
	 * @param src
	 * @param pal 是否包括调色板, 第一个数据是否是调色板信息. 共4个字节.
	 * @return
	 */

	public static int[][][] decode(byte[][] src, boolean pal) {
		int[][][] result = new int[src.length][][];
		for (int i = 0; i < result.length; i++) {
			result[i] = decode(src[i], pal);
		}
		return result;
	}
	
	public static int[][] decode(byte[] src, boolean pal) {
		if (src == null) {
			return null;
		}
		int point = 0;
		int palId = 0;		//默认为0号调色板.
		if(pal) {
			palId = Convert.getInt(src[0]);
			point += 4;
		}
		int[] palette = new Palette(palId).getPalette();
		int width = Convert.getInt(src[point++], src[point++]);
		int height = Convert.getInt(src[point++], src[point++]);
		
		int[] result = new int[height*width];
		int index = 0;
		while (index < width * height) {
			int n = src[point++] & 0xff;
			if (n > 0x80) {
				for (int i = 0; i < n - 0x80; i++) {
					result[index++] = 0x00FFFFFF;				//alpha位为0, 即透明.
				}
			} else {
				for (int i = 0; i < n; i++) {
					result[index++] = palette[(src[point++] & 0xff)];
				}
			}
		}		
		return ArraysUtil.getArrays(result, width, height);
	}	
}

















