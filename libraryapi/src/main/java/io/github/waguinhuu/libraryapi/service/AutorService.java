package io.github.waguinhuu.libraryapi.service;

import io.github.waguinhuu.libraryapi.exceptions.OperacaoNaoPermitidaException;
import io.github.waguinhuu.libraryapi.model.Autor;
import io.github.waguinhuu.libraryapi.repository.AutorRepository;
import io.github.waguinhuu.libraryapi.repository.LivroRepository;
import io.github.waguinhuu.libraryapi.validator.AutorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AutorService {

    private final AutorRepository repository;
    private final AutorValidator validator;
    private final LivroRepository livroRepository;

    public Autor salvar(Autor autor){
        validator.validar(autor);
        return repository.save(autor);
    }

    public void atualizar(Autor autor){
        if(autor.getId() == null){
            throw new IllegalArgumentException("Para atualizar, é necessario que o autor já esteja salvo na base.");
        }
        validator.validar(autor);
        repository.save(autor);
    }

    public Optional<Autor> obterPorId(UUID id){
        return repository.findById(id);
    }

    public void deletar(Autor autor) {
        if(possuiLivro(autor)){
            throw new OperacaoNaoPermitidaException(
                    "Não é permitido excluir um Autor que possui livros cadastrados!");
        }
        repository.delete(autor);
    }

    public List<Autor> pesquisa(String nome, String nacionalidade){

        if(nome == null && nacionalidade == null){
            return repository.findByNomeAndNacionalidade(nome, nacionalidade);
        }

        if (nome != null){
            return repository.findByNome(nome);
        }

        if(nacionalidade != null){
            return repository.findByNacionalidade(nacionalidade);
        }

        return repository.findAll();

    }

    public List<Autor> pesquisaByExample(String nome, String nacionalidade){
        var autor = new Autor();
        autor.setNome(nome);
        autor.setNacionalidade(nacionalidade);
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnorePaths("id", "dataNascimento", "dataCadastro")
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<Autor> autorExample = Example.of(autor, matcher);

        return repository.findAll(autorExample);
    }

    public boolean possuiLivro(Autor autor){
        return livroRepository.existsByAutor(autor);
    }
}
