package com.rest.rest;




import io.spring.guides.gs_producing_web_service.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


@RestController
public class ProductRoutes {
    ProductControllers controller;

    @Autowired
    public ProductRoutes(ProductControllers controller) {
        this.controller = controller;
    }


    @RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        controller.delete(id);
        Map<String, String> body = Collections.singletonMap("message", String.format("The product with id: %s has been deleted successfully", id));
        return new ResponseEntity<>(body, HttpStatus.OK);
    }


    @RequestMapping(value = "/products", method = RequestMethod.POST)
    public ResponseEntity<Object> createProduct(@RequestBody Product product) {
        controller.createProduct(product);
        Map<String, Object> body = new HashMap<>();
        body.put("product", product);
        body.put("message", "Product is created successfully");
        return new ResponseEntity<>(body, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/products/{id}")
    public ResponseEntity<Object> getProductById(@PathVariable("id") String id, @RequestParam(value = "currency", required = false) String currency) {
        Product result = controller.getProductById(id, currency);
        if (result != null)
            return new ResponseEntity<>(result, HttpStatus.OK);
        else return new ResponseEntity<>("This Product Doesn't Exist", HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/products")
    public ResponseEntity<Object> getProduct() {
        ArrayList<Object> body = controller.getProduct();
        return new ResponseEntity<>(body, HttpStatus.OK);
    }
}