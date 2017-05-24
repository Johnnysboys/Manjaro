import com.paypal.api.payments.CreditCard;
import electroshop.connectors.PayPalConnector;

/**
 * Created by thesinding on 5/18/17.
 */
public class testPaypal {
    public static void main(String[] args) {
        
       boolean passed = PayPalConnector.validateCard("visa", "4417119669820331", 11,2019,012,"John", "Doe");
        if(passed){
            System.out.println("Yeah boysss");
        } else {
            System.out.println("Invalid card");
        }
    }
}
