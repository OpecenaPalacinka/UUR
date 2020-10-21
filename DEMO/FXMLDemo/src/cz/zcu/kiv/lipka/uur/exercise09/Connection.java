package cz.zcu.kiv.lipka.uur.exercise09;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 
 * Representation of one connection displayed in the table 
 * 
 * @author Richard Lipka
 *
 */
public class Connection {
	// default address used for creating default instances - represented as array of bytes 
	public static final byte[] defaultIP = new byte[] {(byte)192, (byte)168, (byte)0, (byte)0};
	
	private String name;
	private InetAddress source;
	private InetAddress target;
	
	// creates connection with default address
	public Connection() {		
		name = "default";
		try {
			source = InetAddress.getByAddress(defaultIP);
			target = InetAddress.getByAddress(defaultIP);
		} catch (UnknownHostException e) {
			source = null;
			target = null;
		}
	}
	
	// creates connection from provided address
	public Connection(String name, InetAddress source, InetAddress target) {
		super();
		this.name = name;
		this.source = source;
		this.target = target;
	}
	
	//
	// Getters and setters
	//
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public InetAddress getSource() {
		return source;
	}
	
	public void setSource(InetAddress source) {
		this.source = source;
	}
	
	public InetAddress getTarget() {
		return target;
	}
	
	public void setTarget(InetAddress target) {
		this.target = target;
	}
}
