package org.jaxws.wsdl2bytecodes.service;

import static org.jaxws.wsdl2bytecodes.service.Wsdl2ByteCodes.formatDate;
import static org.jaxws.wsdl2bytecodes.service.Wsdl2ByteCodes.generatePackageName;

import java.util.Date;

import org.junit.Test;


/** 
*
* @author chenjianjx
*/

public class Wsdl2ByteCodesTest  {
	 

	/**
	 * @see org.jaxws.wsdl2bytecodes.service.Wsdl2ByteCodes#generatePakcageName
	 */
	@Test
	public void testGeneratePakcageName() {
		String packageName = generatePackageName(formatDate(new Date(), "yyyyMMddHHmmssSSS"));
		System.out.println(packageName);
	}
}
