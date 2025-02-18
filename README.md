# 🔐 SecureAuth-2FA-JavaEE

**An Enterprise-Grade Two-Factor Authentication (2FA) Module for Java EE Applications**

## Project Overview

SecureAuth-2FA-JavaEE is a robust and scalable Two-Factor Authentication (2FA) module designed to enhance the security of Java EE applications. This system requires a One-Time Password (OTP) as an additional authentication factor, ensuring improved protection against unauthorized access.

## Key Features

- **Support for Email and SMS OTP Verification** (Configurable SMTP & SMS Gateway)
- **Implementation of Time-Based One-Time Passwords (TOTP)**
- **Secure User Session Management**
- **Role-Based Access Control (RBAC) Integration**
- **Seamless Integration with Enterprise Java EE Applications**
- **Database-Backed OTP Storage with Expiry Mechanisms**

## Technologies Utilized

- **Java EE 8** (Servlets, JSP, JDBC)
- **Tomcat 9.0** (Application Server)
- **MySQL / PostgreSQL** (Database Support)
- **SMTP / Twilio API** (For OTP Delivery)

## Project Structure

```
SecureAuth-2FA-JavaEE/
│── src/main/java/com/example/auth/      # Core Authentication Logic (Servlets)
│── src/main/java/com/example/utils/     # Utility Classes
│── src/main/webapp/                     # Frontend (JSP, CSS, JavaScript)
│── WEB-INF/web.xml                       # Web Configuration File
│── WEB-INF/lib/                          # External Dependencies
│── build/classes/com/example/auth/      # Compiled Servlet Classes
│── build/classes/com/example/utils/     # Compiled Utility Classes
│── META-INF/                             # Application Metadata
│── .settings/                            # Eclipse Settings
│── .classpath                            # Eclipse Classpath File
│── .project                              # Eclipse Project Metadata
│── README.md                             # Project Documentation
```

## Installation & Deployment

### **1️⃣ Clone the Repository**

```bash
git clone git@github.com:IhabChahidi/SecureAuth-2FA-JavaEE.git
cd SecureAuth-2FA-JavaEE
```

### **2️⃣ Configure the Database**

- Modify `WEB-INF/lib/dbconfig.properties` to match your database credentials.
- Execute the SQL script located in `sql/setup.sql` to create the necessary tables.

### **3️⃣ Configure SMTP for Email OTP**

Modify `WEB-INF/lib/config.properties` with your SMTP details:

```
SMTP_HOST=smtp.yourmail.com
SMTP_PORT=587
SMTP_USER=your-email@example.com
SMTP_PASS=your-email-password
```

### **4️⃣ Deploy the Application on Tomcat**

1. Copy the project folder to the Tomcat `webapps` directory:
   ```bash
   cp -r SecureAuth-2FA-JavaEE /path/to/tomcat/webapps/
   ```
2. Start the Tomcat server and access the application at:
   ```
   http://localhost:8080/SecureAuth-2FA-JavaEE
   ```

## Security Best Practices

- **Enforce HTTPS for all requests**
- **Secure database credentials using environment variables**
- **Implement strong password policies**
- **Enable rate limiting on OTP requests**
- **Ensure OTP expiration is strictly enforced**

## Future Enhancements

- Multi-Factor Authentication (MFA) Support
- Google Authenticator & Authy Integration
- REST API for 2FA Authentication

## License

This project is released under the **MIT License**. Refer to the [LICENSE](LICENSE) file for details.

---

## Contributions & Support

Contributions are welcomed! If you encounter any issues or have suggestions for improvements, please submit an **issue** or create a **pull request**.

**For inquiries or support, contact:** ihabchahidi00\@gmail.com

---

**🔐 Secure Your Java EE Applications with SecureAuth-2FA-JavaEE! 🔐**

