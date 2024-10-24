package com.owino.mobiletranslate.rest.customs;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.core.type.TypeReference;
import com.owino.mobiletranslate.rest.exception.ValidationException;
import com.owino.mobiletranslate.rest.payload.*;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
/*
  - @SneakyTrows
    To boldly throw checked exceptions where no one has thrown them before!
    This somewhat contentious ability should be used carefully, of course.
    The code generated by lombok will not ignore, wrap, replace, or otherwise modify
    the thrown checked exception; it simply fakes out the compiler. On the JVM
    (class file) level, all exceptions, checked or not, can be thrown
    regardless of the throws clause of your methods, which is why this works.

   */

public class TranslatePayloadDeserializer extends StdDeserializer<TranslatePayload> {

    public TranslatePayloadDeserializer() {
        this(null);
    }

    public TranslatePayloadDeserializer(Class<?> vc) {
        super(vc);
    }

    @SneakyThrows(ValidationException.class)
    @Override
    public TranslatePayload deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException  {
        ObjectNode node = jp.getCodec().readTree(jp);

        validateCommonFields(node);

        String targetOS = node.get("targetOS").asText().toUpperCase();
        List<String> distinctLanguages = deserializeSet(jp, node, "distinctLanguages");

        return switch (targetOS) {
            case "ANDROID" -> deserializeAndroidPayload(jp, node, targetOS, distinctLanguages);
            case "IOS" -> deserializeIOSPayload(jp, node, targetOS, distinctLanguages);
            default -> throw new ValidationException("Unknown targetOS: " + targetOS);
        };
    }

    private void validateCommonFields(ObjectNode node) throws ValidationException {
        validateField(node, "workflow", "Workflow must not be empty");
        validateField(node, "targetOS", "TargetOS must not be empty");
        validateField(node, "distinctLanguages", "DistinctLanguages must not be empty");
    }

    private void validateField(ObjectNode node, String fieldName, String errorMessage) throws ValidationException {
        JsonNode field = node.get(fieldName);
        if (field == null || field.isNull() || (field.isTextual() && field.asText().isEmpty()) ||
                (field.isArray() && field.size() == 0) ) {
            throw new ValidationException(errorMessage);
        }
    }


    private AndroidPayload deserializeAndroidPayload(JsonParser jp, ObjectNode node, String targetOS, List<String> distinctLanguages) throws IOException, ValidationException {

        List<XmlMessage> xmlContent = new ArrayList<>();
        JsonNode xmlContentNode = node.get("xmlContent");

        if (xmlContentNode == null || xmlContentNode.isEmpty()) {
            throw new ValidationException("XmlContent must not be empty for Android payload");
        }
        /* treat exceptions here as IOExceptions */

        if (xmlContentNode.isArray()) {
            for (JsonNode elementNode : xmlContentNode) {
                XmlMessage xmlMessage = jp.getCodec().treeToValue(elementNode, XmlMessage.class);
                xmlContent.add(xmlMessage);
            }
        }


        return new AndroidPayload(
                node.get("workflow").asText(),
                targetOS,
                distinctLanguages,
                xmlContent
        );
    }


    private IOSPayload deserializeIOSPayload(JsonParser jp, ObjectNode node, String targetOS, List<String> distinctLanguages) throws IOException, ValidationException {
        List<IOSMessage> iosContent = new ArrayList<>();
        JsonNode iosContentNode=node.get("iosContent");
        if (iosContentNode == null || iosContentNode.isEmpty()) {
            throw new ValidationException("iosContent must not be empty for iOS payload");
        }
        if(iosContentNode.isArray()){
            for(JsonNode elementNode : iosContentNode){
                IOSMessage iosMessage=jp.getCodec().treeToValue(elementNode, IOSMessage.class);
                iosContent.add(iosMessage);
            }

        }
        return new IOSPayload(
                node.get("workflow").asText(),
                targetOS,
                distinctLanguages,
                iosContent
        );
    }


    private <T> List<T> deserializeSet(JsonParser jp, ObjectNode node, String fieldName) throws IOException, ValidationException {
        JsonNode contentNode = node.get(fieldName);
        if (contentNode == null || !contentNode.isArray()) {
            throw new ValidationException(fieldName + " must be a non-null array");
        }
        return jp.getCodec().readValue(
                contentNode.traverse(jp.getCodec()),
                new TypeReference<List<T>>() {}
        );
    }
}