package com.tudog.graphqldemo01.config.graphql;

/**
 * GraphQL处理器后缀名枚举
 */
enum GraphQLProcessorSuffix {
    /**
     * 查询处理去后缀名
     */
    QUERY("Query"),
    /**
     * 变体处理器后缀名
     */
    MUTATION("Mutation");

    private final String name;

    private GraphQLProcessorSuffix(String name) {
        this.name = name;
    }

     /**
     * Factory method to create an GraphQLProcessorSuffix from a name.
     *
     * @param name  the name to find
     * @return the GraphQLProcessorSuffix object
     * @throws IllegalArgumentException if the name is invalid
     */
    public static GraphQLProcessorSuffix forName(final String name) {
        for (final GraphQLProcessorSuffix processorSuffix : GraphQLProcessorSuffix.values())
        {
            if (processorSuffix.getName().equals(name))
            {
                return processorSuffix;
            }
        }
        throw new IllegalArgumentException("Invalid GraphQLProcessorSuffix name: " + name);
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

}