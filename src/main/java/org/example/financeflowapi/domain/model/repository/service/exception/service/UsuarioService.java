package org.example.financeflowapi.domain.model.repository.service.exception.service;

import org.example.financeflowapi.domain.model.repository.service.exception.model.Usuario;
import org.example.financeflowapi.domain.model.repository.service.exception.repository.UsuarioRepository;
import org.example.financeflowapi.dto.usuario.titulo.centrodecusto.usuario.UsuarioRequestDto;
import org.example.financeflowapi.dto.usuario.titulo.centrodecusto.usuario.UsuarioResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService implements ICRUDService<UsuarioRequestDto, UsuarioResponseDto>{

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<UsuarioResponseDto> obterTodos() {
        List<Usuario> usuarios = usuarioRepository.findAll();

        List<UsuarioResponseDto> usuariosDto = new ArrayList<>();
        for(Usuario usuario : usuarios){
            UsuarioResponseDto dto = (mapper.map(usuario, UsuarioResponseDto.class));
            usuariosDto.add(dto);
        }

        return usuariosDto;
    }

    @Override
    public UsuarioResponseDto obterPorId(Long id) {
        Optional<Usuario> optUsuario = usuarioRepository.findById(id);

        if(optUsuario.isEmpty()){
            //exception
        }

        return mapper.map(optUsuario.get(), UsuarioResponseDto.class);
    }

    @Override
    public UsuarioResponseDto cadastrar(UsuarioRequestDto dto) {
        return null;
    }

    @Override
    public UsuarioResponseDto atualizar(Long id, UsuarioRequestDto dto) {
        return null;
    }

    @Override
    public void remover(Long id) {

    }
}
