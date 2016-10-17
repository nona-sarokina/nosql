package jmp.nosql;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nona_Sarokina on 10/17/2016.
 */
public class Runner {

    @Inject
    MongoEditHandler processor;
    public static void main(String[] args) {
        Weld theWeld = new Weld();
        WeldContainer theContainer = theWeld.initialize();
        theContainer.instance().select(Runner.class).get().run();
        theWeld.shutdown();
    }

    private void run() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        loop: do {
            System.out.println("Please choose an option: ");
            System.out.println("1. create");
            System.out.println("2. find");
            System.out.println("3. delete");
            System.out.println("4. exit");

            try
            {
                int input = Integer.valueOf(reader.readLine());
                switch (input) {
                    case 1:
                        create(reader);
                        break;
                    case 2:
                        find(reader);
                        break;
                    case 3:
                        delete(reader);
                        break;
                    case 4:
                        break loop;
                    default:
                        System.out.println("Wrong option, please try again");
                }
            }
            catch (IOException e)
            {
                System.out.println("smth happened");
            }
        } while (true);
    }

    private void create(BufferedReader reader) throws IOException
    {
        Map<String, Object> toCreate = new HashMap<String, Object>();
        boolean isAddingAllowed = true;
        while (isAddingAllowed) {
            System.out.println("Please enter name");
            String name = reader.readLine();
            if (name.isEmpty()) {

                isAddingAllowed = false;
                System.out.println(isAddingAllowed);
                continue;
            }
            System.out.println("Please enter value");
            toCreate.put(name, reader.readLine());
            System.out.println("name is" + name);
        }
        System.out.println("going to create");
        processor.creteDocument(toCreate);
        System.out.println("created");

    }

    private void find(BufferedReader scanner) throws IOException
    {
        System.out.println("Please enter field name to search");
        System.out.println("Please enter name");
        String name = scanner.readLine();
        System.out.println("Please enter value");
        String value = scanner.readLine();
        System.out.println(processor.find(name, value));
    }

    private void delete(BufferedReader scanner) throws IOException
    {
        System.out.println("Please enter the id of element:");
        String id = scanner.readLine();
        processor.delete(id);
    }
}
