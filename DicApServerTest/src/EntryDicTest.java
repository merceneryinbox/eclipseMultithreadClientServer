import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class EntryDicTest {

	public static void main(String[] args) throws UnknownHostException, IOException {
		try	(Socket socket = new Socket("localhost", 23345);
				ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())){
			oos.writeObject(new EntryDic(0,"icmp","search"));
			oos.flush();
			
			Thread.sleep(1000);
			EntryDic e = (EntryDic) ois.readObject();
			
			String def = e.getDefinition();
			System.out.println(" icmP - " + def);
		
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
