package io.github.knishant.dsql;

class EndExpression extends Expression
{
    @Override
    public String evaluate(ExecutionContext context)
    {
        throw new AssertionError("no evaluation needed for #end");
    }
}
