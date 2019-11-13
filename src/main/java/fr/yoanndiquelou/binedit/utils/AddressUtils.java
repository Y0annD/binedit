package fr.yoanndiquelou.binedit.utils;

import java.util.Locale;

public final class AddressUtils {

	private AddressUtils() {
	}

	public static String getHexString(long addr) {
		return getHexString(addr, false);
	}

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
		return addrStr.toUpperCase(Locale.getDefault());
	}
}
