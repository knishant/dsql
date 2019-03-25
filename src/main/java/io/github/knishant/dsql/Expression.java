package io.github.knishant.dsql;

abstract class Expression
{
    public abstract String evaluate(ExecutionContext context);
}
