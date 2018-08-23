package cn.webank.mumble.mpc.giftcard.common.utils;

import org.apache.http.HttpHost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.util.StringUtils;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * HttpClient工具
 */
public class HttpClientUtils {
    /**
     * 对https访问信任，不需要证书；
     * 设置http连接池的最大连接数，和同一个主机(IP)的最大并发连接数
     *
     * @param proxyHost 代理ip
     * @param proxyPort      代理端口
     * @return
     * @throws KeyStoreException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public static CloseableHttpClient acceptsUntrustedCertsHttpClient(String proxyHost, int proxyPort)
            throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

        // setup a Trust Strategy that allows all certificates.
        SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
            public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                return true;
            }
        }).build();

        httpClientBuilder.setSSLContext(sslContext);

        // don't check Hostnames, either.
        // use SSLConnectionSocketFactory.getDefaultHostnameVerifier(), if you don't want to weaken
        HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;

        // here's the special part:
        // -- need to create an SSL Socket Factory, to use our weakened "trust strategy";
        // -- and create a Registry, to register it.
        SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", sslSocketFactory).build();
        // now, we create connection-manager using our Registry. allows multi-threaded use
        PoolingHttpClientConnectionManager connMgr = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        connMgr.setMaxTotal(200);
        connMgr.setDefaultMaxPerRoute(100);
        httpClientBuilder.setConnectionManager(connMgr);

        HttpHost httpHost = new HttpHost(proxyHost, proxyPort);
        httpClientBuilder.setProxy(httpHost);

        // finally, build the HttpClient;
        CloseableHttpClient client = httpClientBuilder.build();
        return client;
    }
    
    public static CloseableHttpClient acceptsHttpClient(String proxyHost, int proxyPort)
            throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

        // setup a Trust Strategy that allows all certificates.
        SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
            public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                return true;
            }
        }).build();

        httpClientBuilder.setSSLContext(sslContext);

        // don't check Hostnames, either.
        // use SSLConnectionSocketFactory.getDefaultHostnameVerifier(), if you don't want to weaken
        HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;

        // here's the special part:
        // -- need to create an SSL Socket Factory, to use our weakened "trust strategy";
        // -- and create a Registry, to register it.
        SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", sslSocketFactory).build();
        // now, we create connection-manager using our Registry. allows multi-threaded use
        PoolingHttpClientConnectionManager connMgr = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        connMgr.setMaxTotal(200);
        connMgr.setDefaultMaxPerRoute(100);
        httpClientBuilder.setConnectionManager(connMgr);
        
        //不用代理
        if(!StringUtils.isEmpty(proxyHost)){
        HttpHost httpHost = new HttpHost(proxyHost, proxyPort);
        httpClientBuilder.setProxy(httpHost);
        }
        
        // finally, build the HttpClient;
        CloseableHttpClient client = httpClientBuilder.build();
        return client;
    }
}