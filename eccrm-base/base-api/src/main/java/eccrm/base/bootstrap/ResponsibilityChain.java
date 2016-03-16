package eccrm.base.bootstrap;

/**
 * @author miles
 * @datetime 2014/4/25 11:26
 */
public abstract class ResponsibilityChain {
    private ResponsibilityChain next;

    public abstract Object handle();

    public Object invoke() {
        if (next != null) return next.handle();
        return handle();
    }
}
