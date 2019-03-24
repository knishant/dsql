package com.github.knishant.dsql;

import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class TemplateParserTest
{
    @Test
    public void testParser()
    {
        String template = "a b c #if($var1) some content #end";
        JpaQueryBuilderFactory jqbFactory = new JpaQueryBuilderFactory();
        assertEquals("a b c  some content ",
                jqbFactory.create(template).addConditionalParam("var1", true).build());
        assertEquals("a b c ",
                jqbFactory.create(template).addConditionalParam("var1", Optional.empty()).build());
    }
}