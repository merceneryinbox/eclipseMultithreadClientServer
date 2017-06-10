import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 */

/**
 * @author mercenery
 *
 */
public class MultiThreadServer {

	static ExecutorService executeIt = Executors.newFixedThreadPool(2);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// �������� ������ �� ����� 3345 � �������������� ���������� ��� ��������� ���������� ������ � ������ �������
		try (ServerSocket server = new ServerSocket(3345);
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
			System.out.println("Server socket created, command console reader for listen to server commands");

			// �������� ���� ��� ������� ��� ��������� ����� �� ������
			while (!server.isClosed()) {

				// ��������� ����������� �������� �� ������� ������� ���� �����
				// ����
				if (br.ready()) {
					System.out.println("Main Server found any messages in channel, let's look at them.");

					// ���� ������� - quit �� �������������� �������� ������� �
					// ����� �� ����� �������� ����� ������������ ��������
					String serverCommand = br.readLine();
					if (serverCommand.equalsIgnoreCase("quit")) {
						System.out.println("Main Server initiate exiting...");
						server.close();
						break;
					}
				}

				// ���� ������� �� ������� ��� �� ���������� � ��������
				// ����������� � ������ ������� ��� ������ - "clientDialog" ��
				// ��������� �������
				Socket client = server.accept();

				// ����� ��������� ������� �� ����������� ������ ������ �����
				// ��� ������� � �������� � ���������� ��� � ��������� ����
				// � Runnable(��� ������������� ����� ������� Callable)
				// ������������ ���� = ������ - MonoThreadClientHandler � ���
				// ���������� ������� �� ���� �������
				executeIt.execute(new MonoThreadClientHandler(client));
				System.out.print("Connection accepted.");
			}

			// �������� ���� ����� ����� ���������� ������ ���� �����
			executeIt.shutdown();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}