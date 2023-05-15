<%@page import="java.sql.*"%>
<%
String flight_id = request.getParameter("flightid");
Connection con = null;
PreparedStatement pstmt = null;
ResultSet rs = null;

try {
    DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
    String url = "jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";
    String user = "akheratk";
    String password = "oapsuzic";
    con = DriverManager.getConnection(url, user, password);
    pstmt = con.prepareStatement("SELECT f.dept_datetime, f.arrival_datetime, f.flight_miles, t.trip_id, t.trip_miles FROM Flights f, Flights_Trips ft, Trips t WHERE f.flight_id = ft.flight_id AND ft.trip_id = t.trip_id AND f.flight_id = ?");
    pstmt.setString(1, flight_id);
    rs = pstmt.executeQuery();
    boolean dataFound = false;

    while(rs.next()) {        
        out.print(rs.getString("dept_datetime"));
        out.print(",");
        out.print(rs.getString("arrival_datetime"));
        out.print(",");        
        out.print(rs.getString("flight_miles"));
        out.print(",");        
        out.print(rs.getString("trip_id"));
        out.print(",");
        out.print(rs.getString("trip_miles"));        
        out.print("#");
        dataFound = true;
    }
     
    if(!dataFound)
    {
        out.print("null");
    }

    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        if (rs != null) rs.close();
        if (pstmt != null) pstmt.close();
        if (con != null) con.close();
    }
%>