package org.jaxws.integrationtest;

import static org.jaxws.stub2html.view.freemarker.ClasspathFreemarkerWebServiceDisplayEngine.createEngine;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.jws.WebService;
import javax.xml.ws.Endpoint;

import org.apache.commons.io.FileUtils;
import org.jaxws.integrationtest.exampleWebService.Order;
import org.jaxws.integrationtest.exampleWebService.OrderSOAPService;
import org.jaxws.stub2html.model.WebMethodStubSet;
import org.jaxws.stub2html.model.WebServiceStubSet;
import org.jaxws.stub2html.service.WebServiceStubSetFactory;
import org.jaxws.stub2html.view.WebMethodDisplayEngine;
import org.jaxws.stub2html.view.WebServiceDisplayEngine;
import org.jaxws.stub2html.view.simple.SimpleJavaNameDisplayStrategy;
import org.jaxws.stub2html.view.simple.SimpleWebMethodDisplayEngine;
import org.jaxws.wsdl2html.service.Wsdl2Html;
import org.junit.Test;

/**
 * 
 * @author chenjianjx@gmail.com
 *
 */
public class FalabellaTCase {
/*
	@Test
	public void fromStubTest_SingleReport() throws IOException {
		Class<?> webServiceClass = OrderSOAPService.class;
		WebServiceStubSet serviceStubSet = WebServiceStubSetFactory
				.createWebServiceStubSet(webServiceClass);

		File outputDir = new File("output/" + System.currentTimeMillis());
		outputDir.mkdirs();

		WebServiceDisplayEngine displayEngine = createEngine(
				new SimpleJavaNameDisplayStrategy(),

				// you can use your own template here. this is a classpath 
				"/main/resources/service.ftl");

		String html = displayEngine.displayWebService(serviceStubSet);
		File outputFile = new File(outputDir, "report.html");
		FileUtils.writeStringToFile(outputFile, html);

		System.out.println("Please find the HTML files at "
				+ outputFile.getAbsolutePath());

	}

	@Test
	public void fromStubTest_OneMethodOneFile() throws IOException {
		Class<?> webServiceClass = OrderSOAPService.class;
		WebServiceStubSet serviceStubSet = WebServiceStubSetFactory
				.createWebServiceStubSet(webServiceClass);

		File outputDir = new File("output/" + System.currentTimeMillis());
		outputDir.mkdirs();

		for (WebMethodStubSet methodStubSet : serviceStubSet.getMethodStubs()) {
			System.out.println("Converting method "
					+ methodStubSet.getMethodName());
			WebMethodDisplayEngine display = new SimpleWebMethodDisplayEngine(
					new SimpleJavaNameDisplayStrategy());
			String html = display.displayWebMethod(methodStubSet);
			File outputFile = new File(outputDir, methodStubSet.getMethodName()
					+ ".html");
			FileUtils.writeStringToFile(outputFile, html);
		}

		System.out.println("Please find the HTML files at "
				+ outputDir.getAbsolutePath());
	}
*/
	
	@Test
	public void formWsdl() throws Exception {
	
		String wsdlUrl = "file:///C:/ws11/FIF_CORP_Cliente_MediosPagoObtener-v1_0_EXP/Resources/WSDL/OSB_Cliente_MediosPago_Obtener.wsdl";
		System.out.println("Generating from " + wsdlUrl);
		String html = Wsdl2Html.generateHtml(wsdlUrl);
		File htmlDir = new File("output/" + System.currentTimeMillis());
		FileUtils.writeStringToFile(new File(htmlDir, "OSB_Cliente_MediosPago_Obtener.html"), html);
		System.out.println("Please find the HTML files at "+htmlDir.getAbsolutePath());
	}
/*
	public static void main(String[] args) {
		Endpoint.publish("http://localhost:7007/ws/order",
				new OrderSOAPServiceTestImpl());		
	}
*/
}
