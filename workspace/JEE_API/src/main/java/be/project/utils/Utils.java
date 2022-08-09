package be.project.utils;

public class Utils {

	
	public static boolean intToBoolean(int val) {
	    if (val <= 0) return false;
	    return true;
	}
	public static boolean charToBoolean(char val) {
	    if (val == '0') return false;
	    return true;
	}
}
