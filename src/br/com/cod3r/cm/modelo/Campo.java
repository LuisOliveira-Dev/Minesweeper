package br.com.cod3r.cm.modelo;

import java.util.ArrayList;
import java.util.List;

public class Campo {

	private final int linha;
	private final int coluna;
	
	private boolean aberto; //contém valor falso por padrão
	private boolean minado; //contém valor falso por padrão
	private boolean marcado ;//indica que possui uma mina (contém valor falso por padrão)
	
	private List<Campo> vizinhos = new ArrayList<>();
	private List<CampoObservador> observador = new ArrayList<>();
	
	Campo(int linha, int coluna){
		this.linha = linha;
		this.coluna = coluna;
	}
	
	public void registerObserver(CampoObservador observer) {
		observador.add(observer);
	}
	
	private void notificarObserver(CampoEvento evento) {
		observador.stream()
		.forEach(o -> o.EventoOcorrencia(this, evento));
	}
	
	boolean adicionarVizinho(Campo vizinho)	{
		
		boolean linhaDiferente = linha != vizinho.linha;
		boolean colunaDiferente = linha != vizinho.coluna;
		boolean diagonal = linhaDiferente && colunaDiferente;
		
		int deltaLinha = Math.abs(linha - vizinho.linha); 
		int deltaColuna = Math.abs(coluna - vizinho.coluna);
		int deltaGeral = deltaColuna + deltaLinha;
		
		if(deltaGeral == 1 && !diagonal) {
			vizinhos.add(vizinho);
		
			return true;
			
		}else if(deltaGeral == 2 && diagonal) {
			vizinhos.add(vizinho);
			
			return true;
			
		}else {
			return false;
		}
	}
		//alternar marcação
	public void alternarMarking() {
		
		if(!aberto) {
			marcado = !marcado;
			
		if(marcado) {
			notificarObserver(CampoEvento.MARCAR);	
		}else {
			notificarObserver(CampoEvento.DESMARCAR);
		}
		}
		
	}
	
	boolean abrir() {
		
		if(!aberto && !marcado) {
			
			if(minado) {
				notificarObserver(CampoEvento.EXPLODIR);
				return true;
			}
			
			setAberto(true);
			
			
			if(vizinhancaSegura()) {
				vizinhos.forEach(V -> V.abrir());
			}
			
			return true;
		} else {
			return false;
			}
		
	}
	
	boolean vizinhancaSegura() {
		return vizinhos.stream().noneMatch(v -> v.minado);
	}
	
	void minar() {
		minado = true;
		
	}
		
	public boolean isMinado() {
		return minado;
	}
		
	public boolean isMarcado() { 
		return marcado;
	}
	
	void setAberto(boolean aberto) {
		this.aberto = aberto;
		
		if(aberto) {
			notificarObserver(CampoEvento.ABRIR);
		}
	}

	public boolean isAberto() {
		return aberto;
	}
	
	public boolean isFechado() {
		return !isAberto();
	}

	public int getLinha() {
		return linha;
	}

	public int getColuna() {
		return coluna;
	}
	
	boolean objetivoAlcancado() {
		boolean desvendado = !minado && aberto;
		boolean protegido = minado && marcado;
		
		return desvendado || protegido;
	}
	
	long minasNaVizinhanca() {
		
		//filtra os vizinhos minados
		return vizinhos.stream().filter(v -> v.minado).count();
	}
	
	void reiniciar() {
		aberto = false;
		minado = false;
		marcado = false;
		
	}
	

}


