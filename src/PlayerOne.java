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
        }
    }
}
