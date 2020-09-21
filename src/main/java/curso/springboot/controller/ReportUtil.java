package curso.springboot.controller;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.stereotype.Component;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Component
public class ReportUtil<T> implements Serializable {
	
	/*Retorna nosso pdf em Byte para download no navegador*/
	public byte[] geraRelatório(List<T> listDados, 
			String relatorio, ServletContext context) throws Exception {
		
		/*Cria a lista de dados para o relatorio com a nossa lista de objetos para imprimir*/
		JRBeanCollectionDataSource jrbcds = new JRBeanCollectionDataSource(listDados);
		
		/*Carrega o caminho do arquivo Jasper compilado*/
		String caminhoJasper = context.getRealPath("relatorios") + File.separator + relatorio + ".jasper";
		
		/*Carrega o arquivo jasper passando os dados*/
		JasperPrint impressoraJasper = JasperFillManager.fillReport(caminhoJasper, new HashMap<>(), jrbcds);
		
		/*Exporta para bytes para fazer o download em pdf*/
		return JasperExportManager.exportReportToPdf(impressoraJasper);
		
	}
}
