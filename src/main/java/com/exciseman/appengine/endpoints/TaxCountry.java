package com.exciseman.appengine.endpoints;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.exciseman.country.Country;
import com.exciseman.country.State;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.utils.URIBuilder;

public class TaxCountry extends HttpServlet {

    private final String USER_AGENT = "Mozilla/5.0";
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        URL url = TaxCountry.class.getResource("/usa.json");


        ServletContext context = getServletContext();
        InputStream resourceContent = url.openStream();
        Type listType = new TypeToken<List<State>>() {}.getType();

        try (Reader reader = new InputStreamReader(resourceContent)) {
            Gson gson = new GsonBuilder().create();
            List<State> states = gson.fromJson(reader, listType);
//            out.println(states.toString());
            response.setContentType("application/json");
            out.println(sendGet(request));

        } catch (Exception e) {
            out.println("Failure");
            out.println(e.getLocalizedMessage());
        }
    }


    private String sendGet(HttpServletRequest request) throws Exception {

        URIBuilder b = new URIBuilder("https://taxrates.api.avalara.com:443/postal");
        b.addParameter("country", request.getParameter("country"));
        b.addParameter("postal", request.getParameter("postal"));
        b.addParameter("apikey","O4BNrdZhH1iVtlWMmS/6q4QrdGV+Ab3nahHyG5NnA1zka0dfJdbKV6ro17J4KmgM/h176KvFPqpCK6gUgoAYeQ==" );
        URL url = b.build().toURL();

        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();

    }

}
