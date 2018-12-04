package br.com.academiadev.bumblebee.controller;

import br.com.academiadev.bumblebee.dto.Cidade.CidadeDTO;
import br.com.academiadev.bumblebee.dto.Cidade.CidadeDTOResponse;
import br.com.academiadev.bumblebee.exception.ObjectNotFoundException;
import br.com.academiadev.bumblebee.mapper.CidadeMapper;
import br.com.academiadev.bumblebee.model.Cidade;
import br.com.academiadev.bumblebee.model.Uf;
import br.com.academiadev.bumblebee.service.CidadeService;
import br.com.academiadev.bumblebee.service.UfService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cidade")
@Api(description = "Cidades")
public class CidadeController {

    @Autowired
    private CidadeMapper cidadeMapper;

    @Autowired
    private CidadeService cidadeService;

    @Autowired
    private UfService ufService;

    @ApiOperation(value = "Retorna um cidade")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Cidade encontrado com sucesso")
    })
    @GetMapping("/{id}")
    public CidadeDTOResponse buscarPor(@PathVariable Long id) throws ObjectNotFoundException {
        Cidade cidade = cidadeService.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Cidade com id " + id + " não encontrado"));
        return cidadeMapper.toDTOResponse(cidade);
    }

    @ApiOperation(value = "Cria uma cidade")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Cidade criada com sucesso")
    })
    @PostMapping("/{uf}")
    public CidadeDTOResponse criar(@RequestBody @Valid CidadeDTO cidadeDTO, @PathVariable(value = "uf") Long idUf) {
        Uf uf = ufService.findById(idUf).orElseThrow(() -> new ObjectNotFoundException("Uf não encontrada"));
        Cidade cidade = cidadeMapper.toEntity(cidadeDTO);
        cidade.setUf(uf);
        cidadeService.save(cidade);
        return cidadeMapper.toDTOResponse(cidade);
    }

    @ApiOperation(value = "Buscar todas as cidades")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Cidades encontradas com sucesso")
    })
    @GetMapping("/cidades")
    public List<CidadeDTOResponse> buscarTodos() {
        List<Cidade> listaCidade = cidadeService.findAll();
        return cidadeMapper.toDTOResponse(listaCidade);
    }

    @ApiOperation(value = "Deleta uma cidade")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Cidade deletada com sucesso")
    })
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) throws ObjectNotFoundException {
        Cidade cidade = cidadeService.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Cidade com id " + id + " não encontrado"));
        cidade.setExcluido(Boolean.TRUE);
        cidadeService.save(cidade);
    }

}
