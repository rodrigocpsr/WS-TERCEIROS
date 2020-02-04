package br.com.comven.corews.transacao.util;

/**
 * Classe com funcoes de validação da aplicação
 * 
 * @author Cateno Viglio
 * @since 12/07/2016
 * 
 */
public class ValidatorUtil {

	private static final int[] pesoCPF = { 11, 10, 9, 8, 7, 6, 5, 4, 3, 2 };
	private static final int[] pesoCNPJ = { 6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2 };

	private static int calcularDigito(String str, int[] peso) {
		int soma = 0;
		for (int indice = str.length() - 1, digito; indice >= 0; indice--) {
			digito = Integer.parseInt(str.substring(indice, indice + 1));
			soma += digito * peso[peso.length - str.length() + indice];
		}
		soma = 11 - soma % 11;
		return soma > 9 ? 0 : soma;
	}

	public static boolean isValidCPF(String cpf) {
		if ((cpf == null) || (cpf.length() != 11))
			return false;

		if (cpf.equals("00000000000") || cpf.equals("11111111111") || cpf.equals("22222222222") || cpf.equals("33333333333") || cpf.equals("44444444444") || cpf.equals("55555555555") || cpf.equals("66666666666") || cpf.equals("77777777777") || cpf.equals("88888888888") || cpf.equals("99999999999"))
			return false;

		Integer digito1 = calcularDigito(cpf.substring(0, 9), pesoCPF);
		Integer digito2 = calcularDigito(cpf.substring(0, 9) + digito1, pesoCPF);
		return cpf.equals(cpf.substring(0, 9) + digito1.toString() + digito2.toString());
	}

	public static boolean isValidCNPJ(String cnpj) {
		if ((cnpj == null) || (cnpj.length() != 14))
			return false;

		if (cnpj.equals("00000000000000") || cnpj.equals("11111111111111") || cnpj.equals("22222222222222") || cnpj.equals("33333333333333") || cnpj.equals("44444444444444") || cnpj.equals("55555555555555") || cnpj.equals("66666666666666") || cnpj.equals("77777777777777") || cnpj.equals("88888888888888") || cnpj.equals("99999999999999"))
			return false;

		Integer digito1 = calcularDigito(cnpj.substring(0, 12), pesoCNPJ);
		Integer digito2 = calcularDigito(cnpj.substring(0, 12) + digito1, pesoCNPJ);
		return cnpj.equals(cnpj.substring(0, 12) + digito1.toString() + digito2.toString());
	}

	public static boolean isValidPlaca(String placa) {

		if ((placa == null) || (placa.length() != 7))
			return false;

		if (placa.equals("00000000000"))
			return false;

		try {

			// verifica se os 3 primeiros digitos sao string
			if (!placa.substring(0, 3).matches("[a-zA-Z]+"))
				return false;

			if (!placa.substring(3, 4).matches("[0-9]") || !placa.substring(5, 7).matches("[0-9]+"))
				return false;

		} catch (Exception e) {
			return false;
		}

		return true;
	}

	public static boolean validarRenavam(String renavam) {
		// Pegando como exemplo o renavam = 639884962

		// Completa com zeros a esquerda se for no padrao antigo de 9 digitos
		// renavam = 00639884962
		if (renavam.matches("^([0-9]{9})$")) {
			renavam = "00" + renavam;
		}

		// Valida se possui 11 digitos pos formatacao
		if (!renavam.matches("[0-9]{11}")) {
			return false;
		}

		// Remove o digito (11 posicao)
		// renavamSemDigito = 0063988496
		String renavamSemDigito = renavam.substring(0, 10);

		// Inverte os caracteres (reverso)
		// renavamReversoSemDigito = 69488936
		String renavamReversoSemDigito = new StringBuffer(renavamSemDigito).reverse().toString();

		int soma = 0;

		// Multiplica as strings reversas do renavam pelos numeros multiplicadores
		// para apenas os primeiros 8 digitos de um total de 10
		// Exemplo: renavam reverso sem digito = 69488936
		// 6, 9, 4, 8, 8, 9, 3, 6
		// * * * * * * * *
		// 2, 3, 4, 5, 6, 7, 8, 9 (numeros multiplicadores - sempre os mesmos [fixo])
		// 12 + 27 + 16 + 40 + 48 + 63 + 24 + 54
		// soma = 284
		for (int i = 0; i < 8; i++) {
			Integer algarismo = Integer.parseInt(renavamReversoSemDigito.substring(i, i + 1));
			Integer multiplicador = i + 2;
			soma += algarismo * multiplicador;
		}

		// Multiplica os dois ultimos digitos e soma
		soma += Character.getNumericValue(renavamReversoSemDigito.charAt(8)) * 2;
		soma += Character.getNumericValue(renavamReversoSemDigito.charAt(9)) * 3;

		// mod11 = 284 % 11 = 9 (resto da divisao por 11)
		int mod11 = soma % 11;

		// Faz-se a conta 11 (valor fixo) - mod11 = 11 - 9 = 2
		int ultimoDigitoCalculado = 11 - mod11;

		// ultimoDigito = Caso o valor calculado anteriormente seja 10 ou 11, transformo ele em 0
		// caso contrario, eh o proprio numero
		ultimoDigitoCalculado = (ultimoDigitoCalculado >= 10 ? 0 : ultimoDigitoCalculado);

		// Pego o ultimo digito do renavam original (para confrontar com o calculado)
		int digitoRealInformado = Integer.valueOf(renavam.substring(renavam.length() - 1, renavam.length()));

		// Comparo os digitos calculado e informado
		if (ultimoDigitoCalculado == digitoRealInformado) {
			return true;
		}
		return false;
	}

	public static boolean validaTimezone(String timezone) {

		Integer intTimeZone = Integer.parseInt(timezone);
		if (intTimeZone <= 14 && intTimeZone >= -12) {
			return true;
		}
		return false;
	}

	/**
	 * Function for finding and returning sum of digits of a number
	 * 
	 * @param n : number
	 * @return
	 */
	private static int sumDig(int n) {
		int a = 0;
		while (n > 0) {
			a = a + n % 10;
			n = n / 10;
		}
		return a;
	}

	public static boolean validaIMEI(String imei) {

		int l = imei.length();

		if (l != 15)
			return false;
		else {

			Long n = Long.parseLong(imei);

			int d = 0, sum = 0;
			for (int i = 15; i >= 1; i--) {
				d = (int) (n % 10);

				if (i % 2 == 0) {
					d = 2 * d; // Doubling every alternate digit
				}

				sum = sum + sumDig(d); // Finding sum of the digits

				n = n / 10;
			}

			if (sum % 10 == 0)
				return true;
			else
				return false;
		}
	}

	public static String cleanMask(String value) {
		if (value != null)
			return value.replaceAll("[^a-zA-Z0-9]", "");
		else
			return null;
	}

}