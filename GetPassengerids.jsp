<%@page import="java.sql.*"%>
<%
String passid = request.getParameter("pid");
Connection con = null;
PreparedStatement pstmt = null;
ResultSet rs = null;

try {
    DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
    String url = "jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";
    String user = "akheratk";
    String password = "oapsuzic";
    con = DriverManager.getConnection(url, user, password);
    pstmt = con.prepareStatement("SELECT passid from passengers where passid != ?");
    pstmt.setString(1, passid);
    rs = pstmt.executeQuery();

    boolean dataFound = false;
    while(rs.next()) {
        out.println(rs.getString("passid"));
        out.println("#");
        dataFound = true;
    } 

    if(!dataFound)
    {
        out.println("Passenger IDs not found.");
    }

} catch (Exception e) {
    e.printStackTrace();
} finally {
    if (rs != null) rs.close();
    if (pstmt != null) pstmt.close();
    if (con != null) con.close();
}
%>