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
        System.out.println("Which one would you like to choose?(Type the number)");
        System.out.println("1) I enter my own number");
        System.out.println("2) Computer shall go automatically");
        Scanner in = new Scanner(System.in);
        int choiceNumber = in.nextInt();
        int sendingNumber = 0;
        if(choiceNumber == 1)
        {
            System.out.println("Enter a whole number between 1 to 100");
            sendingNumber = in.nextInt();
        }
        else {
            Random random = new Random();
            sendingNumber = random.nextInt(100);
        }
        try{
            DatagramSocket datagramSocket = new DatagramSocket(5557);
            do {
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                byteBuffer.order(ByteOrder.BIG_ENDIAN);
                byteBuffer.putInt(sendingNumber);
                DatagramPacket datagramPacket = new DatagramPacket(byteBuffer.array(), byteBuffer.position(),
                        InetAddress.getLocalHost(), 5558);
                datagramSocket.send(datagramPacket);

                ByteBuffer byteBufferResponse = ByteBuffer.allocate(1024);
                byteBufferResponse.order(ByteOrder.BIG_ENDIAN);
                DatagramPacket datagramPacketRes = new DatagramPacket(byteBufferResponse.array(),
                        byteBufferResponse.array().length);
                datagramSocket.receive(datagramPacketRes);
                int receivedNumber = byteBufferResponse.getInt();
                if (receivedNumber == 0)
                {
                    System.out.println("Player One lost the game!");
                    return;
                }
                else {
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
            System.out.println("End of the game: Player One won!");
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
