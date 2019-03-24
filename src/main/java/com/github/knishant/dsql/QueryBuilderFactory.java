package com.github.knishant.dsql;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class QueryBuilderFactory
{
    private final Map<String, Template> TEMPLATE_MAP = new ConcurrentHashMap<>();

    public QueryBuilder create(String queryTemplate)
    {
        Template template = TEMPLATE_MAP.computeIfAbsent(queryTemplate, s -> TemplateParser.parse(queryTemplate));
        return new QueryBuilder(template);
    }
}
