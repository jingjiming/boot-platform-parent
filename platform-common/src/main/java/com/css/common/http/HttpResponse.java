package com.css.common.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class HttpResponse {

    public static Logger log = LoggerFactory.getLogger("HttpResponse");

    private int statusCode;
    private InputStream inputStream;
    private HttpURLConnection connection;
    private String resultStr = null;
    private boolean streamConsumed = false;

    public HttpResponse() {

    }

    public HttpResponse(HttpURLConnection connection) throws Exception {
        this.connection = connection;
        try {
            this.statusCode = connection.getResponseCode();
        } catch (Exception e) {
            if (e == null || e.toString().trim().equals("java.lang.NullPointerException")) {
                throw new Exception("Url Invalid");
            }
        }

        if (null == (inputStream = connection.getErrorStream())) {
            inputStream = connection.getInputStream();
        }

    }

    public InputStream asStream() {
        if (streamConsumed) {
            throw new IllegalStateException("Stream has already been consumed.");
        }

        return inputStream;
    }

    public String toString() {
        if (null == resultStr) {
            BufferedReader reader;
            try {
                InputStream is = asStream();
                if (null == is) {
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                StringBuffer buffer = new StringBuffer();
                String line;
                while (null != (line = reader.readLine())) {
                    buffer.append(line).append("\n");
                }

                this.resultStr = buffer.toString();
                is.close();
                connection.disconnect();
                streamConsumed = true;
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }

        return resultStr;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
