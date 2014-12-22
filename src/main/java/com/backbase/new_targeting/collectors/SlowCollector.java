package com.backbase.new_targeting.collectors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * Created by borislav on 19/12/14.
 */
public class SlowCollector implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        System.out.println("SlowCollector started!");
        Thread.sleep(2000);
        String body = exchange.getIn().getBody(String.class);
        System.out.println("SlowCollector executed!");
        exchange.getIn().setBody(body + " + some slow data");
    }
}
