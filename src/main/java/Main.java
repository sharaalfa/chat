import java.util.Scanner;

/**
 * Created by tatar on 14.03.17.
 */
public class Main {
    public static void main(String[] argv){

        Chat chat = new Chat();
        chat.chatter("artur");

        final String nick;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Write your nickname!");
        if(scanner.hasNextLine()){
            nick=scanner.nextLine();
            Thread thread = new Thread(new Consumer());
            thread.start();
            Thread prodThread = new Thread(new Producer(nick));
            prodThread.start();
        }


    }
    public static void printUser(){
        StringBuffer use = new StringBuffer();
        System.err.println(use);
    }
}
