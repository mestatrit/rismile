package com.risetek.scada.client;


public class Base64Encoder {

	/**
	 * The BASE64 encoding standard's 6-bit alphabet, from RFC 1521, plus the
	 * padding character at the end.
	 */
	private static final char[] Base64Chars = { 'A', 'B', 'C', 'D', 'E', 'F',
			'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
			'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',
			'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
			't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', '+', '/', '=' };

	/**
	 * Performs RFC1521 style Base64 encoding of arbitrary binary data. The
	 * output is a java String containing the Base64 characters representing the
	 * binary data. Be aware that this string is in Unicode form, and should be
	 * converted to UTF8 with the usual java conversion routines before it is
	 * sent over a network. The output string is guaranteed to only contain
	 * characters that are a single byte in UTF8 format. Also be aware that this
	 * routine leaves it to the caller to break the string into 70 byte lines as
	 * per RFC1521.
	 * 
	 * @param bytes
	 *            The array of bytes to convert to Base64 encoding.
	 * @return An string containing the specified bytes in Base64 encoded form.
	 */
	public static final String toBase64String(byte[] bytes) {
		return toBase64String(bytes, Base64Chars);
	}

	/**
	 * Performs encoding of arbitrary binary data based on a 6 bit alphabet. The
	 * output is a java String containing the encoded characters representing
	 * the binary data. Be aware that this string is in Unicode form, and should
	 * be converted to UTF8 with the usual java conversion routines before it is
	 * sent over a network. The alphabet passed in via <code>chars</code> is
	 * used without further checks, it's the callers responsibility to set it to
	 * something meaningful.
	 * 
	 * @param bytes
	 *            The array of bytes to convert to Base64 encoding.
	 * @param chars
	 *            The alphabet used in encoding. Must contain exactly 65
	 *            characters: A 6 bit alphabet plus one padding char at position
	 *            65.
	 * @return An string containing the specified bytes in Base64 encoded form.
	 */
	private static final String toBase64String(byte[] bytes, char[] chars) {
		StringBuffer sb = new StringBuffer();
		int len = bytes.length, i = 0, ival;
		while (len >= 3) {
			ival = ((int) bytes[i++] + 256) & 0xff;
			ival <<= 8;
			ival += ((int) bytes[i++] + 256) & 0xff;
			ival <<= 8;
			ival += ((int) bytes[i++] + 256) & 0xff;
			len -= 3;
			sb.append(chars[(ival >> 18) & 63]);
			sb.append(chars[(ival >> 12) & 63]);
			sb.append(chars[(ival >> 6) & 63]);
			sb.append(chars[ival & 63]);
		}
		switch (len) {
		case 0: // No pads needed.
			break;
		case 1: // Two more output bytes and two pads.
			ival = ((int) bytes[i++] + 256) & 0xff;
			ival <<= 16;
			sb.append(chars[(ival >> 18) & 63]);
			sb.append(chars[(ival >> 12) & 63]);
			sb.append(chars[64]);
			sb.append(chars[64]);
			break;
		case 2: // Three more output bytes and one pad.
			ival = ((int) bytes[i++] + 256) & 0xff;
			ival <<= 8;
			ival += ((int) bytes[i] + 256) & 0xff;
			ival <<= 8;
			sb.append(chars[(ival >> 18) & 63]);
			sb.append(chars[(ival >> 12) & 63]);
			sb.append(chars[(ival >> 6) & 63]);
			sb.append(chars[64]);
			break;
		}
		return new String(sb);
	}

}
