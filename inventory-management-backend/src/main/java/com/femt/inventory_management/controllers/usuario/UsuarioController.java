package com.femt.inventory_management.controllers.usuario;

import com.femt.inventory_management.dto.request.UsuarioRequestDTO;
import com.femt.inventory_management.dto.response.UsuarioResponseDTO;
import com.femt.inventory_management.models.seguridad.Usuario;
import com.femt.inventory_management.repository.SegUsuarioRepository;
import com.femt.inventory_management.service.seguridad.imp.UsuarioServiceImp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/user")
public class UsuarioController {
    private final UsuarioServiceImp usuarioServiceImp;
    private final SegUsuarioRepository segUsuarioRepository;

    public UsuarioController(UsuarioServiceImp usuarioServiceImp, SegUsuarioRepository segUsuarioRepository) {
        this.usuarioServiceImp = usuarioServiceImp;
        this.segUsuarioRepository = segUsuarioRepository;
    }

    // Listar todos los usuarios
    @GetMapping("/all")
    public ResponseEntity<?> listarUsuarios(){
        try{
            List<UsuarioResponseDTO> usuarios = usuarioServiceImp.listarUsuarios();
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Error al traer a todos los usuarios: "+e.getMessage());
        }
    }

    // Modificar usuario existente
    @PutMapping("/update")
    public ResponseEntity<?> modificarUsuario(@RequestBody UsuarioRequestDTO request) {
        try {
            Usuario usuarioActualizado = usuarioServiceImp.modificarUsuario(request);
            return ResponseEntity.ok(usuarioActualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error de validación: " + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body("Error al modificar el usuario: " + e.getMessage());
        }
    }

    // Eliminar usuario
    @DeleteMapping("/delete")
    public ResponseEntity<?> eliminarUsuario(@RequestBody Map<String, String> body) {
        String auth0Id = body.get("auth0Id");
        try {
            usuarioServiceImp.eliminarUsuario(auth0Id);
            return ResponseEntity.ok("Usuario eliminado correctamente: " + auth0Id);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body("Error al eliminar el usuario: " + e.getMessage());
        }
    }


}
