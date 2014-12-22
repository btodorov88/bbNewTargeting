package com.backbase.new_targeting.collectors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * Created by borislav on 19/12/14.
 */
public class VerySlowCollector implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        System.out.println("VerySlowCollector started!");
        Thread.sleep(6000);
        String body = exchange.getIn().getBody(String.class);
        System.out.println("VerySlowCollector executed!");
        exchange.getIn().setBody(body + " + some very slow data");
    }
}
