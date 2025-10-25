package com.femt.inventory_management.models.ventas;

import com.femt.inventory_management.models.inventario.InventarioProducto;
import com.femt.inventory_management.models.seguridad.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "ventas_pedido")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @Column(name="fecha_creada",nullable = true) // Cambiar en produccion
    private LocalDate fechaCreada;

    @NonNull
    @Column(name = "fecha_entrega", nullable = true) // Cambiar en produccion
    private LocalDate fechaEntrega;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_producto", nullable = false)
    private InventarioProducto inventarioProducto;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    //Acceder a las ventas de un pedido
    @NonNull
    @OneToMany(
            mappedBy = "pedido",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<Venta> ventas = new ArrayList<>();
}
