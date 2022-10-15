package soccer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DBConnection {
	private Connection connection = null;
	private Statement statement = null;
	private ResultSet rs = null;
	private int id = -1;

	// connection
	public void connect() {

		Properties properties = new Properties();

		try {
			FileInputStream fis = new FileInputStream(
					"C:\\Users\\fkrbt\\eclipse-workspace\\soccer\\src\\soccer\\db.properties");
			properties.load(fis);
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException" + e.getStackTrace());
		} catch (IOException e) {
			System.out.println("Fproperties.load error" + e.getStackTrace());
		}

		try {
			Class.forName(properties.getProperty("driver"));
			connection = DriverManager.getConnection(properties.getProperty("url"), properties.getProperty("userid"),
					properties.getProperty("password"));
		} catch (ClassNotFoundException e) {
			System.out.println("Class.forname load error" + e.getMessage());
		} catch (SQLException e) {
			System.out.println("connection error" + e.getStackTrace());
		}
	}

	// insert statement
	public int insert(SoccerPlayer soccerPlayer) {

		PreparedStatement ps = null;
		int insertReturnValue = -1;
		String insertQuery = "CALL procedure_insert_soccerplayer(?,?,?,?,?)";

		try {
			ps = connection.prepareStatement(insertQuery);
			ps.setString(1, soccerPlayer.getName());
			ps.setString(2, soccerPlayer.getTeam());
			ps.setInt(3, soccerPlayer.getGoal());
			ps.setInt(4, soccerPlayer.getAssist());
			ps.setInt(5, soccerPlayer.getFoul());
			
			
			insertReturnValue = ps.executeUpdate();

		} catch (Exception e) {
			System.out.println("insert 오류발생" + e.getMessage());
		}

		try {
			if (ps != null)
				ps.close();
		} catch (SQLException e) {
			System.out.println("PrepareStatement error" + e.getStackTrace());
		}

		return insertReturnValue;
	}

	// delete statement
	public int delete(String name) {
		PreparedStatement ps = null;
		int deleteReturnValue = -1;
		String deleteQuery = "CALL procedure_delete_soccerplayer(?)";

		try {
			ps = connection.prepareStatement(deleteQuery);
			ps.setString(1, name);
			
			deleteReturnValue = ps.executeUpdate();

		} catch (Exception e) {
			System.out.println("delete 오류발생" + e.getMessage());
		} finally {
			try {
				if (ps != null)
					ps.close();
			} catch (SQLException e) {
				System.out.println("PrepareStatement error" + e.getStackTrace());
			}
		}
		return deleteReturnValue;
	}

	// select statement
	public List<SoccerPlayer> select() {
		List<SoccerPlayer> list = new ArrayList<SoccerPlayer>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String selectQuery = "select * from soccerPlayer";

		try {
			ps = connection.prepareStatement(selectQuery);
			rs = ps.executeQuery(selectQuery);

			if (!(rs != null || rs.isBeforeFirst())) {
				return list;
			}
			// rs.next() : 현재 커서에 있는 레코드 위치로 간다.
			while (rs.next()) {
				String name = rs.getString("name");
				String team = rs.getString("team");
				int goal = rs.getInt("goal");
				int assist = rs.getInt("assist");
				int point = rs.getInt("point");
				int foul = rs.getInt("foul");
				int rate = rs.getInt("rate");

				list.add(new SoccerPlayer(name,team,goal,assist,foul,point,rate));
			}
		} catch (Exception e) {
			System.out.println("select 오류발생" + e.getMessage());
		} finally {
			try {
				if (ps != null)
					ps.close();
			} catch (SQLException e) {
				System.out.println("PrepareStatement error" + e.getMessage());
			}
		}
		return list;
	}

	// search statement
	public List<SoccerPlayer> selectSearch(String data) {
		List<SoccerPlayer> list = new ArrayList<SoccerPlayer>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String selectSearchQuery = "select * from soccerPlayer where name like ? ";

		try {
			
			ps = connection.prepareStatement(selectSearchQuery);
			String namePattern = "%" + data + "%";
			ps.setString(1, namePattern);
			rs = ps.executeQuery();

			if (!(rs != null || rs.isBeforeFirst())) {
				return list;
			}
			// rs.next() : 현재 커서에 있는 레코드 위치로 간다.
			while (rs.next()) {
				String name = rs.getString("name");
				String team = rs.getString("team");
				int goal = rs.getInt("goal");
				int assist = rs.getInt("assist");
				int point = rs.getInt("point");
				int foul = rs.getInt("foul");
				int rate = rs.getInt("rate");

				list.add(new SoccerPlayer(name,team,goal,assist,foul,point,rate));
			}
		} catch (Exception e) {
			System.out.println("selectSearch 오류발생" + e.getMessage());
		}

		try {
			if (ps != null)
				ps.close();
		} catch (SQLException e) {
			System.out.println("PrepareStatement error" + e.getStackTrace());
		} finally {
			try {
				if (ps != null)
					ps.close();
			} catch (SQLException e) {
				System.out.println("PrepareStatement error" + e.getMessage());
			}
		}
		return list;
	}

	// update statement
	public int update(SoccerPlayer soccerPlayer) {

		PreparedStatement ps = null;
		int updateReturnValue = -1;
		String updateQuery = "Call procedure_update_soccerplayer(?,?,?,?,?)";

		try {
			ps = connection.prepareStatement(updateQuery);
			
			ps.setString(1, soccerPlayer.getName());
			ps.setString(2, soccerPlayer.getTeam());
			ps.setInt(3, soccerPlayer.getGoal());
			ps.setInt(4, soccerPlayer.getAssist());
			ps.setInt(5, soccerPlayer.getFoul());

			updateReturnValue = ps.executeUpdate();

		} catch (Exception e) {
			System.out.println("update 오류발생" + e.getMessage());
		} finally {
			try {
				if (ps != null)
					ps.close();
			} catch (SQLException e) {
				System.out.println("PrepareStatement error" + e.getMessage());
			}
		}
		return updateReturnValue;
	}

	// select order by statement
	public List<SoccerPlayer> selectOrderBy(int type) {
		List<SoccerPlayer> list = new ArrayList<SoccerPlayer>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String selectOrderByQuery = "select * from soccerPlayer order by ";

		try {

			switch (type) {
			case 1:
				selectOrderByQuery += "goal desc ";
				break;
			case 2:
				selectOrderByQuery += "assist desc ";
				break;
			case 3:
				selectOrderByQuery += "point desc ";
				break;
			default:
				System.out.println("정렬타입 오류");
				return list;

			}

			ps = connection.prepareStatement(selectOrderByQuery);
			rs = ps.executeQuery();

			if (!(rs != null || rs.isBeforeFirst())) {
				return list;
			}

			// rs.next() : 현재 커서에 있는 레코드 위치로 간다.
			int rate = 0;
			while (rs.next()) {
				String name = rs.getString("name");
				String team = rs.getString("team");
				int goal = rs.getInt("goal");
				int assist = rs.getInt("assist");
				int point = rs.getInt("point");
				int foul = rs.getInt("foul");
				int rank = rs.getInt("rate");

				
					rate = ++rate;
				
				list.add(new SoccerPlayer(name,team,goal,assist,foul,point,rate));
			}
		} catch (Exception e) {
			System.out.println("select 정렬 오류발생" + e.getMessage());
		} finally {
			try {
				if (ps != null)
					ps.close();
			} catch (SQLException e) {
				System.out.println("PrepareStatement error" + e.getMessage());
			}
		}
		return list;
	}

	// select max min statement
	public List<SoccerPlayer> selectMaxMin(int type) {
		List<SoccerPlayer> list = new ArrayList<SoccerPlayer>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String selectMaxMinQuery = "select name from soccerPlayer where ";

		try {

			switch (type) {
			case 1:
				System.out.print("득점왕 : ");
				selectMaxMinQuery += "goal = (select max(goal) from soccerPlayer)";
				break;
			case 2:
				System.out.print("어시왕 : ");
				selectMaxMinQuery += "assist = (select max(assist) from soccerPlayer)";
				break;
			case 3:
				System.out.print("페어플레이어 : ");
				selectMaxMinQuery += "foul = (select min(foul) from soccerPlayer)";
				break;
			default:
				System.out.println("통계타입 오류");
				return list;

			}

			ps = connection.prepareStatement(selectMaxMinQuery);
			rs = ps.executeQuery();

			if (!(rs != null || rs.isBeforeFirst())) {
				return list;
			}

			// rs.next() : 현재 커서에 있는 레코드 위치로 간다.

			while (rs.next()) {
				String name = rs.getString("name");
				
				list.add(new SoccerPlayer(name));
			}
		} catch (Exception e) {
			System.out.println("타입이 맞지 않습니다." + e.getMessage());
		} finally {
			try {
				if (ps != null)
					ps.close();
			} catch (SQLException e) {
				System.out.println("PrepareStatement error" + e.getMessage());
			}
		}
		return list;
	}

	// close statement
	public void close() {
		try {
			if (connection != null)
				connection.close();
		} catch (SQLException e) {
			System.out.println("Connection close error" + e.getStackTrace());
		}
	}

}
