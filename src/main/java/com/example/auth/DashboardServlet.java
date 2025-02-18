package com.example.auth;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        String username = (session != null) ? (String) session.getAttribute("username") : null;

        if (username == null) {
            response.sendRedirect("login");
            return;
        }

        response.setContentType("text/html");
        response.getWriter().println("<!DOCTYPE html>");
        response.getWriter().println("<html lang='en'>");
        response.getWriter().println("<head>");
        response.getWriter().println("<meta charset='UTF-8'>");
        response.getWriter().println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        response.getWriter().println("<title>User Dashboard</title>");
        response.getWriter().println("<style>");
        response.getWriter().println("body { "
                                   + "display: flex; "
                                   + "flex-direction: column; "
                                   + "min-height: 100vh; "
                                   + "margin: 0; "
                                   + "background-color: #f0f2f5; "
                                   + "font-family: Arial, sans-serif; "
                                   + "}");

        response.getWriter().println(".header { "
                                   + "background-color: #1877f2; "
                                   + "color: white; "
                                   + "width: 100%; "
                                   + "text-align: center; "
                                   + "padding: 20px; "
                                   + "font-size: 24px; "
                                   + "font-weight: bold; "
                                   + "}");

        response.getWriter().println(".nav { "
                                   + "background-color: white; "
                                   + "box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1); "
                                   + "padding: 15px; "
                                   + "display: flex; "
                                   + "justify-content: center; "
                                   + "width: 100%; "
                                   + "}");

        response.getWriter().println(".nav a { "
                                   + "margin: 0 15px; "
                                   + "color: #1877f2; "
                                   + "text-decoration: none; "
                                   + "font-size: 16px; "
                                   + "font-weight: bold; "
                                   + "transition: color 0.3s; "
                                   + "}");

        response.getWriter().println(".nav a:hover { "
                                   + "color: #0056b3; "
                                   + "}");

        response.getWriter().println(".welcome { "
                                   + "margin: 20px; "
                                   + "font-size: 18px; "
                                   + "color: #333; "
                                   + "text-align: center; "
                                   + "}");

        // Center the cards more clearly and allow the footer to stay at the bottom
        response.getWriter().println(".content { "
                                   + "display: flex; "
                                   + "justify-content: center; "
                                   + "align-items: center; "
                                   + "flex-wrap: wrap; "
                                   + "gap: 30px; "
                                   + "padding: 20px; "
                                   + "max-width: 1200px; "
                                   + "margin: 0 auto; "     // Centers the content container
                                   + "flex: 1; "           
                                   + "}");

        response.getWriter().println(".card { "
                                   + "background-color: white; "
                                   + "width: 300px; "
                                   + "padding: 20px; "
                                   + "border-radius: 8px; "
                                   + "box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1); "
                                   + "text-align: center; "
                                   + "}");

        response.getWriter().println(".card h3 { "
                                   + "color: #1877f2; "
                                   + "margin-bottom: 10px; "
                                   + "}");

        response.getWriter().println(".card p { "
                                   + "color: #555; "
                                   + "margin-bottom: 15px; "
                                   + "}");

        response.getWriter().println(".card a { "
                                   + "color: #1877f2; "
                                   + "text-decoration: none; "
                                   + "font-weight: bold; "
                                   + "transition: color 0.3s; "
                                   + "}");

        response.getWriter().println(".card a:hover { "
                                   + "color: #0056b3; "
                                   + "}");

        // Let the footer naturally fall at bottom; margin-top:auto ensures it pushes to bottom
        response.getWriter().println(".footer { "
                                   + "background-color: #1877f2; "
                                   + "color: white; "
                                   + "padding: 10px; "
                                   + "text-align: center; "
                                   + "width: 100%; "
                                   + "margin-top: auto; "
                                   + "}");

        response.getWriter().println("</style>");
        response.getWriter().println("</head>");
        response.getWriter().println("<body>");

        // Header
        response.getWriter().println("<div class='header'>Welcome to Your Dashboard</div>");

        // Navigation Bar
        response.getWriter().println("<div class='nav'>");
        response.getWriter().println("<a href='dashboard'>Home</a>");
        response.getWriter().println("<a href='#'>Profile</a>");
        response.getWriter().println("<a href='#'>Settings</a>");
        response.getWriter().println("<a href='#'>Support</a>");
        response.getWriter().println("<a href='logout'>Logout</a>");
        response.getWriter().println("</div>");

        // Welcome Message
        response.getWriter().println("<div class='welcome'>Hello, " + username + "! Here's your personalized dashboard:</div>");

        // Content Section
        response.getWriter().println("<div class='content'>");
        response.getWriter().println("<div class='card'>");
        response.getWriter().println("<h3>Latest Updates</h3>");
        response.getWriter().println("<p>Stay informed with the newest trends and features.</p>");
        response.getWriter().println("<a href='#'>Learn More</a>");
        response.getWriter().println("</div>");
        response.getWriter().println("<div class='card'>");
        response.getWriter().println("<h3>Explore Tools</h3>");
        response.getWriter().println("<p>Access tools to enhance your productivity and skills.</p>");
        response.getWriter().println("<a href='#'>View Tools</a>");
        response.getWriter().println("</div>");
        response.getWriter().println("<div class='card'>");
        response.getWriter().println("<h3>Training Resources</h3>");
        response.getWriter().println("<p>Discover tutorials and guides to improve your expertise.</p>");
        response.getWriter().println("<a href='#'>Get Started</a>");
        response.getWriter().println("</div>");
        response.getWriter().println("</div>");

        // Footer
        response.getWriter().println("<div class='footer'>© 2025 User Dashboard | All rights reserved.</div>");

        response.getWriter().println("</body>");
        response.getWriter().println("</html>");
    }





}
