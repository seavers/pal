package pal.resource;

public final class Deyj {
	
	static {
		System.loadLibrary("Deyj");
	}
	
	public static byte[] decode(byte[] src) {
		if (src == null || src.length == 0) {
			//System.out.println("error : Deyj null src Or src.length=0");
			return null;
		}
		//System.out.println("Deyj decode length = " + src.length);
		//System.out.println("Deyj : data = " + Arrays.toString(src));
		return decode0(src);
	}
	
	private static native byte[] decode0(byte[] src);

}
