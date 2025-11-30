/******************************************************************************
 * This piece of work is to enhance personahub project functionality.         *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      Spectre.java                                                    *
 * Created:   15/11/2025, 23:33                                               *
 * Modified:  15/11/2025, 23:33                                               *
 *                                                                            *
 * Copyright (c)  2025.  Aerosimo Ltd                                         *
 *                                                                            *
 * Permission is hereby granted, free of charge, to any person obtaining a    *
 * copy of this software and associated documentation files (the "Software"), *
 * to deal in the Software without restriction, including without limitation  *
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,   *
 * and/or sell copies of the Software, and to permit persons to whom the      *
 * Software is furnished to do so, subject to the following conditions:       *
 *                                                                            *
 * The above copyright notice and this permission notice shall be included    *
 * in all copies or substantial portions of the Software.                     *
 *                                                                            *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,            *
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES            *
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND                   *
 * NONINFINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT                 *
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,               *
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING               *
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE                 *
 * OR OTHER DEALINGS IN THE SOFTWARE.                                         *
 *                                                                            *
 ******************************************************************************/

package com.aerosimo.ominet.core.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class Spectre {

    private static final Logger log = LogManager.getLogger(Spectre.class);
    private static final String BASE_URL = "https://ominet.aerosimo.com:9443/spectre/api/errors";
    private static final ObjectMapper mapper = new ObjectMapper();

    public static SpectreResponse recordError(String faultCode, String faultMessage, String faultService) throws Exception {
        String endpoint = BASE_URL + "/stow";
        String payload = mapper.writeValueAsString(Map.of(
                "faultCode", faultCode,
                "faultMessage", faultMessage,
                "faultService", faultService));
        log.info("Calling Spectre endpoint {} to stow the error", endpoint);
        HttpURLConnection conn = (HttpURLConnection) new URL(endpoint).openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);
        conn.setConnectTimeout(4000);
        conn.setReadTimeout(4000);
        try (OutputStream os = conn.getOutputStream()) {
            os.write(payload.getBytes(StandardCharsets.UTF_8));
        }
        int status = conn.getResponseCode();
        log.info("Spectre endpoint {} returned with status {}", endpoint, status);
        BufferedReader br = new BufferedReader(new InputStreamReader(
                (status >= 200 && status < 300) ? conn.getInputStream() : conn.getErrorStream(),
                StandardCharsets.UTF_8
        ));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        conn.disconnect();
        Map<String, Object> responseMap = mapper.readValue(sb.toString(), new TypeReference<>() {});
        String respStatus = responseMap.getOrDefault("status", "unknown").toString();
        String respMessage = responseMap.getOrDefault("message", "no message").toString();

        return new SpectreResponse(respStatus, respMessage);
    }
    public static class SpectreResponse {
        private String status;
        private String message;

        public SpectreResponse(String status, String message) {
            this.status = status;
            this.message = message;
        }
        public String getStatus() { return status; }
        public String getMessage() { return message; }

        @Override
        public String toString() {
            return "SpectreResponse{status='" + status + "', message='" + message + "'}";
        }
    }
}