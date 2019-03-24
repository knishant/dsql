package com.github.knishant.dsql;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class JpaQueryBuilderFactory
{
    private final Map<String, Template> TEMPLATE_MAP = new ConcurrentHashMap<>();

    public JpaQueryBuilder create(String queryTemplate)
    {
        Template template = TEMPLATE_MAP.computeIfAbsent(queryTemplate, s -> TemplateParser.parse(queryTemplate));
        return new JpaQueryBuilder(template);
    }
}
