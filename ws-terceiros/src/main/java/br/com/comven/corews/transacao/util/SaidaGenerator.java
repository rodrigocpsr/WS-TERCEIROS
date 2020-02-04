package br.com.comven.corews.transacao.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class SaidaGenerator {
	private List<RegistroSaida> registros;
	private String header;
	private String trailler;
	private String caminhoSaida;
	
	public SaidaGenerator() {
	}

	public SaidaGenerator(String header, String trailler, String caminhoSaida) {
		super();
		this.registros = new ArrayList<RegistroSaida>();
		this.header = header;
		this.trailler = trailler;
		this.caminhoSaida = caminhoSaida;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getTrailler() {
		return trailler;
	}

	public void setTrailler(String trailler) {
		this.trailler = trailler;
	}

	public String getCaminhoSaida() {
		return caminhoSaida;
	}

	public void setCaminhoSaida(String caminhoSaida) {
		this.caminhoSaida = caminhoSaida;
	}

	public List<RegistroSaida> getRegistros() {
		return registros;
	}

	public void salvar(int digitosRenavam) throws IOException{
		if (!getRegistros().isEmpty()){
			int c = 2;
			FileWriter fw = new FileWriter(caminhoSaida);
			PrintWriter pr = new PrintWriter(fw, true);			
			
			pr.print(getHeaderFormatado(header) + "\r\n");
			for (RegistroSaida rs : registros) {
				pr.print(getRegistroFormatado(rs, digitosRenavam) + "\r\n");
				c++;
			}
			
			String t[] = trailler.split(";");
			
			t[3] = Util.preencheCom(String.valueOf(c), "0", 6, 1, null);
			
			String novoTrailler = "";
			for (String a : t){
				novoTrailler += a + ";";
			}
			pr.print( getTraillerFormatado(novoTrailler) );
			
			//libera os recursos
			pr.close();
			fw.close();
		}
	}
	
	public String getHeaderFormatado(String h){
		String novo_h[] = h.split(";");
		
		novo_h[0] = Util.preencheCom(novo_h[0], "0", 1, 1, null);
		novo_h[1] = Util.preencheCom(novo_h[1], " ", 3, 2, null);
		novo_h[2] = Util.preencheCom(novo_h[2], "0", 14, 1, null);
		novo_h[3] = Util.preencheCom(novo_h[3], " ", 14, 2, null);
		novo_h[4] = Util.preencheCom(novo_h[4], "0", 5, 1, null);
		
		return 	novo_h[0] + ";" +
				novo_h[1] + ";" +
				novo_h[2] + ";" +
				novo_h[3] + ";" +
				novo_h[4] + ";";
	}
	
	public String getTraillerFormatado(String t){
		String novo_t[] = t.split(";");
		
		novo_t[0] = Util.preencheCom(novo_t[0], "0", 1, 1);
		novo_t[1] = Util.preencheCom(novo_t[1], " ", 14, 2);
		novo_t[2] = Util.preencheCom(novo_t[2], "0", 5, 1);
		novo_t[3] = Util.preencheCom(novo_t[3], "0", 9, 1);		

		
		return 	novo_t[0] + ";" +
				novo_t[1] + ";" +
				novo_t[2] + ";" +
				novo_t[3] + ";";	
	}
	
	public String getRegistroFormatado(RegistroSaida saida, int digitosRenavam) {
		
		if(digitosRenavam == 0)
		{
		
			/*
			 *  Left e right padding incluidos para
			 *  acerto de layout retorno para que o ITAU Mainframe nao tenha problemas 
			 */
			
			return	
					RegistroSaida.getTipoRegistro() + ";" + 
					Util.preencheCom( String.valueOf(saida.getDiaJuliano()), "0", 3, 1) + ";" +
					Util.preencheCom( String.valueOf(saida.getNumeroTransacaoComven()), "0", 12, 1) + ";" +
					Util.preencheCom( String.valueOf(saida.getRetornoTransacao()), "0", 6, 1) + ";" +
					Util.preencheCom( saida.getUfVeiculo(), " ", 2, 2) + ";" +
					Util.preencheCom( saida.getPlaca(), " ", 7, 2) + ";" +
					Util.preencheCom( saida.getRenavam(), "0", 9, 1) + ";" +
					Util.preencheCom(saida.getDataHoraTransacao(), "0", 14, 1) + ";";
		}
		else
		{
			return	
					RegistroSaida.getTipoRegistro() + ";" + 
					Util.preencheCom( String.valueOf(saida.getDiaJuliano()), "0", 3, 1) + ";" +
					Util.preencheCom( String.valueOf(saida.getNumeroTransacaoComven()), "0", 12, 1) + ";" +
					Util.preencheCom( String.valueOf(saida.getRetornoTransacao()), "0", 6, 1) + ";" +
					Util.preencheCom( saida.getUfVeiculo(), " ", 2, 2) + ";" +
					Util.preencheCom( saida.getPlaca(), " ", 7, 2) + ";" +
					Util.preencheCom( saida.getRenavam(), "0", 11, 1) + ";" +
					Util.preencheCom(saida.getDataHoraTransacao(), "0", 14, 1) + ";";
		}
	}	
}
