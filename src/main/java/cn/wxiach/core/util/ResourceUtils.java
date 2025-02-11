package cn.wxiach.core.util;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * @author wxiach 2025/1/20
 */
public class ResourceUtils {

    private static final char PACKAGE_SEPARATOR = '.';

    private static final char PATH_SEPARATOR = '/';

    private static final String CLASS_SUFFIX = ".class";

    private static final Path basePath;

    static {
        // Get the default resource path from the class loader
        URL resource = ClassUtils.getDefaultClassLoader().getResource("");

        try {
            basePath = Paths.get(Objects.requireNonNull(resource).toURI()).normalize();
        } catch (URISyntaxException e) {
            throw new IllegalStateException("Invalid URI format for the resource path", e);
        }
    }

    public static Path convertToSearchPath(String packageName) {
        return basePath.resolve(packageName.replace(PACKAGE_SEPARATOR, PATH_SEPARATOR));
    }

    public static boolean checkClassResource(Path path) {
        return path.getFileName().toString().endsWith(CLASS_SUFFIX);
    }

    /**
     * Converts a file path of a compiled class to the corresponding Class resource.
     *
     * This method extracts the class name from the given file path, ensuring it matches
     * the package structure and class name format required for class loading.
     * The file path is expected to be relative to the base path.
     *
     * @param path The file path to the compiled class file (e.g., "com/example/MyClass.class").
     * @return The corresponding Class object representing the class defined in the file.
     * @throws ClassNotFoundException If the class cannot be loaded by the class loader.
     */

    public static Class<?> convertToClassResource(Path path) throws ClassNotFoundException {
        String className = path.toAbsolutePath().toString()
                .substring(basePath.toString().length() + 1)
                .replace(File.separatorChar, PACKAGE_SEPARATOR)
                .replace(CLASS_SUFFIX, "");

        return ClassUtils.getDefaultClassLoader().loadClass(className);
    }
}
