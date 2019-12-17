package fr.yoanndiquelou.binedit.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * 
 * @author yoann
 *
 */
public class AddressUtilsTest {

	/**
	 * 
	 */
	public AddressUtilsTest() {
		super();
	}

	@Test
	public void testGetHexStringWithArgs() {
		assertEquals("0X00", AddressUtils.getHexString(0, false));
		assertEquals("0X00 (0)", AddressUtils.getHexString(0, true));
		assertEquals("-0X01", AddressUtils.getHexString(-1, false));
		assertEquals("-0X01 (-1)", AddressUtils.getHexString(-1, true));
	}
}
