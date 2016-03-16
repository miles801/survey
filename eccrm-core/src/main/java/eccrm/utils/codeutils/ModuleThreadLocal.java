package eccrm.utils.codeutils;

/**
 * @author miles
 * @datetime 13-12-24 下午12:00
 */
public class ModuleThreadLocal {
    private static ThreadLocal<Module> module = new ThreadLocal<Module>();

    public static void setModule(Module m) {
        module.set(m);
    }

    public static Module getModule() {
        return module.get();
    }

    public static void remove() {
        module.remove();
    }
}
