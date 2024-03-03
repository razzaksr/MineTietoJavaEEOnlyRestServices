package com.example.samplejakartaee;


import app.mybank.entity.CreditCard;
import app.mybank.exceptions.CreditCardException;
import app.mybank.middleware.DatabaseTarget;
import app.mybank.remotes.CreditCardRepository;
import app.mybank.remotes.StorageTarget;
import app.mybank.services.CreditCardServices;
import com.google.gson.Gson;

import javax.security.auth.login.CredentialException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/cards/*")
public class KYCServlet extends HttpServlet {
    CreditCardServices services;
    @Override
    public void init() throws ServletException {
        super.init();
        StorageTarget storageTarget=new DatabaseTarget();
        services=new CreditCardServices(storageTarget);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path=req.getPathInfo();
        if(path==null||path.equals("/")){
            List<CreditCard> lists=services.callFindAll();
            Gson gson=new Gson();
            String jsons=gson.toJson(lists);
            resp.setContentType("application/json");
            resp.getWriter().print(jsons);
        }
        else{
            String idInfo=path.substring(1);
            Long number=Long.parseLong(idInfo);
            resp.setContentType("application/json");
            try{
                CreditCard creditCard = services.callFindById(number);
                Gson gson=new Gson();
                String json = gson.toJson(creditCard);
                resp.getWriter().println(json);
            }
            catch (CreditCardException creditCardException){
                resp.getWriter().println(creditCardException);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson=new Gson();
        // while passing inputs through postman pass cardExpiry as yyyy-mm-dd even though its util.Date
        CreditCard creditCard=gson.fromJson(req.getReader(),CreditCard.class);
        services.callSave(creditCard);
        resp.setStatus(HttpServletResponse.SC_CREATED);
    }
}
