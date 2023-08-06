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

		// 메뉴선택
	SoccerFacade soccerFacade = new SoccerFacade();

    	boolean flag = false;

    	while (!flag) {
        	int num = displayMenu();

	        switch (num) {
	            case INPUT:
	                soccerFacade.playerInputData();
	                break;
	            case UPDATE:
	                soccerFacade.updatePlayerData();
	                break;
	            case DELETE:
	                soccerFacade.deletePlayerData();
	                break;
	            case SEARCH:
	                soccerFacade.searchPlayerData();
	                break;
	            case OUTPUT:
	                soccerFacade.playerOutput();
	                break;
	            // ...
	        }
	    }

    // Cerrar la conexión a la base de datos al salir del programa
    soccerFacade.closeConnection();

		} // end of while

		System.out.println("시스템 종료");

	}

	private static void statictPlayerData() {

		List<SoccerPlayer> list = new ArrayList<SoccerPlayer>();

		try {
			System.out.print("득점왕 : 1 , 어시왕 : 2 , 페어플레이어 : 3 >> ");
			int type = sc.nextInt();

			boolean value = checkInputPattern(String.valueOf(type), 5);
			if (!value)
				return;

			DBConnection dbc = new DBConnection();
			dbc.connect();

			list = dbc.selectMaxMin(type);

			if (list.size() <= 0) {
				System.out.println("검색한 선수정보가 없습니다." + list.size());
				return;
			}
			
			dbc.close();

		} catch (InputMismatchException e) {
			System.out.println("타입이 맞지 않습니다. 재입력요청" + e.getMessage());
			return;
		} catch (Exception e) {
			System.out.println("데이터베이스 학생통계 에러" + e.getMessage());
		}

	}

	// 정렬
	private static void sortPlayerData() {
		List<SoccerPlayer> list = new ArrayList<SoccerPlayer>();

		try {
			DBConnection dbc = new DBConnection();
			dbc.connect();

			// 수정할 학생 번호 입력
			System.out.print("정렬방식선택(1.goal 2.assist 3.point) >> ");
			int type = sc.nextInt();

			// 번호 패턴검색
			boolean value = checkInputPattern(String.valueOf(type), 4);
			if (!value)
				return;

			list = dbc.selectOrderBy(type);

			if (list.size() <= 0) {
				System.out.println("보여줄 list가 없습니다." + list.size());
				return;
			}
			System.out.println("이름\t팀\t골\t어시스트\t파울\t공격포인트\t등수");
			for (SoccerPlayer soccerPlayer : list) {
				System.out.println(soccerPlayer);
			}
			dbc.close();

		} catch (Exception e) {
			System.out.println("데이터베이스 정렬 에러" + e.getMessage());
		}
		return;

	}

	// 수정
	public static void UpdatePlayerData() {
		List<SoccerPlayer> list = new ArrayList<SoccerPlayer>();
		try {
			// 수정할 학생 번호 입ㄹ력
			System.out.print("선수 이름 입력 >> ");
			String name = sc.nextLine();
			// 번호 패턴검색
			boolean value = checkInputPattern(name, 2);
			if (!value)
				return;

			
			DBConnection dbc = new DBConnection();
			// Database connection
			dbc.connect();
			// Entering article table data
			list = dbc.selectSearch(name);

			if (list.size() <= 0) {
				System.out.println("입력된 정보가 없습니다.");
			}

			// 리스트 내용을 보여준다.
			System.out.println("이름\t팀\t골\t어시스트\t파울\t공격포인트\t등수");
			for (SoccerPlayer soccerPlayer : list) {
				System.out.println(soccerPlayer);
			}

			// 수정할 리스트를 보여줘야 된다.
			SoccerPlayer imsiSoccerPlayer = list.get(0);
			System.out.print("골 수 입력 >>");
			int goal = sc.nextInt();
			value = checkInputPattern(String.valueOf(goal), 3);
			if (!value)
				return;
			imsiSoccerPlayer.setGoal(goal);

			System.out.print("어시스트 수 입력 >>");
			int assist = sc.nextInt();
			value = checkInputPattern(String.valueOf(assist), 3);
			if (!value)
				return;
			imsiSoccerPlayer.setAssist(assist);

			System.out.print("파울 수 입력 >>");
			int foul = sc.nextInt();
			value = checkInputPattern(String.valueOf(foul), 3);
			if (!value)
				return;
			imsiSoccerPlayer.setFoul(foul);
			
			
			imsiSoccerPlayer.calPoint();

			// 데이터베이스 수정할 부분을 update 진행
			int returnUpdateValue = dbc.update(imsiSoccerPlayer);
			if (returnUpdateValue == -1) {
				System.out.println("선수 수정 정보 없음");
				return;
			}
			System.out.println("선수 정보 수정 완료하였습니다.");

			dbc.close();

		} catch (InputMismatchException e) {
			System.out.println("입력 타입 맞지 않음. 다시 입력하세요");
			sc.nextLine();
			return;
		} catch (Exception e) {
			System.out.println("데이터베이스 삭제 에러 . 다시 입력하세요");
			return;
		}
	}

	// 검색
	private static void searchPlayerData() {
		List<SoccerPlayer> list = new ArrayList<SoccerPlayer>();

		try {
			System.out.print("검색할 선수 이름을 입력하세요 : ");
			String name = sc.nextLine();

			boolean value = checkInputPattern(name, 2);
			if (!value) {
				return;
			}

			DBConnection dbc = new DBConnection();
			dbc.connect();
			list = dbc.selectSearch(name);
			if (list.size() <= 0) {
				System.out.println("보여줄 list가 없습니다." + list.size());
				return;
			}
			System.out.println("이름\t팀\t골\t어시스트\t파울\t공격포인트\t등수");
			for (SoccerPlayer soccerPlayer : list) {
				System.out.println(soccerPlayer);
			}
			dbc.close();

		} catch (InputMismatchException e) {
			System.out.println("타입이 맞지 않습니다. 재입력요청" + e.getStackTrace());
			return;
		} catch (Exception e) {
			System.out.println("데이터베이스 검색 에러" + e.getStackTrace());
		}
	}

	// 출력
	private static void playerOutput() {
		List<SoccerPlayer> list = new ArrayList<SoccerPlayer>();
		try {

			DBConnection dbc = new DBConnection();
			dbc.connect();
			list = dbc.select();
			if (list.size() <= 0) {
				System.out.println("보여줄 list가 없습니다." + list.size());
				return;
			}
			System.out.println("이름\t팀\t골\t어시스트\t파울\t공격포인트\t등수");
			for (SoccerPlayer soccerPlayer : list) {
				System.out.println(soccerPlayer);
			}
			dbc.close();

		} catch (Exception e) {
			System.out.println("데이터베이스 출력 에러" + e.getMessage());
		}
		return;
	}

	// 삭제
	private static void deletePlayerData() {
		try {
			System.out.print("삭제할 선수를 입력해주세요 : ");
			String name = sc.nextLine();

			boolean value = checkInputPattern(name, 2);

			if (!value)
				return;

			DBConnection dbc = new DBConnection();
			dbc.connect();
			int insertReturnValue = dbc.delete(name);
			if (insertReturnValue == -1) {
				System.out.println("삭제실패입니다." + insertReturnValue);
			}
			if (insertReturnValue == 0) {
				System.out.println("삭제할 선수가 존재하지 않습니다." + insertReturnValue);
			} else {
				System.out.println("삭제성공입니다. 리턴값 = " + insertReturnValue);
			}

			dbc.close();

		} catch (InputMismatchException e) {
			System.out.println("타입이 맞지 않습니다. 재입력요청" + e.getStackTrace());
			return;
		} catch (Exception e) {
			System.out.println("데이터베이스 삭제 에러" + e.getStackTrace());
		}
	}

	// 입력
	private static void playerInputData() {

		String pattern = null;
		boolean regex = false;

		try {

			System.out.print("이름입력 : ");
			String name = sc.nextLine();
			boolean value = checkInputPattern(name, 2);
			if (!value)
				return;

			System.out.print("팀입력 : ");
			String team = sc.nextLine();
			value = checkInputPattern(team, 2);
			if (!value)
				return;

			System.out.print("goal입력 : ");
			int goal = sc.nextInt();
			value = checkInputPattern(String.valueOf(goal), 3);
			if (!value)
				return;

			System.out.print("assist입력 : ");
			int assist = sc.nextInt();
			value = checkInputPattern(String.valueOf(assist), 3);
			if (!value)
				return;

			System.out.print("foul입력 : ");
			int foul = sc.nextInt();
			value = checkInputPattern(String.valueOf(foul), 3);
			if (!value)
				return;

			// 데이터베이스 입력
			SoccerPlayer soccerPlayer = new SoccerPlayer(name, team, goal, assist, foul);
			soccerPlayer.calPoint();

			DBConnection dbc = new DBConnection();

			dbc.connect();

			int insertReturnValue = dbc.insert(soccerPlayer);

			if (insertReturnValue == -1) {
				System.out.println("삽입실패입니다.");
			} else {
				System.out.println("삽입성공입니다. 리턴값=" + insertReturnValue);
			}
			dbc.close();

		} catch (InputMismatchException e) {
			System.out.println("입력타입이 맞지 않습니다. 재입력요청" + e.getStackTrace());
			return;
		} catch (Exception e) {
			System.out.println("데이터베이스 입력 에러" + e.getStackTrace());
		} finally {
			sc.nextLine();
		}

	}

	// 메뉴선택
	public static int displayMenu() {
		int num = -1;

		try {
			System.out.print("1.입력 2.수정 3.삭제 4.검색 5.출력 6.정렬 7.통계 8.종료 : ");
			num = sc.nextInt();

			String pattern = "^[1-8]$";
			boolean regex = Pattern.matches(pattern, String.valueOf(num));
		} catch (InputMismatchException e) {
			System.out.println();
			System.out.println("숫자로 입력해주세요");
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
			pattern = "^[가-R]{2,10}$";
			message = "name 재입력요망";
			break;
		case 3:
			pattern = "^[0-9]{1,3}$";
			message = "score 재입력요망";

			break;
		case 4:
			pattern = "^[1-3]$";
			message = "정렬타입 재입력요망";
			break;
		case 5:
			pattern = "^[1-3]$";
			message = "통계타입 재입력요망";
		
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
