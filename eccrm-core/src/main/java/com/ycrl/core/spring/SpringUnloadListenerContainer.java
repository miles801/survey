package com.ycrl.core.spring;

import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.List;

/**
 * 用于存放
 *
 * @author Michael
 */
public class SpringUnloadListenerContainer {
    private static final SpringUnloadListenerContainer instances = new SpringUnloadListenerContainer();;

    private List<SpringUnloadListener> listeners = new ArrayList<SpringUnloadListener>();

    private SpringUnloadListenerContainer() {
    }

    public static final SpringUnloadListenerContainer getInstances() {
        return instances;
    }

    public void setListeners(List<SpringUnloadListener> listeners) {
        this.listeners = listeners;
    }

    public List<SpringUnloadListener> getListeners() {
        return listeners;
    }

    public void add(SpringUnloadListener listener) {
        listeners.add(listener);
    }


    /**
     * 调用所有被注册的监听器
     *
     * @param context 上下文
     */
    public void execute(ServletContext context) {
        System.out.println("监听器的数量：" + listeners.size());
        for (SpringUnloadListener listener : listeners) {
            listener.execute(context);
        }
    }


}
