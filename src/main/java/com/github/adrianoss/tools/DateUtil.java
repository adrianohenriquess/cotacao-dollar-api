package com.github.adrianoss.tools;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Classe DateUtil
 * @author Adriano Silva
 */
public class DateUtil {

	/**
	 * Método que reetorna o dia útil aterior a data informada
	 *  
	 * @author Adriano Silva
	 * @param  dataHoraCotacao
	 * @return dataUtilAnterior
	 */
	public static String retornaDiaUtilAnteriorA(String dataHoraCotacao) {
		dataHoraCotacao = dataHoraCotacao.replace("'", "");
		LocalDate date = LocalDate.parse(dataHoraCotacao, DateTimeFormatter.ofPattern("MM-dd-yyyy"));
		LocalDate diaAnterior = retornaDiaUtilAnterior(date);

		String dataUtilAnterior = diaAnterior.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));

		return new StringBuilder().append("'").append(dataUtilAnterior).append("'").toString();
	}

	/**
	 * Método que usa recursividade para retorna o dia útil em relação a data informada
	 *  
	 * @author Adriano Silva
	 * @param  date - Uma data
	 * @return dataAnterior
	 */
	private static LocalDate retornaDiaUtilAnterior(LocalDate date) {
		LocalDate diaAnterior = date.minusDays(1);

		if (verificaSeEhFimDeSemana(diaAnterior)) {
			diaAnterior = retornaDiaUtilAnterior(diaAnterior);
		}
		return diaAnterior;
	}

	/**
	 * Método que verifica se o dia de uma data é no final de semana
	 *  
	 * @author Adriano Silva
	 * @param  diaAnterior - A data que será verificada
	 * @return true - se for final de semana
	 */
	public static boolean verificaSeEhFimDeSemana(LocalDate diaAnterior) {
		return diaAnterior.getDayOfWeek() == DayOfWeek.SATURDAY || diaAnterior.getDayOfWeek() == DayOfWeek.SUNDAY;
	}

	/**
	 * Método que verifica se o dia de uma data é no final de semana
	 * Aqui a data é String e precisa ser convertida em local date
	 *  
	 * @author Adriano Silva
	 * @param  diaAnterior - A data que será verificada
	 * @return true - se for final de semana
	 */
	public static boolean verificaSeEhFimDeSemana(String dataHoraCotacao) {
		dataHoraCotacao = dataHoraCotacao.replace("'", "");
		LocalDate date = LocalDate.parse(dataHoraCotacao, DateTimeFormatter.ofPattern("MM-dd-yyyy"));
		return date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY;
	}

	/**
	 * Verifica se uma data é válida
	 *  
	 * @author Adriano Silva
	 * @param  dataCotacao - A data que será verificada
	 * @return true - se for válida
	 */
	public static boolean isValid(String dataCotacao) {
		try {
			dataCotacao = dataCotacao.replace("'", "");
			LocalDate.parse(dataCotacao, DateTimeFormatter.ofPattern("MM-dd-yyyy"));
			return true;
		} catch (DateTimeParseException e) {
			return false;
		}
	}

}
