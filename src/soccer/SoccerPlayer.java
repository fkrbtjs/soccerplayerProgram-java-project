package soccer;

public class SoccerPlayer implements Comparable<SoccerPlayer>,SoccerPlayerComponent {

	//필드 이름 , 팀 , 골 , 어시스트 , 반칙 , 공격포인트 , 순위
	
	private String name;
	private String team;
	private int goal;
	private int assist;
	private int foul;
	private int point;
	private int rate;
	
	
	
	
	public SoccerPlayer(String name) {
		super();
		this.name = name;
		System.out.println(name);
	}


	public SoccerPlayer(String name, String team, int goal, int assist, int foul) {
		this(name,team,goal,assist,foul,0,0);
	}


	public SoccerPlayer(String name, String team, int goal, int assist, int foul, int point, int rate) {
		super();
		this.name = name;
		this.team = team;
		this.goal = goal;
		this.assist = assist;
		this.foul = foul;
		this.point = point;
		this.rate = rate;
	}





	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getTeam() {
		return team;
	}


	public void setTeam(String team) {
		this.team = team;
	}


	public int getGoal() {
		return goal;
	}


	public void setGoal(int goal) {
		this.goal = goal;
	}


	public int getAssist() {
		return assist;
	}


	public void setAssist(int assist) {
		this.assist = assist;
	}


	public int getFoul() {
		return foul;
	}


	public void setFoul(int foul) {
		this.foul = foul;
	}


	public int getPoint() {
		return point;
	}


	public void setPoint(int point) {
		this.point = point;
	}


	public int getRate() {
		return rate;
	}


	public void setRate(int rate) {
		this.rate = rate;
	}
	
	
	public void calPoint() {
		this.point = this.goal + this.assist;
	}


	@Override
	public String toString() {
		return name + "\t" + team + "\t" + goal + "\t" + assist + "\t"
				+ foul + "\t" + point + "\t" + rate;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof SoccerPlayer)) {
			return false;
		}
		return this.name.equals(((SoccerPlayer)obj).name);
	}
	
	@Override
	public int hashCode() {
		
		return this.name.hashCode();
				
	}


	@Override
	public int compareTo(SoccerPlayer soccerPlayer) {
		
		return this.name.compareToIgnoreCase(soccerPlayer.name);
	}
	
	@Override
    public void display() {
        System.out.println(name + "\t" + team + "\t" + goal + "\t" + assist + "\t"
                + foul + "\t" + point + "\t" + rate);
    }
	
	
	
}
