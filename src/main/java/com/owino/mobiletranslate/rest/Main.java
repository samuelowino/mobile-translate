package com.owino.mobiletranslate.rest;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.owino.mobiletranslate.rest.controller.ResourcesController;
import com.owino.mobiletranslate.rest.controller.UserController;
import com.owino.mobiletranslate.rest.customs.TranslatePayloadDeserializer;
import com.owino.mobiletranslate.rest.exception.ErrorResponse;
import com.owino.mobiletranslate.rest.exception.ValidationException;
import com.owino.mobiletranslate.rest.payload.TranslatePayload;

import io.javalin.Javalin;
import io.javalin.http.UnauthorizedResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.json.JavalinJackson;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

public class Main {
    static Set<String> publicRoutes=Set.of("/register","/login");
    private static Map<String, String> errordetails= new HashMap<>();
    private static Logger logger=Logger.getLogger(Main.class.getSimpleName());
    /*
      * register custom deserializer with object mapper
     */
    private static ObjectMapper my_objectMapper = new ObjectMapper();
    private static SimpleModule module = new SimpleModule();



    public static void main(String[] args) {
        Database.initializeDatabase();
        module.addDeserializer(TranslatePayload.class, new TranslatePayloadDeserializer());
        my_objectMapper.registerModule(module);
        var app = Javalin.create(javalinConfig ->{
                    javalinConfig.jsonMapper(new JavalinJackson(my_objectMapper,true));
                })
                .start(6969);
        app.before(ctx ->{
            if(!publicRoutes.contains(ctx.path())) {
                java.lang.String api_key = ctx.header("X-API-KEY");
                if (api_key == null) {
                    //logger.info(java.lang.String.valueOf(ctx.url()));
                    errordetails.put("status_code", "401");
                    errordetails.put("reason", "not authenticated");
                    errordetails.put("issue url", "http://localhost:6969/login");
                    throw new UnauthorizedResponse("unauthorized", errordetails);
                }
            }
        });
        app.post("/register", UserController::registerUser);
        app.post("/login", UserController::loginUser);
        app.post("/translate", ResourcesController::translateRoute);
        app.post("/refresh", UserController::refreshApiKey);
        app.get("/",ctx ->{
           ctx.html("<h1>Hello World</h1>");
        });
        app.exception(ValidationException.class, (e, ctx) -> {
            ctx.status(400).json(new ErrorResponse("Bad request", e.getMessage()));
        });
    }
}
