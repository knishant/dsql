package com.github.knishant.dsql;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class JpaQueryBuilder
{
    private static final Logger LOGGER = Logger.getLogger(JpaQueryBuilder.class.getName());

    private final Template template;
    private final ExecutionContext context = new ExecutionContext();

    JpaQueryBuilder(Template template)
    {
        this.template = template;
    }

    public JpaQueryBuilder addConditionalParam(String name, boolean value)
    {
        context.addParam(name, value);
        return this;
    }

    public JpaQueryBuilder addConditionalParam(String name, Optional<?> optional)
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
