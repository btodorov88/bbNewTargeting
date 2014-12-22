package com.backbase.new_targeting.collectors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * Created by borislav on 19/12/14.
 */
public class WeatherCollector implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {

        String body = exchange.getIn().getBody(String.class);
        System.out.println("WeatherCollector executed!");
        exchange.getIn().setBody(body + " + some weather information");
    }
}
