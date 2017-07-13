package org.launchcode.vacationplanner.Models.Helpers;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Created by Dan on 7/12/2017.
 */
//helper methods for adding, removing, and requesting cookies from the browser
public class CookieHelper extends HttpServlet {
        public static String getCookieValue(HttpServletRequest request, String name) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (name.equals(cookie.getName())) {
                        return cookie.getValue();
                    }
                }
            }
            return null;
        }

        public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
            Cookie cookie = new Cookie(name, value);
            cookie.setPath("/vacation/");
            cookie.setMaxAge(maxAge);
            response.addCookie(cookie);
        }

        public static void removeCookie(HttpServletResponse response, String name) {
            addCookie(response, name, null, 0);
        }
}

