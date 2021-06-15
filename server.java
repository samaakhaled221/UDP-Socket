import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Collections;

public class server {
    public static void main(String[] args)  {
        try {
            DatagramSocket server= new DatagramSocket(4000);
            Scanner scanner = new Scanner(System.in);
            System.out.println("server  is booted up");


            while(true)
            {
                byte [] bt=new byte[5000];
                DatagramPacket request= new DatagramPacket(bt,bt.length);
                server.receive(request);
                String clientinput=new String(request.getData());
                clientinput = clientinput.trim();
                System.out.println("Client: "+clientinput);

                InetAddress clientipAddress = request.getAddress();
                int clientPort=request.getPort();

                String rep ="Server: 1. Ascending order.  2. Descending order.";
                byte[] btt = rep.getBytes();
                DatagramPacket reply = new DatagramPacket(btt, btt.length,clientipAddress,clientPort);
                System.out.println(rep);
                server.send(reply);
                //---------------------------------------------

                byte[] xbyt =new byte[5000];
                DatagramPacket choose = new DatagramPacket(xbyt, xbyt.length);
                server.receive(choose);
                String choice = new String(choose.getData());
                choice = choice.trim();
                System.out.println(choice);
                 //------------------------------------------------------------

                int i=0;
                String[] splitarr= clientinput.split(",");
                int sz=splitarr.length;
                Integer[] arr =new Integer[sz];
                while(i<splitarr.length)
                {
                    arr[i] = Integer.parseInt(splitarr[i].trim());
                    i++;
                }

                if(choice.equalsIgnoreCase("1")) {
                    Arrays.sort(arr);
                   String ans = String.join(",", Arrays.toString(arr));
                    byte[] sortbt = ans.getBytes();
                    DatagramPacket sorting = new DatagramPacket(sortbt, sortbt.length,clientipAddress,clientPort);
                    System.out.println(ans);
                    server.send(sorting);
                }
                else if (choice.equalsIgnoreCase("2"))
                {
                   Arrays.sort(arr, Collections.reverseOrder());
                    String ans = String.join(",", Arrays.toString(arr));
                    byte[] sortbt = ans.getBytes();
                    DatagramPacket sorting = new DatagramPacket(sortbt, sortbt.length,clientipAddress,clientPort);
                    server.send(sorting);
                }

            }
        }
        catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}
