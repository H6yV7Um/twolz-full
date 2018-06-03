package com.twolz.qiyi.dc.config;

import com.twolz.qiyi.dc.retrofit.LatestEbikesService;
import com.twolz.qiyi.dc.retrofit.ReceivedCacheService;
import com.twolz.qiyi.dc.retrofit.RokyInfoService;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.converter.protobuf.ProtoConverterFactory;

import javax.net.ssl.*;
import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Created by liuwei
 * date 2017-07-03
 */
@Slf4j
@Component
@Configuration
public class RetrofitConfig {

    private @Value("${system.rokyinfo.token}") String token;

    private @Value("${system.rokyinfo.host1}") String host1;

    private @Value("${system.rokyinfo.host2}") String host2;

    @Bean
    public OkHttpClient httpClient() throws Exception {
        final X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }
            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }
            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
        SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null, new TrustManager[]{trustManager}, new SecureRandom());
        SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .sslSocketFactory(sslSocketFactory, trustManager)
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                })
                .addInterceptor( new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        Request.Builder requestBuilder = original.newBuilder()
                                .header("Authorization", "Basic " + token)
                                .method(original.method(), original.body());
                        Request request = requestBuilder.build();
                        return chain.proceed(request);
                    }
                })
                .build();
        return httpClient;
    }

    @Bean
    @Qualifier("retrofit")
    public Retrofit retrofit() throws Exception {
        return new Retrofit.Builder()
                .baseUrl(host1)
                .addConverterFactory(JacksonConverterFactory.create())
                .client(httpClient())
                .build();
    }

    @Bean
    @Qualifier("retrofit1")
    public Retrofit retrofit1() throws Exception {
        return new Retrofit.Builder()
                .baseUrl(host2)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient())
                .build();
    }

    @Bean
    @Qualifier("retrofitByProtobuf")
    public Retrofit retrofitByProtobuf() throws Exception {
        return new Retrofit.Builder()
                .baseUrl(host1)
                .addConverterFactory(ProtoConverterFactory.create())
                .client(httpClient())
                .build();
    }

    @Bean
    public RokyInfoService rokyInfoService(@Qualifier("retrofit") Retrofit retrofit) {
        return retrofit.create(RokyInfoService.class);
    }

    @Bean
    public LatestEbikesService latestEbikesService(@Qualifier("retrofit1") Retrofit retrofit) {
        return retrofit.create(LatestEbikesService.class);
    }

    @Bean
    public ReceivedCacheService receivedCacheService(@Qualifier("retrofitByProtobuf") Retrofit retrofit) {
        return retrofit.create(ReceivedCacheService.class);
    }


}
