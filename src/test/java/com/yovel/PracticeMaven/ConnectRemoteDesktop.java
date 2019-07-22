package com.yovel.PracticeMaven;

import ch.ethz.ssh2.Connection;

import ch.ethz.ssh2.Session;

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

			System.out.println("connected");

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	public static void main(String[] args) throws Exception {
		ConnectRemoteDesktop crd =new ConnectRemoteDesktop();
		crd.setAuthenticationInfo("192.168.1.6","root","root");
		crd.runCommand();

	}

}
