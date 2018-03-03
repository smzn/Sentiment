package sentiment;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//import com.mysql.jdbc.PreparedStatement;

import io.indico.Indico;
import io.indico.api.results.IndicoResult;
import io.indico.api.utils.IndicoException;

public class MySQL {
	String driver;// JDBCドライバの登録
    String server, dbname, url, user, password;// データベースの指定
    Connection con;
    Statement stmt;
    Map<String, Object> lng = new HashMap<>();
    
	public MySQL() {
		this.driver = "org.gjt.mm.mysql.Driver";
        this.server = "naisyo.sist.ac.jp";
        this.dbname = "naisyo";
        this.url = "jdbc:mysql://" + server + "/" + dbname + "?useUnicode=true&characterEncoding=UTF-8";
        this.user = "naisyo";
        this.password = "sistnaisyo";
        try {
            this.con = DriverManager.getConnection(url, user, password);
            this.stmt = con.createStatement ();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            Class.forName (driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
	}
	
	//keyword_idを返す
	public ResultSet getSelect(int keyword_id) {
		ResultSet rs = null;
		String sql = "SELECT * FROM  `twitters` WHERE  `keyword_id` = "+ keyword_id;
		try {
			rs = stmt.executeQuery (sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	
	public void updateKeyword(double sentimean, int keyword_id) {
		//keywordテーブルへ格納
		StringBuffer buf = new StringBuffer();
		buf.append("UPDATE  keywords SET  `sentimean` = "+ sentimean + "  WHERE  id = " + keyword_id + ";");
		String sql = buf.toString();
		try {
			stmt.execute (sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updateTwitter(double sentiment, int id) {
		String sql = "update twitters set sentiment = ? where id = ?";
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(sql);
			ps.setDouble(1, sentiment);
			ps.setInt(2, id);
			ps.executeUpdate();	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}    
}
