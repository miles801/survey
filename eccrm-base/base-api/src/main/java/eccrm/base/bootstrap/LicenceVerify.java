package eccrm.base.bootstrap;

/**
 * @author miles
 * @datetime 2014/4/25 11:36
 */
public class LicenceVerify extends ResponsibilityChain {
    @Override
    public Object handle() {
        return invoke();
    }
}
