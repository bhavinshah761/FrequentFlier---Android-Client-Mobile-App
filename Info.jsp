<%@page import="java.sql.*"%>
<%
String pid = request.getParameter("pid");
Connection con = null;
PreparedStatement pstmt = null;
ResultSet rs = null;
String output = "";

try {
    DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
    String url = "jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";
    String user = "akheratk";
    String password = "oapsuzic";
    con = DriverManager.getConnection(url, user, password);
    
    pstmt = con.prepareStatement("SELECT pname, total_points FROM passengers NATURAL JOIN Point_Accounts WHERE passid = ?");
    pstmt.setString(1, pid);
    rs = pstmt.executeQuery();

    boolean dataFound = false;

    while (rs.next()) {
        output += rs.getObject(1) + "," + rs.getObject(2);
        dataFound = true;
    } 

    if(!dataFound) {
        output = "null";
    }

} catch (Exception e) {
    e.printStackTrace();
} finally {
    if (rs != null) rs.close();
    if (pstmt != null) pstmt.close();
    if (con != null) con.close();

    out.print(output);
}
%>
