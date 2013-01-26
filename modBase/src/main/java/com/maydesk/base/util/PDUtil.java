/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.util;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.Border;
import nextapp.echo.app.Color;
import nextapp.echo.app.Command;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Font;
import nextapp.echo.app.ImageReference;
import nextapp.echo.app.ResourceImageReference;
import nextapp.echo.webcontainer.command.BrowserOpenWindowCommand;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import com.maydesk.base.DownloadServlet;
import com.maydesk.base.DownloadServlet.Document;
import com.maydesk.base.PDHibernateFactory;

/**
 * @author Alejandro Salas
 */
public class PDUtil {

	public static final String PDF_CONTENT_TYPE = "application/pdf";
	public static final String XLS_CONTENT_TYPE = "application/xls";
	public static final String PROPERTY_PATH_FILES = "path.documents";
	public static long MILLIS_PER_DAY = 24 * 60 * 60 * 1000;

	private static final char[] arrayChars = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
	private static final int LOGIN_CHARS_COUNT = 9;


	public static Object[] addEmpty(Object[] src) {
		Object[] values = new Object[src.length + 1];
		System.arraycopy(src, 0, values, 1, src.length);
		values[0] = "";
		return values;
	}

	public static String createLogin(int charCount) {
		StringBuilder login = new StringBuilder();
		Random r = new Random();
		for (int i = 0; i < charCount; i++) {
			if (i % 3 == 0 && i != 0) {
				login.append('-');
			}
			login.append(arrayChars[r.nextInt(arrayChars.length)]);
		}
		return login.toString();
	}

	public static String createNewLogin() {
		Session session = PDHibernateFactory.getSession();
		Query query = session.createQuery("FROM MUser AS u WHERE u.login =:login");
		String login;
		do {
			login = createLogin(LOGIN_CHARS_COUNT);
			query.setString("login", login);
		} while (query.list().size() > 0);

		return login;
	}

	public static void doDownload(final byte[] content, final String fileName, final String contentType) {
		// if (PDAppInstance.getActivePD().isIE) {
		// // IE does not like the new Open-Window approach
		// // we stay here with the cumbersome download method
		// DownloadProvider d = new DownloadProvider() {
		// public String getContentType() {
		// return contentType;
		// }
		// public String getFileName() {
		// return fileName;
		// }
		// public long getSize() {
		// return content.length;
		// }
		// public void writeFile(OutputStream os) throws IOException {
		// os.write(content);
		// }
		// };
		// DownloadCommand cmd = new DownloadCommand(d);
		// ApplicationInstance.getActive().enqueueCommand(cmd);
		// } else {
		Document doc = new Document();
		doc.setId(new Random().nextInt() + "");
		doc.setContentLength(content.length);
		doc.setContents(content);
		doc.setMimeType(contentType);
		doc.setName(fileName);
		DownloadServlet.addDocument(doc);

		String uri = "download?docId=" + doc.getId();
		Command command = new BrowserOpenWindowCommand(uri, fileName, new Extent(800), new Extent(600), 0);
		ApplicationInstance.getActive().enqueueCommand(command);
		// }
	}

	public static String emptyString(Object o) {
		return o == null ? "(Empty)" : o.toString();
	}

