package br.com.comven.corews.transacao.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

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
@Entity(name = "HORARIO_BLOQUEIO")
@Table
public class HorarioBloqueio {
	
	private static final long serialVersionUID = 637269730761953994L;
	
	private Integer idHorarioBloqueio;
	private Integer diaSemana;
	private String horaInicio;
	private String horaFim;
	private String flgOnline;
	private String flgBatch;
	private Date dataModificacao;
	private Usuario usuarioModificacao;
	private Double dHoraInicio;
	private Double dHoraFim;
	
	@Id
	@Column(name = "IDBLOQUEIO", unique = true, nullable = false, precision = 22, scale = 0)
	@GeneratedValue(generator="HORARIO_BLOQUEIO_ID")
    @SequenceGenerator(name="HORARIO_BLOQUEIO_ID",sequenceName="SEQ_HORARIO_BLOQUEIO", allocationSize=1)
	public Integer getIdHorarioBloqueio() {
		return idHorarioBloqueio;
	}
	
	public void setIdHorarioBloqueio(Integer idHorarioBloqueio) {
		this.idHorarioBloqueio = idHorarioBloqueio;
	}
	
	@Column(name = "DIA_SEMANA", nullable = false, precision = 22, scale = 0)
	public Integer getDiaSemana() {
		return diaSemana;
	}
	
	public void setDiaSemana(Integer diaSemana) {
		this.diaSemana = diaSemana;
	}
	
	@Column(name = "HORA_INICIO", length = 4)
	public String getHoraInicio() {
		return horaInicio;
	}
	
	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}
	
	@Column(name = "HORA_FIM", length = 4)
	public String getHoraFim() {
		return horaFim;
	}
	
	public void setHoraFim(String horaFim) {
		this.horaFim = horaFim;
	}
	
	@Column(name = "FLG_ONLINE", length = 1)
	public String getFlgOnline() {
		return flgOnline;
	}
	
	public void setFlgOnline(String flgOnline) {
		this.flgOnline = flgOnline;
	}
	
	@Column(name = "FLG_BATCH", length = 1)
	public String getFlgBatch() {
		return flgBatch;
	}
	
	public void setFlgBatch(String flgBatch) {
		this.flgBatch = flgBatch;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_MODIFICACAO")	
	public Date getDataModificacao() {
		return dataModificacao;
	}
	
	public void setDataModificacao(Date dataModificacao) {
		this.dataModificacao = dataModificacao;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "FK_USUARIO_MODIFICACAO", nullable = false)
	public Usuario getUsuarioModificacao() {
		return usuarioModificacao;
	}
	
	public void setUsuarioModificacao(Usuario usuarioModificacao) {
		this.usuarioModificacao = usuarioModificacao;
	}

	@Transient
	public Double getDHoraInicio() {
		return dHoraInicio;
	}

	public void setDHoraInicio(Double horaInicio) {
		dHoraInicio = horaInicio;
	}

	@Transient
	public Double getDHoraFim() {
		return dHoraFim;
	}

	public void setDHoraFim(Double horaFim) {
		dHoraFim = horaFim;
	}
}