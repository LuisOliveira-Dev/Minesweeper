package br.com.cod3r.cm.vision;

import br.com.cod3r.cm.modelo.Tabuleiro;

public class Temp {

	public static void main(String[] args) {
			
		Tabuleiro tabuleiro = new Tabuleiro(3, 3, 9);
		
		tabuleiro.registrarObservador(e -> {
			if(e.isWin()) {
				System.out.println("You Win!");
			}else {
				System.out.println("Game Over");
			}
		});
		
		tabuleiro.alternarMarking(0, 0);
		tabuleiro.alternarMarking(0, 1);
		tabuleiro.alternarMarking(0, 2);
		tabuleiro.alternarMarking(1, 0);
		tabuleiro.alternarMarking(1, 1);
		tabuleiro.alternarMarking(1, 2);
		tabuleiro.alternarMarking(2, 0);
		tabuleiro.alternarMarking(2, 1);
		tabuleiro.abrir(2, 2);
		tabuleiro.alternarMarking(2, 2);
	}
}
