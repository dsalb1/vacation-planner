package org.launchcode.vacationplanner.Models.Helpers;

import org.launchcode.vacationplanner.Models.User;

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

      //sets cookies for user id and a hashed password
        public static void setCookieSession(HttpServletResponse response, User theUser) {
                String id = Integer.toString(theUser.getId());
                CookieHelper.addCookie(response, "id", id, (60 * 60 * 24 * 14));
                CookieHelper.addCookie(response, "hex", theUser.getPassword(), (60 * 60 * 24 * 14));
            }

        public static void clearCookieSession(HttpServletResponse response) {
            addCookie(response, "id", null, 0);
            addCookie(response, "hex", null, 0);
        }

}

