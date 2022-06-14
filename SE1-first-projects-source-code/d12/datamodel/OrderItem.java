
import java.util.*;

/**
 * Class of a line item as part of an Order. Orders may have multiple order items.
 * 
 * @version <code style=color:green>{@value application.package_info#Version}</code>
 * @author <code style=color:blue>{@value application.package_info#Author}</code>
 */
public class OrderItem {

    /**
     * Default constructor
     */
    public OrderItem() {
    }

    /**
     * Ordered article, is never null.
     */
    private final Article article;

    /**
     * Number of units ordered, is always a positive number {@code > 0}.
     */
    private int unitsOrdered;

    /**
     * Constructor of ordered line item with article and units arguments.
     * @param article ordered article, throws IllegalArgumentException if article is null
     * @param unitsOrdered number of articles ordered
     */
    protected void OrderItem(Article article, int unitsOrdered) {
        // TODO implement here
    }

    /**
     * Article getter.
     * @return ordered article.
     */
    public Article getArticle() {
        // TODO implement here
        return null;
    }

    /**
     * UnitsOrdered getter.
     * @return number of article ordered.
     */
    public int getUnitsOrdered() {
        // TODO implement here
        return 0;
    }

    /**
     * UnitsOrdered setter.
     * @param units units updated number of articles ordered, must be {@code >= 0}.
     * @throws IllegalArgumentException if units not a positive {@code units >0} number.
     */
    public void setUnitsOrdered(int units) {
        // TODO implement here
    }

}