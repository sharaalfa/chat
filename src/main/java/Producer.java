

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.Scanner;


public class Producer implements Runnable {
    private String nickname;
    public Producer(String nickname){
        this.nickname=nickname;
    }


    public void run() {
        ActiveMQConnectionFactory factory =
                new ActiveMQConnectionFactory("tcp://localhost:61616");
        try {
            Connection myConnection = factory.createConnection();
            myConnection.start();
            Session session = myConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue("Dest");
            MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);


           // TextMessage textMessage = session.createTextMessage("Hello world!");
            Scanner scanner  = new Scanner(System.in);
            String message=scanner.nextLine();
            while (!message.equals("exit")){
                TextMessage textMessage = session.createTextMessage(message);
                producer.send(textMessage);
                message=scanner.nextLine();
            }
            //producer.send(textMessage);
            session.close();
            myConnection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}