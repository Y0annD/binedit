package fr.yoanndiquelou.binedit.utils;

import java.util.Locale;

/**
 * Utils classes for addresses.
 * 
 * @author yoann
 *
 */
public final class AddressUtils {

	/**
	 * Not instanciable.
	 */
	private AddressUtils() {
	}

	/**
	 * Get hex string.
	 * 
	 * @param addr base10 value
	 * @return hex addr
	 */
	public static String getHexString(long addr) {
		return getHexString(addr, false);
	}

	/**
	 * Get hex string with or without base10 value
	 * 
	 * @param addr            base10 value
	 * @param withBase10Value show base10 value
	 * @return hex string
	 */
	public static String getHexString(long addr, boolean withBase10Value) {
		String addrStr;
		if (addr >= 0) {
			addrStr = String.format("0x%02x", addr);
		} else {
			addrStr = String.format("-0x%02x", -addr);
		}
		if (withBase10Value) {
			addrStr = String.format("%s (%s)", addrStr, addr);
		}
		addrStr = addrStr.toUpperCase(Locale.getDefault());
		return addrStr;
	}
}
