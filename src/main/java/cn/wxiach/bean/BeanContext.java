package cn.wxiach.bean;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wxiach 2023/10/12
 */
public class BeanContext {

    private final String basePackageName;
    private final Map<String, BeanDefinition> beans = new ConcurrentHashMap<>(256);


    public BeanContext(String basePackageName) {
        this.basePackageName = basePackageName;
    }

    public List<BeanDefinition> scanPackage() {
        String basePackageSearchPath = convertPackageNameToPath(this.basePackageName);

        // Find all class in base package
        List<Class<?>> candidates = new ArrayList<>();
        findAllClass(new File(basePackageSearchPath), candidates);

        candidates.forEach(this::createBeanDefinition);

        return new ArrayList<>(beans.values());
    }

    private void findAllClass(File folder, List<Class<?>> classes) {
        for (File file : Objects.requireNonNull(folder.listFiles())) {
            if (file.isFile() && file.getName().endsWith(".class")) {
                try {
                    classes.add(Class.forName(extractClassNameFromPath(file.getPath())));
                } catch (ClassNotFoundException e) {
                    throw new BeansException("The class of [" + file.getPath() + "] not found", e);
                }
            } else {
                findAllClass(file, classes);
            }
        }
    }

    private void createBeanDefinition(Class<?> clazz) {
        try {
            Object bean = clazz.getDeclaredConstructor().newInstance();
            beans.put(clazz.getName(), new BeanDefinition(bean, clazz));
        } catch (Exception e) {
            throw new BeansException("Create bean instance failed", e);
        }
    }

    private String convertPackageNameToPath(String packageName) {
        var classLoader = BeanContext.class.getClassLoader();
        var resource = classLoader.getResource(packageName.replace(".", "/"));
        if (Objects.isNull(resource)) {
            throw new BeansException("The package [" + packageName + "] not found");
        }
        return resource.getPath();
    }

    private String extractClassNameFromPath(String path) {
        String className = path.replace(File.separator, ".");
        return className.substring(className.indexOf(basePackageName), className.lastIndexOf("."));
    }
}
