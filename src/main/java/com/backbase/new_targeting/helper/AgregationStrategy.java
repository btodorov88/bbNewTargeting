package com.backbase.new_targeting.helper;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

/**
 * Created by borislav on 19/12/14.
 */
public class AgregationStrategy implements AggregationStrategy {
    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        if (oldExchange == null) {
            return newExchange;
        }

        String str1 = oldExchange.getIn().getBody(String.class);
        String str2 = newExchange.getIn().getBody(String.class);

        String body = str1 + "\n" + str2;
        oldExchange.getIn().setBody(body);

        return oldExchange;
    }
}
