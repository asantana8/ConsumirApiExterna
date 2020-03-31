package br.com.apiExterna.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.apiExterna.domain.EnderecoResumido;
import br.com.apiExterna.dto.EnderecoDTO;
import br.com.apiExterna.dto.EnderecoNewDTO;
import br.com.apiExterna.repository.EnderecoResumidoRepository;

@Service
public class EnderecoService {

	@Autowired
	private EnderecoResumidoRepository repository;

	RestTemplate restTemplate = new RestTemplate();
	HttpHeaders headers = new HttpHeaders();

	public EnderecoDTO findById(Integer id) {
		return restTemplate.getForObject("http://localhost:8080/enderecos/pesquisar/" + id, EnderecoDTO.class);
	}

	public EnderecoDTO[] findAll() {
		return restTemplate.getForEntity("http://localhost:8080/enderecos/listar", EnderecoDTO[].class).getBody();
	}

	public ResponseEntity<EnderecoDTO> pesquisarBairro(EnderecoDTO obj) {

		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<EnderecoDTO> request = new HttpEntity<>(obj, headers);
		return restTemplate.postForEntity("http://localhost:8080/enderecos/pesquisar/bairro", request,
				EnderecoDTO.class);
	}

	public ResponseEntity<EnderecoNewDTO> insert(EnderecoNewDTO obj) {

		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<EnderecoNewDTO> request = new HttpEntity<>(obj, headers);
		return restTemplate.postForEntity("http://localhost:8080/enderecos/salvar", request, EnderecoNewDTO.class);
	}

	// Com o resultado da consulta na Api externa
	// Será alimentada a tebla com alguns dados
	public EnderecoResumido inserindoEnderecoExterno() {

		EnderecoDTO[] enderecos = restTemplate
				.getForEntity("http://localhost:8080/enderecos/listar", EnderecoDTO[].class).getBody();

		EnderecoResumido end = null;

		if (enderecos.length != 0) {
			for (int i = 0; i < enderecos.length; i++) {

				end = new EnderecoResumido(null, enderecos[i].getId(), enderecos[i].getLogradouro(),
						enderecos[i].getBairro(), enderecos[i].getCidade().getNome(),
						enderecos[i].getCidade().getEstado().getNome());

				// Aqui ficará todas as regras de negocio
				// antes de armazenar, atualmente consulta
				// e insere todos os dados retornadoda base externaa

				end.setId(null);
				end = repository.save(end);

			}
		}
		return end;

	}

}
