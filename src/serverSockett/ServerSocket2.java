package serverSockett;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.lang.ClassNotFoundException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.commons.io.IOUtils;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.packager.GenericPackager;

public class ServerSocket2 {
	private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
	// static ServerSocket variable
	private static ServerSocket server;
	// socket server port on which it will listen
	private static int port = 8181;

	public static void main(String argv[]) throws Exception {
//		String fromclient;
//		String toclient;

		ServerSocket Server = new ServerSocket(8181);

		System.out.println("TCPServer Waiting for client on port 8181");

		Socket connected = Server.accept();
		 while (connected.isConnected())
		 {
//		 BufferedReader inFromUser = new BufferedReader(new
//		 InputStreamReader(System.in));
//		
//		 BufferedReader inFromClient = new BufferedReader(new InputStreamReader
//		 (connected.getInputStream()));
//		
		 InputStream is = connected.getInputStream();
		 byte[] bytes = IOUtils.toByteArray(is);
		 System.out.println(bytesToHex(bytes));
		 PrintWriter outToClient = new PrintWriter(connected.getOutputStream(),true);

		
		 GenericPackager packager = new GenericPackager("basic.xml");
		 ISOMsg isoMsg = new ISOMsg();
		 isoMsg.setPackager(packager);
		 isoMsg.unpack(bytes);
		 System.out.println(isoMsg.getMTI());
		 outToClient.println("success");
		 }
		 
//		 DataOutputStream dout=new DataOutputStream(s.getOutputStream()); 
		
//		if (connected.isConnected()) {
//			GenericPackager packager = new GenericPackager("basic.xml");
//			BufferedReader receiveStream = new BufferedReader(new InputStreamReader(connected.getInputStream()));
//			String isoMessage = receiveStream.readLine();
//			System.out.println(isoMessage);
//			ISOMsg isoMsg1 = new ISOMsg();
//			isoMsg1.setPackager(packager);
//
//			byte[] bIsoMessage = new byte[isoMessage.length()];
//			for (int i = 0; i < bIsoMessage.length; i++) {
//				bIsoMessage[i] = (byte) (int) isoMessage.charAt(i);
//			}
//			isoMsg1.unpack(bIsoMessage);
//
//			System.out.println("MTI='" + isoMsg1.getMTI() + "'");
//			for (int i = 1; i <= isoMsg1.getMaxField(); i++) {
//				if (isoMsg1.hasField(i))
//					System.out.println("DE : " + i + " =  " + isoMsg1.getString(i) + "");
//			}
//		}

	}
	
	public static String bytesToHex(byte[] bytes) {
	    char[] hexChars = new char[bytes.length * 2];
	    for ( int j = 0; j < bytes.length; j++ ) {
	        int v = bytes[j] & 0xFF;
	        hexChars[j * 2] = hexArray[v >>> 4];
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	    }
	    return new String(hexChars);
	}

}
