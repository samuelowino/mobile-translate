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
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

public class TranslatePayloadDeserializer extends StdDeserializer<TranslatePayload> {
    private static final Logger logger = Logger.getLogger(TranslatePayloadDeserializer.class.getSimpleName());

    public TranslatePayloadDeserializer() {
        this(null);
    }

    public TranslatePayloadDeserializer(Class<?> vc) {
        super(vc);
    }

    @SneakyThrows
    @Override
    public TranslatePayload deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        ObjectNode node = jp.getCodec().readTree(jp);

        validateCommonFields(node);

        String targetOS = node.get("targetOS").asText().toUpperCase();
        Set<String> distinctLanguages = deserializeSet(jp, node, "distinctLanguages");

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
                (field.isArray() && field.size() == 0)) {
            throw new ValidationException(errorMessage);
        }
    }

    @SneakyThrows
    private AndroidPayload deserializeAndroidPayload(JsonParser jp, ObjectNode node, String targetOS, Set<String> distinctLanguages) throws IOException {

        Set<XmlMessage> xmlContent = new HashSet<>();
        JsonNode xmlContentNode = node.get("xmlContent");

        if (xmlContentNode == null || xmlContentNode.isEmpty()) {
            throw new ValidationException("XmlContent must not be empty for Android payload");
        }

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

    @SneakyThrows
    private IOSPayload deserializeIOSPayload(JsonParser jp, ObjectNode node, String targetOS, Set<String> distinctLanguages) throws IOException {
        Set<IOSMessage> xmlContent = deserializeSet(jp, node, "xmlContent");
        if (xmlContent.isEmpty()) {
            throw new ValidationException("XmlContent must not be empty for iOS payload");
        }
        return new IOSPayload(
                node.get("workflow").asText(),
                targetOS,
                distinctLanguages,
                xmlContent
        );
    }

    @SneakyThrows
    private <T> Set<T> deserializeSet(JsonParser jp, ObjectNode node, String fieldName) throws IOException {
        JsonNode contentNode = node.get(fieldName);
        if (contentNode == null || !contentNode.isArray()) {
            throw new ValidationException(fieldName + " must be a non-null array");
        }
        return jp.getCodec().readValue(
                contentNode.traverse(jp.getCodec()),
                new TypeReference<Set<T>>() {}
        );
    }
}