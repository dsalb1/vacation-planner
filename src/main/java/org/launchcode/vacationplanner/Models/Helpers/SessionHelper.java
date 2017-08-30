package org.launchcode.vacationplanner.Models.Helpers;

import org.launchcode.vacationplanner.Models.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionHelper {



    public static void buildSession(HttpSession session, User theUser) {

        session.setAttribute("id", theUser.getId());
        session.setAttribute("hex", theUser.getPassword());
    }

    public static String[] getSession(HttpSession session, HttpServletRequest request) {
        String[] credentials = {request.getSession().getAttribute("id").toString(), request.getSession().getAttribute("hex").toString()};

        if (session.getAttribute("id") != null && session.getAttribute("hex") != null) {

            return credentials;
        }
        return null;
    }

    public static void clearSession(HttpSession session) {
        session.invalidate();
    }
}
