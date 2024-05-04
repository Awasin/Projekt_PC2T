package projekt_knihovna;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SQL_stuff {
private static final SQL_stuff INSTANCE = new SQL_stuff();
private SQL_stuff() {}
public static SQL_stuff getInstance() {
	return INSTANCE;
}

private Connection conn;
private int numberOfBook = 0;

public boolean connect() {
	conn = null;
	try {
		Class.forName("org.sqlite.JDBC");
		conn = DriverManager.getConnection("jdbc:sqlite:C:\\sqlite\\db\\knihovna");
	}
	catch (SQLException | ClassNotFoundException e) { 
        System.out.println(e.getMessage());
        return false;
	}
	return true;
}
public void disconnect() { 

	if (conn != null) {
	   try {conn.close();} 
       catch (SQLException ex) {System.out.println(ex.getMessage());}
	}
}
public boolean createTables(){
    if (conn==null) return false;
    String sql_k = "CREATE TABLE IF NOT EXISTS knihy ("
    	+ "id integer PRIMARY KEY," 
    	+ "jmeno varchar NOT NULL,"
    	+ "rocnik integer NOT NULL," 
    	+ "dostupnost bit NOT NULL,"
    	+ "typ integer,"
    	+ "property integer" + ");";
    String sql_a = "CREATE TABLE IF NOT EXISTS autori ("
        + "id integer PRIMARY KEY," 
        + "knihaid integer FOREGIN KEY,"
        + "jmeno varchar NOT NULL" + ");";
    try{
        Statement stmt = conn.createStatement(); 
        stmt.executeUpdate(sql_k);
        stmt.executeUpdate(sql_a);
        stmt.close();
        return true;
    } 
    catch (SQLException e) {
    	System.out.println(e.getMessage());
    }
    return false;
}
public boolean dropTables() {
	if (conn==null) return false;
	String sql_k = "DROP TABLE knihy";
	String sql_a = "DROP TABLE autori";
	try{
        Statement stmt = conn.createStatement(); 
        stmt.execute(sql_k);
        stmt.execute(sql_a);
        return true;
    } 
    catch (SQLException e) {
    	System.out.println(e.getMessage());
    }
    return false;
}
public boolean insertBook(Book book) {
	if (conn==null) return false;
    try {
    	numberOfBook++;
    	String sql = "INSERT INTO knihy(id,jmeno,rocnik,dostupnost,typ,property) VALUES(?,?,?,?,?,?)";
        PreparedStatement pstmt = conn.prepareStatement(sql); 
        pstmt.setInt(1, numberOfBook);
        pstmt.setString(2, book.getName());
        pstmt.setInt(3, book.getRocnik());
        pstmt.setBoolean(4, book.isDostupnost());
        pstmt.setInt(5, (book instanceof Roman)?1:2);
        if(book instanceof Roman) {pstmt.setInt(6, ((Roman)book).getZanr().ordinal());}
        else {pstmt.setInt(6, ((Ucebnice)book).getDopor_rocnik());}
        pstmt.executeUpdate();
        for(String autor:book.getAutori()) {
        	sql = "INSERT INTO autori(knihaid,jmeno) VALUES(?,?)";
        	pstmt = conn.prepareStatement(sql);
        	pstmt.setInt(1, numberOfBook);
            pstmt.setString(2, autor);
            pstmt.executeUpdate();
        }
        pstmt.close();
        return true;
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
    return false;
}
public List<Book> databasePull() {
	List<Book> knihy = new ArrayList<>();
	if (conn==null) return knihy;
    try {
    	String sql_k = "SELECT * FROM knihy";
    	Statement stmt  = conn.createStatement();
        ResultSet rs_k  = stmt.executeQuery(sql_k);
        List<String> autori = new ArrayList<>();
        while (rs_k.next()) {
        	String sql_a = "SELECT jmeno FROM autori WHERE knihaid = ?";
        	PreparedStatement pstmt = conn.prepareStatement(sql_a);
        	pstmt.setInt(1, rs_k.getInt("id"));
        	ResultSet rs_a  = pstmt.executeQuery();
        	while(rs_a.next()) {autori.add(rs_a.getString("jmeno"));}
        	switch(rs_k.getInt("typ")) {
        	case 1:
        		Zanr zanr;
        		switch(rs_k.getInt("property")) {
        		case 1:
        			zanr = Zanr.AUTOBIOGRAFICKY;
        			break;
        		case 2:
        			zanr = Zanr.DETEKTIVNI;
        			break;
        		case 3:
        			zanr = Zanr.KLICOVY;
        			break;
        		case 4:
        			zanr = Zanr.SCIFI;
        			break;
        		default:
        			zanr = Zanr.UTOPICKY;
        			break;
        		}
        		knihy.add(new Roman(rs_k.getString("jmeno"),autori,rs_k.getInt("rocnik"),zanr,rs_k.getBoolean("dostupnost")));
        		break;
        	case 2:
        		knihy.add(new Ucebnice(rs_k.getString("jmeno"),autori,rs_k.getInt("rocnik"),rs_k.getInt("property"),rs_k.getBoolean("dostupnost")));
        	}
        }
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
    return knihy;
}

}
