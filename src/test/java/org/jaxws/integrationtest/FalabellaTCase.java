package org.jaxws.integrationtest;

import java.io.File;
import org.apache.commons.io.FileUtils;
import org.jaxws.wsdl2html.service.Wsdl2Html;
import org.junit.Test;

public class FalabellaTCase {

	@Test
	public void formWsdl() throws Exception {
	
		String wsdlUrl = "file:///C:/ws11/FIF_CORP_Cliente_MediosPagoObtener-v1_0_EXP/Resources/WSDL/OSB_Cliente_MediosPago_Obtener.wsdl";
		System.out.println("Generating from " + wsdlUrl);
		String html = Wsdl2Html.generateHtml(wsdlUrl);
		File htmlDir = new File("C:/workspace/wsdl2html/output/" + System.currentTimeMillis());
		FileUtils.writeStringToFile(new File(htmlDir, "OSB_Cliente_MediosPago_Obtener.html"), html);
		System.out.println("Please find the HTML files at "+htmlDir.getAbsolutePath());
	}
}
