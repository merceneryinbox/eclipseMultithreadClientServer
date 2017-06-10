import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class TestAsRunnableClient implements Runnable {
	static Socket socket;

	public TestAsRunnableClient() {
		try {

			// ������ ����� ������� �� ������� �������
			socket = new Socket("localhost", 3345);
			System.out.println("Client connected to socket");
			Thread.sleep(2000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {

				// ������ ������ ��� ���������� � ������� �������, ��� ������
				// ����� � ��������� �����, ��� ������ ����� �� ������
				// � try-with-resources �����
		try (	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				DataOutputStream oos = new DataOutputStream(socket.getOutputStream());
				DataInputStream ois = new DataInputStream(socket.getInputStream())) {
			System.out.println("Client oos & ois initialized");

			// ������ ������� ���� � ��������� �� ������ �� ����� ������ �
			// ������ � ���������� ���� �� ������
			while (!socket.isOutputShutdown()) {

				// ��� ������� ������� �� ������� ��������� � ��� ������
				if (br.ready()) {

					// ������ ���������! - ��������
					System.out.println("Client start writing in channel...");
					String clientCommand = br.readLine();
					Thread.sleep(1000);

					// ����� ��������� � ������� ������� � ����� ������ ���
					// �������
					oos.writeUTF(clientCommand);

					// ������������ ��������� �� ������ ������� ��������� �
					// �����
					oos.flush();
					System.out.println("Clien sent message " + clientCommand + " to server.");

					// ��� ����� ������ ����� �������� ��������� �� ������ �
					// ��������
					Thread.sleep(1000);

					// ��������� ������� ������ �� ���������� �� �������� �����
					// , ������� ������� � ������� ������
					if (clientCommand.equalsIgnoreCase("quit")) {

						// ���� ������� ������ ���������� ������������� �����
						// ���������� ���������� ��������� �� �������
						System.out.println("Client kill connection.");
						Thread.sleep(2000);

						// ������� ��� ��� ������� ������ �� ��������, ����
						// ������� ����� ����� � ������� �������
						if (ois.available() != 0) {
							System.out.println("reading...");
							String in = ois.readUTF();
							System.out.println("");
							System.out.println("��������� ��������� �� ������� - " + in);
						}

						// ����� ��������������� ������������� ������� �� �����
						// ������ ������
						break;
					}

					// ���� ������� ������������ �� ���������� ���������� ������
					System.out.println("Client wrote & start waiting for data from server...");
					Thread.sleep(2000);

					// ���������, ��� ��� ������� ������ �� ���������(��
					// ��������������� ��� ����� � ����� �� ������ ��� ������
					// �������� � ��������
					if (ois.available() != 0) {

						// ���� ����� �������� ����� �� ������ ������� � ������
						// ������� � ���������� � ois ����������, �������� ��
						// �������
						System.out.println("reading...");
						String in = ois.readUTF();
						System.out.println(in);
					}
				}
			}

			// ����� ������ �� ��������� ����� ������ � ������ �������
			// ����������� ���� ��� ��� � ������ ������ ��� ���� ������� �
			// ������� ������� ����� try ��� Autoclosable ����������������
			// �������� � ����� try-with-resource
			System.out.println("Closing connections & channels on clentSide.");

			// ������� ���� Socket �� �������������� Autoclosable interface
			// ������� ������� ���� ������� ����� �������
			socket.close();
			System.out.println("Closing connections & channels on clentSide - DONE.");

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
