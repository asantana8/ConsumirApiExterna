package br.com.apiExterna.resource;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.apiExterna.dto.EnderecoDTO;
import br.com.apiExterna.dto.EnderecoNewDTO;
import br.com.apiExterna.service.EnderecoService;
import javassist.tools.rmi.ObjectNotFoundException;

//@Component
//@EnableScheduling
@RestController
@RequestMapping(value = "/enderecosExterno")
public class EnderecoResource {

	@Autowired
	private EnderecoService service;

	// Pesquisando endereço através do ID na APi
	// Externa, projeto nome endereço
	@GetMapping("/pesquisar/{id}")
	public EnderecoDTO findById(@PathVariable Integer id) throws ObjectNotFoundException {
		EnderecoDTO obj = service.findById(id);
		return obj;
	}

	// Listando todos os endereço na APi
	// Externa, projeto nome endereço
	@GetMapping("/listar")
	public ResponseEntity<EnderecoDTO[]> listarEndereco() {
		EnderecoDTO[] lista = service.findAll();
		return ResponseEntity.ok().body(lista);
	}

	// Pesquisando endereço através do bairro
	// na APi Externa, projeto nome endereço
	@PostMapping("/pesquisar/bairro")
	public ResponseEntity<EnderecoDTO> pesquisarBairro(@RequestBody EnderecoDTO objDto) {
		ResponseEntity<EnderecoDTO> obj = service.pesquisarBairro(objDto);
		return obj;
	}

	// Salvando endereco na APi
	// Externa, projeto nome endereço
	@PostMapping("/salvar")
	public ResponseEntity<Void> salvarEndereco(@Valid @RequestBody EnderecoNewDTO objDto) {
		service.insert(objDto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(objDto.getBairro())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

}
