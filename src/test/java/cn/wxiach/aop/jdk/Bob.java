package cn.wxiach.aop.jdk;

import cn.wxiach.context.annotation.Component;

/**
 * @author wxiach 2025/1/22
 */
@Component
public class Bob implements People {

    @Override
    public void doSomething() {
        System.out.println("Doing something...");
    }

    @Override
    public String doSomethingElse(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }
        return "Processed: " + input;
    }
}
