package org.launchcode.vacationplanner.Models.Helpers;

import org.launchcode.vacationplanner.Models.User;

import javax.servlet.http.HttpSession;

public class SessionHelper {



    public static void buildSession(HttpSession session, User theUser) {

        session.setAttribute("id", theUser.getId());
        session.setAttribute("hex", theUser.getPassword());
    }

    public static String[] getSession(HttpSession session) {
        String[] credentials = {session.getAttribute("id").toString(), session.getAttribute("hex").toString()};
        return credentials;
    }

    public static String getSessionId(HttpSession session) {
        return session.getAttribute("id").toString();
    }

    public static void clearSession(HttpSession session) {
        session.invalidate();
    }
}
