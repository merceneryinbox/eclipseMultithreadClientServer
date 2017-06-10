import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class RunHand implements Runnable {

	Socket socket;
	RunHand(Socket socket){
		this.socket=socket;
	}
	@Override
	public void run() {
		try(DataInputStream din = new DataInputStream(socket.getInputStream());
			DataOutputStream dout = new DataOutputStream(socket.getOutputStream())){
			while (!socket.isClosed()){
				
				String in = din.readUTF();
				System.out.println("Came from client - "+in);
				
				dout.writeUTF("Echo reply from server: "+ in);
				Thread.sleep(100);
			}
			System.out.println("Socket closed, closing channels");
			din.close();
			dout.close();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}

}
