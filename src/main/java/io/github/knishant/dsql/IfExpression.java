package io.github.knishant.dsql;

import java.util.ArrayList;
import java.util.Collection;

class IfExpression extends Expression
{
    private final String varName;
    private final Collection<Expression> expressions = new ArrayList<>();

    IfExpression(String varName)
    {
        this.varName = varName;
    }

    @Override
    public String evaluate(ExecutionContext context)
    {
        if (context.getBooleanParam(varName))
        {
            StringBuilder builder = new StringBuilder(100);
            expressions.forEach(expression -> builder.append(expression.evaluate(context)));
            return builder.toString();
        }
        else
        {
            return "";
        }
    }

    void addContent(Expression expression)
    {
        expressions.add(expression);
    }
}
