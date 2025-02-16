# imitate-spring

A lightweight framework that imitates Spring, implementing IoC (Inversion of Control) and AOP (Aspect-Oriented Programming).

## Features

+ Register bean definitions with `@Component` and use `@ComponentScan` to scan all component classes within a specified package.
+ Register bean definitions via java configuration, annotated with `@Configuration` and `@Bean`.
+ Use `AnnotatedConfigApplicaionContext` to create a container, and call `refresh()` to complete the initialization.
+ Implement five types of AOP advice: `@Before`, `@After`, `@Around`, `@AfterReturning`, and `@AfterThrowing`, with the advices being executed in a predefined order.

## Special Considerations


## Conclusion


## Environment

