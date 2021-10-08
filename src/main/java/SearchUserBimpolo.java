import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SearchUserBimpolo")
public class SearchUserBimpolo extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public SearchUserBimpolo() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String keyword = request.getParameter("keyword");
      
      search(keyword, response);
   }

   void search(String keyword, HttpServletResponse response) throws IOException {
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "User Result";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + //
            "transitional//en\">\n"; //
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h1 align=\"center\">" + title + "</h1>\n");

      Connection connection = null;
      PreparedStatement preparedStatement = null;
      try {
         DBConnectionBimpolo.getDBConnection(getServletContext());
         connection = DBConnectionBimpolo.connection;

         if (keyword.isEmpty()) {
            String selectSQL = "SELECT * FROM MyTableUsers";
            preparedStatement = connection.prepareStatement(selectSQL);
         } else {
            String selectSQL = "SELECT * FROM MyTableUsers WHERE MYSER LIKE ?";
            String theUserName = keyword + "%";
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, theUserName);
         }
         ResultSet rs = preparedStatement.executeQuery();

         while (rs.next()) {
            int id = rs.getInt("ID");
            String userName = rs.getString("MYUSER").trim();
            String email = rs.getString("EMAIL").trim();
            String phone = rs.getString("PHONE").trim();
            String address = rs.getString("ADDRESS").trim();
            String book = rs.getString("BOOK").trim();

            if (keyword.isEmpty() || userName.contains(keyword)) {
               out.println("ID: " + id + ", ");
               out.println("User: " + userName + ", ");
               out.println("Email: " + email + ", ");
               out.println("Phone: " + phone + ", ");
               out.println("Address: " + address + ", ");
               out.println("Book: " + book + "<br>");
            }
         }
         out.println("<a href=/webproject-T3-1006-Bimpolo/search_UserBimpolo.html>Search User</a> <br>");
         out.println("<a href=/webproject-T3-1006-Bimpolo/search_BookBimpolo.html>Search Book</a> <br>");
         out.println("<a href=/webproject-T3-1006-Bimpolo/searchBookInUserBimpolo.html>Search Book Rent</a> <br>");
         out.println("<a href=/webproject-T3-1006-Bimpolo/insert_BookBimpolo.html>Insert Book</a> <br>");
         out.println("<a href=/webproject-T3-1006-Bimpolo/insert_UserBimpolo.html>Insert User</a> <br>");
         out.println("</body></html>");
         rs.close();
         preparedStatement.close();
         connection.close();
      } catch (SQLException se) {
         se.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         try {
            if (preparedStatement != null)
               preparedStatement.close();
         } catch (SQLException se2) {
         }
         try {
            if (connection != null)
               connection.close();
         } catch (SQLException se) {
            se.printStackTrace();
         }
      }
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
