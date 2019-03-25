package io.github.knishant.dsql;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class QueryBuilder
{
    private static final Logger LOGGER = Logger.getLogger(QueryBuilder.class.getName());

    private final Template template;
    private final ExecutionContext context = new ExecutionContext();

    QueryBuilder(Template template)
    {
        this.template = template;
    }

    /**
     * Adds a conditional param. this param must be there in the query template.
     * @param name name of the param
     * @param value boolean value of the param
     * @return this instance
     */
    public QueryBuilder addConditionalParam(String name, boolean value)
    {
        context.addParam(name, value);
        return this;
    }

    /**
     * Adds a conditional param. this param must be there in the query template.
     * The condition is set to true if the optional value isPresent().
     * @param name name of the param
     * @param value Optional instance
     * @return this instance
     */
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public QueryBuilder addConditionalParam(String name, Optional<?> optional)
    {
        return addConditionalParam(name, optional.isPresent());
    }

    /**
     * Returns the dynamic query based on the query template and the passed conditional params.
     * @return
     */
    public String build()
    {
        String query = template.evaluate(context);
        LOGGER.log(Level.FINE, "QUERY = {0}", query);
        return query;
    }
}
