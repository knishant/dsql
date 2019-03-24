package io.github.knishant.dsql;

public class EndExpression extends Expression
{
    @Override
    public String evaluate(ExecutionContext context)
    {
        throw new AssertionError("no evaluation needed for #end");
    }
}
