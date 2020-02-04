package br.com.comven.corews.transacao.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity(name = "VW_TRANSACOES_BLOQUEADAS")
@Table
public class VwTransacoesBloqueadas implements java.io.Serializable {

    private static final long serialVersionUID = -3018468878245664751L;

    @Id
    @Column(name = "IDTRANSACAO", unique = true, nullable = false, precision = 22, scale = 0)
    private Integer IdTransacao;
    
    @Column(name = "FK_CODIGO_ERRO", nullable = false, length = 3)
    private Integer codigoErro;
    
    @Column(name = "PLACA", nullable = false, length = 7)
    private String placa;
    
    @Column(name = "RENAVAM", nullable = false, precision = 11, scale = 0)
    private Long renavam;
    
    @Column(name = "NUM_IDENT_PROPRIETARIO", nullable = false, length = 14)
	private String numIdentProprietario;
    
    @Column(name = "NUM_IDENT_OPERADOR", nullable = false, length = 14)
	private String numIdentOperador;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATA_BLOQUEIO", nullable = false)
    private Date dataBloqueio;
    
    @Column(name = "DATA_BLOQUEIO_FORMATADA", nullable = false)
    private String dataBloqueioFormatada;
     
	
}
