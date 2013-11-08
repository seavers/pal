package pal.util;

import java.util.Arrays;

public class ArraysUtil {
	
	/**
	 * return byte[x+0..w-1][y+0..h-1]
	 */
	public static byte[][] copyOfRangeR(byte[][] src, int x, int y, int w, int h){
		return copyOfRange(src, y, x, h, w);
	}	
	
	/**
	 * return byte[y+0..h-1][x+0..w-1]
	 */
	public static byte[][] copyOfRange(byte[][] src, int x, int y, int w, int h){
		byte[][] dst = new byte[h][w];
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				dst[i][j] = src[y+i][x+j];
			}
		}
		return dst;
	}

	public static int[][] copyOfRange(int[][] src, int x, int y, int w, int h) {
		int[][] dst = new int[h][w];
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				dst[i][j] = src[y+i][x+j];
			}
		}
		return dst;		
	}

	///////////////////////////////////////////////////////////
	//两维转一维.
	
	public static int[] getArrays(int[][] src) {
		int width = src[0].length;
		int height = src.length;
		int[] dst = new int[width*height];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				dst[i*width+j] = src[i][j];
			}
		}
		return dst;
		
	}
	

	public static byte[] getArrays(byte[][] src) {
		int width = src[0].length;
		int height = src.length;
		byte[] dst = new byte[width*height];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				dst[i*width+j] = src[i][j];
			}
		}
		return dst;
		
	}

	///////////////////////////////////////////////////////////
	//一维转两维
	public static int[][] getArrays(int[] src, int width, int height) {
		if (width * height < src.length) {
			throw new IllegalArgumentException("width * height must be greatter than src.length");
		}
		int[][] dst = new int[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				dst[i][j] = src[i*width+j];
			}
		}
		return dst;
	}
	
	public static byte[][] getArrays(byte[] src, int width, int height) {
		if (width * height < src.length) {
			throw new IllegalArgumentException("width * height must be greatter than src.length");
		}
		byte[][] dst = new byte[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				dst[i][j] = src[i*width+j];
			}
		}
		return dst;
	}
	
	public static byte[][] getArrays(byte[] src, int width) {
		return getArrays(src, width, src.length/width);
		
	}
}
