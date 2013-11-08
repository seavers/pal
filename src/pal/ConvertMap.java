package pal;

import pal.resource.Deyj;
import pal.resource.FileUtil;
import pal.resource.MkfUtil;

public class ConvertMap {
	public static void main(String[] args) {
		String filePath = "data\\map.mkf";
		byte[][] result = MkfUtil.decode(FileUtil.readFile(filePath));
		for (int i = 0; i < result.length; i++) {
			if (result[i] == null) {
				System.out.println("no data " + i);
			}
			result[i] = Deyj.decode(result[i]);
		}
		byte[] b = MkfUtil.encode(result);
		
		FileUtil.writeFile("R:\\map.mkf", b);
	}

}
