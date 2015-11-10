package cn.com.chaser.restapi;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class vauleStruct {
	@XmlElement public String param1;
    @XmlElement public String param2;
}
