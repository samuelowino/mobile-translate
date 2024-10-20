package com.owino.mobiletranslate.rest.controller;

import com.owino.mobiletranslate.android.AndroidTranslationProcessor;
import com.owino.mobiletranslate.android.model.Resources;
import com.owino.mobiletranslate.ios.iOSTranslationProcessor;
import com.owino.mobiletranslate.rest.DatabaseConfig;
import com.owino.mobiletranslate.rest.exception.ErrorResponse;
import com.owino.mobiletranslate.rest.exception.ValidationException;
import com.owino.mobiletranslate.rest.payload.*;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.*;
import java.sql.*;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class ResourcesController {
    private static Logger logger=Logger.getLogger(ResourcesController.class.getSimpleName());
    static Resources translationResources =new Resources();
    private static final long API_KEY_EXPIRATION = 7L * 24 * 60 * 60 * 1000;  // 7 days in milliseconds
    public static void translateRoute(Context ctx) throws IOException {
        java.lang.String apiKey = ctx.header("X-API-Key");
        if (apiKey == null || apiKey.trim().isEmpty()) {
            ctx.status(HttpStatus.UNAUTHORIZED).json(new ErrorResponse("Unauthorized","API key is required"));
            logger.info("Attempt to access protected route without API key");
            return;
        }
        if(ctx.body().isEmpty()) {
            logger.info("Empty request body received for login");
            ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse("Bad Request","provide a request"));
            return;
        }

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                     "SELECT user_id, created_at FROM api_keys WHERE api_key = ?")) {

            pstmt.setString(1, apiKey);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Timestamp createdAt = rs.getTimestamp("created_at");
                    int userId = rs.getInt("user_id");
                    if (isApiKeyExpired(createdAt)) {
                        //todo method to delete key if expired [after father deliberation this is not required but scripts can be run by dba"]
                        ctx.status(HttpStatus.UNAUTHORIZED).json(new ErrorResponse("Unauthorized","API key has expired"));
                        logger.info("Expired API key used for user ID: " + userId);
                    } else {
                        try {
                            TranslatePayload payload = ctx.bodyAsClass(TranslatePayload.class);
                            processPayload(payload,ctx);
                        }catch(ValidationException e){
                            ctx.status(400).json(new ErrorResponse("Bad request", e.getMessage()));
                        }

                        //ctx.result("Access granted to protected resource");
                        //logger.info("Successful access to protected route for user ID: " + userId);
                    }
                } else {
                    ctx.status(HttpStatus.UNAUTHORIZED).json(new ErrorResponse("Invalid API key","Refresh api token or login to acquire new key"));
                    logger.info("Attempt to access protected route with invalid API key: " + apiKey);
                }
            }
        } catch (SQLException e) {
            logger.info("Error validating API key"+ e);
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

                    logger.info("NDEBUG FLAG: "+ x.name() + x.content());
                    androidJsonstring[index++]=new com.owino.mobiletranslate.android.model.String(x.name(), x.content());
                }
                translationResources.setStrings(androidJsonstring);
                //new AndroidTranslationProcessor().runTranslation(translationResources,target_lang);
                ctx.status(HttpStatus.OK).json(new AndroidTranslationProcessor().runTranslation(translationResources,target_lang));
                java.lang.String outputfile="src/main/resources/files/input.xml";

            }
            case IOSPayload iosPayload ->{
                List<IOSMessage> ios_content =iosPayload.iosContent();
                List<String>  distinct_languages=iosPayload.distinctLanguages();
                 ctx.status(HttpStatus.OK).json(new iOSTranslationProcessor().runTranslation(ios_content,distinct_languages));

            }
        }
    }
    private static void toXml(Resources contentRoot,java.lang.String outputFileName) {
        try {
            JAXBContext contextObj = JAXBContext.newInstance(Resources.class);
            Marshaller marshallerObj = contextObj.createMarshaller();
            marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshallerObj.marshal(contentRoot, new FileOutputStream(outputFileName));
        } catch (JAXBException | FileNotFoundException e) {
            e.printStackTrace();
        }

    }
    private static boolean isApiKeyExpired(Timestamp createdAt) {
        return System.currentTimeMillis() - createdAt.getTime() > API_KEY_EXPIRATION;
    }
}
