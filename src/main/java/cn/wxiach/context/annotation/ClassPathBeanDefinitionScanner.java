package cn.wxiach.context.annotation;

import cn.wxiach.beans.BeanDefinitionStoreException;
import cn.wxiach.beans.config.BeanDefinition;
import cn.wxiach.beans.support.BeanDefinitionRegistry;
import cn.wxiach.beans.support.BeanDefinitionUtils;
import cn.wxiach.core.annotation.AnnotationMetadata;
import cn.wxiach.core.annotation.AnnotationMetadataReader;
import cn.wxiach.core.util.ResourceUtils;
import cn.wxiach.core.util.StringUtils;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * @author wxiach 2025/1/9
 */
public class ClassPathBeanDefinitionScanner {

    private final BeanDefinitionRegistry registry;

    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public void scan(String basePackageName) {
        doScan(basePackageName);
        AnnotationConfigUtils.registerAnnotationConfigProcessors(registry);
    }

    protected void doScan(String basePackageName) {
        Path basePath = ResourceUtils.convertToSearchPath(basePackageName);
        try {
            Files.walkFileTree(basePath, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) {
                    if (ResourceUtils.checkClassResource(path)) {
                        Class<?> clazz;
                        try {
                            clazz = ResourceUtils.convertToClassResource(path);
                        } catch (ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                        registerBeanDefinitionIfPossible(clazz);
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            throw new BeanDefinitionStoreException("Failed to scan package: " + basePackageName, e);
        }
    }

    private void registerBeanDefinitionIfPossible(Class<?> beanClass) {
        BeanDefinition beanDefinition = new BeanDefinition(beanClass);

        AnnotationMetadata metadata = AnnotationMetadataReader.getAnnotationMetadata(beanClass);

        // Register only bean annotated with @Component
        if (!metadata.hasAnnotation(Component.class)) return;

        String beanName = BeanDefinitionUtils.generateBeanName(beanClass, metadata.findAnnotation(Component.class));

        if (registry.containsBeanDefinition(beanName)) return;

        // Parse @Scope
        if (metadata.hasAnnotation(Scope.class)) {
            Scope scope = metadata.findAnnotation(Scope.class);
            if (StringUtils.hasText(scope.value())) {
                beanDefinition.setScope(scope.value());
            }
        }

        this.registry.registerBeanDefinition(beanName, beanDefinition);
    }
}
