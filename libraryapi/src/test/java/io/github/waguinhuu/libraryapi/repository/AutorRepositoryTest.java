package io.github.waguinhuu.libraryapi.repository;

import io.github.waguinhuu.libraryapi.model.Autor;
import io.github.waguinhuu.libraryapi.model.GeneroLivro;
import io.github.waguinhuu.libraryapi.model.Livro;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    AutorRepository repository;

    @Autowired
    LivroRepository livroRepository;

    @Test
    public void salvarTest(){
        Autor autor = new Autor();
        autor.setNome("Jose");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(1931,2,10));

        var autorSalvo = repository.save(autor);
        System.out.println("Autor salvo " + autorSalvo);
    }


    @Test
    public void atualizarTest(){

        var id = UUID.fromString("d93e4a8c-1927-403b-9b94-faabe95242b0");

        Optional<Autor> possivelAutor = repository.findById(id);

        if(possivelAutor.isPresent()){
            Autor autorEncontrado = possivelAutor.get();
            System.out.println("dados do autor: ");
            System.out.println(autorEncontrado);

            autorEncontrado.setDataNascimento(LocalDate.of(1960,6,5));
            autorEncontrado.setNome("Jose");

            repository.save(autorEncontrado);

        }
    }

    @Test
    public void listarTest(){
        List<Autor> lista = repository.findAll();
        lista.forEach(System.out::println);
    }

    @Test
    public void countTest(){
        System.out.println("Contagem de autores: " + repository.count());
    }

    @Test
    public void deletarTest(){
        var id = UUID.fromString("920bbcd3-404f-4f30-9a03-4fa14a9aa41d");
        repository.deleteById(id);
    }


     @Test
    public void deleteTest(){
        var id = UUID.fromString("e72621f4-2b6d-4f0d-b53f-87b3c0bbb127");
        var Jose = repository.findById(id).get();
        repository.delete(Jose);
    }

    @Test
    void salvarAutorComLivrosTest(){
        Autor autor = new Autor();
        autor.setNome("Antonio");
        autor.setNacionalidade("Americano");
        autor.setDataNascimento(LocalDate.of(1970,10,24));

        Livro livro = new Livro();
        livro.setIsbn("20283-32113");
        livro.setPreco(BigDecimal.valueOf(330));
        livro.setGenero(GeneroLivro.MISTERIO);
        livro.setTitulo("Harry Potter");
        livro.setDataPublicacao(LocalDate.of(2004,4,20));
        livro.setAutor(autor);

        Livro livro2 = new Livro();
        livro2.setIsbn("242442-321513");
        livro2.setPreco(BigDecimal.valueOf(110));
        livro2.setGenero(GeneroLivro.FANTASIA);
        livro2.setTitulo("A bela e a fera");
        livro2.setDataPublicacao(LocalDate.of(2010,5,10));
        livro2.setAutor(autor);

        autor.setLivros(new ArrayList<>());
        autor.getLivros().add(livro);
        autor.getLivros().add(livro2);

        repository.save(autor);

        livroRepository.saveAll(autor.getLivros());

    }

    @Test
    void listarLivrosAutor(){
        var idAutor = UUID.fromString("e3e6a0ac-7da5-4fa4-8970-bedfb77212de");
        var autor = repository.findById(idAutor).get();

        // buscar os livros do autor
        List<Livro> livrosLista = livroRepository.findByAutor(autor);
        autor.setLivros(livrosLista);

        autor.getLivros().forEach(System.out::println);
    }

}
