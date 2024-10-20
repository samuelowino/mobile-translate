package com.owino.mobiletranslate.rest.controller;

import com.owino.mobiletranslate.android.AndroidTranslationProcessor;
import com.owino.mobiletranslate.android.model.Resources;
import com.owino.mobiletranslate.ios.iOSTranslationProcessor;
import com.owino.mobiletranslate.rest.db.DatabaseManager;
import com.owino.mobiletranslate.rest.exception.ErrorResponse;
import com.owino.mobiletranslate.rest.exception.ValidationException;
import com.owino.mobiletranslate.rest.payload.*;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.sql.*;
import java.util.List;

@Slf4j
public class ResourcesController {

    static Resources translationResources =new Resources();
    private static final long API_KEY_EXPIRATION = 7L * 24 * 60 * 60 * 1000;  // 7 days in milliseconds
    public static void translateRoute(Context ctx) throws IOException {
        java.lang.String apiKey = ctx.header("X-API-Key");
        if (apiKey == null || apiKey.trim().isEmpty()) {
            ctx.status(HttpStatus.UNAUTHORIZED).json(new ErrorResponse("Unauthorized","API key is required"));
            return;
        }
        if(ctx.body().isEmpty()) {
            ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse("Bad Request","provide a request"));
            return;
        }

        try (Connection conn = DatabaseManager.getDataSource().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                     "SELECT user_id, created_at FROM api_keys WHERE api_key = ?")) {

            pstmt.setString(1, apiKey);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Timestamp createdAt = rs.getTimestamp("created_at");
                    int userId = rs.getInt("user_id");
                    if (isApiKeyExpired(createdAt)) {
                        ctx.status(HttpStatus.UNAUTHORIZED).json(new ErrorResponse("Unauthorized","API key has expired"));

                    } else {
                        try {
                            TranslatePayload payload = ctx.bodyAsClass(TranslatePayload.class);
                            processPayload(payload,ctx);
                        }catch(ValidationException e){
                            ctx.status(400).json(new ErrorResponse("Bad request", "could not process request"));
                        }
                    }
                } else {
                    ctx.status(HttpStatus.UNAUTHORIZED).json(new ErrorResponse("Invalid API key","Refresh api token or login to acquire new key"));

                }
            }
        } catch (SQLException e) {
            log.warn("Error validating API key {}", e.getMessage());
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).result("Error validating API key");
        }

    }

    private static void processPayload(TranslatePayload payload,Context ctx) throws ValidationException, IOException {
        switch(payload){
            case AndroidPayload androidPayload ->{
                List<String> target_lang=androidPayload.distinctLanguages();
                List<XmlMessage> xml_content=androidPayload.xmlContent();
                com.owino.mobiletranslate.android.model.String[] androidJsonstring=new com.owino.mobiletranslate.android.model.String[xml_content.size()];
                int index=0;
                for(XmlMessage x: xml_content){
                    androidJsonstring[index++]=new com.owino.mobiletranslate.android.model.String(x.name(), x.content());
                }
                translationResources.setStrings(androidJsonstring);
                ctx.status(HttpStatus.OK).json(new AndroidTranslationProcessor().runTranslation(translationResources,target_lang));

            }
            case IOSPayload iosPayload ->{
                List<IOSMessage> ios_content =iosPayload.iosContent();
                List<String>  distinct_languages=iosPayload.distinctLanguages();
                 ctx.status(HttpStatus.OK).json(new iOSTranslationProcessor().runTranslation(ios_content,distinct_languages));

            }
        }
    }

    private static boolean isApiKeyExpired(Timestamp createdAt) {
        return System.currentTimeMillis() - createdAt.getTime() > API_KEY_EXPIRATION;
    }
}
