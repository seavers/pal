package pal.resource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import pal.util.Convert;

/**
 * 
 * @author lhj
 * @time   2007-2-4 - 下午07:49:31
 * 
 */
public class MkfUtil {
	/**
	 * 从mkf文件中读出第id个文件.			
	 * [0, src[0]-1];    [0,src[0])
	 * mkf 文件中以四字节为单位,依次表示各子文件偏移.
	 * mkf文件只是简单的将一系列文件集合在了一起. 
	 * 
	 * @param filaName
	 * @param id
	 * @return
	 */
	public static byte[] decode(byte[] src, int id) {
		int max = Convert.getInt4(src, 0) / 4 - 1;
		int start = Convert.getInt4(src, id*4);
		int end = Convert.getInt4(src, id*4+4);
		if (start - end == 0 || id > max - 1) {
			//System.out.println("mkf id = " + id + " " + start + " " + end);
			//System.exit(-1);
		}
		return Arrays.copyOfRange(src, start, end);	
	}
	
	public static byte[][] decode(byte[] src) {
		int max = Convert.getInt4(src, 0) / 4 - 1;
		byte[][] result = new byte[max][];
		for (int i = 0; i < result.length; i++) {
			result[i] = decode(src, i);
		}
		return result;
	}

	/**
	 * 这个是SHORT型的压缩. 且与上式压缩不同, 它不表示地址, 而表示地址/2, 且最后一个标为了0, 应更为文件长度.
	 * 
	 * 20111107   注释 :  其实就是长度, 表示有多少个短整数
	 */
	public static byte[] decode2(byte[] src, int id) {
		int max = Convert.getInt2(src, 0) - 1;
		int start = 2 * Convert.getInt2(src, id*2);
		int end = 2 * Convert.getInt2(src, id*2+2);
		if (start == end || id > max - 1) {
			//System.out.println("mkf id = " + id + " " + start + " " + end);
			//System.exit(-1);
			return null;
		}
		if (id == max - 1) {
			end = src.length;
		}
		return Arrays.copyOfRange(src, start, end);			
	}
	
	public static byte[][] decode2(byte[] src) {
		int max = Convert.getInt2(src, 0) - 1;			//应该要除2的吧??
		byte[][] result = new byte[max][];
		for (int i = 0; i < result.length; i++) {
			result[i] = decode2(src, i);			
		}
		return result;
	}
	
	

	
	
	
	
	//////////////////////////////////下面是压缩 ///////////////////////////
	
	/**
	 * 从mkf文件中读出第id个文件.			
	 * [0, src[0]-1];    [0,src[0])
	 * mkf 文件中以四字节为单位,依次表示各子文件偏移.
	 * mkf文件只是简单的将一系列文件集合在了一起. 
	 * 
	 * @param filaName
	 * @param id
	 * @return
	 */
	public static byte[] encode(byte[][] src) {
		try {
			ByteArrayOutputStream head = new ByteArrayOutputStream();
			ByteArrayOutputStream body = new ByteArrayOutputStream();
			
			int start = (src.length + 1) * 4;
			head.write(Convert.fromInt4(start));
			
			for (int i = 0; i < src.length; i++) {
				if (src[i] == null) {
					System.out.println("write null " + i);
					head.write(Convert.fromInt4(start));
				} else {
					start += src[i].length;
					head.write(Convert.fromInt4(start));
					body.write(src[i]);
				}
			}
			
			//merge
			head.write(body.toByteArray());
			return head.toByteArray();
		} catch (IOException e) {
			//ignore...
			return null;
		}
	}
}











