package org.jaxws.integrationtest.exampleWebService;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 
 * @author chenjianjx
 * 
 */
@XmlType(name = "order")
@XmlAccessorType(XmlAccessType.FIELD)
public class Order {

	@XmlElement(name = "order-id")
	private Integer orderId;

	@XmlElement(name = "products", required = true)
	private List<Product> products;

}
