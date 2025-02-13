package cn.wxiach.context.annotation;

import cn.wxiach.beans.BeanFactory;
import cn.wxiach.beans.BeanFactoryAware;
import cn.wxiach.beans.config.BeanDefinition;
import cn.wxiach.beans.support.DefaultListableBeanFactory;
import net.sf.cglib.core.ClassGenerator;
import net.sf.cglib.core.Constants;
import net.sf.cglib.core.DefaultGeneratorStrategy;
import net.sf.cglib.proxy.*;
import net.sf.cglib.transform.ClassEmitterTransformer;
import net.sf.cglib.transform.TransformingClassGenerator;
import org.objectweb.asm.Type;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author wxiach 2025/1/13
 */
public class ConfigurationClassEnhancer {

    private static final Callback[] CALLBACKS = new Callback[]{
            new BeanMethodInterceptor(), new BeanFactoryAwareMethodInterceptor(), NoOp.INSTANCE
    };

    private static final ConditionalCallbackFilter CALLBACK_FILTER = new ConditionalCallbackFilter(CALLBACKS);

    private static final String BEAN_FACTORY_FIELD = "$$beanFactory";

    public void enhance(BeanDefinition beanDefinition) {
        // create enhancer
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(beanDefinition.getBeanClass());
        enhancer.setInterfaces(new Class[]{EnhancedConfiguration.class});
        enhancer.setUseFactory(false);
        enhancer.setStrategy(new BeanFactoryAwareGeneratorStrategy());
        enhancer.setCallbackFilter(CALLBACK_FILTER);
        enhancer.setCallbackTypes(CALLBACK_FILTER.getCallbackTypes());

        // create enhanced config class
        Class<?> enhancedConfigClass = enhancer.createClass();
        Enhancer.registerStaticCallbacks(enhancedConfigClass, CALLBACKS);

        // update BeanDefinition's class
        beanDefinition.setBeanClass(enhancedConfigClass);
    }

    private static class BeanFactoryAwareMethodInterceptor implements MethodInterceptor, ConditionalCallback {
        @Override
        public Object intercept(Object enhancedConfigInstance, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
            Field field = enhancedConfigInstance.getClass().getDeclaredField(BEAN_FACTORY_FIELD);
            field.set(enhancedConfigInstance, args[0]);
            return null;
        }

        @Override
        public boolean isMatch(Method method) {
            return isSetBeanFactory(method);
        }

        public static boolean isSetBeanFactory(Method method) {
            return (method.getName().equals("setBeanFactory") &&
                    method.getParameterCount() == 1 &&
                    BeanFactory.class == method.getParameterTypes()[0] &&
                    BeanFactoryAware.class.isAssignableFrom(method.getDeclaringClass()));
        }
    }

    private static class BeanMethodInterceptor implements MethodInterceptor, ConditionalCallback {

        @Override
        public Object intercept(Object enhancedConfigInstance, Method beanMethod, Object[] beanMethodArgs,
                                MethodProxy cglibMethodProxy) throws Throwable {

            DefaultListableBeanFactory beanFactory = getBeanFactory(enhancedConfigInstance);
            return beanFactory.getBean(beanMethod.getName());
        }

        @Override
        public boolean isMatch(Method beanMethod) {
            return (beanMethod.getDeclaringClass() != Object.class &&
                    !BeanFactoryAwareMethodInterceptor.isSetBeanFactory(beanMethod) &&
                    beanMethod.isAnnotationPresent(Bean.class));
        }

        private DefaultListableBeanFactory getBeanFactory(Object enhancedConfigInstance)
                throws NoSuchFieldException, IllegalAccessException {

            Field field = enhancedConfigInstance.getClass().getField(BEAN_FACTORY_FIELD);
            return (DefaultListableBeanFactory) field.get(enhancedConfigInstance);
        }
    }
    public interface EnhancedConfiguration extends BeanFactoryAware {
    }

    private interface ConditionalCallback extends Callback {
        boolean isMatch(Method method);
    }

    private static class ConditionalCallbackFilter implements CallbackFilter {

        private final Callback[] callbacks;
        private final Class<?>[] callbackTypes;

        public ConditionalCallbackFilter(Callback[] callbacks) {
            this.callbacks = callbacks;
            this.callbackTypes = Arrays.stream(callbacks).map(Callback::getClass).toArray(Class[]::new);
        }

        @Override
        public int accept(Method method) {
            for (int i = 0; i < this.callbacks.length; i++) {
                Callback callback = this.callbacks[i];
                if (!(callback instanceof ConditionalCallback) || ((ConditionalCallback) callback).isMatch(method)) {
                    return i;
                }
            }
            throw new IllegalStateException("No callback available for method " + method.getName());
        }

        public Class<?>[] getCallbackTypes() {
            return callbackTypes;
        }
    }

    /**
     * Custom extension of CGLIB's DefaultGeneratorStrategy, introducing a BeanFactory field.
     */
    private static class BeanFactoryAwareGeneratorStrategy extends DefaultGeneratorStrategy {
        @Override
        protected ClassGenerator transform(ClassGenerator cg) throws Exception {
            ClassEmitterTransformer transformer = new ClassEmitterTransformer() {
                @Override
                public void end_class() {
                    declare_field(Constants.ACC_PUBLIC, BEAN_FACTORY_FIELD, Type.getType(BeanFactory.class), null);
                    super.end_class();
                }
            };
            return new TransformingClassGenerator(cg, transformer);
        }
    }


}
