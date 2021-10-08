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

@WebServlet("/SearchBookBimpolo")
public class SearchBookBimpolo extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public SearchBookBimpolo() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String keyword = request.getParameter("keyword");
      search(keyword, response);
   }

   void search(String keyword, HttpServletResponse response) throws IOException {
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Book Result";
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
            String selectSQL = "SELECT * FROM MyTableBook";
            preparedStatement = connection.prepareStatement(selectSQL);
         } else {
            String selectSQL = "SELECT * FROM MyTableBook WHERE TITLE LIKE ?";
            String theBookTitle = keyword + "%";
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, theBookTitle);
         }
         ResultSet rs = preparedStatement.executeQuery();

         while (rs.next()) {
            int id = rs.getInt("ID");
            String bookTitle = rs.getString("TITLE").trim();
            String author = rs.getString("AUTHOR").trim();
            String type = rs.getString("TYPE").trim();
            String isbn = rs.getString("ISBN").trim();

            if (keyword.isEmpty() || bookTitle.contains(keyword)) {
               out.println("ID: " + id + ", ");
               out.println("Title: " + bookTitle + ", ");
               out.println("Author: " + author + ", ");
               out.println("Phone: " + type + ", ");
               out.println("ISBN: " + isbn + "<br>");
               
            }
         }
         
         out.println("<a href=/webproject-T3-1006-Bimpolo/search_BookBimpolo.html>Search Book</a> <br>");
         out.println("<a href=/webproject-T3-1006-Bimpolo/search_UserBimpolo.html>Search User</a> <br>");
         out.println("<a href=/webproject-T3-1006-Bimpolo/searchBookInUser.html>Search Book Rent</a> <br>");
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
