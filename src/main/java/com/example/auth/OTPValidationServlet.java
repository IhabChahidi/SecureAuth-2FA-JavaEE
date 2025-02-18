package com.example.auth;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/otp")
public class OTPValidationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Validate OTP
        String userOTP = request.getParameter("otp");
        String generatedOTP = (String) request.getSession().getAttribute("generatedOTP");

        response.setContentType("text/html");
        response.getWriter().println("<!DOCTYPE html>");
        response.getWriter().println("<html>");
        response.getWriter().println("<head>");
        response.getWriter().println("<title>OTP Validation</title>");
        response.getWriter().println("<style>");
        response.getWriter().println("body { font-family: Arial, sans-serif; background-color: #f0f2f5; display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; }");
        response.getWriter().println(".otp-container { background-color: #fff; padding: 30px; border-radius: 10px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); text-align: center; width: 350px; }");
        response.getWriter().println(".otp-container h2 { color: #1877f2; margin-bottom: 20px; }");
        response.getWriter().println(".otp-container p { color: #333; margin-bottom: 20px; }");
        response.getWriter().println(".otp-container a { color: #1877f2; text-decoration: none; font-weight: bold; transition: color 0.3s; }");
        response.getWriter().println(".otp-container a:hover { color: #0056b3; }");
        response.getWriter().println("</style>");
        response.getWriter().println("</head>");
        response.getWriter().println("<body>");

        // OTP validation logic
        if (userOTP == null || generatedOTP == null) {
            response.getWriter().println("<div class='otp-container'>");
            response.getWriter().println("<h2>Error</h2>");
            response.getWriter().println("<p>Missing OTP data. Please generate a new OTP.</p>");
            response.getWriter().println("<a href='generate-otp'>Generate a new OTP</a>");
            response.getWriter().println("</div>");
        } else if (userOTP.equals(generatedOTP)) {
            // Clear OTP from the session after successful validation
            request.getSession().removeAttribute("generatedOTP");

            // Redirect directly to DashboardServlet
            response.sendRedirect("dashboard");
        } else {
            response.getWriter().println("<div class='otp-container'>");
            response.getWriter().println("<h2>Invalid OTP</h2>");
            response.getWriter().println("<p>The OTP you entered is incorrect. Please try again.</p>");
            response.getWriter().println("<a href='generate-otp'>Generate a new OTP</a>");
            response.getWriter().println("</div>");
        }

        response.getWriter().println("</body>");
        response.getWriter().println("</html>");
    }

}

