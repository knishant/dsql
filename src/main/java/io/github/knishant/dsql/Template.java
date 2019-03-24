package io.github.knishant.dsql;

import java.util.ArrayList;
import java.util.Collection;

public class Template extends Expression
{
    private final Collection<Expression> expressions = new ArrayList<>();
    private IfExpression ifExpr;

    @Override
    public String evaluate(ExecutionContext context)
    {
        StringBuilder builder = new StringBuilder(100);
        expressions.forEach(expression -> builder.append(expression.evaluate(context)));
        return builder.toString();
    }

    void addExpression(Expression expression)
    {
        if (expression instanceof ContentExpression)
        {
            if (ifExpr != null)
            {
                ifExpr.addContent(expression);
            }
            else
            {
                expressions.add(expression);
            }
        }
        else if (expression instanceof IfExpression)
        {
            if (ifExpr != null)
            {
                throw new RuntimeException("nested #if is not supported");
            }
            ifExpr = (IfExpression) expression;
            expressions.add(expression);
        }
        else if (expression instanceof EndExpression)
        {
            if (ifExpr == null)
            {
                throw new RuntimeException("#end does not match #if");
            }
            ifExpr = null;
        }
    }

    void close()
    {
        if (ifExpr != null)
        {
            throw new RuntimeException("#if not closed");
        }
    }
}
