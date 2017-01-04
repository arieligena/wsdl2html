package org.jaxws.wsdl2html.service;

import java.util.List;

import javax.jws.WebService;

import org.jaxws.bytecodes2stub.service.ByteCodePackageLoadingService;
import org.jaxws.stub2html.model.WebServiceStubSet;
import org.jaxws.stub2html.service.WebServiceStubSetFactory;
import org.jaxws.stub2html.view.WebServiceDisplayEngine;
import org.jaxws.stub2html.view.freemarker.ClasspathFreemarkerWebServiceDisplayEngine;
import org.jaxws.stub2html.view.freemarker.FreemarkerWebServiceDisplayEngine;
import org.jaxws.wsdl2bytecodes.model.ByteCodePackage;
import org.jaxws.wsdl2bytecodes.service.Wsdl2ByteCodes;
import org.jaxws.wsdl2bytecodes.service.WsdlImportException;

/**
 * 
 * @author chenjianjx
 * 
 */
public class Wsdl2Html {

	public static String generateHtml(String byteCodesDirParent, String wsdlUrl, WebServiceDisplayEngine displayEngine) throws WsdlImportException {
		ByteCodePackage byteCodePackage = Wsdl2ByteCodes.generate(byteCodesDirParent, wsdlUrl);
		Class<?> webServiceClass = getWebServiceClass(byteCodePackage);
		WebServiceStubSet serviceStubSet = WebServiceStubSetFactory.createWebServiceStubSet(webServiceClass);
		return displayEngine.displayWebService(serviceStubSet);
	}
	
	/**	
	 *  
	 * @param wsdlUrl
	 * @param template TODO
	 * @return
	 * @throws WsdlImportException
	 */
	public static String generateHtml(String wsdlUrl, String template) throws WsdlImportException{
		FreemarkerWebServiceDisplayEngine displayEngine = 
				ClasspathFreemarkerWebServiceDisplayEngine.createEngine(template);
		String byteCodesDirParent = System.getProperty("java.io.tmpdir") + "/wsdl2html";
		return generateHtml(byteCodesDirParent, wsdlUrl, displayEngine);
	}

	private static Class<?> getWebServiceClass(ByteCodePackage byteCodePackage) {
		List<Class<?>> allClasses = ByteCodePackageLoadingService.loadAll(byteCodePackage);

		for (Class<?> clazz : allClasses) {
			if (clazz.isInterface() && clazz.isAnnotationPresent(WebService.class)) {
				return clazz;
			}
		}
		throw new IllegalStateException("No WebService Class found ! ");
	}

}
