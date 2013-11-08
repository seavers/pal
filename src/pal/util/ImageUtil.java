package pal.util;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.MemoryImageSource;

import javax.swing.JPanel;

public class ImageUtil {

	public static void draw(int[][] image, int[][] tile, int x, int y) {
		if (image == null || tile == null) {
			throw new NullPointerException("ImageUtil : image Or data is null");
		}		
		int width = tile[0].length;
		int height = tile.length;
		if (x < 0 || y < 0 || x + width > image[0].length || y + height > image.length) {
			return ;
		}
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if ((tile[i][j] & 0xff000000) == 0xff000000) {				//只分0x00与0xff两种情况.
					image[y+i][x+j] = tile[i][j];
				}
			}
		}
	}

	public static void draw(Graphics2D graph, int[][] data, int x, int y) {
		if (data == null) {
			throw new NullPointerException("ImageUtil : image Or data is null");
		}
		int width = data[0].length;
		int height = data.length;
		MemoryImageSource mis = new MemoryImageSource(width, height, ArraysUtil.getArrays(data), 0, width);
		Image image = Toolkit.getDefaultToolkit().createImage(mis);
		graph.drawImage(image, x, y, null);
	}
	

	public static void draw(Graphics2D graph, int[][] data, int x, int y, JPanel observer) {
		if (data == null) {
			throw new NullPointerException("ImageUtil : image Or data is null");
		}
		int width = data[0].length;
		int height = data.length;
		MemoryImageSource mis = new MemoryImageSource(width, height, ArraysUtil.getArrays(data), 0, width);
		Image image = Toolkit.getDefaultToolkit().createImage(mis);
		graph.drawImage(image, x, y, observer);
	}

	public static void drawEx(int[][] image, int[][] data, int x, int y, int layer) {
		if (data == null) {
			throw new NullPointerException("ImageUtil : image Or data is null");
		}
		draw(image, data, x, y-layer-data.length);
	}
}




