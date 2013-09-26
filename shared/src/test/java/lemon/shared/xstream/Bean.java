package lemon.shared.xstream;

import lemon.shared.xstream.annotations.XStreamCDATA;
import lemon.shared.xstream.annotations.XStreamProcessCDATA;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("xml")
@XStreamProcessCDATA
public class Bean {
	@XStreamAlias("Name")
	@XStreamCDATA
	private String name;
	@XStreamCDATA
	@XStreamAlias("Address")
	private String address;
	
	public Bean(String name, String address){
		this.name=name;
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
}
