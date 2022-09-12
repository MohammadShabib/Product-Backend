package com.rest.rest;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Bin;
import com.aerospike.client.Key;
import com.aerospike.client.Record;
import com.aerospike.client.policy.Policy;
import com.aerospike.client.policy.RecordExistsAction;
import com.aerospike.client.policy.ScanPolicy;
import com.aerospike.client.policy.WritePolicy;
import com.rest.Services;

import io.spring.guides.gs_producing_web_service.Product;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


import java.util.ArrayList;


@Service
public class ProductControllers {
    AerospikeClient client;
    static String NAMESPACE = "Product";
    static String SET = "test";


    @Autowired
    public ProductControllers(AerospikeClient client) {
        this.client = client;
    }


    Boolean delete(String id) {

        WritePolicy wp = new WritePolicy();
        wp.durableDelete = true;
        Key key = new Key(NAMESPACE, SET, id);
        client.delete(wp, key);
        return true;
    }


    public int createProduct(Product product) {

        System.out.println(client);
        WritePolicy wp = new WritePolicy();
        wp.recordExistsAction = RecordExistsAction.REPLACE;

        Key key = new Key(NAMESPACE, SET, product.getId());
        Bin id = new Bin("id", product.getId());
        Bin name = new Bin("name", product.getName());
        Bin price = new Bin("price", product.getPrice());
        Bin currency = new Bin("currency", product.getCurrency());

        client.put(wp, key, id, name, price, currency);
        return 1;


    }

    public Product getProductById(String id, String currency) {
        Key key = new Key(NAMESPACE, SET, id);
        Record record = client.get(null, key);

        Product product = null;
        if (record != null) {

            product = new Product();
            product.setId(record.getString("id"));
            product.setName(record.getString("name"));
            product.setPrice(record.getFloat("price"));
            product.setCurrency(record.getString("currency"));
        }
            if (currency != null) {
                final float price = Services.convertCurrency(record.getFloat("price"), record.getString("currency"), currency);
                product.setPrice(price);
                product.setCurrency(currency.toUpperCase());
            }
        return product;
    }

    public ArrayList<Object> getProduct() {

        ScanPolicy policy = new ScanPolicy();
        policy.concurrentNodes = true;
        policy.includeBinData = true;

        ArrayList<Object> body = new ArrayList<>();

        client.scanAll(policy, NAMESPACE, SET, (key, record) -> body.add(record.bins));

        return body;

    }
}