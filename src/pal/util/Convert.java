package pal.util;

public class Convert {

	////////////////////////////////////////////////////
	//将一个BYTE型数组深转换为INT型数组. 
	public static int getInt(byte data) {
		return data & 0xff;
	}
	
	public static int[] getInt(byte[] data) {
		int[] result = new int[data.length];
		for (int i = 0; i < data.length; i++) {
			result[i] = getInt(data[i]);
		}
		return result;
	}
	
	public static int[][] getInt(byte[][] data) {
		int[][] result = new int[data.length][];
		for (int i = 0; i < data.length; i++) {
			result[i] = getInt(data[i]);
		}
		return result;
		
	}

	public static int[][][] getInt(byte[][][] data) {
		int[][][] result = new int[data.length][][];
		for (int i = 0; i < data.length; i++) {
			result[i] = getInt(data[i]);
		}
		return result;
	}
	
	///////////////////////////////////////////////////////////////////
	// little-endian 转换数据.
	
	public static int getInt(byte x, byte y) {
		return (x & 0xff) + ((y & 0xff)<< 8);
	}
	
	public static int getInt(byte b1, byte b2, byte b3, byte b4) {
		return
			(b1 << 0 &  0x000000ff) +
			(b2 << 8 &  0x0000ff00) +
			(b3 << 16 & 0x00ff0000) +
			(b4 << 24 & 0xff000000) ;
	}
	
	public static int getInt2(byte[] src, int offset) {
		return getInt(src[offset], src[offset+1]);
	}
	
	public static int getInt4(byte[] src, int offset) {
		return getInt(src[offset], src[offset+1], src[offset+2], src[offset+3]);
	}

	
	//将int转换为byte数组
	public static byte[] fromInt4(int data) {
		byte[] dst = new byte[4];
		dst[0] = (byte)((data & 0x000000ff) >> 0);
		dst[1] = (byte)((data & 0x0000ff00) >> 8);
		dst[2] = (byte)((data & 0x00ff0000) >> 16);
		dst[3] = (byte)((data & 0xff000000) >> 24);
		return dst;
	}
	
}












