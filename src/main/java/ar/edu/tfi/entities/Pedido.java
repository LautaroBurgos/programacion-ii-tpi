package ar.edu.tfi.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Pedido {
    private Long id;
    private Boolean eliminado = false;
    private String numero;
    private LocalDate fecha;
    private String clienteNombre;
    private BigDecimal total;
    private EstadoPedido estado;
    private Envio envio;

    public Pedido() {}

    public Pedido(Long id, Boolean eliminado, String numero, LocalDate fecha, String clienteNombre,
                  BigDecimal total, EstadoPedido estado, Envio envio) {
        this.id = id;
        this.eliminado = eliminado;
        this.numero = numero;
        this.fecha = fecha;
        this.clienteNombre = clienteNombre;
        this.total = total;
        this.estado = estado;
        this.envio = envio;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Boolean getEliminado() { return eliminado; }
    public void setEliminado(Boolean eliminado) { this.eliminado = eliminado; }
    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public String getClienteNombre() { return clienteNombre; }
    public void setClienteNombre(String clienteNombre) { this.clienteNombre = clienteNombre; }
    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }
    public EstadoPedido getEstado() { return estado; }
    public void setEstado(EstadoPedido estado) { this.estado = estado; }
    public Envio getEnvio() { return envio; }
    public void setEnvio(Envio envio) { this.envio = envio; }

    @Override
    public String toString() {
        return "Pedido{id=" + id + ", numero='" + numero + "', fecha=" + fecha + ", clienteNombre='" + clienteNombre +
                "', total=" + total + ", estado=" + estado + ", envio=" + (envio!=null? envio.getId() : "null") +
                ", eliminado=" + eliminado + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pedido)) return false;
        Pedido pedido = (Pedido) o;
        return Objects.equals(id, pedido.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }
}
