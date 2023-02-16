package com.css.common.http;

import com.css.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

public class HttpClient {

    private static final int OK = 200;// OK: Success!
    private static final int NOT_MODIFIED = 304;
    private static final int BAD_REQUEST = 400;
    private static final int NOT_AUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int NOT_ACCEPTABLE = 406;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;

    private static final int CONNECT_TIMEOUT = 60000;
    public static final int READ_TIMEOUT = 60000;
    private static final int UPLOAD_TIMEOUT = 10 * 10000000;

    public static final String DEF_CHATSET = "UTF-8";
    public static String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";

    public static Logger log = LoggerFactory.getLogger(HttpClient.class);

    /**
     * GET请求
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public static String doGet(String url, HttpParams[] params) throws Exception {
        try {
            HttpURLConnection connection
                    = (HttpURLConnection) new URL(url + "?" + encodeParams(params)).openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(CONNECT_TIMEOUT);
            connection.connect();

            HttpResponse response = new HttpResponse(connection);
            int responseCode = response.getStatusCode();

            if (responseCode != OK) {
                if (responseCode < INTERNAL_SERVER_ERROR) {
                    log.error("code:{}", responseCode);
                }
            }
            return response.toString();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    /**
     * GET请求
     * @param url
     * @return
     */
    public static String doGet(String url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(CONNECT_TIMEOUT);
            connection.connect();

            HttpResponse response = new HttpResponse(connection);
            int responseCode = response.getStatusCode();

            if (responseCode != OK) {
                if (responseCode < INTERNAL_SERVER_ERROR) {
                    log.error("code:{}", responseCode);
                }
            }
            return response.toString();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    public static String doGet(String url, String token) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(CONNECT_TIMEOUT);
            connection.setRequestProperty("slw.jwt.token", token);
            connection.connect();

            HttpResponse response = new HttpResponse(connection);
            int responseCode = response.getStatusCode();

            if (responseCode != OK) {
                if (responseCode < INTERNAL_SERVER_ERROR) {
                    log.error("code:{}", responseCode);
                }
            }
            return response.toString();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    /**
     * POST请求
     *
     * @param url
     * @param httpParams
     * @return
     * @throws Exception
     */
    public static String doPost(String url, HttpParams httpParams) throws Exception {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("POST");
        connection.setConnectTimeout(CONNECT_TIMEOUT);
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setDoOutput(true);

        String paramStr = "";
        if (httpParams != null) {
            //只有一个参数body，且不用进行编码
            paramStr = httpParams.getObject().toString();
        }

        byte[] bytes = paramStr.getBytes("UTF-8");
        connection.setRequestProperty("Content-Length", Integer.toString(bytes.length));
        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();

        HttpResponse response = new HttpResponse(connection);
        int responseCode = response.getStatusCode();

        if (responseCode != OK) {
            if (responseCode < INTERNAL_SERVER_ERROR) {
                log.error("code:{}", responseCode);
            }
        }
        return response.toString();
    }

    /**
     * @param url    请求地址
     * @param params 请求参数
     * @param method 请求方法
     * @return 网络请求字符串
     * @throws Exception
     */
    public static String net(String url, Map params, String method) throws Exception {
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String rs = null;
        try {
            StringBuffer sb = new StringBuffer();
            if (method == null || method.equals("GET")) {
                url = url + "?" + encodeParams(params);
            }
            conn = (HttpURLConnection) new URL(url).openConnection();
            if (method == null || method.equals("GET")) {
                conn.setRequestMethod("GET");
            } else {
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
            }
            conn.setRequestProperty("User-agent", userAgent);
            conn.setUseCaches(false);
            conn.setConnectTimeout(CONNECT_TIMEOUT);
            conn.setReadTimeout(READ_TIMEOUT);
            conn.setInstanceFollowRedirects(false);
            conn.setRequestProperty("Accept-Charset", DEF_CHATSET);
            conn.connect();
            if (params != null && method.equals("POST")) {
                try {
                    DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                    out.writeBytes(encodeParams(params));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            InputStream is = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sb.append(strRead);
            }
            rs = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return rs;
    }

    /**
     * @param url  请求地址
     * @param json json格式请求参数
     * @return 网络请求字符串
     * @throws Exception
     */
    public static String json(String url, String json) throws Exception {
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String rs = null;
        try {
            StringBuffer sb = new StringBuffer();
            conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("User-agent", userAgent);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setUseCaches(false);
            conn.setConnectTimeout(CONNECT_TIMEOUT);
            conn.setReadTimeout(READ_TIMEOUT);
            conn.setInstanceFollowRedirects(false);
            conn.setRequestProperty("Accept-Charset", DEF_CHATSET);
            conn.connect();
            if (json != null) {
                DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                out.writeBytes(json);
            }
            InputStream is = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sb.append(strRead);
            }
            rs = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return rs;
    }

    private static String encodeParams(HttpParams[] httpParams) throws Exception {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < httpParams.length; i++) {
            if (i != 0) {
                buffer.append("&");
            }
            buffer.append(URLEncoder.encode(httpParams[i].getName(), "UTF-8"))
                    .append("=").append(
                    URLEncoder.encode(httpParams[i].getObject()
                            .toString(), "UTF-8"));
        }

        return buffer.toString();
    }

    public static String encodeParams(Map<String, Object> map) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry entry : map.entrySet()) {
            try {
                sb.append(entry.getKey()).append("=")
                        .append(URLEncoder.encode(entry.getValue().toString(), "UTF-8")).append("&");

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        if (StringUtils.isNotBlank(sb.toString())) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

}
