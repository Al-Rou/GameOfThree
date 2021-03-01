import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class PlayerTwo {
    public static void main(String[] args)
    {
        try {
            DatagramSocket datagramSocket = new DatagramSocket(5558);
            while (true){
                ByteBuffer byteBuffer2 = ByteBuffer.allocate(1024);
                byteBuffer2.order(ByteOrder.BIG_ENDIAN);
                DatagramPacket datagramPacket2 = new DatagramPacket(byteBuffer2.array(),
                        byteBuffer2.array().length);
                datagramSocket.receive(datagramPacket2);
                int recievedNumber = byteBuffer2.getInt();
                int sendingNumber = 0;
                if ((recievedNumber%3) == 0)
                {
                    System.out.println(recievedNumber+" + 0 = "+recievedNumber);
                    sendingNumber = recievedNumber/3;
                    System.out.println("Sending number is "+(recievedNumber/3));
                }
                else if ((recievedNumber%3) == 1)
                {
                    System.out.println(recievedNumber+" - 1 = "+(recievedNumber-1));
                    sendingNumber = (recievedNumber-1)/3;
                    System.out.println("Sending number is "+((recievedNumber-1)/3));
                }
                else
                {
                    System.out.println(recievedNumber+" + 1 = "+(recievedNumber+1));
                    sendingNumber = (recievedNumber+1)/3;
                    System.out.println("Sending number is "+((recievedNumber+1)/3));
                }
                if (sendingNumber == 1)
                {

                    System.out.println("Player Two won!");
                }
                else
                {
                    ByteBuffer byteBuffer3 = ByteBuffer.allocate(1024);
                    byteBuffer3.order(ByteOrder.BIG_ENDIAN);
                    byteBuffer3.putInt(sendingNumber);
                    DatagramPacket datagramPacket3 = new DatagramPacket(byteBuffer3.array(),
                            byteBuffer3.position(), datagramPacket2.getAddress(),
                            datagramPacket2.getPort());
                    datagramSocket.send(datagramPacket3);
                }
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
