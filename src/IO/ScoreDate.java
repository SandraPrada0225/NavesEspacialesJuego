package IO;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class ScoreDate {

	private String date;
	private int score;
	public ScoreDate(int score) {
		this.score = score;
		
		Date todate= new Date(System.currentTimeMillis());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		date= format.format(todate);
	}
	public ScoreDate() {
		
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	
	
	
	
}
