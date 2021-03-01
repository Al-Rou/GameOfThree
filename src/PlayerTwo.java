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
                int receivedNumber = byteBuffer2.getInt();
                if(receivedNumber == 0)
                {
                    System.out.println("Player Two lost the game!");
                    break;
                }
                else {
                    int sendingNumber = 0;
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
                    if (sendingNumber == 1) {
                        System.out.println("End of the game: Player Two won!");
                        ByteBuffer byteBuffer3 = ByteBuffer.allocate(1024);
                        byteBuffer3.order(ByteOrder.BIG_ENDIAN);
                        byteBuffer3.putInt(0);
                        DatagramPacket datagramPacket3 = new DatagramPacket(byteBuffer3.array(),
                                byteBuffer3.position(), datagramPacket2.getAddress(),
                                datagramPacket2.getPort());
                        datagramSocket.send(datagramPacket3);
                        break;
                    } else {
                        ByteBuffer byteBuffer3 = ByteBuffer.allocate(1024);
                        byteBuffer3.order(ByteOrder.BIG_ENDIAN);
                        byteBuffer3.putInt(sendingNumber);
                        DatagramPacket datagramPacket3 = new DatagramPacket(byteBuffer3.array(),
                                byteBuffer3.position(), datagramPacket2.getAddress(),
                                datagramPacket2.getPort());
                        datagramSocket.send(datagramPacket3);
                    }
                }
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
