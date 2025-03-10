package com.cybersecurity.progetto_cybersecurity.utility;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

public class Utils {

    public static  Map<String, Object> createResponse(String message, int statusCode) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", message);
        response.put("status", statusCode);
        return response;
    }

    public static String extractJwtFromCookie(HttpServletRequest request) {
        // Estrai il token dal cookie
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public static String extractIdUserFormCookie(HttpServletRequest request) {
        // Estrai il token dal cookie
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("userId")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public static String extractUserCodeFromCookie(HttpServletRequest request) {
        // Estrai il token dal cookie
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("userId")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
