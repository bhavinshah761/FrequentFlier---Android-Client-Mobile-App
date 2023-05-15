<%@page import="java.sql.*"%>
<%
String source_passid = request.getParameter("spid");
String destination_passid = request.getParameter("dpid");
int npoints = Integer.parseInt(request.getParameter("npoints"));
Connection con = null;
PreparedStatement pstmt1 = null;
PreparedStatement pstmt2 = null;
int rs1=0, rs2=0;

try {
    DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
    String url = "jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";
    String user = "akheratk";
    String password = "oapsuzic";
    con = DriverManager.getConnection(url, user, password);
    
    pstmt1 = con.prepareStatement("Update point_accounts set total_points = total_points - " + npoints + " where passid=?");
    pstmt2 = con.prepareStatement("Update point_accounts set total_points = total_points + " + npoints + "  where passid=?");
    
    pstmt1.setString(1, source_passid);
    pstmt2.setString(1, destination_passid);
    
    rs1 = pstmt1.executeUpdate();
    rs2 = pstmt2.executeUpdate();
    
    boolean dataFound = false;
    
    if(rs1 == 1 && rs2 == 1) {
        out.print("Transfer is successful");
        dataFound = true;
    }

    if(!dataFound)
    {
        out.println("Transfer is unsuccessful");
    }

} catch (Exception e) {
    e.printStackTrace();
} 

con.close();

%>