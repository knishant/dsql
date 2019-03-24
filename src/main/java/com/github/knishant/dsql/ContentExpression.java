package com.github.knishant.dsql;

public class ContentExpression extends Expression
{
    private final String content;

    public ContentExpression(String content)
    {
        this.content = content;
    }

    @Override
    public String evaluate(ExecutionContext context)
    {
        return content;
    }
}
