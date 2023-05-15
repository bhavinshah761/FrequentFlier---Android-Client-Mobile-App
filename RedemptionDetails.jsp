<%@page import="java.sql.*"%>
<%
String awardid = request.getParameter("awardid");
String pid = request.getParameter("pid");
Connection con = null;
PreparedStatement pstmt = null;
ResultSet rs = null;

try {
    DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
    String url = "jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";
    String user = "akheratk";
    String password = "oapsuzic";
    con = DriverManager.getConnection(url, user, password);
    pstmt = con.prepareStatement("SELECT a_description, points_required, redemption_date, center_name FROM awards NATURAL JOIN redemption_history NATURAL JOIN ExchgCenters WHERE award_id = ? AND passid = ?");
    pstmt.setString(1, awardid);
    pstmt.setString(2, pid);
    rs = pstmt.executeQuery();
    
    boolean dataFound = false;

    while (rs.next()) {
        out.println(rs.getString("a_description"));
        out.print(",");
        out.println(rs.getString("points_required"));
        out.print(",");
        out.println(rs.getString("redemption_date"));
        out.print(",");
        out.println(rs.getString("center_name"));
        out.print("#");
             
        dataFound = true;
    } 
    
    if(!dataFound) {
        out.println("null");
    }

} catch (Exception e) {    
    e.printStackTrace();
} finally {
    if (rs != null) rs.close();
    if (pstmt != null) pstmt.close();
    if (con != null) con.close();
}
%>
