package com.rest;



import com.aerospike.client.AerospikeClient;
import com.aerospike.client.AerospikeException;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

import java.util.Properties;

@EnableWs
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class WebServiceConfig extends WsConfigurerAdapter {
    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/soap/*");
    }

    @Bean(name = "products")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema countriesSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("ProductsPort");
        wsdl11Definition.setLocationUri("/soap");
        wsdl11Definition.setTargetNamespace("http://spring.io/guides/gs-producing-web-service");
        wsdl11Definition.setSchema(countriesSchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema geDispatcherServletSchema() {
        return new SimpleXsdSchema(new ClassPathResource("products.xsd"));
    }


    @Bean(destroyMethod="close")
    public AerospikeClient asClient() throws AerospikeException {
        try {
            Dotenv dotenv = Dotenv.load();
            int PORT = Boolean.parseBoolean(dotenv.get("AEROSPIKE_PORT")) ? Integer.parseInt("AEROSPIKE_PORT") : 3000;
            String URL = dotenv.get("AEROSPIKE_URL");
            System.out.println("Connected to Aerospike...");
            return new AerospikeClient(URL, PORT);
        }
        catch (AerospikeException err)
        {
            System.out.println(err);
            System.out.println("ERROR!! CAN NOT CONNECT TO THE DATABASE");
            return null;
        }
    }

    /*@Bean
    public MultipartConfigElement multipartConfigElement() {
        return new MultipartConfigElement("");
    }*/

    @Bean
    public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory>
    webServerFactoryCustomizer() {
        return factory -> factory.setContextPath("/api");
    }
}