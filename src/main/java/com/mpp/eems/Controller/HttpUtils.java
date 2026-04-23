package com.mpp.eems.Controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;

public class HttpUtils {

    public static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class,
                    (com.google.gson.JsonSerializer<LocalDate>) (src, type, ctx) ->
                            new com.google.gson.JsonPrimitive(src.toString()))
            .registerTypeAdapter(LocalDate.class,
                    (com.google.gson.JsonDeserializer<LocalDate>) (json, type, ctx) ->
                            LocalDate.parse(json.getAsString()))
            .create();

    /** Send a JSON response with the given status code. */
    public static void sendJson(HttpExchange exchange, int status, Object body) throws IOException {
        String json = GSON.toJson(body);
        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(status, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }

    /** Send a plain status with no body (e.g. 204 No Content). */
    public static void sendStatus(HttpExchange exchange, int status) throws IOException {
        exchange.sendResponseHeaders(status, -1);
        exchange.getResponseBody().close();
    }

    /** Read the full request body as a UTF-8 string. */
    public static String readBody(HttpExchange exchange) throws IOException {
        try (InputStream is = exchange.getRequestBody()) {
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        }
    }

    /**
     * Extract the last path segment as an integer ID.
     * e.g. "/employees/42" → 42. Returns -1 if no ID segment is present.
     */
    public static int extractId(HttpExchange exchange) {
        String[] parts = exchange.getRequestURI().getPath().split("/");
        try {
            return Integer.parseInt(parts[parts.length - 1]);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /** Parse a query string param, e.g. "?projectId=3" → 3. Returns -1 if absent. */
    public static int queryParam(HttpExchange exchange, String name) {
        String query = exchange.getRequestURI().getQuery();
        if (query == null) return -1;
        for (String pair : query.split("&")) {
            String[] kv = pair.split("=", 2);
            if (kv.length == 2 && kv[0].equals(name)) {
                try { return Integer.parseInt(kv[1]); } catch (NumberFormatException e) { return -1; }
            }
        }
        return -1;
    }
}