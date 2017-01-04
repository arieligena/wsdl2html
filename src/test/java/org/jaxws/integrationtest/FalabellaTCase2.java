package org.jaxws.integrationtest;

import java.io.File;
import org.apache.commons.io.FileUtils;
import org.jaxws.wsdl2html.service.Wsdl2Html;
import org.junit.Test;

public class FalabellaTCase2 {

	@Test
	public void formWsdl() throws Exception {	
		String wsdlUrl = "file:///C:/workspace/wsdl2html/src/main/resources/WSDL/UsersOOB_1.wsdl";
		System.out.println("Generating from " + wsdlUrl);
		String html = Wsdl2Html.generateHtml(wsdlUrl, "/falabella2.ftl");
		File htmlDir = new File("C:/workspace/wsdl2html/output/usersOOB/");
		FileUtils.writeStringToFile(new File(htmlDir, "UsersOOB.html"), html);
		System.out.println("Please find the HTML files at "+htmlDir.getAbsolutePath());
	}
}
