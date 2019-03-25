# dsql

Library to create dynamic sql / jpa query.

Instead of creating dynamic sql by conditional string concatenation, one can add conditional template sections in the query.

Here is an example of quering for students given the part of student name 
and / or part of her department name.

```java
    private final QueryBuilderFactory qbFactory = new QueryBuilderFactory();

    private static final String QUERY_TEMPLATE = "SELECT s FROM StudentEntity AS s\n" +
            "#if($hasDepName)JOIN DepartmentEntity AS de ON s.depId = de.id #end\n" +
            "WHERE 1=1\n" +
            "#if($hasDepName)AND de.name like :depName #end\n" +
            "#if($hasName)AND s.name like :name #end\n" +
            "order by s.name";

    public List<StudentEntity> search(String studentName, String departmentName)
    {
        Optional<String> name = trim(studentName);
        Optional<String> depName = trim(departmentName);
        String queryString = qbFactory.create(QUERY_TEMPLATE)
                .addConditionalParam("hasName", name)
                .addConditionalParam("hasDepName", depName)
                .build();

        TypedQuery<StudentEntity> query = em.createQuery(queryString, StudentEntity.class);
        name.ifPresent(s -> query.setParameter("name", s + "%"));
        depName.ifPresent(s -> query.setParameter("depName", s + "%"));
        return query.getResultList();
    }
```

Code 

  * Create a class level instance of QueryBuilderFactory. 
    * Factory caches the parsed template.
    * Factory is thread safe.
  * Create a query template
    * query is a regular jpa query with some template contents like #if($hasDepName) and #end
    * if the conditional param hasDepName is set to true then the content within
     #if($hasDepName) and #end is part of the generated dynamic query.
     if not then that content is omitted.
    * 1=1 is required as both the where conditions might be omitted
  * Create Optional instances from the params.
    * here trim is just a utility function which trims the string and return Optional of that string.
  * Create queryBuilder from the factory
  * Set all the template variables
    * Here Optional instances are set but boolean values could have been used instead.
  * Build the dynamic query
  * Rest is just a sample of creating JPA Typed Query
    * Note how using optional made setting params easier. 
    
Resulting queryString based on the value of the two params.

hasName: true, hasDepName: true
```
SELECT s FROM StudentEntity AS s
JOIN DepartmentEntity AS de ON s.depId = de.id 
WHERE 1=1
AND de.name like :depName 
AND s.name like :name 
order by s.name
```

hasName: false, hasDepName: false
```
SELECT s FROM StudentEntity AS s

WHERE 1=1


order by s.name
```
This is where you need the condition 1=1, otherwise the query will be incorrect.

hasName: true, hasDepName: false
```
SELECT s FROM StudentEntity AS s

WHERE 1=1

AND s.name like :name 
order by s.name
```

hasName: false, hasDepName: true
```
SELECT s FROM StudentEntity AS s
JOIN DepartmentEntity AS de ON s.depId = de.id 
WHERE 1=1
AND de.name like :depName 

order by s.name
```

Rules

  * #If conditions cannot be nested. ie #end must appear before the next #if starts.
  * All the variables mentioned in #if statement must be set before building the query
  * A variable may appear multiple times in a query template, like hasDepName
  * Variable can be named anything, need not be a boolean name starting with has or is

