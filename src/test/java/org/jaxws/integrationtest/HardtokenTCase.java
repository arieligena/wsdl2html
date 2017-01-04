package org.jaxws.integrationtest;

import java.io.File;
import org.apache.commons.io.FileUtils;
import org.jaxws.wsdl2html.service.Wsdl2Html;
import org.junit.Test;

public class HardtokenTCase {

	@Test
	public void hardtokenAsignarExp() throws Exception {	
		String wsdlUrl = "file:///C:/workspace/wsdl2html/src/main/resources/WSDL/FIF_CORP_OSB_Cliente_Hardtoken_Asignar.wsdl";
		System.out.println("Generating from " + wsdlUrl);
		String html = Wsdl2Html.generateHtml(wsdlUrl, "/falabella2.ftl");
		File htmlDir = new File("C:/workspace/wsdl2html/output/ht/");
		FileUtils.writeStringToFile(new File(htmlDir, "Cliente_Hardtoken_Asignar.html"), html);
		System.out.println("Please find the HTML files at "+htmlDir.getAbsolutePath());
	}
	
	@Test
	public void hardtokenBE() throws Exception {	
		String wsdlUrl = "file:///C:/workspace/wsdl2html/src/main/resources/WSDL/Hardtoken_1.wsdl";
		System.out.println("Generating from " + wsdlUrl);
		String html = Wsdl2Html.generateHtml(wsdlUrl, "/falabella2.ftl");
		File htmlDir = new File("C:/workspace/wsdl2html/output/ht/");
		FileUtils.writeStringToFile(new File(htmlDir, "Hardtoken.html"), html);
		System.out.println("Please find the HTML files at "+htmlDir.getAbsolutePath());
	}
}
