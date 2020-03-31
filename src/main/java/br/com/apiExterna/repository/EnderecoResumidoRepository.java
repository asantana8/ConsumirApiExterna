package br.com.apiExterna.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.apiExterna.domain.EnderecoResumido;

@Repository
public interface EnderecoResumidoRepository extends JpaRepository<EnderecoResumido, Integer> {

}
