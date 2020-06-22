package com.fth.zuul.fallback;

import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
public class MyFallBack implements FallbackProvider {

    /**
     * 表明为哪个微服务提供回退
     * 服务Id ，若需要所有服务调用都支持回退，返回null 或者 * 即可
     * （返回的服务id 为 配置文件中的 对应的 zuul: routes: service-id）
     */
    @Override
    public String getRoute() {
        // TODO Auto-generated method stub
//        return "*";
        return "service-zuul-config";
    }

    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        System.out.println("cause:" + cause);
        //if(cause instanceof ClientException){
            return response(HttpStatus.BAD_GATEWAY);
       // }
       // return null;
    }

    private ClientHttpResponse response(final HttpStatus status) {
        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return status;
//                return HttpStatus.BAD_REQUEST;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return status.value();
//                return HttpStatus.BAD_REQUEST.value();
            }

            @Override
            public String getStatusText() throws IOException {
                return status.getReasonPhrase();
                //return HttpStatus.BAD_REQUEST.name();
//                return HttpStatus.BAD_REQUEST.getReasonPhrase();
            }

            @Override
            public void close() {
            }

            @Override
            public InputStream getBody() throws IOException {
                String msg = "{\"msg\":\"服务故障\",\"value\":\""+status.value()+"\",\"status phrase\":\""+getStatusText()+"\",\"status\":\""+status+"\"}";
                return new ByteArrayInputStream(msg.getBytes());
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                return headers;
            }
        };
    }
}
