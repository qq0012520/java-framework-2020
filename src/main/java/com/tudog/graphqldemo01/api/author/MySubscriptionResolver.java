package com.tudog.graphqldemo01.api.author;

import org.reactivestreams.Publisher;
import org.springframework.stereotype.Component;

import graphql.kickstart.tools.GraphQLSubscriptionResolver;
import graphql.schema.DataFetchingEnvironment;

@Component
public class MySubscriptionResolver implements GraphQLSubscriptionResolver {

    private MyPublisher publisher = new MyPublisher();

    Publisher<Integer> hello(DataFetchingEnvironment env) {
        return publisher;
    }
}