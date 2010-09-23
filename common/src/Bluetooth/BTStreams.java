package Bluetooth;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public interface BTStreams {
	
	DataInputStream getDataInputStream();
	DataOutputStream getDataOutputStream();
	
	void close();
	void closeStreams();
}
