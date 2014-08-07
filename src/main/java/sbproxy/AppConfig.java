package sbproxy;

import org.eclipse.jetty.http.HttpURI;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.ConnectHandler;
import org.eclipse.jetty.servlets.ProxyServlet;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;

@Configuration
public class AppConfig {
    @Bean
    ServletRegistrationBean proxyServlet() {
        ServletRegistrationBean registrationBean = new ServletRegistrationBean();
        registrationBean.addUrlMappings("/");
        ProxyServlet proxyServlet = new ProxyServlet() {
            @Override
            protected HttpURI proxyHttpURI(String scheme, String serverName, int serverPort, String uri) throws MalformedURLException {
                System.out.println(scheme + " " + serverName + " " + serverPort + " " + uri);
                return super.proxyHttpURI(scheme, serverName, serverPort, uri);
            }

            @Override
            public void handleConnect(HttpServletRequest request, HttpServletResponse response) throws IOException {
                System.out.println("hoge " + request.getRequestURI());
                super.handleConnect(request, response);
            }
        };
        registrationBean.setServlet(proxyServlet);
        return registrationBean;
    }
//
//    @Bean
//    EmbeddedServletContainerFactory servletContainer() {
//        JettyEmbeddedServletContainerFactory factory = new JettyEmbeddedServletContainerFactory();
//        factory.addServerCustomizers((server) -> {
//            System.out.println(server);
//            server.setHandler(new ConnectHandler() {
//                @Override
//                protected void handleConnect(Request baseRequest, HttpServletRequest request, HttpServletResponse response, String serverAddress) throws ServletException, IOException {
//                    System.out.println(baseRequest + " " + request.getRequestURI() + " " + serverAddress);
//                    super.handleConnect(baseRequest, request, response, serverAddress);
//                }
//            });
//        });
//        return factory;
//    }
}
