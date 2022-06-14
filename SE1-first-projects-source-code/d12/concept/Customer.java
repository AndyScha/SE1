
import java.util.*;

/**
 * Class for entity type <i>Customer</i>.
 * <p>
 * Customer is an individual (a person) that creates and owns orders.
 * </p>
 * 
 * @version <code style=color:green>{@value application.package_info#Version}</code>
 * @author <code style=color:blue>{@value application.package_info#Author}</code>
 */
public class Customer {

    /**
     * Default constructor
     */
    public Customer() {
    }

    /**
     * Unique Customer id attribute.
     */
    private long id = -1;

    /**
     * Customer's surname attribute.
     */
    private String lastName = "";

    /**
     * None-surname name parts.
     */
    private String firstName = "";

    /**
     * Customer contact information with multiple contacts.
     */
    private List<String> contacts;

}