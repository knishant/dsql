package io.github.knishant.dsql;

import java.util.HashMap;
import java.util.Map;

public final class ExecutionContext
{
    private final Map<String, Object> params = new HashMap<>();

    void addParam(String name, Object value)
    {
        params.put(name, value);
    }

    boolean getBooleanParam(String name)
    {
        Object value = params.get(name);
        if (value == null)
        {
            throw new RuntimeException("boolean param " + name + " is missing");
        }
        if (!(value instanceof Boolean))
        {
            throw new RuntimeException("param " + name + " is not a boolean variable");
        }
        return (Boolean) value;
    }

    Object getParam(String name)
    {
        return params.get(name);
    }

}
