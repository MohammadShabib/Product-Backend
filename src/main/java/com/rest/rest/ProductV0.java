/*
package com.rest.rest;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Bin;
import com.aerospike.client.Key;
import com.aerospike.client.Record;
import com.aerospike.client.policy.RecordExistsAction;
import com.aerospike.client.policy.ScanPolicy;
import com.aerospike.client.policy.WritePolicy;
import com.rest.Services;
import com.rest.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


@RestController
public class ProductV0 {
    @Autowired
    AerospikeClient client;
    static String NAMESPACE = "Product";
    static String SET = "test";


    @RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        Key key = new Key(NAMESPACE, SET, id);
        client.delete(null, key);
        Map body = Collections.singletonMap("message", String.format("The product with id: %s has been deleted successfully", id));
        return new ResponseEntity<>(body, HttpStatus.OK);
    }


    @RequestMapping(value = "/products", method = RequestMethod.POST)
    public ResponseEntity<Object> createProduct(@RequestBody Product product) {


        WritePolicy wp = new WritePolicy();
        wp.recordExistsAction = RecordExistsAction.REPLACE;
        Key key = new Key(NAMESPACE, SET, product.getId());
        Bin id = new Bin("id", product.getId());
        Bin name = new Bin("name", product.getName());
        Bin price = new Bin("price", product.getPrice());
        Bin currency = new Bin("currency", product.getCurrency());
        client.put(wp, key, id, name, price, currency);


        Map<String, Object> body = new HashMap<>();
        body.put("product", product);
        body.put("message", "Product is created successfully");


        return new ResponseEntity<>(body, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/products/{id}")
    public ResponseEntity<Object> getProductById(@PathVariable("id") String id, @RequestParam(value = "currency", required = false) String currency) {

        Key key = new Key(NAMESPACE, SET, id);
        Record record = client.get(null, key);

        ResponseEntity<Object> response;
        if (record != null) {

            Product product = new Product(record.getString("id"),
                    record.getString("name"),
                    record.getFloat("price"),
                    record.getString("currency"));

            if (currency != null) {
                final float price = Services.convertCurrency(record.getFloat("price"), record.getString("currency"), currency);
                product.setPrice(price);
                product.setCurrency(currency.toUpperCase());


            }
            response = new ResponseEntity<>(product, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>("This Product Doesn't Exist", HttpStatus.NOT_FOUND);
        }

        return response;
    }

    @RequestMapping(value = "/products")
    public ResponseEntity<Object> getProduct() {

        ScanPolicy policy = new ScanPolicy();
        policy.concurrentNodes = true;
        policy.includeBinData = true;

        ArrayList<Object> body = new ArrayList<>();

        client.scanAll(policy, NAMESPACE, SET, (key, record) -> body.add(record.bins));

        return new ResponseEntity<>(body, HttpStatus.OK);
    }
}*/
