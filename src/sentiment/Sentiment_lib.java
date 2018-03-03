package sentiment;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import io.indico.Indico;
import io.indico.api.results.IndicoResult;
import io.indico.api.utils.IndicoException;

public class Sentiment_lib {
	Map<String, Object> lng = new HashMap<>();
	Indico indico;
	private int keyword_id;

	public Sentiment_lib(int keyword_id) {
		this.keyword_id = keyword_id;
		lng.put("language","japanese");
		try {
			indico = new Indico("247de42156639746789f2f0110fcff38");
		} catch (IndicoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void getSentiment() {
		MySQL mysql = new MySQL();
		ResultSet rs = mysql.getSelect(keyword_id);
		Double result = null;
		double sum = 0;
		int count = 0;
		try {
			while(rs.next()){
			    int id = rs.getInt("id");
			    String comment = rs.getString("comment");
				double sentiment = rs.getDouble("sentiment");
				if(sentiment == -1) {
					result = this.getsentiValue(comment);
					sum += result;
					count ++;
					System.out.println("id = "+ id + " : comment = "+comment + " sentiment : = "+result );
					mysql.updateTwitter(result, id);
				}
			}
			System.out.println("mean = "+sum / count);
			mysql.updateKeyword( sum / count, keyword_id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	//1件API問い合わせ
	public double getsentiValue(String comment) {
		double result = -1;
		try {
			IndicoResult single = indico.sentiment.predict(
					comment,lng
					);
			result = single.getSentiment();
		} catch (UnsupportedOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IndicoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
		
	}

}
