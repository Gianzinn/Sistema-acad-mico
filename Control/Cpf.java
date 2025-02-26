package Control;

import java.text.ParseException;

import javax.swing.text.MaskFormatter;

public class Cpf {
    public static String formatarCpf(String cpf) {
        try {
            MaskFormatter mascaraCPF = new MaskFormatter("###.###.###-##");
            mascaraCPF.setValueContainsLiteralCharacters(false); 
            return mascaraCPF.valueToString(cpf); 
        } catch (ParseException e) {
            e.printStackTrace();
            return cpf; 
        }
    }
}

