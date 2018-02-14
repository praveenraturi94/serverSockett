package serverSockett;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

import org.apache.commons.io.IOUtils;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOUtil;
import org.jpos.iso.packager.GenericPackager;

public class ServerSockett {
	private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
	private final static byte[] header = ISOUtil.hex2byte("00D3000016010200D40000000000000000000000000000000000");
	                                                       
	// static ServerSocket variable
	// private static ServerSocket server;
	// socket server port on which it will listen
	private static int port = 8181;

	public static void main(String argv[]) throws Exception {
		ServerSocket server = new ServerSocket(port);

		System.out.println("TCPServer Waiting for client on port " + port);
		Socket connected = server.accept();
		InputStream is = connected.getInputStream();
//		BufferedReader in = new BufferedReader(new InputStreamReader(is));
		byte[] bytes = IOUtils.toByteArray(is);
//		byte[] daata = Arrays.copyOfRange(bytes, getHeaderLength(), bytes.length);
	System.out.println(bytesToHex(bytes));
//	System.out.println(bytesToHex(daata));
		if (connected.isConnected()) {
			PrintWriter outToClient = new PrintWriter(connected.getOutputStream(), true);
			outToClient.println("success");
			// server.close();
			// connected.close();
		}
//System.out.println("###########"+bytesToHex(daata).getBytes());
	
			GenericPackager packager = new GenericPackager("basic1987_2.xml");
			ISOMsg isoMsg = new ISOMsg();
			isoMsg.setPackager(packager);
			
			isoMsg.unpack(bytesToHex(bytes).getBytes());
			System.out.println(isoMsg);
			try {
				System.out.println("  MTI : " + isoMsg.getMTI());
				for (int i=1;i<=isoMsg.getMaxField();i++) {
					if (isoMsg.hasField(i)) {
						System.out.println("    Field-"+i+" : "+isoMsg.getString(i));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				System.out.println("--------------------");
			}


		// DataOutputStream dout=new DataOutputStream(s.getOutputStream());

		// if (connected.isConnected()) {
		// GenericPackager packager = new GenericPackager("basic.xml");
		// BufferedReader receiveStream = new BufferedReader(new
		// InputStreamReader(connected.getInputStream()));
		// String isoMessage = receiveStream.readLine();
		// System.out.println(isoMessage);
		// ISOMsg isoMsg1 = new ISOMsg();
		// isoMsg1.setPackager(packager);
		//
		// byte[] bIsoMessage = new byte[isoMessage.length()];
		// for (int i = 0; i < bIsoMessage.length; i++) {
		// bIsoMessage[i] = (byte) (int) isoMessage.charAt(i);
		// }
		// isoMsg1.unpack(bIsoMessage);
		//
		// System.out.println("MTI='" + isoMsg1.getMTI() + "'");
		// for (int i = 1; i <= isoMsg1.getMaxField(); i++) {
		// if (isoMsg1.hasField(i))
		// System.out.println("DE : " + i + " = " + isoMsg1.getString(i) + "");
		// }
		// }

	}

	public static String bytesToHex(byte[] bytes) {
		char[] hexChars = new char[bytes.length * 2];
		for (int j = 0; j < bytes.length; j++) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}

	public static int getHeaderLength() {
		return header.length;
	}
}
