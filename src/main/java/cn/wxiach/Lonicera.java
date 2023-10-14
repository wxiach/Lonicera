package cn.wxiach;


import cn.wxiach.bean.BeanContext;
import cn.wxiach.route.RoutesRegistrationDelegate;
import cn.wxiach.server.Server;

/**
 * @author wxiach 2023/10/5
 */
public class Lonicera {

    private final Environment environment = new Environment();
    private final BeanContext beanContext;
    private final Server server = new Server();


    private Lonicera(Class<?> applicationMainClass) {
        this.beanContext = new BeanContext(applicationMainClass.getPackageName());
    }

    /**
     * Run the application
     *
     * @param applicationMainClass The main class of the application
     * @param args                 Command line arguments
     * @return Lonicera
     */
    public static Lonicera run(Class<?> applicationMainClass, String... args) {
        return new Lonicera(applicationMainClass).run(args);
    }

    private Lonicera run(String... args) {
        this.environment.parseCommandLineArgs(args);
        registerRoutes();
        this.server.run(this.environment.serverPort());
        return this;
    }

    private void registerRoutes() {
        RoutesRegistrationDelegate.registerRoutes(this.beanContext);
    }
}