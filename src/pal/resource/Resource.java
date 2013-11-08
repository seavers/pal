package pal.resource;

public class Resource {
	public static byte[] getWord() {
		return FileUtil.readFile("data\\Word.dat");		
	}

	public static byte[] getTalkContent() {
		return FileUtil.readFile("data\\m.msg");		
	}	
	
	public static byte[] getMap(int id) {
		return getFile("data\\Map.mkf",id, true);
	}
	
	public static int[][][] getGop(int id) {
		byte[] src = getFile("data\\Gop.mkf", id, false);
		if (src == null || src.length == 0) {
			return null;
		}
		return getRles(src, false);
	}
	
	public static int[][][] getMgo(int id) {
		byte[] src = getFile("data\\Mgo.mkf", id, true);
		if (src == null) {
			return null;
		}
		return getRles(src, false);
	}

	public static int[][][][] getMgo() {
		byte[][] src = getFile("data\\Mgo.mkf", true);
		return getRles(src, false);				
	}


	public static byte[] getPalette(int id) {
		return getFile("data\\Pat.mkf", id, false);		
	}

	public static byte[] getEvent() {
		return getFile("data\\Sss.mkf", 0, false);		
	}
	
	public static byte[] getScene() {
		return getFile("data\\Sss.mkf", 1, false);
	}

	public static byte[] getItem() {
		return getFile("data\\Sss.mkf", 2, false);		
	}

	public static byte[] getTalk() {
		return getFile("data\\Sss.mkf", 3, false);
	}

	public static byte[] getScript() {
		return getFile("data\\Sss.mkf", 4, false);		
	}
	
	private static byte[][] getFile(String filePath, boolean isDeyj) {
		byte[][] result = MkfUtil.decode(FileUtil.readFile(filePath));
		for (int i = 0; i < result.length; i++) {
			if (result[i] == null) {
				return null;
			}
			if (isDeyj) {
				result[i] = Deyj.decode(result[i]);
			}			
		}
		return result;
	}
	
	private static byte[] getFile(String filePath, int id, boolean isDeyj) {
		byte[] result = MkfUtil.decode(FileUtil.readFile(filePath), id);
		if (result == null) {
			return null;
		}
		if (isDeyj) {
			result = Deyj.decode(result);
		}
		return result;
	}
	
	private static int[][][] getRles(byte[] src, boolean pal) {
		if (src == null) {
			return null;
		}
		return RleUtil.decode(MkfUtil.decode2(src), pal);
	}
	
	private static int[][][][] getRles(byte[][] src, boolean pal) {
		int[][][][] result = new int[src.length][][][];
		for (int i = 0; i < src.length; i++) {
			if (src[i] == null || src[i].length == 0) {
				continue;
			}
			//System.out.println(i);
			result[i] = RleUtil.decode(MkfUtil.decode2(src[i]), pal);				
		}
		return result;
	}

}























