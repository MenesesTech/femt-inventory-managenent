package com.femt.inventory_management.models.ventas;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ventas_cliente")
public class VentasCliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre_razon_social", nullable = false, length = 30)
    private String nombreRazonSocial;

    @Column(nullable = false, length = 30)
    private String apellido;

    @Column(nullable = false, length = 50)
    private String direccion;

    @Column(nullable = false, length = 9)
    private String telefono;

    @Column(nullable = false, length = 10)
    private String ruc;

    @Column(nullable = false, length = 8)
    private String dni;

    // Acceder desde clientes a sus pedidos
    @OneToMany(
            mappedBy = "cliente",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @JsonIgnore
    private List<VentasPedido> pedidos = new ArrayList<>();

    public void addPedido(VentasPedido pedido){
        pedidos.add(pedido);
        pedido.setCliente(this);
    }

    public void removePedido(VentasPedido pedido) {
        pedidos.remove(pedido);
        pedido.setCliente(null);
    }
}
