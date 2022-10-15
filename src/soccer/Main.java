package soccer;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {

	public static Scanner sc = new Scanner(System.in);
	public static final int INPUT = 1, UPDATE = 2, DELETE = 3, SEARCH = 4, OUTPUT = 5, SORT = 6, STATS = 7, EXIT = 8;

	public static void main(String[] args) {

		DBConnection dbc = new DBConnection();

		// Database connection
		dbc.connect();

		// ¸Þ´º¼±ÅÃ
		boolean flag = false;

		while (!flag) {

			int num = displayMenu();

			switch (num) {
			case INPUT:
				playerInputData();
				break;
			case UPDATE:
				UpdatePlayerData();
				break;
			case DELETE:
				deletePlayerData();
				break;
			case SEARCH:
				searchPlayerData();
				break;
			case OUTPUT:
				playerOutput();
				break;
			case SORT:
				sortPlayerData();
				break;
			case STATS:
				statictPlayerData();
				break;
			case EXIT:
				flag = true;
				break;
			default:
				System.out.println("1~7¹øÁß¿¡ ¼±ÅÃÇØÁÖ¼¼¿ä.");
				break;
			}

		} // end of while

		System.out.println("½Ã½ºÅÛ Á¾·á");

	}

	private static void statictPlayerData() {

		List<SoccerPlayer> list = new ArrayList<SoccerPlayer>();

		try {
			System.out.print("µæÁ¡¿Õ : 1 , ¾î½Ã¿Õ : 2 , Æä¾îÇÃ·¹ÀÌ¾î : 3 >> ");
			int type = sc.nextInt();

			boolean value = checkInputPattern(String.valueOf(type), 5);
			if (!value)
				return;

			DBConnection dbc = new DBConnection();
			dbc.connect();

			list = dbc.selectMaxMin(type);

			if (list.size() <= 0) {
				System.out.println("°Ë»öÇÑ ¼±¼öÁ¤º¸°¡ ¾ø½À´Ï´Ù." + list.size());
				return;
			}
			
			dbc.close();

		} catch (InputMismatchException e) {
			System.out.println("Å¸ÀÔÀÌ ¸ÂÁö ¾Ê½À´Ï´Ù. ÀçÀÔ·Â¿äÃ»" + e.getMessage());
			return;
		} catch (Exception e) {
			System.out.println("µ¥ÀÌÅÍº£ÀÌ½º ÇÐ»ýÅë°è ¿¡·¯" + e.getMessage());
		}

	}

	// Á¤·Ä
	private static void sortPlayerData() {
		List<SoccerPlayer> list = new ArrayList<SoccerPlayer>();

		try {
			DBConnection dbc = new DBConnection();
			dbc.connect();

			// ¼öÁ¤ÇÒ ÇÐ»ý ¹øÈ£ ÀÔ·Â
			System.out.print("Á¤·Ä¹æ½Ä¼±ÅÃ(1.goal 2.assist 3.point) >> ");
			int type = sc.nextInt();

			// ¹øÈ£ ÆÐÅÏ°Ë»ö
			boolean value = checkInputPattern(String.valueOf(type), 4);
			if (!value)
				return;

			list = dbc.selectOrderBy(type);

			if (list.size() <= 0) {
				System.out.println("º¸¿©ÁÙ list°¡ ¾ø½À´Ï´Ù." + list.size());
				return;
			}
			System.out.println("ÀÌ¸§\tÆÀ\t°ñ\t¾î½Ã½ºÆ®\tÆÄ¿ï\t°ø°ÝÆ÷ÀÎÆ®\tµî¼ö");
			for (SoccerPlayer soccerPlayer : list) {
				System.out.println(soccerPlayer);
			}
			dbc.close();

		} catch (Exception e) {
			System.out.println("µ¥ÀÌÅÍº£ÀÌ½º Á¤·Ä ¿¡·¯" + e.getMessage());
		}
		return;

	}

	// ¼öÁ¤
	public static void UpdatePlayerData() {
		List<SoccerPlayer> list = new ArrayList<SoccerPlayer>();
		try {
			// ¼öÁ¤ÇÒ ÇÐ»ý ¹øÈ£ ÀÔ¤©·Â
			System.out.print("¼±¼ö ÀÌ¸§ ÀÔ·Â >> ");
			String name = sc.nextLine();
			// ¹øÈ£ ÆÐÅÏ°Ë»ö
			boolean value = checkInputPattern(name, 2);
			if (!value)
				return;

			
			DBConnection dbc = new DBConnection();
			// Database connection
			dbc.connect();
			// Entering article table data
			list = dbc.selectSearch(name);

			if (list.size() <= 0) {
				System.out.println("ÀÔ·ÂµÈ Á¤º¸°¡ ¾ø½À´Ï´Ù.");
			}

			// ¸®½ºÆ® ³»¿ëÀ» º¸¿©ÁØ´Ù.
			System.out.println("ÀÌ¸§\tÆÀ\t°ñ\t¾î½Ã½ºÆ®\tÆÄ¿ï\t°ø°ÝÆ÷ÀÎÆ®\tµî¼ö");
			for (SoccerPlayer soccerPlayer : list) {
				System.out.println(soccerPlayer);
			}

			// ¼öÁ¤ÇÒ ¸®½ºÆ®¸¦ º¸¿©Áà¾ß µÈ´Ù.
			SoccerPlayer imsiSoccerPlayer = list.get(0);
			System.out.print("°ñ ¼ö ÀÔ·Â >>");
			int goal = sc.nextInt();
			value = checkInputPattern(String.valueOf(goal), 3);
			if (!value)
				return;
			imsiSoccerPlayer.setGoal(goal);

			System.out.print("¾î½Ã½ºÆ® ¼ö ÀÔ·Â >>");
			int assist = sc.nextInt();
			value = checkInputPattern(String.valueOf(assist), 3);
			if (!value)
				return;
			imsiSoccerPlayer.setAssist(assist);

			System.out.print("ÆÄ¿ï ¼ö ÀÔ·Â >>");
			int foul = sc.nextInt();
			value = checkInputPattern(String.valueOf(foul), 3);
			if (!value)
				return;
			imsiSoccerPlayer.setFoul(foul);
			
			
			imsiSoccerPlayer.calPoint();

			// µ¥ÀÌÅÍº£ÀÌ½º ¼öÁ¤ÇÒ ºÎºÐÀ» update ÁøÇà
			int returnUpdateValue = dbc.update(imsiSoccerPlayer);
			if (returnUpdateValue == -1) {
				System.out.println("¼±¼ö ¼öÁ¤ Á¤º¸ ¾øÀ½");
				return;
			}
			System.out.println("¼±¼ö Á¤º¸ ¼öÁ¤ ¿Ï·áÇÏ¿´½À´Ï´Ù.");

			dbc.close();

		} catch (InputMismatchException e) {
			System.out.println("ÀÔ·Â Å¸ÀÔ ¸ÂÁö ¾ÊÀ½. ´Ù½Ã ÀÔ·ÂÇÏ¼¼¿ä");
			sc.nextLine();
			return;
		} catch (Exception e) {
			System.out.println("µ¥ÀÌÅÍº£ÀÌ½º »èÁ¦ ¿¡·¯ . ´Ù½Ã ÀÔ·ÂÇÏ¼¼¿ä");
			return;
		}
	}

	// °Ë»ö
	private static void searchPlayerData() {
		List<SoccerPlayer> list = new ArrayList<SoccerPlayer>();

		try {
			System.out.print("°Ë»öÇÒ ¼±¼ö ÀÌ¸§À» ÀÔ·ÂÇÏ¼¼¿ä : ");
			String name = sc.nextLine();

			boolean value = checkInputPattern(name, 2);
			if (!value) {
				return;
			}

			DBConnection dbc = new DBConnection();
			dbc.connect();
			list = dbc.selectSearch(name);
			if (list.size() <= 0) {
				System.out.println("º¸¿©ÁÙ list°¡ ¾ø½À´Ï´Ù." + list.size());
				return;
			}
			System.out.println("ÀÌ¸§\tÆÀ\t°ñ\t¾î½Ã½ºÆ®\tÆÄ¿ï\t°ø°ÝÆ÷ÀÎÆ®\tµî¼ö");
			for (SoccerPlayer soccerPlayer : list) {
				System.out.println(soccerPlayer);
			}
			dbc.close();

		} catch (InputMismatchException e) {
			System.out.println("Å¸ÀÔÀÌ ¸ÂÁö ¾Ê½À´Ï´Ù. ÀçÀÔ·Â¿äÃ»" + e.getStackTrace());
			return;
		} catch (Exception e) {
			System.out.println("µ¥ÀÌÅÍº£ÀÌ½º °Ë»ö ¿¡·¯" + e.getStackTrace());
		}
	}

	// Ãâ·Â
	private static void playerOutput() {
		List<SoccerPlayer> list = new ArrayList<SoccerPlayer>();
		try {

			DBConnection dbc = new DBConnection();
			dbc.connect();
			list = dbc.select();
			if (list.size() <= 0) {
				System.out.println("º¸¿©ÁÙ list°¡ ¾ø½À´Ï´Ù." + list.size());
				return;
			}
			System.out.println("ÀÌ¸§\tÆÀ\t°ñ\t¾î½Ã½ºÆ®\tÆÄ¿ï\t°ø°ÝÆ÷ÀÎÆ®\tµî¼ö");
			for (SoccerPlayer soccerPlayer : list) {
				System.out.println(soccerPlayer);
			}
			dbc.close();

		} catch (Exception e) {
			System.out.println("µ¥ÀÌÅÍº£ÀÌ½º Ãâ·Â ¿¡·¯" + e.getMessage());
		}
		return;
	}

	// »èÁ¦
	private static void deletePlayerData() {
		try {
			System.out.print("»èÁ¦ÇÒ ¼±¼ö¸¦ ÀÔ·ÂÇØÁÖ¼¼¿ä : ");
			String name = sc.nextLine();

			boolean value = checkInputPattern(name, 2);

			if (!value)
				return;

			DBConnection dbc = new DBConnection();
			dbc.connect();
			int insertReturnValue = dbc.delete(name);
			if (insertReturnValue == -1) {
				System.out.println("»èÁ¦½ÇÆÐÀÔ´Ï´Ù." + insertReturnValue);
			}
			if (insertReturnValue == 0) {
				System.out.println("»èÁ¦ÇÒ ¼±¼ö°¡ Á¸ÀçÇÏÁö ¾Ê½À´Ï´Ù." + insertReturnValue);
			} else {
				System.out.println("»èÁ¦¼º°øÀÔ´Ï´Ù. ¸®ÅÏ°ª = " + insertReturnValue);
			}

			dbc.close();

		} catch (InputMismatchException e) {
			System.out.println("Å¸ÀÔÀÌ ¸ÂÁö ¾Ê½À´Ï´Ù. ÀçÀÔ·Â¿äÃ»" + e.getStackTrace());
			return;
		} catch (Exception e) {
			System.out.println("µ¥ÀÌÅÍº£ÀÌ½º »èÁ¦ ¿¡·¯" + e.getStackTrace());
		}
	}

	// ÀÔ·Â
	private static void playerInputData() {

		String pattern = null;
		boolean regex = false;

		try {

			System.out.print("ÀÌ¸§ÀÔ·Â : ");
			String name = sc.nextLine();
			boolean value = checkInputPattern(name, 2);
			if (!value)
				return;

			System.out.print("ÆÀÀÔ·Â : ");
			String team = sc.nextLine();
			value = checkInputPattern(team, 2);
			if (!value)
				return;

			System.out.print("goalÀÔ·Â : ");
			int goal = sc.nextInt();
			value = checkInputPattern(String.valueOf(goal), 3);
			if (!value)
				return;

			System.out.print("assistÀÔ·Â : ");
			int assist = sc.nextInt();
			value = checkInputPattern(String.valueOf(assist), 3);
			if (!value)
				return;

			System.out.print("foulÀÔ·Â : ");
			int foul = sc.nextInt();
			value = checkInputPattern(String.valueOf(foul), 3);
			if (!value)
				return;

			// µ¥ÀÌÅÍº£ÀÌ½º ÀÔ·Â
			SoccerPlayer soccerPlayer = new SoccerPlayer(name, team, goal, assist, foul);
			soccerPlayer.calPoint();

			DBConnection dbc = new DBConnection();

			dbc.connect();

			int insertReturnValue = dbc.insert(soccerPlayer);

			if (insertReturnValue == -1) {
				System.out.println("»ðÀÔ½ÇÆÐÀÔ´Ï´Ù.");
			} else {
				System.out.println("»ðÀÔ¼º°øÀÔ´Ï´Ù. ¸®ÅÏ°ª=" + insertReturnValue);
			}
			dbc.close();

		} catch (InputMismatchException e) {
			System.out.println("ÀÔ·ÂÅ¸ÀÔÀÌ ¸ÂÁö ¾Ê½À´Ï´Ù. ÀçÀÔ·Â¿äÃ»" + e.getStackTrace());
			return;
		} catch (Exception e) {
			System.out.println("µ¥ÀÌÅÍº£ÀÌ½º ÀÔ·Â ¿¡·¯" + e.getStackTrace());
		} finally {
			sc.nextLine();
		}

	}

	// ¸Þ´º¼±ÅÃ
	public static int displayMenu() {
		int num = -1;

		try {
			System.out.print("1.ÀÔ·Â 2.¼öÁ¤ 3.»èÁ¦ 4.°Ë»ö 5.Ãâ·Â 6.Á¤·Ä 7.Åë°è 8.Á¾·á : ");
			num = sc.nextInt();

			String pattern = "^[1-8]$";
			boolean regex = Pattern.matches(pattern, String.valueOf(num));
		} catch (InputMismatchException e) {
			System.out.println();
			System.out.println("¼ýÀÚ·Î ÀÔ·ÂÇØÁÖ¼¼¿ä");
			num = -1;
		} finally {
			sc.nextLine();
		}
		return num;
	}

	private static boolean checkInputPattern(String data, int patternType) {
		String pattern = null;
		boolean regex = false;
		String message = null;
		switch (patternType) {
		case 2:
			pattern = "^[°¡-ÆR]{2,10}$";
			message = "name ÀçÀÔ·Â¿ä¸Á";
			break;
		case 3:
			pattern = "^[0-9]{1,3}$";
			message = "score ÀçÀÔ·Â¿ä¸Á";

			break;
		case 4:
			pattern = "^[1-3]$";
			message = "Á¤·ÄÅ¸ÀÔ ÀçÀÔ·Â¿ä¸Á";
			break;
		case 5:
			pattern = "^[1-3]$";
			message = "Åë°èÅ¸ÀÔ ÀçÀÔ·Â¿ä¸Á";
		
		}

		regex = Pattern.matches(pattern, data);
		if (patternType == 3) {
			if (Integer.parseInt(data) < 0 || Integer.parseInt(data) > 100) {
				System.out.println(message);
				return false;
			}
		} else {
			if (!regex) {
				System.out.println(message);
				return false;
			}
		}
		return regex;
	}
}
