package br.com.apiExterna.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.apiExterna.service.EnderecoService;

@Component
@EnableScheduling
public class Util {

	@Autowired
	private EnderecoService servico;

	// Tempo para realização de agendamento de tarefas
	public final static long SEGUNDO = 1000;
	public final static long MINUTO = SEGUNDO * 60;
	public final static long HORA = MINUTO * 60;

	// Metos a serem execultado
	// Automaticamente
	@Scheduled(fixedDelay = MINUTO)
	public void updateAllScreensScheduled() {
		servico.inserindoEnderecoExterno();
	}

}
