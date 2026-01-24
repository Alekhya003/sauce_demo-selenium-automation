package utils;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class WhatsAppNotifier {

    private static final String TWILIO_ACCOUNT_SID = System.getenv("TWILIO_ACCOUNT_SID");
    private static final String TWILIO_AUTH_TOKEN = System.getenv("TWILIO_AUTH_TOKEN");
    private static final String TWILIO_WHATSAPP_NUMBER = "whatsapp:+14155238886";

    public static void sendMessage(String toNumber, String body) {

        if (TWILIO_ACCOUNT_SID == null || TWILIO_AUTH_TOKEN == null) {
            System.err.println("⚠️ Twilio credentials not set. Skipping WhatsApp notification.");
            return;   // ✅ VALID → inside method
        }

        try {
            Twilio.init(TWILIO_ACCOUNT_SID, TWILIO_AUTH_TOKEN);

            Message message = Message.creator(
                    new PhoneNumber("whatsapp:" + toNumber),
                    new PhoneNumber(TWILIO_WHATSAPP_NUMBER),
                    body
            ).create();

            System.out.println("✅ WhatsApp Message Sent: " + message.getSid());

        } catch (Exception e) {
            System.err.println("⚠️ WhatsApp notification failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
