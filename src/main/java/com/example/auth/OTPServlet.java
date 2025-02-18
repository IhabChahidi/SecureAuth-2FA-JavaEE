package com.example.auth;

import java.io.IOException;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.example.utils.Base32decoder;
import j2fa.otp.OTPAuthentication;
import j2fa.otp.HMACAlgorithmEnum;

@WebServlet("/generate-otp")
public class OTPServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = (String) request.getSession().getAttribute("email");

        if (email == null || email.isEmpty()) {
            response.setContentType("text/html");
            response.getWriter().println("<!DOCTYPE html>");
            response.getWriter().println("<html>");
            response.getWriter().println("<head>");
            response.getWriter().println("<title>Error</title>");
            response.getWriter().println("<style>");
            response.getWriter().println("body { font-family: Arial, sans-serif; background-color: #f0f2f5; display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; }");
            response.getWriter().println(".error-container { background-color: #fff; padding: 20px; border-radius: 10px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); text-align: center; }");
            response.getWriter().println("h2 { color: red; }");
            response.getWriter().println("</style>");
            response.getWriter().println("</head>");
            response.getWriter().println("<body>");
            response.getWriter().println("<div class='error-container'>");
            response.getWriter().println("<h2>Error: No email found in session. Please log in again.</h2>");
            response.getWriter().println("</div>");
            response.getWriter().println("</body>");
            response.getWriter().println("</html>");
            return;
        }

        try {
            // Example secret key
            String secretKey = "JBSWY3DPEHPK3PXP";
            byte[] secret = Base32decoder.decode(secretKey);

            // Instantiate OTPAuthentication for TOTP
            OTPAuthentication otpAuth = new OTPAuthentication(
                    secret,
                    "MyApp",
                    email,
                    HMACAlgorithmEnum.SHA1,
                    6,
                    30
            );

            // Generate OTP
            String otp = otpAuth.password();
            request.getSession().setAttribute("generatedOTP", otp);

            // Send OTP via email
            sendEmail(email, otp);

            response.setContentType("text/html");
            response.getWriter().println("<!DOCTYPE html>");
            response.getWriter().println("<html>");
            response.getWriter().println("<head>");
            response.getWriter().println("<title>OTP Verification</title>");
            response.getWriter().println("<style>");
            response.getWriter().println("body { font-family: Arial, sans-serif; background-color: #f0f2f5; display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; }");
            response.getWriter().println(".otp-container { background-color: #fff; padding: 30px; border-radius: 10px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); text-align: center; width: 350px; }");
            response.getWriter().println(".otp-container h2 { color: #1877f2; margin-bottom: 20px; }");
            response.getWriter().println(".otp-container form { margin-top: 20px; }");
            response.getWriter().println(".otp-container input[type='text'] { width: 100%; padding: 10px; margin-bottom: 20px; border: 1px solid #ccc; border-radius: 5px; }");
            response.getWriter().println(".otp-container button { width: 100%; padding: 10px; background-color: #1877f2; color: #fff; border: none; border-radius: 5px; font-size: 16px; font-weight: bold; cursor: pointer; transition: background-color 0.3s; }");
            response.getWriter().println(".otp-container button:hover { background-color: #0056b3; }");
            response.getWriter().println("</style>");
            response.getWriter().println("</head>");
            response.getWriter().println("<body>");
            response.getWriter().println("<div class='otp-container'>");
            response.getWriter().println("<h2>OTP Sent</h2>");
            response.getWriter().println("<p>Your OTP has been sent to: " + email + "</p>");
            response.getWriter().println("<form action='otp' method='post'>");
            response.getWriter().println("<input type='text' name='otp' placeholder='Enter OTP' required>");
            response.getWriter().println("<button type='submit'>Validate OTP</button>");
            response.getWriter().println("</form>");
            response.getWriter().println("</div>");
            response.getWriter().println("</body>");
            response.getWriter().println("</html>");
        } catch (Exception e) {
            e.printStackTrace(); // Log the error details
            response.setContentType("text/html");
            response.getWriter().println("<!DOCTYPE html>");
            response.getWriter().println("<html>");
            response.getWriter().println("<head>");
            response.getWriter().println("<title>Error</title>");
            response.getWriter().println("<style>");
            response.getWriter().println("body { font-family: Arial, sans-serif; background-color: #f0f2f5; display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; }");
            response.getWriter().println(".error-container { background-color: #fff; padding: 20px; border-radius: 10px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); text-align: center; }");
            response.getWriter().println("h2 { color: red; }");
            response.getWriter().println("</style>");
            response.getWriter().println("</head>");
            response.getWriter().println("<body>");
            response.getWriter().println("<div class='error-container'>");
            response.getWriter().println("<h2>Error occurred while sending the email: " + e.getMessage() + "</h2>");
            response.getWriter().println("</div>");
            response.getWriter().println("</body>");
            response.getWriter().println("</html>");
        }
    }


    private void sendEmail(String recipientEmail, String otp) throws MessagingException {
        // Set up email server properties for MailHog
        Properties props = new Properties();
        props.put("mail.smtp.host", "localhost");
        props.put("mail.smtp.port", "1025");
        props.put("mail.smtp.auth", "false");
        props.put("mail.smtp.starttls.enable", "false");

        Session session = Session.getInstance(props);

        // Create email message
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress("test@example.com")); // Sender email (can be anything)
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
        message.setSubject("Your OTP Code");
        message.setText("Your OTP code is: " + otp);

        // Send the email
        Transport.send(message);
        System.out.println("OTP email sent successfully.");
    }
}
