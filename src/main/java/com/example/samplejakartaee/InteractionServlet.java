package com.example.samplejakartaee;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet("/crud/basic/*")
public class InteractionServlet extends HttpServlet {
    private List<String> myPortfolio;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson=new Gson();
        String myResponse="";
        resp.setContentType("application/json");
        String pathInfo=req.getPathInfo();
        String index=req.getParameter("index");
        System.out.println(index);
//        System.out.println(pathInfo.substring(1)+"\n"+pathInfo.substring(2));
//        if(pathInfo==null||pathInfo.equals("/")){
//            myResponse = gson.toJson(myPortfolio);
//        }
//        else if(pathInfo.substring(1)!=null){
//            int index=Integer.parseInt(pathInfo.substring(1));
//            myResponse = gson.toJson(myPortfolio.get(index));
//        }
        if(index!=null){
            int ind = Integer.parseInt(req.getParameter("index"));
            myResponse = gson.toJson(myPortfolio.get(ind));
        }
        else{
            myResponse = gson.toJson(myPortfolio);
        }
        resp.getWriter().println(myResponse);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }

    @Override
    public void init() throws ServletException {
        super.init();
        myPortfolio = Arrays.asList("SIP","ETF","Gold","Mutual","Stock");
    }
}
