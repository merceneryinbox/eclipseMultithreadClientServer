import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class TestRunnableClientTester implements Runnable {

		static Socket socket;
		
		public TestRunnableClientTester(){
			try{

// создаём сокет общения на стороне клиента	 в конструкторе объекта		
				socket = new Socket("localhost", 3345);	
				System.out.println("Client connected to socket");
				Thread.sleep(2000);
			} catch(Exception e){
				e.printStackTrace();
			}
		}

		@Override
		public void run() {
			
			try(
					
// создаём объект для записи строк в созданный скокет, для чтения строк из сокета
// в try-with-resources стиле				
					DataOutputStream oos = new DataOutputStream(socket.getOutputStream());
					DataInputStream ois = new DataInputStream(socket.getInputStream())
				)
			{
				System.out.println("Client oos & ois initialized");
				int i = 0;
// создаём рабочий чикл с проверкой количества проходов по циклу и работаем если не проходов было не больше 10
					while(i<=10){
						
// пишем сообщение с автогенерируемое циклом клиента в канал сокета для сервера			
						oos.writeUTF("clientCommand " + i);
						
// проталкиваем сообщение из буфера сетевых сообщений в канал					
						oos.flush();

// ждём чтобы сервер успел прочесть сообщение из сокета и ответить					
				Thread.sleep(10);		
				System.out.println("Client wrote & start waiting for data from server...");	
				
// проверяем, что нам ответит сервер на сообщение(за предоставленное ему время в паузе он должен был успеть прочесть и ответить			
				if(ois.available()!=0){	
							
// если успел забираем ответ из канала сервера в сокете клиента и сохраняемеё в ois переменную,  печатаем на консоль						
				System.out.println("reading...");
				String in = ois.readUTF();
				System.out.println(in);
						}	
				i++;
					}
// после выхода из основного цикла записи и чтения ресурсы закрываются сами так как в данном случае они были открыты в 
// круглых скобках блока try для Autoclosable имплементирующих объектов в стиле try-with-resource
								System.out.println("Closing connections & channels on clentSide.");

					// объекты типа Socket не имплементируют Autoclosable interface поэтому доолжны быть закрыты явным образом			
								socket.close();
								System.out.println("Closing connections & channels on clentSide - DONE.");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					

			}	
		} 
	
	
	
	
	
