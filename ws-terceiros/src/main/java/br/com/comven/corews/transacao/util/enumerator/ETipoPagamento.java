package br.com.comven.corews.transacao.util.enumerator;

public enum ETipoPagamento {

		PREPAGO("PRE"),
		POSPAGO("POS");

		private String sigla;
		
		ETipoPagamento(String sigla){
			this.sigla = sigla;
		}

		public String getSigla() {
			return this.sigla;
		}
		
		public void setSigla(String sigla) {
			this.sigla = sigla;
		}

}
