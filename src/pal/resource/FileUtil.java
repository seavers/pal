package pal.resource;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.swing.JOptionPane;

public class FileUtil {
	public static byte[] readFile(String filePath) {
		if (filePath == null) {
			throw new IllegalArgumentException("file path is null");
		}		
		byte[] result = null;
		try {
			File file = new File(filePath);
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
			result = new byte[(int)file.length()];
			in.read(result);
			in.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "no file " + filePath);
		}		
		return result;			
	}

	public static void writeFile(String filePath, byte[] b) {
		if (filePath == null) {
			throw new IllegalArgumentException("file path is null");
		}		
		try {
			File file = new File(filePath);
			BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
			out.write(b);
			out.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "no file " + filePath);
		}		
		
	}
}
