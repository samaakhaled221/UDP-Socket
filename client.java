import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class client {
    public static void main(String[] args) {
        try {
            DatagramSocket client = new DatagramSocket();

            Scanner scanner  = new Scanner (System.in);

            while (true) {
                System.out.println("server: Please enter the list of numbers to be arranged or 'close' to close.");
                String inputt = scanner.nextLine();

                if(inputt.equalsIgnoreCase("close")) {
                    client.close();
                    System.out.println("client is terminated");
                    break;
                }
                else
                {
                byte[] bt = inputt.getBytes();
                InetAddress ipAddress = InetAddress.getByName("localhost");
                DatagramPacket request = new DatagramPacket(bt, bt.length, ipAddress, 4000);
                client.send(request);

                byte[] btt = new byte[5000];
                DatagramPacket reply = new DatagramPacket(btt, btt.length);
                client.receive(reply);
                String ans= new String(reply.getData());
                System.out.println(ans.trim());

                String choice= scanner.nextLine();
                byte[] xbyt = choice.getBytes();
                DatagramPacket choose = new DatagramPacket(xbyt, xbyt.length, ipAddress, 4000);
                client.send(choose);

                byte[] sortbt = new byte[5000];
                DatagramPacket sorting = new DatagramPacket(sortbt, sortbt.length);
                client.receive(sorting);
                String sort= new String(sorting.getData());
                sort= sort.trim();
                System.out.println("server :"+ sort);

            }}
        } catch (SocketException e) {
            e.printStackTrace();
        }
        catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
