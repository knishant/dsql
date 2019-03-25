package io.github.knishant.dsql;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Factory to create QueryBuilder instances.
 * It is recommended to have one instance of this factory for each repository or one global instance.
 * Donot create this factory instance on each call.
 * This class is thread safe.
 */
public final class QueryBuilderFactory
{
    private final Map<String, Template> TEMPLATE_MAP = new ConcurrentHashMap<>();

    /**
     * Creates a query builder for the passed query template.
     * @param queryTemplate
     * @return query builder instance
     */
    public QueryBuilder create(String queryTemplate)
    {
        Template template = TEMPLATE_MAP.computeIfAbsent(queryTemplate, s -> TemplateParser.parse(queryTemplate));
        return new QueryBuilder(template);
    }
}
