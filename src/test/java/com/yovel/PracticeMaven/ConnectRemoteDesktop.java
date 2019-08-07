
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import ch.ethz.ssh2.Connection;

import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

public class ConnectRemoteDesktop {

	String host;
	String userid;
	String password;
	String recentCommand;

	public void setAuthenticationInfo(String hostname, String username, String password) {

		this.host = hostname;

		this.userid = username;

		this.password = password;

		this.recentCommand = "";

		System.out.println("setting authentication info completed for host=" + host);

	}

	public void runCommand() throws Exception {

		try {

			// Setup ssh session with endpoint

			System.out.println("starting connection with " + host);

			Connection connection = new Connection(host);

			System.out.println("connection object created..");

			connection.connect();

			System.out.println("Connect to connection");

			connection.authenticateWithPassword(userid, password);

			System.out.println(connection.isAuthenticationComplete());

			Session session = connection.openSession();
			session.execCommand("uname -a && date && uptime && who");

			System.out.println("connected");

			InputStream stdout = new StreamGobbler(session.getStdout());

			BufferedReader br = new BufferedReader(new InputStreamReader(stdout));

			while (true) {
				String line = br.readLine();
				if (line == null)
					break;
				System.out.println(line);
			}

			/* Show exit status, if available (otherwise "null") */

			System.out.println("ExitCode: " + session.getExitStatus());

			/* close buffer Reader */

			br.close();

			/* Close this session */

			session.close();

			/* Close the connection */

			connection.close();

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	public static void main(String[] args) throws Exception {
		ConnectRemoteDesktop crd = new ConnectRemoteDesktop();
		crd.setAuthenticationInfo("192.168.1.5", "yoveltech", "v1");
		crd.runCommand();

	}

}
