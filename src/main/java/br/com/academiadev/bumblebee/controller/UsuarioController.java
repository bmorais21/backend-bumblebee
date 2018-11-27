package br.com.academiadev.bumblebee.controller;

import br.com.academiadev.bumblebee.dto.Usuario.UsuarioDTO;
import br.com.academiadev.bumblebee.dto.Usuario.UsuarioDTOResponse;
import br.com.academiadev.bumblebee.exception.ObjectNotFoundException;
import br.com.academiadev.bumblebee.mapper.UsuarioMapper;
import br.com.academiadev.bumblebee.model.Usuario;
import br.com.academiadev.bumblebee.repository.UsuarioRepository;
import br.com.academiadev.bumblebee.service.UsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/usuario")
@Api(description = "Usuarios")
public class UsuarioController{

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private UsuarioService usuarioService;

    @ApiOperation(value = "Retorna uma usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Usuario encontrado com sucesso")
    })
    @GetMapping("/{id}")
    public UsuarioDTOResponse buscarPor(@PathVariable Long id) throws ObjectNotFoundException {
        Usuario usuario =  repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Usuário com id " + id + " não encontrado"));
        UsuarioDTOResponse usuarioDTOResponse = usuarioMapper.toDTOResponse(usuario);
        return usuarioDTOResponse;
    }

    @ApiOperation(value = "Cria um Usuário")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Usuário criado com sucesso")
    })
    @PostMapping
    public UsuarioDTOResponse criar(@RequestBody @Valid UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioMapper.toEntity(usuarioDTO);
        repository.save(usuario);
        UsuarioDTOResponse usuarioDTOResponse = usuarioMapper.toDTOResponse(usuario);
        return usuarioDTOResponse;
    }

    @ApiOperation(value = "Buscar todos os usuários")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Usuários encontrados com sucesso")
    })
    @GetMapping("/usuarios")
    public List<UsuarioDTOResponse> buscarTodos() {
        List<Usuario> listaUsuario = usuarioService.findAll();
        return usuarioMapper.toDTOResponse(listaUsuario);
    }

    @ApiOperation(value = "Deleta um Usuário")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Usuario deletado com sucesso")
    })
    @DeleteMapping("/{id}")
    public UsuarioDTOResponse deletar(@PathVariable Long id) throws ObjectNotFoundException {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Usuário com id " + id + " não encontrado"));
        usuario.setExcluido(Boolean.TRUE);
        repository.save(usuario);
        UsuarioDTOResponse usuarioDTOResponse = usuarioMapper.toDTOResponse(usuario);
        return usuarioDTOResponse;
    }

}
