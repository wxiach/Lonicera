package cn.wxiach.context;

import cn.wxiach.beans.BeanDefinition;
import cn.wxiach.beans.BeanDefinitionRegistry;

import java.io.File;
import java.net.URL;

/**
 * @author wxiach 2025/1/9
 */
public class ClassPathBeanDefinitionScanner {
    private final BeanDefinitionRegistry registry;
    private final BeanNameGenerator beanNameGenerator;

    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        this.registry = registry;
        this.beanNameGenerator = new DefaultBeanNameGenerator();
    }

    public void scanPackage(String basePackageName) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        findAllClassesInPackage(basePackageName, classLoader);
    }

    private void findAllClassesInPackage(String packageName, ClassLoader classLoader) {
        String searchPath = packageName.replace('.', '/');
        URL url = this.getClass().getClassLoader().getResource(searchPath);
        if (url == null) {
            return;
        }
        File dir = new File(url.getFile());
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                findAllClassesInPackage(packageName + "." + file.getName(), classLoader);
            }
            else {
                String fileName = file.getName();
                if (fileName.endsWith(".class")) {
                    String className = packageName + '.' + fileName.substring(0, fileName.length() - 6);
                    try {
                        Class<?> clazz = classLoader.loadClass(className);
                        BeanDefinition beanDefinition = new BeanDefinition(clazz);
                        this.registry.registerBeanDefinition(beanNameGenerator.generateBeanName(clazz), beanDefinition);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

}
