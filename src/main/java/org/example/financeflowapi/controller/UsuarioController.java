package org.example.financeflowapi.controller;

import org.example.financeflowapi.domain.model.repository.service.exception.service.UsuarioService;
import org.example.financeflowapi.dto.usuario.titulo.centrodecusto.usuario.UsuarioRequestDto;
import org.example.financeflowapi.dto.usuario.titulo.centrodecusto.usuario.UsuarioResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDto>> obterTodos() {
        return ResponseEntity.ok(usuarioService.obterTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> obterPorID(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.obterPorId(id));
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDto> cadastrar(@RequestBody UsuarioRequestDto usuarioRequestDto) {
        UsuarioResponseDto usuario = usuarioService.cadastrar(usuarioRequestDto);
        return new ResponseEntity<>(usuario, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> atualizar(@PathVariable Long id, @RequestBody UsuarioRequestDto usuarioRequestDto) {
        UsuarioResponseDto usuario = usuarioService.atualizar(id, usuarioRequestDto);
        return ResponseEntity.ok(usuario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id){
        usuarioService.remover(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
