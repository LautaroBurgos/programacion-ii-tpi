package ar.edu.tfi.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Envio {
    private Long id;
    private Boolean eliminado = false;
    private String tracking;
    private EmpresaEnvio empresa;
    private TipoEnvio tipo;
    private BigDecimal costo;
    private LocalDate fechaDespacho;
    private LocalDate fechaEstimada;
    private EstadoEnvio estado;

    public Envio() {}

    public Envio(Long id, Boolean eliminado, String tracking, EmpresaEnvio empresa, TipoEnvio tipo,
                 BigDecimal costo, LocalDate fechaDespacho, LocalDate fechaEstimada, EstadoEnvio estado) {
        this.id = id;
        this.eliminado = eliminado;
        this.tracking = tracking;
        this.empresa = empresa;
        this.tipo = tipo;
        this.costo = costo;
        this.fechaDespacho = fechaDespacho;
        this.fechaEstimada = fechaEstimada;
        this.estado = estado;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Boolean getEliminado() { return eliminado; }
    public void setEliminado(Boolean eliminado) { this.eliminado = eliminado; }
    public String getTracking() { return tracking; }
    public void setTracking(String tracking) { this.tracking = tracking; }
    public EmpresaEnvio getEmpresa() { return empresa; }
    public void setEmpresa(EmpresaEnvio empresa) { this.empresa = empresa; }
    public TipoEnvio getTipo() { return tipo; }
    public void setTipo(TipoEnvio tipo) { this.tipo = tipo; }
    public BigDecimal getCosto() { return costo; }
    public void setCosto(BigDecimal costo) { this.costo = costo; }
    public LocalDate getFechaDespacho() { return fechaDespacho; }
    public void setFechaDespacho(LocalDate fechaDespacho) { this.fechaDespacho = fechaDespacho; }
    public LocalDate getFechaEstimada() { return fechaEstimada; }
    public void setFechaEstimada(LocalDate fechaEstimada) { this.fechaEstimada = fechaEstimada; }
    public EstadoEnvio getEstado() { return estado; }
    public void setEstado(EstadoEnvio estado) { this.estado = estado; }

    @Override
    public String toString() {
        return "Envio{id=" + id + ", tracking='" + tracking + "', empresa=" + empresa + ", tipo=" + tipo +
                ", costo=" + costo + ", fechaDespacho=" + fechaDespacho + ", fechaEstimada=" + fechaEstimada +
                ", estado=" + estado + ", eliminado=" + eliminado + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Envio)) return false;
        Envio envio = (Envio) o;
        return Objects.equals(id, envio.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }
}
