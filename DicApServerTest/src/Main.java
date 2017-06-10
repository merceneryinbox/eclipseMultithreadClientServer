import java.io.IOException;
//import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

	// private static ServerSocket server;

	public static void main(String[] args) throws IOException, InterruptedException {
		ExecutorService exec = Executors.newFixedThreadPool(10);
		// server = new ServerSocket(3345);
		int j = 0;
		while (j < 10) {
			j++;
			// exec.execute(new RunHand(server.accept()));
			exec.execute(new TestRunnableClientTester());
			Thread.sleep(10);
		}
		exec.shutdown();
	}
}
