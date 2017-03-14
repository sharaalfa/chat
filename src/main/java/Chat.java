import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;

import javax.jms.*;
import javax.jms.TextMessage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;



/**
 * Created by tatar on 14.03.17.
 */
public class Chat implements MessageListener {
    Logger logger = Logger.getLogger(Chat.class);

    private static final String APP_TOPIC="chat";

    private Connection connect;
    private Session pubSession;
    private Session subSession;
    private MessageProducer publisher;


    public void chatter(String username) {
        try {
            ConnectionFactory connectionFactory;
            connectionFactory = new ActiveMQConnectionFactory();
            connect = connectionFactory.createConnection();
            pubSession = connect.createSession(false, Session.AUTO_ACKNOWLEDGE);
            subSession = connect.createSession(false, Session.AUTO_ACKNOWLEDGE);

        } catch (JMSException e) {
            logger.error("нет соединения!");
            System.exit(1);
        }

        try {
            Topic topic = pubSession.createTopic(APP_TOPIC);
            MessageConsumer messageConsumer = subSession.createConsumer(topic);
            messageConsumer.setMessageListener(this);
            publisher = pubSession.createProducer(topic);
            connect.start();
        } catch (JMSException e) {
            logger.trace("полная хрень");
        }

        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("введите текст какой-нибудь" + username);
            while (true) {
                String s = bufferedReader.readLine();
                if (s == null) {
                    exit();
                    if (s.length() > 0) {
                        TextMessage msg = pubSession.createTextMessage();
                        msg.setText(s);
                        publisher.send(msg);
                    }

                }
            }

        } catch (IOException e) {
            logger.error("хрень!");
        } catch (JMSException e) {
            logger.error("хрень!");
        }
    }

    public void onMessage(Message aMessage){
        try {
            TextMessage textMessage = (TextMessage) aMessage;
            try {
                String s = textMessage.getText();
                System.out.println(s);
            } catch (JMSException e){
                logger.error("хрень!");
            }
        } catch (RuntimeException e){
            logger.error("хрень!");
        }

    }
    public void exit(){
        try {
            connect.close();
        }
         catch (JMSException e){
            logger.error("чд=рень");
         }
        System.out.println(0);
    }
}
