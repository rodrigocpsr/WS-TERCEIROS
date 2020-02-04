package br.com.comven.corews.transacao.util.enumerator;

public enum ESiglaEstado {

	AC("AC", "Acre"), 
	AL("AL", "Alagoas"), 
	AP("AP", "Amapá"), 
	AM("AM", "Amazonas"), 
	BA("BA", "Bahia"), 
	CE("CE", "Ceará"), 
	DF("DF", "Distrito Federal"), 
	ES("ES", "Espírito Santo"), 
	GO("GO", "Goiás"), 
	MA("MA", "Maranhão"), 
	MT("MT", "Mato Grosso"), 
	MS("MS", "Mato Grosso do Sul"), 
	MG("MG", "Minas Gerais"), 
	PA("PA", "Pará"),
	PB("PB", "Paraíba"), 
	PR("PR", "Paraná"), 
	PE("PE", "Pernambuco"), 
	PI("PI", "Piauí"), 
	RJ("RJ", "Rio de Janeiro"), 
	RN("RN", "Rio Grande do Norte"), 
	RS("RS", "Rio Grande do Sul"),
	RO("RO", "Rondônia"), 
	RR("RR", "Roraima"), 
	SC("SC", "Santa Catarina"), 
	SP("SP", "São Paulo"), 
	SE("SE", "Sergipe"), 
	TO("TO", "Tocantins");

	private String uf;
	private String nome;

	private ESiglaEstado(String uf, String nome) {
		this.uf = uf;
		this.nome = nome;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public static ESiglaEstado fromId(String uf) {
		for (ESiglaEstado value : values()) {
			if (uf != null && value.uf.equals(uf)) {
				return value;
			}
		}
		return null;
	}

}