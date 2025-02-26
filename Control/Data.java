package Control;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Data {
	private static final SimpleDateFormat telaFormat = new SimpleDateFormat("dd-MM-yyyy");

	public static String formatarParaTela(Date data) {
		return telaFormat.format(data);
	}

	public static String modificarFormatoData(String data) {
		String[] partes = data.split("-");

		// As partes são separadas como [ano, mês, dia]
		// o formato dd-MM-yyyy
		return partes[2] + "-" + partes[1] + "-" + partes[0]; 
	}

}
