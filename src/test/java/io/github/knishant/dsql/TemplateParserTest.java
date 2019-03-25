package io.github.knishant.dsql;

import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TemplateParserTest
{
    @Test
    public void testParser1()
    {
        String template = "a b c #if($var1) content1 #end";
        QueryBuilderFactory jqbFactory = new QueryBuilderFactory();
        assertEquals("a b c  content1 ",
                jqbFactory.create(template)
                        .addConditionalParam("var1", true)
                        .build());
        assertEquals("a b c  content1 ",
                jqbFactory.create(template)
                        .addConditionalParam("var1", Optional.of("abc"))
                        .build());
        assertEquals("a b c ",
                jqbFactory.create(template)
                        .addConditionalParam("var1", Optional.empty())
                        .build());
    }

    @Test
    public void testParser2()
    {
        String template = "a b c #if($var1) content1 #end d e f #if($var2) content2 #end g h";
        QueryBuilderFactory jqbFactory = new QueryBuilderFactory();
        assertEquals("a b c  content1  d e f  content2  g h",
                jqbFactory.create(template)
                        .addConditionalParam("var1", true)
                        .addConditionalParam("var2", true)
                        .build());
        assertEquals("a b c  d e f  content2  g h",
                jqbFactory.create(template)
                        .addConditionalParam("var1", false)
                        .addConditionalParam("var2", true)
                        .build());
        assertEquals("a b c  content1  d e f  content2  g h",
                jqbFactory.create(template)
                        .addConditionalParam("var1", Optional.of("dsf"))
                        .addConditionalParam("var2", Optional.of("abc"))
                        .build());
        assertEquals("a b c  d e f  content2  g h",
                jqbFactory.create(template)
                        .addConditionalParam("var1", Optional.empty())
                        .addConditionalParam("var2", Optional.of("abc"))
                        .build());
        assertEquals("a b c  content1  d e f  g h",
                jqbFactory.create(template)
                        .addConditionalParam("var1", Optional.of("dsf"))
                        .addConditionalParam("var2", Optional.empty())
                        .build());
        assertEquals("a b c  d e f  g h",
                jqbFactory.create(template)
                        .addConditionalParam("var1", Optional.empty())
                        .addConditionalParam("var2", Optional.empty())
                        .build());
    }

    @Test
    public void testParserFailure()
    {
        String template = "a b c #if($var1) some content #end";
        QueryBuilderFactory jqbFactory = new QueryBuilderFactory();
        try
        {
            jqbFactory.create(template)
                    .addConditionalParam("var2", true)
                    .build();
        }
        catch (RuntimeException e)
        {
            assertTrue(e.getMessage().contains("var1"));
        }
    }

    @Test
    public void testParserFailure2()
    {
        String template = "a b c #if($var1) some content #if($var2) content2 #end #end";
        QueryBuilderFactory jqbFactory = new QueryBuilderFactory();
        try
        {
            jqbFactory.create(template)
                    .addConditionalParam("var1", true)
                    .addConditionalParam("var2", true)
                    .build();
        }
        catch (RuntimeException e)
        {
            assertTrue(e.getMessage().contains("nested #if"));
        }
    }

    @Test
    public void testParserFailure3()
    {
        String template = "a b c #if($var1) some content #end #end";
        QueryBuilderFactory jqbFactory = new QueryBuilderFactory();
        try
        {
            jqbFactory.create(template)
                    .addConditionalParam("var1", true)
                    .build();
        }
        catch (RuntimeException e)
        {
            assertTrue(e.getMessage().contains("#end"));
        }
    }

    @Test
    public void testParserFailure4()
    {
        String template = "a b c #if($var1) some content";
        QueryBuilderFactory jqbFactory = new QueryBuilderFactory();
        try
        {
            jqbFactory.create(template)
                    .addConditionalParam("var1", true)
                    .build();
        }
        catch (RuntimeException e)
        {
            assertTrue(e.getMessage().contains("#if not closed"));
        }
    }
}