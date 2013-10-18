package lemon.shared.robotmsg.bean;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Robot message bean
 * @author lemon
 * @version 1.0
 *
 */
public class RobotMsgBean {
	private int id;
	private int cust_id;
	@NotEmpty(message="关键字不能为空")
	@Length(max = 1024, message = "关键字长度不能超过30")
	private String key;
	@NotEmpty(message="内容不能为空")
	@Length(max = 1024, message = "内容长度不能超过1024")
	private String value;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCust_id() {
		return cust_id;
	}
	public void setCust_id(int cust_id) {
		this.cust_id = cust_id;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj == null)
			return false;
		if(!(obj instanceof RobotMsgBean))
			return false;
		RobotMsgBean target = (RobotMsgBean) obj;
		if (key == null)
			return target.key == null;
		return key.equals(target.key);
	}
	
	@Override
	public int hashCode(){
		return this.getKey().hashCode();
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("id=").append(id);
		sb.append(", cust_id=").append(cust_id);
		sb.append(", key=").append(key);
		sb.append(", value=").append(value);
		sb.append("}");
		return sb.toString();
	}
}
