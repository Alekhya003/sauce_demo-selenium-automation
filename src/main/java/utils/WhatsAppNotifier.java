package utils;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class WhatsAppNotifier {
    public static final String TWILIO_ACCOUNT_SID = System.getenv("TWILIO_ACCOUNT_SID");
    public static final String TWILIO_AUTH_TOKEN = System.getenv("TWILIO_AUTH_TOKEN");

    static {
        if (TWILIO_ACCOUNT_SID == null || TWILIO_AUTH_TOKEN == null) {
            throw new IllegalStateException(
                    "Twilio credentials not set. Please set TWILIO_ACCOUNT_SID and TWILIO_AUTH_TOKEN as environment variables."
            );
        }
        Twilio.init(TWILIO_ACCOUNT_SID, TWILIO_AUTH_TOKEN);
    }
    private static final String TWILIO_WHATSAPP_NUMBER = "whatsapp:+14155238886";
    public static void sendMessage(String toNumber, String body) {
        Twilio.init(TWILIO_ACCOUNT_SID, TWILIO_AUTH_TOKEN);

        Message message = Message.creator(
                new PhoneNumber("whatsapp:" + toNumber),
                new PhoneNumber(TWILIO_WHATSAPP_NUMBER),
                body
        ).create();

        System.out.println("✅ WhatsApp Message Sent: " + message.getSid());
    }
}
