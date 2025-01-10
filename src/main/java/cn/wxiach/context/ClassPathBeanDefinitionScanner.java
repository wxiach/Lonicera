package cn.wxiach.context;

import cn.wxiach.annotations.Component;
import cn.wxiach.annotations.Scope;
import cn.wxiach.beans.BeanDefinition;
import cn.wxiach.beans.BeanDefinitionRegistry;
import cn.wxiach.utils.ClassUtils;
import cn.wxiach.utils.StringUtils;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Objects;
import java.util.Optional;

/**
 * @author wxiach 2025/1/9
 */
public class ClassPathBeanDefinitionScanner {
    private final BeanDefinitionRegistry registry;

    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public void scanPackage(String basePackageName) {
        Path path = convertToSearchPath(basePackageName);
        if (Files.notExists(path) || !Files.isDirectory(path)) return;

        try {
            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if (file.toString().endsWith(".class")) {
                        processClassFile(file, basePackageName);
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            throw new BeanDefinitionStoreException("Error while walking path: " + path, e);
        }
    }

    private Path convertToSearchPath(String packageName) {
        try {
            String searchPath = packageName.replace('.', '/');
            URI uri = Optional.of(Objects.requireNonNull(ClassUtils.getDefaultClassLoader().getResource(searchPath)).toURI())
                    .orElseThrow(() -> new BeanDefinitionStoreException("Resource not found for package: " + packageName));
            return Paths.get(uri);
        } catch (URISyntaxException e) {
            throw new BeanDefinitionStoreException("URISyntaxException while walking the directory tree for package: " + packageName, e);
        }
    }

    private void processClassFile(Path file, String packageName) {
        String  className = file.toString().replace(File.separatorChar, '.');
        className = className.substring(className.indexOf(packageName), className.length() - 6);
        try {
            Class<?> clazz = ClassUtils.getDefaultClassLoader().loadClass(className);
            if (!clazz.isAnnotationPresent(Component.class)) return;
            BeanDefinition beanDefinition = new BeanDefinition(clazz);
            parseScopeOfBeanDefinition(beanDefinition);
            this.registry.registerBeanDefinition(BeanNameGenerator.generate(clazz), beanDefinition);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void parseScopeOfBeanDefinition(BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        if (beanClass.isAnnotationPresent(Scope.class)) {
            String scope = beanClass.getAnnotation(Scope.class).value();
            if (StringUtils.hasText(scope)) beanDefinition.setScope(scope);
        }
        if (!StringUtils.hasText(beanDefinition.getScope())) beanDefinition.setScope(BeanDefinition.SCOPE_SINGLETON);
    }

}
