package com.github.knishant.dsql;

public abstract class Expression
{
    public abstract String evaluate(ExecutionContext context);
}