	public static Calendar getCalendar(int d, int m, int y) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, d);
		cal.set(Calendar.MONTH, m);
		cal.set(Calendar.YEAR, y);
		return cal;
	}

	public static Calendar getDateOnlyCalendar() {
		return PDUtil.getDateOnlyCalendar(null);
	}

	public static Calendar getDateOnlyCalendar(Calendar cal) {
		Calendar ret;

		if (cal == null) {
			ret = Calendar.getInstance();
		} else {
			ret = (Calendar) cal.clone();
		}

		ret.set(Calendar.HOUR_OF_DAY, 0);
		ret.set(Calendar.MINUTE, 0);
		ret.set(Calendar.SECOND, 0);
		ret.set(Calendar.MILLISECOND, 0);

		return ret;
	}

	public static boolean isBefore(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		cal1.set(Calendar.HOUR, 0);
		cal1.set(Calendar.MINUTE, 0);
		cal1.set(Calendar.SECOND, 0);
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(date1);
		cal1.set(Calendar.HOUR, 0);
		cal1.set(Calendar.MINUTE, 0);
		cal1.set(Calendar.SECOND, 0);
		return cal1.before(cal2);
	}

	public static boolean isEmpty(Object o) {
		if (o == null)
			return true;
		if (o.toString() == null)
			return true;
		return o.toString().trim().length() == 0;
	}

	/**
	 * The base path of the application In a deployed environment this would be:
	 * $CATALINA_BASE/name_of_application
	 */
	public File getBasePath() {
		return null;
	}

	public static int[] htmlColorToIntRGBArray(String str) throws ParseException {
		if (str == null) {
			throw new NullPointerException("str == null");
		}

		str = str.trim();

		if (str.length() != 7) {
			throw new ParseException("str.length() != 7", 0);
		}

		if (!str.startsWith("#")) {
			throw new ParseException("!str.startsWith(\"#\")", 0);
		}

		int[] ret = new int[3];

		ret[0] = Integer.parseInt(str.substring(1, 3), 16);
		ret[1] = Integer.parseInt(str.substring(3, 5), 16);
		ret[2] = Integer.parseInt(str.substring(5, 7), 16);

		return ret;
	}

	public static String intRGBArrayToHtmlColor(int[] rgbArray) {
		if (rgbArray == null) {
			throw new NullPointerException("rgbArray == null");
		}

		if (rgbArray.length != 3) {
			throw new IllegalArgumentException("rgbArray.length != 3");
		}

		StringBuffer ret = new StringBuffer("#");

		for (int i = 0; i < 3; i++) {
			String curr = Integer.toHexString(rgbArray[i]);

			if (curr.length() == 1) {
				ret.append("0");
			}

			ret.append(curr);
		}

		return ret.toString();
	}

	public static byte[] readBytesFromFile(File file) throws IOException {
		byte[] bytesOfFile = new byte[(int) file.length()];
		DataInputStream dis = new DataInputStream(new FileInputStream(file));
		dis.readFully(bytesOfFile);
		dis.close();
		return bytesOfFile;
	}

	public static String null2empty(Object s) {
		if (s == null)
			return "";
		return s.toString();
	}

	public static String null2dots(Object s) {
		if (s == null || s.toString().length() == 0)
			return "...";
		return s.toString();
	}

	public static Border getGreyBorder() {
		Border border = new Border(1, Color.LIGHTGRAY, Border.STYLE_DOTTED);
		return border;
	}

	public static String getLimitedText(String text, int length) {
		if (text == null)
			return "";
		if (text.length() < length) {
			return text;
		}
		return text.substring(0, length - 3) + "...";
	}

	public static int getDaysDifference(Calendar refDate, Date date) {
		long delta = date.getTime() - refDate.getTimeInMillis();
		long deltaDays = (delta) / MILLIS_PER_DAY;
		return (int) deltaDays;
	}

	public static Border emptyBorder() {
		return new Border(0, Color.BLACK, Border.STYLE_NONE);
	}

	public static Border emptyBorder3() {
		return new Border(6, Color.GREEN, Border.STYLE_DOTTED);
	}

	public static String generatePassword() {
		return new RandomPassword().getPass(8);
	}

	/**
	 * Validate the form of an email address.
	 * 
	 * <P>
	 * Return <tt>true</tt> only if
	 * <ul>
	 * <li> <tt>aEmailAddress</tt> can successfully construct an
	 * {@link javax.mail.internet.InternetAddress}
	 * <li>when parsed with "@" as delimiter, <tt>aEmailAddress</tt> contains
	 * two tokens which satisfy
	 * {@link hirondelle.web4j.util.Util#textHasContent}.
	 * </ul>
	 * 
	 * <P>
	 * The second condition arises since local email addresses, simply of the
	 * form "<tt>albert</tt>", for example, are valid for
	 * {@link javax.mail.internet.InternetAddress}, but almost always undesired.
	 */
	public static boolean isValidEmailAddress(String aEmailAddress) {
		if (aEmailAddress == null)
			return false;
		boolean result = true;
		try {
			InternetAddress emailAddr = new InternetAddress(aEmailAddress);
			if (!hasNameAndDomain(aEmailAddress)) {
				result = false;
			}
		} catch (AddressException ex) {
			result = false;
		}
		return result;
	}

	private static boolean hasNameAndDomain(String aEmailAddress) {
		String[] tokens = aEmailAddress.split("@");
		return (tokens.length == 2 && tokens[0].length() > 0 && tokens[1].length() > 0);
	}

	public static String replaceParams(String text, String... params) {
		if (params == null)
			return text;
		for (int i = 0; i < params.length; i++) {
			if (params[i] != null) {
				text = StringUtils.replace(text, "$" + (i + 1), params[i]);
			}
		}
		return text;
	}

	public static Font getFontBold() {
		return new Font(Font.COURIER, Font.BOLD, new Extent(12));
	}

	public static Font getSmallBold() {
		return new Font(Font.COURIER, Font.PLAIN, new Extent(11));
	}

	public static ImageReference getImg(String img) {
		return new ResourceImageReference(img);
	}
	
	public static Object getSopletEntry(String sopletClassName, String sopletName) {
		try {
			Class enumClass = Class.forName(sopletClassName);
			for (Object enumConstant : enumClass.getEnumConstants()) {
				if (((Enum)enumConstant).name().equals(sopletName)) {
					return enumConstant;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;		
	}
}