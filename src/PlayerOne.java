import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Random;
import java.util.Scanner;

public class PlayerOne {
    public static void main(String[] args)
    {
        //Player One can choose if he/she starts by themselves or lets the computer go
        System.out.println("Which one would you like to choose?(Type the number)");
        System.out.println("1) I enter my own number");
        System.out.println("2) Computer shall go automatically");
        Scanner in = new Scanner(System.in);
        int choiceNumber = in.nextInt();
        //Sending number has to be declared here, until it can be initialized either by the user or by the machine
        int sendingNumber;
        if(choiceNumber == 1)
        {
            System.out.println("Enter a whole number between 2 to 100");
            sendingNumber = in.nextInt();
        }
        else {
            Random random = new Random();
            sendingNumber = random.nextInt(100);
        }
        //Now, a connection is established and the first number is sent to Player Two
        try{
            DatagramSocket datagramSocket = new DatagramSocket(5557);
            //This loop continues sending and receiving data until the game finishes
            do {
                //Sending data
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                byteBuffer.order(ByteOrder.BIG_ENDIAN);
                byteBuffer.putInt(sendingNumber);
                DatagramPacket datagramPacket = new DatagramPacket(byteBuffer.array(), byteBuffer.position(),
                        InetAddress.getLocalHost(), 5558);
                datagramSocket.send(datagramPacket);
                //Receiving data
                ByteBuffer byteBufferResponse = ByteBuffer.allocate(1024);
                byteBufferResponse.order(ByteOrder.BIG_ENDIAN);
                DatagramPacket datagramPacketRes = new DatagramPacket(byteBufferResponse.array(),
                        byteBufferResponse.array().length);
                datagramSocket.receive(datagramPacketRes);
                int receivedNumber = byteBufferResponse.getInt();
                //Here, the received number from Player Two is checked to see if he has already won
                //This checking is important in order to finish the connection in case Player Two has won the game
                if (receivedNumber == 0)
                {
                    System.out.println("Player One lost the game!");
                    return;
                }
                else {
                    //Processing the received number according to rules of the game
                    if ((receivedNumber % 3) == 0) {
                        System.out.println(receivedNumber + " + 0 = " + receivedNumber);
                        sendingNumber = receivedNumber / 3;
                        System.out.println("Sending number is " + (receivedNumber / 3));
                        System.out.println("************");
                    } else if ((receivedNumber % 3) == 1) {
                        System.out.println(receivedNumber + " - 1 = " + (receivedNumber - 1));
                        sendingNumber = (receivedNumber - 1) / 3;
                        System.out.println("Sending number is " + ((receivedNumber - 1) / 3));
                        System.out.println("************");
                    } else {
                        System.out.println(receivedNumber + " + 1 = " + (receivedNumber + 1));
                        sendingNumber = (receivedNumber + 1) / 3;
                        System.out.println("Sending number is " + ((receivedNumber + 1) / 3));
                        System.out.println("************");
                    }
                }
            }while (sendingNumber > 1);
            //Leaving the loop means that Player One has won the game
            System.out.println("End of the game: Player One won!");
            //So, number zero is sent to Player Two to signal him that he should stop waiting for new numbers
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            byteBuffer.order(ByteOrder.BIG_ENDIAN);
            byteBuffer.putInt(0);
            DatagramPacket datagramPacket = new DatagramPacket(byteBuffer.array(), byteBuffer.position(),
                    InetAddress.getLocalHost(), 5558);
            datagramSocket.send(datagramPacket);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
