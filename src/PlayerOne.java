import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Random;

public class PlayerOne {
    public static void main(String[] args)
    {
        Random random = new Random();
        try{
            DatagramSocket datagramSocket = new DatagramSocket(5557);
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            byteBuffer.order(ByteOrder.BIG_ENDIAN);
            byteBuffer.putInt(random.nextInt(100));
            DatagramPacket datagramPacket = new DatagramPacket(byteBuffer.array(), byteBuffer.position(),
                    InetAddress.getLocalHost(), 5558);
            datagramSocket.send(datagramPacket);

            ByteBuffer byteBufferResponse = ByteBuffer.allocate(1024);
            byteBufferResponse.order(ByteOrder.BIG_ENDIAN);
            DatagramPacket datagramPacketRes = new DatagramPacket(byteBufferResponse.array(),
                    byteBufferResponse.array().length);
            datagramSocket.receive(datagramPacketRes);
            int receivedNumber = byteBufferResponse.getInt();
            int sendingNumber = 0;
            if ((receivedNumber%3) == 0)
            {
                System.out.println(receivedNumber+" + 0 = "+receivedNumber);
                sendingNumber = receivedNumber/3;
                System.out.println("Sending number is "+(receivedNumber/3));
                System.out.println("************");
            }
            else if ((receivedNumber%3) == 1)
            {
                System.out.println(receivedNumber+" - 1 = "+(receivedNumber-1));
                sendingNumber = (receivedNumber-1)/3;
                System.out.println("Sending number is "+((receivedNumber-1)/3));
                System.out.println("************");
            }
            else
            {
                System.out.println(receivedNumber+" + 1 = "+(receivedNumber+1));
                sendingNumber = (receivedNumber+1)/3;
                System.out.println("Sending number is "+((receivedNumber+1)/3));
                System.out.println("************");
            }
            if (sendingNumber == 1)
            {

                System.out.println("Player Two won!");
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
