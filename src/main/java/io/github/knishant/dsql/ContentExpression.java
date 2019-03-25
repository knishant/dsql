package io.github.knishant.dsql;

class ContentExpression extends Expression
{
    private final String content;

    ContentExpression(String content)
    {
        this.content = content;
    }

    @Override
    public String evaluate(ExecutionContext context)
    {
        return content;
    }
}
