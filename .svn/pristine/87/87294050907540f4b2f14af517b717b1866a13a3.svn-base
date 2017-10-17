package com.wlg.Dao;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	private static final String ESCAPE_CHARS = ".,'+-&|!^\\:(){}[]\"~：（）｛｝【】";

	private static final String REGEX = "@\\d@";

	public static boolean valiteUrl(String url) {
		if (null != url && !isEmptyStr(url)) {
			if (url.startsWith("http:") || url.startsWith("HTTP:")) {
				return true;
			}
		}
		return false;
	}

	public static String addLeadingZero(int num) {
		DecimalFormat df = new DecimalFormat("00000");
		return df.format(num);
	}

	public static String[] split(String str, char c) {
		List<String> list = split2List(str, c);
		return list.toArray(new String[list.size()]);
	}

	public static String[] split(String s, String splitter) {
		if (s == null || splitter == null || s.length() == 0
				|| splitter.length() == 0)
			return null;
		char[] src = s.toCharArray();
		char[] split = splitter.toCharArray();
		List<String> list = new ArrayList<String>();
		int i, j, splitLen = split.length, begin = 0;
		for (i = 0; i < src.length;) {
			for (j = 0; j < splitLen; j++)
				if ((i + j) >= src.length || src[i + j] != split[j])
					break;
			if (j == splitLen) {
				list.add((begin == i) ? null
						: new String(src, begin, i - begin));
				i += splitLen;
				begin = i;
			} else
				i++;
		}
		list.add((begin == src.length) ? null : new String(src, begin,
				src.length - begin));
		String[] ret = new String[list.size()];

		return list.toArray(ret);
	}

	public static String[] split(String s, String splitter, int num) {
		if (s == null || splitter == null || s.length() == 0
				|| splitter.length() == 0)
			return null;
		char[] src = s.toCharArray();
		char[] split = splitter.toCharArray();
		List<String> list = new ArrayList<String>();
		int i, j, splitLen = split.length, begin = 0;
		for (i = 0; i < src.length;) {
			for (j = 0; j < splitLen; j++)
				if ((i + j) >= src.length || src[i + j] != split[j])
					break;
			if (j == splitLen) {
				list.add((begin == i) ? null
						: new String(src, begin, i - begin));
				i += splitLen;
				begin = i;
				if (list.size() == num)
					return list.toArray(new String[num]);
			} else
				i++;
		}
		list.add((begin == src.length) ? null : new String(src, begin,
				src.length - begin));
		String[] ret = new String[list.size()];

		return list.toArray(ret);
	}

	public static List<String> split2List(String str, char c) {
		int len = str.length();
		List<String> list = new ArrayList<String>();
		StringBuffer sb = new StringBuffer("");
		for (int i = 0; i < len; i++) {
			char ch = str.charAt(i);
			if (ch == c) {
				list.add(sb.toString());
				sb.delete(0, sb.length());
			} else {
				sb.append(ch);
			}
		}

		if (sb.length() > 0)
			list.add(sb.toString());

		return list;
	}

	public static boolean isDigitStr(String str) {
		if (isEmptyStr(str))
			return false;

		for (char c : str.toCharArray()) {
			if (c < '0' || c > '9')
				return false;
		}

		return true;
	}

	public static boolean isLetterStr(String str) {
		if (isEmptyStr(str))
			return false;

		for (char c : str.toCharArray()) {
			if (!isLetter(c))
				return false;
		}

		return true;
	}

	public static boolean isLetter(char c) {
		int k = 0x80;
		return c / k == 0 ? true : false;
	}

	public static boolean isNotNumCode(String str) {
		Pattern pattern = Pattern.compile("[a-zA-Z0-9*?\\s]*");
		Matcher isNumCode = pattern.matcher(str);
		if (!isNumCode.matches()) {
			return true;
		}

		return false;
	}

	public static boolean isEmptyStr(String str) {
		if (str == null || str.trim().equals("")
				|| str.equalsIgnoreCase("null"))
			return true;
		else
			return false;
	}

	public static boolean isEmpty(Set<String> missingParams) {
		if (missingParams == null || missingParams.size() == 0) {
			return true;
		}
		return false;
	}

	public static String array2Str(int[] array, String delimit) {
		String str = "";

		if (array != null && array.length > 0) {
			for (int i = 0; i < array.length; i++) {
				if (i > 0)
					str += delimit;
				str += array[i];
			}
		}

		return str;
	}

	public static String array2Str(String[] array, String delimit) {
		String str = "";

		if (array != null && array.length > 0) {
			for (int i = 0; i < array.length; i++) {
				if (i > 0)
					str += delimit;
				str += array[i];
			}
		}

		return str;
	}

	public static byte[] str2Bytes(String str, String charset) {
		if (null != str) {
			try {
				return str.getBytes(charset);
			} catch (UnsupportedEncodingException e) {
			}
		}

		return null;
	}

	public static String bytes2Str(byte[] bs, String charset) {
		try {
			return new String(bs, charset);
		} catch (Exception e) {
		}

		return null;
	}

	public static String preHandleUseTrim(String qry) {
		return trimInValidChar(preHandleSimple(qry));
	}

	public static String preHandleUseEscape(String qry) {
		return transEscapeChars(preHandleSimple(qry), false);
	}

	public static String preHandleSimple(String qry) {
		StringBuilder sb = new StringBuilder();
		qry = (qry == null ? "" : qry.trim());
		qry = qry.replace('　', ' ');
		qry = trimDelimiter(qry, "/");
		qry = trimDelimiter(qry, "=");

		for (int i = 0; i < qry.length(); i++) {
			char c = qry.charAt(i);
			switch (c) {
				case '*':
					if (i > 0 && qry.charAt(i - 1) != '/'
							&& qry.charAt(i - 1) != '*' && qry.charAt(i - 1) != '?'
							&& qry.charAt(i - 1) != '(') {
						if (qry.charAt(i - 1) != ' ')
							sb.append(c);
					}
					break;
				case '?':
					if (i > 0 && qry.charAt(i - 1) != '/'
							&& qry.charAt(i - 1) != '*' && qry.charAt(i - 1) != '(') {
						if (qry.charAt(i - 1) != ' ')
							sb.append(c);
					}
					break;
				case ' ':
					if (i > 0 && qry.charAt(i - 1) != ' '
							&& qry.charAt(i - 1) != '(' && qry.charAt(i + 1) != ')') {
						sb.append(c);
					}
					break;
				default:
					sb.append(c);
			}
		}

		return sb.toString();
	}

	public static String trimDelimiter(String str, String delimiter) {
		String[] s = StringUtil.split(str, delimiter);
		if (s != null) {
			String temp = "";
			for (int i = 0; i < s.length; i++) {
				if (i > 0)
					temp += delimiter;
				temp += (s[i] == null ? "" : s[i].trim());
			}

			return temp;
		}

		return str;
	}

	public static String trimInValidChar(String s) {
		if (s == null || s.length() == 0)
			return "";

		StringBuffer sb = new StringBuffer(s);
		for (int i = 0; i < sb.length();) {
			char c = sb.charAt(i);
			if (ESCAPE_CHARS.indexOf(c) != -1) {
				sb.deleteCharAt(i);
			} else {
				i++;
			}
		}

		return sb.toString();
	}

	public static String transEscapeChars(String s, boolean wildcard) {
		if (s == null || s.length() == 0)
			return "";

		StringBuilder sb = new StringBuilder();
		char[] chars = s.toCharArray();
		for (char c : chars) {
			if (wildcard && (c == '*' || c == '?')) {
			} else {
				for (int i = 0; i < ESCAPE_CHARS.length(); i++) {
					if (c == ESCAPE_CHARS.charAt(i)) {
						sb.append('\\');
					}
				}
			}
			sb.append(c);
		}

		return sb.toString();
	}

	public static String getSaveName(String className) {
		if (!isEmptyStr(className)) {
			int idx = Math.max(className.lastIndexOf('.') + 1, 0);
			return className.substring(idx);
		}

		return null;
	}

	public static String defaultString(String str) {
		if (str == null)
			return "";
		else
			return str;
	}

	public static String defaultString(String str, String defaultStr) {
		if (str == null)
			if (defaultStr != null)
				return defaultStr;
			else
				return "";
		else
			return str;
	}

	public static String replace(String ori, String replacement) {
		return replace(REGEX, ori, replacement);
	}

	public static String replace(String regex, String ori, String replacement) {
		if (regex != null && ori != null && replacement != null)
			return ori.replaceAll(regex, replacement);

		return ori;
	}

	public static String replace(String ori, List<String> itemList) {
		return replace(REGEX, ori, itemList);
	}

	public static String replace(String regex, String ori, List<String> itemList) {
		String tmp = ori;
		if (tmp != null && itemList != null) {
			for (String item : itemList) {
				tmp = tmp.replaceFirst(regex, item);
			}
		}

		return tmp;
	}

	public static String GBK2Unicode(final String str) {
		char[] utfBytes = str.toCharArray();
		StringBuffer buffer = new StringBuffer();
		for (int byteIndex = 0; byteIndex < utfBytes.length; byteIndex++) {
			String hexB = Integer.toHexString(utfBytes[byteIndex]);
			if (hexB.length() <= 2) {
				hexB = "00" + hexB;
			}
			buffer.append("\\u");
			buffer.append(hexB);
		}

		return buffer.toString();
	}

	public static String Unicode2GBK(final String str) {
		int start = 0;
		int end = 0;
		final StringBuffer buffer = new StringBuffer();
		while (start > -1) {
			end = str.indexOf("\\u", start + 2);
			String charStr = "";
			if (end == -1) {
				charStr = str.substring(start + 2, str.length());
			} else {
				charStr = str.substring(start + 2, end);
			}
			char letter = (char) Integer.parseInt(charStr, 16); // 16进制parse整形字符串。
			buffer.append(new Character(letter).toString());
			start = end;
		}

		return buffer.toString();
	}

	public static String Str2Hex(String msg) {
		if (isEmptyStr(msg))
			return null;

		StringBuffer res = new StringBuffer();
		char[] chars = msg.toCharArray();
		for (char ch : chars) {
			res.append(Integer.toHexString((int) ch));
		}

		return res.toString();
	}

	public static String Hex2Str(String hex) {
		if (isEmptyStr(hex) || hex.length() % 2 != 0)
			return null;

		StringBuffer res = new StringBuffer();
		for (int i = 0; i < hex.length();) {
			res.append((char) Integer.parseInt(hex.substring(i, i + 2), 16));
			i += 2;
		}

		return res.toString();
	}

	public static String getDefaultStr(String str, String defaultStr) {
		if (isEmptyStr(str))
			return defaultStr;
		else
			return str;
	}

	public static String getDefaultStr(String str) {
		return getDefaultStr(str, "");
	}

	public static void main(String[] args) {
		System.out.println(isLetterStr("383430343133"));
	}
}