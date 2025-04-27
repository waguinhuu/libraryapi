package io.github.waguinhuu.libraryapi.repository;

import io.github.waguinhuu.libraryapi.model.Autor;
import io.github.waguinhuu.libraryapi.model.GeneroLivro;
import io.github.waguinhuu.libraryapi.model.Livro;
import org.hibernate.query.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @see LivroRepositoryTest
 */
public interface LivroRepository extends JpaRepository<Livro, UUID>, JpaSpecificationExecutor {


    // QUERY METHOD
    // SELECT * FROM livro WHERE id_autor = id do autor
    List<Livro> findByAutor(Autor autor);

    // SELECT * FROM livro WHERE titulo = titulo
    List<Livro> findByTitulo(String titulo);

    // SELECT * FROM livro WHERE isbn = isbn
    Optional<Livro> findByIsbn(String isbn);

    // SELECT * FROM livro WHERE titulo = ? and preco = ?
    List<Livro> findByTituloAndPreco(String titulo, BigDecimal preco);

    // select * from livro where titulo = ? or isbn = ?
    List<Livro> findByTituloOrIsbn(String titulo, String isbn);

    // JPQL ->  referencia as entidades e as propriedades
    @Query("select l from Livro as l order by l.titulo, l.preco ")
    List<Livro> listarTodosOrdenarPorTituloAndPreco();


    /**
     * select a.*
     * from livro l
     * join autor a on a.id = l.id_autor
     */
    @Query("select a from Livro l join l.autor a ")
    List<Autor> listarAutoresDosLivros();

    // select distinct l.* from livro l
    @Query("select distinct l.titulo from Livro l ")
    List<String> listarNomesDiferentesLivros();


    @Query("""
        select l.genero
        from Livro l
        join l.autor a
        where a.nacionalidade = 'Brasileiro'
        order by l.genero
""")
    List<String> listarGenerosAutoresBrasileiros();



    // named parameters -> parametros nomeados
    @Query("select l from Livro l where l.genero = :genero order by :paramOrdenacao")
    List<Livro> findByGenero(
            @Param("genero") GeneroLivro generoLivro,
            @Param("paramOrdenacao") String nomePropriedade
    );

    // positional parameters -> parametros posicionais
    @Query("select l from Livro l where l.genero = ?1 order by ?2")
    List<Livro> findByGeneroPositionalParameters(
            GeneroLivro generoLivro,
            String nomePropriedade
    );


    @Modifying
    @Transactional
    @Query("delete from Livro where genero = ?1 ")
    void deleteByGenero(GeneroLivro generoLivro);


    @Modifying
    @Transactional
    @Query("update Livro set dataPublicacao = ?1")
    void updateDataPublicacao(LocalDate novaData);

    boolean existsByAutor(Autor autor);

}
