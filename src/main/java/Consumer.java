import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;




/**
 * Created by tatar on 14.03.17.
 */
public class Consumer implements Runnable{
    public void run() {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");

        try{
            Connection mconnection;
            mconnection = factory.createConnection();
            mconnection.start();
            Session session = mconnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue("Dest");
            MessageConsumer messageConsumer = session.createConsumer(destination);
            Message message = messageConsumer.receive(10);
            System.out.println(((TextMessage )message).getText());
            session.close();
            mconnection.close();
        } catch (JMSException  e){
            e.printStackTrace();
        }
    }
}
