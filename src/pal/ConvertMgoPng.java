package pal;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import pal.resource.Resource;
import pal.util.ArraysUtil;

public class ConvertMgoPng {
	public static void main(String[] args) throws IOException {
		//BufferedImage tag = new BufferedImage((int) newWidth, (int) newHeight, BufferedImage.TYPE_INT_RGB);
		
		//for (int i = 0; i < 570; i++) {					//>570时 会报错 99396 > 59516
		for (int i = 0; i < 570; i++) {					//>570时 会报错 99396 > 59516
			int[][][] images = Resource.getMgo(i);
			
			if (images == null) {
				continue;
			}
			
			for (int j = 0; j < images.length; j++) {
				int[][] data = images[j];
				
				int width = data[0].length;
				int height = data.length;
				
				BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);		//RleUtil.decode
				bi.setRGB(0, 0, width, height, ArraysUtil.getArrays(data), 0, width);
				
				String name = String.format("%03d_%03d", i, j);
				String filePath = "G:\\LanGame\\pal\\study\\MKF2BMP\\mgo_png\\" + name + ".PNG";
				ImageIO.write(bi, "PNG", new File(filePath));
				
				
				System.out.println("mgo bmp " + name);
			}
		}
	}
}





