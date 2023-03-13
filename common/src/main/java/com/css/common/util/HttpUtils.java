package com.css.common.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Http/Https 请求工具类
 * HTTPS协议 = HTTP协议 + SSL/TSL协议
 * 在HTTPS数据传输的过程中，需要用SSL/TSL对数据进行加密和解密，需要用HTTP对加密后的数据进行传输
 * HTTP是由HTTP和SSL/TSL一起合作完成
 *
 * Created by jiming.jing on 2023/3/1
 */
public class HttpUtils {

    /**
     * Http协议 GET请求
     *
     * @param url
     * @return
     * @throws Exception
     */
    public static String httpGet(String url) throws Exception {
        // 初始化HttpClient
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 创建HttpGet
        HttpGet httpGet = new HttpGet(url);
        // 发起请求，获取response对象
        CloseableHttpResponse response = httpClient.execute(httpGet);
        // 获取请求状态码
        response.getStatusLine().getStatusCode();
        // 获取返回数据实体对象
        HttpEntity entity = response.getEntity();
        // 转为字符串
        String result = EntityUtils.toString(entity, "UTF-8");
        return result;
    }

    /**
     * Http协议POST请求
     *
     * @param url
     * @param json
     * @return
     * @throws Exception
     */
    public static String httpPost(String url, String json) throws Exception {
        // 初始化HttpClient
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 创建Post对象
        HttpPost httpPost = new HttpPost(url);
        // 设置Content-type
        httpPost.setHeader("Content-Type", "application/json");
        // 写入JSON数据
        httpPost.setEntity(new StringEntity(json));
        // 发起请求，获取response对象
        CloseableHttpResponse response = httpClient.execute(httpPost);
        // 获取请求码
        response.getStatusLine().getStatusCode();
        // 获取返回数据实体对象
        HttpEntity entity = response.getEntity();
        // 转为字符串
        String result = EntityUtils.toString(entity, "UTF-8");
        return result;
    }

    /**
     * Https协议GET请求
     *
     * @param url
     * @return
     * @throws Exception
     */
    public static String httpsGet(String url) throws Exception {
        CloseableHttpClient httpClient = createSSLClientDefault();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        String content = EntityUtils.toString(entity, "UTF-8");
        httpClient.close();
        return content;
    }

    /**
     * Https协议POST请求
     *
     * @param url
     * @param json
     * @return
     * @throws Exception
     */
    public static String httpsPost(String url, String json) throws Exception {
        CloseableHttpClient httpClient = createSSLClientDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setEntity(new StringEntity(json));
        CloseableHttpResponse response = httpClient.execute(httpPost);
        HttpEntity entity = response.getEntity();
        String content = EntityUtils.toString(entity, "UTF-8");
        httpClient.close();
        return content;
    }

    /**
     * JDK提供了一个Java数字证书管理工具keytool，在jdk/bin目录下，通过以下命令可生成一个数字证书
     * keytool -genkey  -alias tomcathttps -keyalg RSA -keysize 2048 -keystore sang.p12 -validity 365
     *
     * 如果报如下错误，需要在JDK中添加安全证书即可
     * sun.security.validator.ValidatorException: PKIX path building failed: sun.security.provider.certpath.SunCertPathBuilderException:
     * unable to find valid certification path to requested target
     *
     * 添加证书命令如下：
     * 在命令行切换到证书解压后所在目录  cd /d D:\cert
     * 设置JDK的路径　set cacerts="C:\Program Files\java\jdk1.8.0_231\jre\lib\security\cacerts"
     * 添加证书　keytool -importcert -keystore %cacerts% -alias nexus -file D:\cert\nexus.cer -storepass changeit -noprompt
     * 查看证书列表  keytool -list -v -alias cacert -keystore "C:/Program Files/java/jdk1.8.0_231/jre/lib/security/cacerts" -storepass changeit
     *
     * @return
     * @throws Exception
     */
    public static CloseableHttpClient createSSLClientDefault() throws Exception {
        // 如果下面的方法证书还是不过，报错的话试试下面第二种
        /*SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
            //信任所有
            public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                return true;
            }
        }).build();
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
        return HttpClients.custom().setSSLSocketFactory(sslsf).build();*/

        SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(
                SSLContexts.custom().loadTrustMaterial(
                        null, new TrustSelfSignedStrategy()
                ).build(),
                NoopHostnameVerifier.INSTANCE);
        return HttpClients.custom().setSSLSocketFactory(socketFactory).build();

    }
}
