package io.github.knishant.dsql;

import java.util.HashMap;
import java.util.Map;

final class ExecutionContext
{
    private final Map<String, Boolean> params = new HashMap<>();

    void addParam(String name, boolean value)
    {
        params.put(name, value);
    }

    boolean getBooleanParam(String name)
    {
        Boolean value = params.get(name);
        if (value == null)
        {
            throw new RuntimeException("boolean param " + name + " is missing");
        }
        return value;
    }
}
