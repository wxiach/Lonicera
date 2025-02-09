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
        URL resource = ClassUtils.getDefaultClassLoader().getResource("");
        try {
            basePath = Paths.get(Objects.requireNonNull(resource).toURI()).normalize();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static Path convertToSearchPath(String packageName) {
        return basePath.resolve(packageName.replace(PACKAGE_SEPARATOR, PATH_SEPARATOR));
    }

    public static boolean checkClassResource(Path path) {
        return path.getFileName().toString().endsWith(CLASS_SUFFIX);
    }

    public static Class<?> convertToClassResource(Path path) throws ClassNotFoundException {
        String className = path.toAbsolutePath().toString()
                .substring(basePath.toString().length() + 1)
                .replace(File.separatorChar, PACKAGE_SEPARATOR)
                .replace(CLASS_SUFFIX, "");

        return ClassUtils.getDefaultClassLoader().loadClass(className);
    }
}
