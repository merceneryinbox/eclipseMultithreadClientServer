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

			// создаём сокет общения на стороне клиента
			socket = new Socket("localhost", 3345);
			System.out.println("Client connected to socket");
			Thread.sleep(2000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {

				// создаём объект для считывания с консоли клиента, для записи
				// строк в созданный сокет, для чтения строк из сокета
				// в try-with-resources стиле
		try (	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				DataOutputStream oos = new DataOutputStream(socket.getOutputStream());
				DataInputStream ois = new DataInputStream(socket.getInputStream())) {
			System.out.println("Client oos & ois initialized");

			// создаём рабочий цикл с проверкой не закрыт ли канал записи в
			// сокете и продолжаем если не закрыт
			while (!socket.isOutputShutdown()) {

				// ждём консоли клиента на предмет появления в ней данных
				if (br.ready()) {

					// данные появились! - работаем
					System.out.println("Client start writing in channel...");
					String clientCommand = br.readLine();
					Thread.sleep(1000);

					// пишем сообщение с консоли клиента в канал сокета для
					// сервера
					oos.writeUTF(clientCommand);

					// проталкиваем сообщение из буфера сетевых сообщений в
					// канал
					oos.flush();
					System.out.println("Clien sent message " + clientCommand + " to server.");

					// ждём чтобы сервер успел прочесть сообщение из сокета и
					// ответить
					Thread.sleep(1000);

					// проверяем условие выхода из соединения по кодовому слову
					// , которое написал в консоли клиент
					if (clientCommand.equalsIgnoreCase("quit")) {

						// если условие выхода достигнуто разъединяемся после
						// считывания последнего сообщения от сервера
						System.out.println("Client kill connection.");
						Thread.sleep(2000);

						// смотрим что нам ответил сервер на последок, если
						// ответил пишем ответ в консоль клиента
						if (ois.available() != 0) {
							System.out.println("reading...");
							String in = ois.readUTF();
							System.out.println("");
							System.out.println("Последнее сообщение от сервера - " + in);
						}

						// после предварительных приготовлений выходим из цикла
						// записи чтения
						break;
					}

					// если условие разъединения не достигнуто продолжаем работу
					System.out.println("Client wrote & start waiting for data from server...");
					Thread.sleep(2000);

					// проверяем, что нам ответит сервер на сообщение(за
					// предоставленное ему время в паузе он должен был успеть
					// прочесть и ответить
					if (ois.available() != 0) {

						// если успел забираем ответ из канала сервера в сокете
						// клиента и сохраняемеё в ois переменную, печатаем на
						// консоль
						System.out.println("reading...");
						String in = ois.readUTF();
						System.out.println(in);
					}
				}
			}

			// после выхода из основного цикла записи и чтения ресурсы
			// закрываются сами так как в данном случае они были открыты в
			// круглых скобках блока try для Autoclosable имплементирующих
			// объектов в стиле try-with-resource
			System.out.println("Closing connections & channels on clentSide.");

			// объекты типа Socket не имплементируют Autoclosable interface
			// поэтому доолжны быть закрыты явным образом
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
