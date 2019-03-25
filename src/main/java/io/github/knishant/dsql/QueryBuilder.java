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

    public QueryBuilder addConditionalParam(String name, boolean value)
    {
        context.addParam(name, value);
        return this;
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public QueryBuilder addConditionalParam(String name, Optional<?> optional)
    {
        return addConditionalParam(name, optional.isPresent());
    }

    public String build()
    {
        String query = template.evaluate(context);
        LOGGER.log(Level.FINE, "QUERY = {0}", query);
        return query;
    }
}
