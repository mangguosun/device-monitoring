package cn.dunn.im.util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Random;

public class HttpUtil {
	public static boolean isPortUsing(int port) {
		boolean flag = false;
		try {
			InetAddress theAddress = InetAddress.getByName("127.0.0.1");

			Socket socket = new Socket(theAddress, port);
			flag = true;
			socket.close();
		} catch (IOException e) {
		}
		return flag;
	}

	public static int getUsableProt() {
		Random random = new Random();
		int port = random.nextInt(2000) + 4000;
		while (isPortUsing(port)) {
			port = random.nextInt(2000) + 4000;
		}
		return port;
	}
}
