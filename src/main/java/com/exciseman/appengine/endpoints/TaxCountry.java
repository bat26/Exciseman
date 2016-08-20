package com.exciseman.appengine.endpoints;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

import com.exciseman.country.State;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class TaxCountry extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        ServletContext context = getServletContext();
        String fullPath = context.getRealPath("usa.json");
        InputStream resourceContent = context.getResourceAsStream(fullPath.trim());

        try (Reader reader = new InputStreamReader(resourceContent)) {
            Gson gson = new GsonBuilder().create();
            State state = gson.fromJson(reader, State.class);
            out.println(state);
        } catch (Exception e) {
            out.println("Failure");
            out.println(e.getLocalizedMessage());
        }
    }

}
