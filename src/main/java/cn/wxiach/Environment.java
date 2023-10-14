package cn.wxiach;

import java.util.Properties;

/**
 * @author wxiach 2023/10/14
 */
public class Environment {

    public static final String SERVER_PORT = "server.port";
    public static final String SERVER_PORT_DEFAULT = "8080";

    public final Properties properties;

    public Environment() {
        this.properties = new Properties();
    }

    public void parseCommandLineArgs(String[] args) {
        for (String arg : args) {
            String[] kv = arg.substring(2).split("=");
            if (kv.length != 2) {
                throw new IllegalArgumentException("invalid argument: " + arg);
            }
            properties.setProperty(kv[0], kv[1]);
        }
    }

    public int serverPort() {
        return Integer.parseInt(properties.getProperty(SERVER_PORT, SERVER_PORT_DEFAULT));
    }

}
