package cn.wxiach.template;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wxiach 2023/11/3
 */
public class ModelAndView {

    private String view;

    private Map<String, Object> model;

    public ModelAndView(String view) {
        this.view = view;
        this.model = new HashMap<>();
    }

    public ModelAndView(String view, Map<String, Object> model) {
        this.view = view;
        this.model = model;
    }

    public String getView() {
        return view;
    }

    public Map<String, Object> getModel() {
        return model;
    }
}

