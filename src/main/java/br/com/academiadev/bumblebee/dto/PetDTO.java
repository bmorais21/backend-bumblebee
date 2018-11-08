package br.com.academiadev.bumblebee.dto;

import br.com.academiadev.bumblebee.enums.Categoria;
import br.com.academiadev.bumblebee.enums.Especie;
import br.com.academiadev.bumblebee.enums.Porte;
import br.com.academiadev.bumblebee.model.Localizacao;
import br.com.academiadev.bumblebee.model.Usuario;

import java.util.Date;

public class PetDTO {

    private String id;
    private String nome;
    private String descricao;
    private String Sexo;
    private Date dataPostagem;
    private Usuario usuario;
    private Localizacao localizacao;
    private Categoria categoria;
    private Porte porte;
    private Especie especie;
    private Boolean excluido;
}
