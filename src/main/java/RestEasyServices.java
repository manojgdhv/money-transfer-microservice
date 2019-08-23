import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class RestEasyServices extends Application {
    private Set<Object> singletons;

    public RestEasyServices() {
        singletons = new HashSet<Object>();
        singletons.add(new MoneyTransferController());
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}