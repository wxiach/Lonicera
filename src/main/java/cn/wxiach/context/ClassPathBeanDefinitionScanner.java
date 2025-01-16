package cn.wxiach.context;

import cn.wxiach.annotations.Component;
import cn.wxiach.annotations.Scope;
import cn.wxiach.beans.BeanDefinition;
import cn.wxiach.beans.BeanDefinitionRegistry;
import cn.wxiach.context.util.AnnotationConfigUtils;
import cn.wxiach.context.util.BeanNameGenerator;
import cn.wxiach.core.util.AnnotationUtils;
import cn.wxiach.core.util.ClassUtils;
import cn.wxiach.core.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Objects;

/**
 * @author wxiach 2025/1/9
 */
public class ClassPathBeanDefinitionScanner {

    private static final char PACKAGE_SEPARATOR = '.';

    private static final char PATH_SEPARATOR = '/';

    private static final String CLASS_SUFFIX = ".class";

    private final BeanDefinitionRegistry registry;

    private final ClassLoader classLoader;

    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        this.registry = registry;
        this.classLoader = ClassUtils.getDefaultClassLoader();
        AnnotationConfigUtils.registerAnnotationConfigProcessors(registry);
    }

    public void scanPackage(String basePackageName) {
        doScanPackage(basePackageName);
    }

    protected void doScanPackage(String basePackageName) {
        Path basePath = convertToSearchPath(basePackageName);
        try {
            Files.walkFileTree(basePath, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) {
                    Class<?> clazz = convertToClassResourceIfPossible(path, basePackageName);
                    if (clazz != null) {
                        registerBeanDefinitionIfPossible(clazz);
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            throw new BeanDefinitionStoreException("Failed to scan package: " + basePackageName, e);
        }
    }

    private Path convertToSearchPath(String packageName) {
        try {
            String searchPath = packageName.replace(PACKAGE_SEPARATOR, PATH_SEPARATOR);
            return Paths.get(Objects.requireNonNull(classLoader.getResource(searchPath)).toURI());
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Resource not found for package: " + packageName, e);
        }
    }

    private Class<?> convertToClassResourceIfPossible(Path path, String packageName) {
        if (!path.getFileName().toString().endsWith(CLASS_SUFFIX)) {
            return null;
        }
        String className = path
                .subpath(path.getNameCount() - packageName.split("\\.").length - 1, path.getNameCount())
                .toString()
                .replace(File.separatorChar, PACKAGE_SEPARATOR)
                .replace(CLASS_SUFFIX, "");

        try {
            return classLoader.loadClass(className);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    private void registerBeanDefinitionIfPossible(Class<?> beanClass) {
        BeanDefinition beanDefinition = new BeanDefinition(beanClass);

        // parse @Component
        Component component = AnnotationUtils.findAnnotation(beanClass, Component.class);
        if (component == null) return;

        String beanName = BeanNameGenerator.generate(beanClass, component);
        if (registry.containsBeanDefinition(beanName)) return;

        // parse @Scope
        Scope scope = AnnotationUtils.findAnnotation(beanClass, Scope.class);
        if (scope != null && StringUtils.hasText(scope.value())) {
            beanDefinition.setScope(scope.value());
        }

        this.registry.registerBeanDefinition(beanName, beanDefinition);
    }
}
