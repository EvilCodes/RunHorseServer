package cn.lyj.hero.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
 * @author liuyujie
 */
public class DBUtil {
	
	static{
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getCon(){
		Connection con = null;
		String url = "jdbc:sqlite://"+PropertiesUtil.getValue("dbpath");
		try {
			con = DriverManager.getConnection(url);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return con;
	}
	
	public static void closeCon(Connection con,PreparedStatement ps,ResultSet rs){
		try{
			if(rs!=null){
				rs.close();
			}
			if(ps!=null){
				ps.close();
			}
			if(con!=null){
				con.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
