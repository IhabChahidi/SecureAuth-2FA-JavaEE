package com.example.auth;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to the database
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/my2fawebsite", "root", "Salam123.");

            // Query to check if the user exists with the provided credentials
            String sql = "SELECT email FROM users WHERE username = ? AND hashed_password = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);

            // Hash the entered password using SHA-256
            String hashedPassword = org.apache.commons.codec.digest.DigestUtils.sha256Hex(password);
            pstmt.setString(2, hashedPassword);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                // User found, set username and email in session
                String email = rs.getString("email");
                request.getSession().setAttribute("username", username);
                request.getSession().setAttribute("email", email); // Set email in session

                // Redirect to OTPServlet
                response.sendRedirect("generate-otp");
            } else {
                // Invalid credentials
                response.getWriter().println("Invalid credentials! Please try again.");
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Error occurred during login: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
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
        out.println("  <title>Login Page</title>");
        // Enhanced CSS for modern look
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
        out.println("    .login-container {");
        out.println("      width: 350px;");
        out.println("      padding: 30px;");
        out.println("      background-color: #fff;");
        out.println("      border-radius: 10px;");
        out.println("      box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);");
        out.println("      transition: transform 0.3s ease;");
        out.println("    }");
        out.println("    .login-container:hover {");
        out.println("      transform: scale(1.02);");
        out.println("    }");
        out.println("    .login-container h2 {");
        out.println("      text-align: center;");
        out.println("      margin-bottom: 20px;");
        out.println("      color: #1877f2;");
        out.println("      font-size: 24px;");
        out.println("    }");
        out.println("    .login-container label {");
        out.println("      display: block;");
        out.println("      margin-bottom: 8px;");
        out.println("      font-weight: bold;");
        out.println("      color: #333;");
        out.println("    }");
        out.println("    .login-container input[type='text'],");
        out.println("    .login-container input[type='password'] {");
        out.println("      width: 100%;");
        out.println("      padding: 12px;");
        out.println("      margin-bottom: 15px;");
        out.println("      border: 1px solid #ccc;");
        out.println("      border-radius: 5px;");
        out.println("      font-size: 14px;");
        out.println("    }");
        out.println("    .login-container input[type='submit'] {");
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
        out.println("    .login-container input[type='submit']:hover {");
        out.println("      background-color: #165cda;");
        out.println("    }");
        out.println("    .login-container a {");
        out.println("      display: block;");
        out.println("      text-align: center;");
        out.println("      margin-top: 10px;");
        out.println("      font-size: 14px;");
        out.println("      color: #1877f2;");
        out.println("      text-decoration: none;");
        out.println("      transition: color 0.3s ease;");
        out.println("    }");
        out.println("    .login-container a:hover {");
        out.println("      color: #165cda;");
        out.println("    }");
        out.println("  </style>");
        out.println("</head>");

        out.println("<body>");
        out.println("  <div class='login-container'>");
        out.println("    <h2>Login</h2>");
        out.println("    <form action='login' method='POST'>");
        out.println("      <label for='username'>Username</label>");
        out.println("      <input type='text' id='username' name='username' placeholder='Enter your username' required>");
        out.println("      <label for='password'>Password</label>");
        out.println("      <input type='password' id='password' name='password' placeholder='Enter your password' required>");
        out.println("      <input type='submit' value='Login'>");
        out.println("    </form>");
        out.println("    <a href='#'>Forgot your password?</a>");
        out.println("    <a href='register'>Don't have an account? Register here</a>");
        out.println("  </div>");
        out.println("</body>");
        out.println("</html>");
    }

}