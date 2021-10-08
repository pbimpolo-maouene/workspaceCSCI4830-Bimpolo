
/**
 * @file InsertBookBimpolo.java
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/InsertBookBimpolo")
public class InsertBookBimpolo extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public InsertBookBimpolo() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String bookTitle = request.getParameter("title");
      String author = request.getParameter("author");
      String type = request.getParameter("type");
      String isbn = request.getParameter("isbn");

      Connection connection = null;
      String insertSql = " INSERT INTO MyTableBook (id,TITLE,AUTHOR,TYPE,ISBN) values (default, ?, ?, ?, ?)";

      try {
         DBConnectionBimpolo.getDBConnection(getServletContext());
         connection = DBConnectionBimpolo.connection;
         PreparedStatement preparedStmt = connection.prepareStatement(insertSql);
         preparedStmt.setString(1, bookTitle);
         preparedStmt.setString(2, author);
         preparedStmt.setString(3, type);
         preparedStmt.setString(4, isbn);
         preparedStmt.execute();
         connection.close();
      } catch (Exception e) {
         e.printStackTrace();
      }

      // Set response content type
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Book Inserted to Book table";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h2 align=\"center\">" + title + "</h2>\n" + //
            "<ul>\n" + //

            "  <li><b>Title</b>: " + bookTitle + "\n" + //
            "  <li><b>Author</b>: " + author + "\n" + //
            "  <li><b>Type</b>: " + type + "\n" + //
            "  <li><b>ISBN</b>: " + isbn + "\n" + //

            "</ul>\n");

      out.println("<a href=/webproject-T3-1006-Bimpolo/search_BookBimpolo.html>Search Book</a> <br>");
      out.println("<a href=/webproject-T3-1006-Bimpolo/search_UserBimpolo.html>Search User</a> <br>");
      out.println("<a href=/webproject-T3-1006-Bimpolo/searchBookInUser.html>Search Book Rent</a> <br>");
      out.println("<a href=/webproject-T3-1006-Bimpolo/insert_BookBimpolo.html>Insert Book</a> <br>");
      out.println("<a href=/webproject-T3-1006-Bimpolo/insert_UserBimpolo.html>Insert User</a> <br>");
      out.println("</body></html>");
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
