package sentiment;

public class Sentiment_main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int keyword_id = 102;
		Sentiment_lib slib = new Sentiment_lib(keyword_id);
		slib.getSentiment();
	}

}
