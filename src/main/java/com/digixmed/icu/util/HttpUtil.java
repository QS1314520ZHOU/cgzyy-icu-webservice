package com.digixmed.icu.util;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.UUID;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUtil {
    private static final Logger log = LoggerFactory.getLogger(HttpUtil.class);

    public static String doPostSoap1_1(String data, String address) {
        String retStr = "";
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{new X509TrustManager() { // from class: com.digixmed.icu.util.HttpUtil.1
                @Override // javax.net.ssl.X509TrustManager
                public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                }

                @Override // javax.net.ssl.X509TrustManager
                public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                }

                @Override // javax.net.ssl.X509TrustManager
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            }}, null);
        } catch (KeyManagementException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        if (sslContext != null) {
            SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            httpClientBuilder.setSSLSocketFactory(sslConnectionSocketFactory);
        }
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
        HttpPost httpPost = new HttpPost(address);
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(3000).setConnectionRequestTimeout(3000).build();
        httpPost.setConfig(requestConfig);
        try {
            try {
                httpPost.setHeader("Content-Type", "text/xml;charset=UTF-8");
                httpPost.setHeader("SOAPAction", "http://www.dhcc.com.cn/DHC.Published.RICUSystems.BS.RICUSystems.HIPMessageServer");
                StringEntity entity = new StringEntity(data, StandardCharsets.UTF_8);
                httpPost.setEntity(entity);
                CloseableHttpResponse response = closeableHttpClient.execute(httpPost);
                log.info("请求方式:" + httpPost.getMethod());
                if (response.getStatusLine().getStatusCode() == 200) {
                    HttpEntity httpEntity = response.getEntity();
                    if (httpEntity != null) {
                        String retStr2 = EntityUtils.toString(httpEntity, StandardCharsets.UTF_8);
                        log.info("请求成功：" + retStr2);
                        try {
                            closeableHttpClient.close();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                        return retStr2;
                    }
                } else {
                    HttpEntity httpEntity2 = response.getEntity();
                    if (httpEntity2 != null) {
                        retStr = EntityUtils.toString(httpEntity2, StandardCharsets.UTF_8);
                        log.info("请求失败：" + retStr);
                    }
                }
                try {
                    closeableHttpClient.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            } catch (Exception e4) {
                e4.printStackTrace();
                retStr = "请求报错:" + e4.getMessage();
                try {
                    closeableHttpClient.close();
                } catch (IOException e5) {
                    e5.printStackTrace();
                }
            }
            return retStr;
        } catch (Throwable th) {
            try {
                closeableHttpClient.close();
            } catch (IOException e6) {
                e6.printStackTrace();
            }
            throw th;
        }
    }

    public static String doPost(String inPut, String url,String yhipSender) {
        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(java.time.Duration.ofSeconds(3))
                .build();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
                .timeout(java.time.Duration.ofSeconds(5))
                .header("Content-type", "application/json;charset=UTF-8").header("yhip_sender", yhipSender).header("yhip_messageId", UUID.randomUUID().toString()).POST(HttpRequest.BodyPublishers.ofString(inPut)).build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return (String) response.body();
            }
            log.error("请求失败，状态码：" + response.statusCode());
            return null;
        } catch (IOException | InterruptedException e) {
            log.error("异常：" + e.getMessage());
            return null;
        }
    }
    public static String doPostXML(String inPut, String url,String yhipSender) {
        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(java.time.Duration.ofSeconds(3))
                .build();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
                .timeout(java.time.Duration.ofSeconds(5))
                .header("Content-type", "application/xml")
                .header("yhip_sender", yhipSender).header("Show-Logic-Delete", "1").header("yhip_messageId", UUID.randomUUID().toString()).POST(HttpRequest.BodyPublishers.ofString(inPut)).build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return (String) response.body();
            }
            log.error("请求失败，状态码：" + response.statusCode());
            return null;
        } catch (IOException | InterruptedException e) {
            log.error("异常：" + e.getMessage());
            return null;
        }
    }
}