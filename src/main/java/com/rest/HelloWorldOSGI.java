package com.rest;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class HelloWorldOSGI implements BundleActivator {
    public void start(BundleContext ctx) {
        System.out.println("Hello world.");
    }
    public void stop(BundleContext bundleContext) {
        System.out.println("Goodbye world.");
    }
}