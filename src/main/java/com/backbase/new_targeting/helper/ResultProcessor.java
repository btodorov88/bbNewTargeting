package com.backbase.new_targeting.helper;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * Created by borislav on 19/12/14.
 */
public class ResultProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        System.out.println("----------End result---------");
        System.out.println(exchange.getIn().getBody(String.class));
        System.out.println("-----------------------------");
    }
}
