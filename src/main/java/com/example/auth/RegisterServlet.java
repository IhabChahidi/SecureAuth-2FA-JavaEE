package com.example.auth;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            // Hash the password using a secure algorithm (e.g., SHA-256 or BCrypt)
            String hashedPassword = org.apache.commons.codec.digest.DigestUtils.sha256Hex(password);

            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to the database
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/my2fawebsite", "root", "Salam123.");

            // Insert user data into the 'users' table
            String sql = "INSERT INTO users (username, hashed_password, email) VALUES (?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, hashedPassword);
            pstmt.setString(3, email);
            pstmt.executeUpdate();

            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<h2>Registration successful!</h2>");
            out.println("<a href='login'>Go to Login</a>");

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Error occurred during registration: " + e.getMessage());
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("  <title>Register</title>");
        // CSS for a modern and professional registration form
        out.println("  <style>");
        out.println("    body {");
        out.println("      background-color: #f0f2f5;");
        out.println("      margin: 0;");
        out.println("      padding: 0;");
        out.println("      font-family: Arial, sans-serif;");
        out.println("      display: flex;");
        out.println("      justify-content: center;");
        out.println("      align-items: center;");
        out.println("      height: 100vh;");
        out.println("    }");
        out.println("    .register-container {");
        out.println("      width: 350px;");
        out.println("      padding: 30px;");
        out.println("      background-color: #fff;");
        out.println("      border-radius: 10px;");
        out.println("      box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);");
        out.println("    }");
        out.println("    .register-container h2 {");
        out.println("      text-align: center;");
        out.println("      margin-bottom: 20px;");
        out.println("      color: #1877f2;");
        out.println("    }");
        out.println("    .register-container label {");
        out.println("      display: block;");
        out.println("      margin-bottom: 8px;");
        out.println("      font-weight: bold;");
        out.println("      color: #333;");
        out.println("    }");
        out.println("    .register-container input[type='text'],");
        out.println("    .register-container input[type='password'],");
        out.println("    .register-container input[type='email'] {");
        out.println("      width: 100%;");
        out.println("      padding: 12px;");
        out.println("      margin-bottom: 15px;");
        out.println("      border: 1px solid #ccc;");
        out.println("      border-radius: 5px;");
        out.println("      font-size: 14px;");
        out.println("    }");
        out.println("    .register-container input[type='submit'] {");
        out.println("      width: 100%;");
        out.println("      padding: 12px;");
        out.println("      background-color: #1877f2;");
        out.println("      border: none;");
        out.println("      border-radius: 5px;");
        out.println("      color: white;");
        out.println("      font-size: 16px;");
        out.println("      font-weight: bold;");
        out.println("      cursor: pointer;");
        out.println("      transition: background-color 0.3s ease;");
        out.println("    }");
        out.println("    .register-container input[type='submit']:hover {");
        out.println("      background-color: #165cda;");
        out.println("    }");
        out.println("    .register-container a {");
        out.println("      display: block;");
        out.println("      text-align: center;");
        out.println("      margin-top: 10px;");
        out.println("      font-size: 14px;");
        out.println("      color: #1877f2;");
        out.println("      text-decoration: none;");
        out.println("      transition: color 0.3s ease;");
        out.println("    }");
        out.println("    .register-container a:hover {");
        out.println("      color: #165cda;");
        out.println("    }");
        out.println("  </style>");
        out.println("</head>");

        out.println("<body>");
        out.println("  <div class='register-container'>");
        out.println("    <h2>Register</h2>");
        out.println("    <form action='register' method='POST'>");
        out.println("      <label for='username'>Username</label>");
        out.println("      <input type='text' id='username' name='username' placeholder='Enter your username' required>");
        out.println("      <label for='password'>Password</label>");
        out.println("      <input type='password' id='password' name='password' placeholder='Enter your password' required>");
        out.println("      <label for='email'>Email</label>");
        out.println("      <input type='email' id='email' name='email' placeholder='Enter your email' required>");
        out.println("      <input type='submit' value='Register'>");
        out.println("    </form>");
        out.println("    <a href='login'>Already have an account? Login here</a>"); // Added login option
        out.println("  </div>");
        out.println("</body>");
        out.println("</html>");
    }


}
