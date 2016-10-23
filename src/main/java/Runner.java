import jmp.nosql.MongoCLIRunner;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

/**
 * Created by Nona_Sarokina on 10/23/2016.
 */
public class Runner {
    public static void main(String[] args) {
        Weld theWeld = new Weld();
        WeldContainer theContainer = theWeld.initialize();
        theContainer.instance().select(MongoCLIRunner.class).get().run();
        theWeld.shutdown();
    }
}
