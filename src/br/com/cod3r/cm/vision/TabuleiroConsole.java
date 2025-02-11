package br.com.cod3r.cm.vision;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import br.com.cod3r.cm.exception.ExitException;
import br.com.cod3r.cm.exception.ExplosaoException;
import br.com.cod3r.cm.modelo.Tabuleiro;

public class TabuleiroConsole {

	private Tabuleiro tabuleiro;
	private Scanner entrada = new Scanner(System.in);
	
	public TabuleiroConsole(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;
		
		executarJogo();
	}

	private void executarJogo() {
		try {
			boolean continuar = true;
			
			while(continuar) {
				gameLoop();
				 
				System.out.println("Outra partida? (S/n) ");
				String resposta = entrada.nextLine();
				
				if("n".equalsIgnoreCase(resposta)) {
					continuar = false;
				} else {
					tabuleiro.reiniciar();
				}
			}
			
		} catch(ExitException e) {
			System.out.println("Fim de Jogo");
		} finally {
			entrada.close();
		}
	}

	private void gameLoop() {
		try {
			
			 //caso o objetivo não seja alcançado
			while(!tabuleiro.objetivoAlcancado()) {
				
				//mostra o tabuleiro
				System.out.println(tabuleiro.toString()); 
				
				//pega o valor das cordenadas
				String digitado = capturarValorDigitado("Digite (x, y): "); 
				
				//converte o valor das cordenadas pra inteiro
				Iterator<Integer> xy = Arrays.stream(digitado.split(","))
					.map(e -> Integer.parseInt(e.trim())).iterator();
				
				//captura a opção de marcar ou desmarcar
				digitado = capturarValorDigitado("1 - Abrir ou 2 - (Des)Marcar: ");
				
				//nesta estrutura condicional, caso seja 1, x e y são abertos, caso seja 2, x e serão marcados
				if("1".equals(digitado)) {
					tabuleiro.abrir(xy.next(), xy.next());
					
				}else if("2".equals(digitado)) {
					tabuleiro.alternarMarking(xy.next(), xy.next());
				}
			}
			
			System.out.println(tabuleiro);
			System.out.println("You Win!");
		} catch(ExplosaoException e) {
			System.out.println(tabuleiro);
			System.out.println("Game Over");
		}
		
	}
	
	//exit encerra o jogo
	private String capturarValorDigitado(String texto) {
		System.out.print(texto);
		String digitado = entrada.nextLine();
		
		if("exit".equalsIgnoreCase(digitado)) {
		throw new ExitException();
		
	}
		
	return digitado;
 }
}
