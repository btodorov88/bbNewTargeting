package com.backbase.new_targeting;

import com.backbase.new_targeting.collectors.SlowCollector;
import com.backbase.new_targeting.collectors.TagCollector;
import com.backbase.new_targeting.collectors.VerySlowCollector;
import com.backbase.new_targeting.collectors.WeatherCollector;
import com.backbase.new_targeting.helper.AgregationStrategy;
import org.apache.camel.*;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.MulticastDefinition;

import java.util.concurrent.Future;


public final class BBTargetingExample {

    public static final int TIMEOUT = 3000;
    private static Processor[] collectors = {new WeatherCollector(), new TagCollector(), new SlowCollector(), new VerySlowCollector()};

    public static void main(String args[]) throws Exception {
        final CamelContext context = new DefaultCamelContext();

        defineRoutes(context);

        context.start();

        startCollectors(context);

        // wait a bit and then stop
        Thread.sleep(1000);
        context.stop();
    }

    private static void defineRoutes(CamelContext context) throws Exception {
        context.addRoutes(new RouteBuilder() {
            public void configure() {

                MulticastDefinition multiCast = from("direct:executeCollectors").setExchangePattern(ExchangePattern.InOut)
                        .multicast(new AgregationStrategy()).parallelProcessing().timeout(TIMEOUT);

                // Setup collectors
                for(Processor collector : collectors){
                    multiCast.bean(collector);
                }

                // Merge the results
                multiCast.end();
            }
        });
    }

    private static void startCollectors(CamelContext context) {
        ProducerTemplate producerTemplate = context.createProducerTemplate();
        Exchange result = producerTemplate.request("direct:executeCollectors", new Processor() {
            @Override
            public void process(Exchange exchange) throws Exception {
                exchange.getIn().setBody("Initial Message");
            }
        });

        System.out.println("----------End result---------");
        System.out.println(result.getOut().getBody());
        System.out.println("-----------------------------");
    }

    private static void startCollectorsAsync(CamelContext context) throws Exception{
        ProducerTemplate producerTemplate = context.createProducerTemplate();
        Future<Exchange> result = producerTemplate.asyncSend("direct:executeCollectors", new Processor() {
            @Override
            public void process(Exchange exchange) throws Exception {
                exchange.getIn().setBody("Initial Message");
            }
        });

        System.out.println("----------End result---------");
        System.out.println(result.get().getOut().getBody());
        System.out.println("-----------------------------");
    }
}