package com.rest.soap;



import com.rest.rest.ProductControllers;
import io.spring.guides.gs_producing_web_service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class ProductEndpoint {
    private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";

    ProductControllers controller;

    @Autowired
    public ProductEndpoint(ProductControllers controller) {
        this.controller = controller;
    }


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProductRequest")
    @ResponsePayload
    public GetProductResponse getProduct(@RequestPayload GetProductRequest request) {
        GetProductResponse response = new GetProductResponse();
        Product product  = controller.getProductById(request.getId(), request.getCurrency());
        response.setProduct(product);
        return response;
    }
}
