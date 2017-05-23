package electroshop.connectors;

import com.paypal.api.payments.CreditCard;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

/**
 * Created by thesinding on 5/18/17.
 */
public class PayPalConnector {
    private static String clientId = "AYSq3RDGsmBLJE-otTkBtM-jBRd1TCQwFf9RGfwddNXWz0uFU9ztymylOhRS";
    private static String clientSecret = "EGnHDxD_qRPdaLdZz8iCr8N7_MzF-YHPTkjs6NKYQvQSBngp4PTTVWkPZRbL";

    public PayPalConnector() {

    }

    public static boolean validateCard(CreditCard card){
        try {
            APIContext context = new APIContext(clientId, clientSecret, "sandbox");
            card.create(context);
            System.out.println(card);
            return true;
        } catch (PayPalRESTException e) {
            System.err.println(e.getDetails());
            return false;
        }
    }

    public static CreditCard createCard(String type, String cardNumber, int expMonth, int expYear, int CCV, String firstName, String lastName){
        return new CreditCard()
                .setType(type)
                .setNumber(cardNumber)
                .setFirstName(firstName)
                .setLastName(lastName)
                .setExpireMonth(expMonth)
                .setExpireYear(expYear)
                .setCvv2(CCV);
    }
}
