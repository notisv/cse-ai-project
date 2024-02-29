/*
* Notis Vouzalis, AM 2653
* 
* Contact email: cs02653@uoi.gr
*
* Code written and ran on Windows 10 version 1809 OS build 17763.1131 
* Using Eclipse Java IDE Version 2020-03 (4.15.0) Build id 20200313-1211
* Last succesful run on: 16 May 2020
* 
* README: No error check are performed on inputs so be careful
*/

import java.io.IOException;
import java.util.Scanner;

public class SosGame {
	
	/**************************************** PEDIA ****************************************/
	
	// Sta8eres
	public static final int MAX = 11; 	// O MAX pairnei enan 8etiko ari8mo
	public static final int MIN = -11; 	// O MIN pairnei enan arnitiko ari8mo
	public static final int TIE = 0; 	// I isopalia pairnei ena omorfo koulouraki sti mesi tou panw diastimatos
	
	/**************************************** MiniMax ****************************************/
	
	// COM is a cheater and simulates every possible outcome in order to win. Returns the best possible outcome.
	public int minimax (GameStateNode game_state, int alpha, int beta, int currentPlayer){
		
		/* Useful Links:
		 * https://bit.ly/2XkOogo
		 * https://bit.ly/3caFSF2
		 * https://www.geeksforgeeks.org/minimax-algorithm-in-game-theory-set-1-introduction/
		 */
		
		/* Check to see if we have reached the desired simulation - exploration depth or if the game is over on the current position (leaf node)
		 * TODO: GIA NA MPORESW NA XRHSIMOPOIHSW TO BA8OS ANAZHTHSHS PREPEI NA EXW MIA EVRETIKI SINARTISI h(n) GIA NA DWSEI TIMES STA PARAKATW NODES
		 * If any of those conditions are met then we assign value to the leaf nodes and return this value
		 * Edw to dentro exei teleiwsei kai eimaste se fyllo node
		 * Oi 3 epiloges timwn evaluation pou mpoorume na epistrepsoume einai:
		 * Isopalia an exw isopalia
		 * MAX an einai i seira tou MIN na paiksei kai exei idi sximatistei sos diladi nikise o MAX
		 * MIN an einai i seira tou MAX na paiksei kai exei idi sximatistei sos diladi nikise o MIN 
		 */
		
		if (!game_state.check_for_free_spots()) return TIE; 						// An exoun teleiwsei oi kenes 8eseis tote epistrefw isopalia dld 0 value
		if (currentPlayer == MAX && game_state.check_if_SOS_found()) return MIN; 	// An paei na paiksei o MAX kai idi exei sximatistei sos tote epistrefw min value 
		if (currentPlayer == MIN && game_state.check_if_SOS_found()) return MAX; 	// An paei na paiksei o MIN kai idi exei sximatistei sos tote epistrefw max value
		
		// An den exw ftasei to epi8imito ba8os ekserevnisis i den exw game over stin trexousa 8esi tote synexizw me tous paiktes mas kai paizw to game		
		if (currentPlayer == MAX) {
			
			int bestMove_pos = 0; // Postition of best move - best possible game node - in the next_possible_states array of game node class 
			GameStateNode best_move = null;
			int maxEvaluation = Integer.MIN_VALUE ; // -infinity
			for (int i = 0; i < game_state.grid_size; i++) {
				
				for (int j = 0; j < game_state.grid_size; j++) {
					
					if (game_state.game_grid[i][j] == '-') { // Otan synantw keni 8esi psaxnw na brw ti me simferei na balw ekei
						
						// Dimiourgw ta paidia tou node giati den eimai se fyllo
						
						int evaluation;
						
						// TODO: Periptwsi pou bazw S - leitourgei san contrstructor
						game_state.next_possible_states.add(new GameStateNode(game_state.grid_size));
						game_state.inherit_to_child(game_state.next_possible_states.get(bestMove_pos));
						game_state.next_possible_states.get(bestMove_pos).game_grid[i][j] = 'S';
						evaluation = minimax(game_state.next_possible_states.get(bestMove_pos), alpha, beta, MIN);
						if (evaluation > maxEvaluation) {
							
							maxEvaluation = evaluation;
							best_move = game_state.next_possible_states.get(bestMove_pos);
						}
						
						// TODO: To disable AB Pruning, apla balto se sxolia
						// AB Pruning
						alpha = Math.max(alpha, evaluation);
						if (beta <= alpha) break;
						bestMove_pos ++;
						
						// TODO: Periptwsi pou bazw O - leitourgei san contrstructor
						game_state.next_possible_states.add(new GameStateNode(game_state.grid_size));
						game_state.inherit_to_child(game_state.next_possible_states.get(bestMove_pos));
						game_state.next_possible_states.get(bestMove_pos).game_grid[i][j] = 'O';
						evaluation = minimax(game_state.next_possible_states.get(bestMove_pos), alpha, beta, MIN);
						if(evaluation > maxEvaluation) {
							
							maxEvaluation = evaluation;
							best_move = game_state.next_possible_states.get(bestMove_pos);
						}
						
						// TODO: To disable AB Pruning, apla balto se sxolia
						// AB Pruning
						alpha = Math.max(alpha, evaluation);
						if (beta <= alpha) break;
						bestMove_pos++;
					}
				}
			}
			
			game_state.next_game_state = best_move;
			return maxEvaluation;
			
		} else { // CurrentPlayer == MIN
			
			int bestMove_pos = 0;
			GameStateNode best_move = null;
			int minEvaluation = Integer.MAX_VALUE; // +infinity
			for (int i = 0; i < game_state.grid_size; i++) {
				
				for (int j = 0; j < game_state.grid_size; j++) {
					
					if (game_state.game_grid[i][j] == '-') { // Otan synantw keni 8esi psaxnw na brw ti me simferei na balw ekei
						
						// Dimiourgw ta paidia tou node giati den eimai se fyllo
						
						int evaluation;
						
						// TODO: Periptwsi pou bazw S - leitourgei san contrstructor
						game_state.next_possible_states.add(new GameStateNode(game_state.grid_size));
						game_state.inherit_to_child(game_state.next_possible_states.get(bestMove_pos));
						game_state.next_possible_states.get(bestMove_pos).game_grid[i][j] = 'S';
						evaluation = minimax(game_state.next_possible_states.get(bestMove_pos), alpha, beta, MAX);
						if(evaluation < minEvaluation) {
							
							minEvaluation = evaluation;
							best_move = game_state.next_possible_states.get(bestMove_pos);
						}
						
						// TODO: To disable AB Pruning, apla balto se sxolia
						// AB Pruning
						beta = Math.min(beta, evaluation);
						if (beta <= alpha) break;
						bestMove_pos++;
						
						// TODO: periptwsi pou bazw O - leitourgei san contrstructor
						game_state.next_possible_states.add(new GameStateNode(game_state.grid_size));
						game_state.inherit_to_child(game_state.next_possible_states.get(bestMove_pos));
						game_state.next_possible_states.get(bestMove_pos).game_grid[i][j] = 'O';
						evaluation = minimax(game_state.next_possible_states.get(bestMove_pos), alpha, beta, MAX);
						if(evaluation < minEvaluation) {
							
							minEvaluation = evaluation;
							best_move = game_state.next_possible_states.get(bestMove_pos);
						}
						
						// TODO: To disable AB Pruning, apla balto se sxolia
						// AB Pruning
						beta = Math.min(beta, evaluation);
						if (beta <= alpha) break;
						bestMove_pos++;
					}
				}
			}
			
			game_state.next_game_state = best_move;
			return minEvaluation;
		}
	}
	
	/**************************************** MAIN ****************************************/
	
	public static void main (String[] args) {
		
		Scanner userInput = new Scanner(System.in);
		
		char playAgain = ' '; 					// Initialization
		boolean previous_winner_is_COM = false; // Initialization
		char userSymbol; 						// Initialization
		int userPosRow; 						// Initialization
		int userPosColumn; 						// Initialization
		
		do {
			
			SosGame myGame = new SosGame();
			
			System.out.println("Starting Game...");
			System.out.println("Choose grid size:");
			int userSizeChoice = userInput.nextInt();
			GameStateNode state = new GameStateNode(userSizeChoice); // Dimiourgw ti riza tou minimax dentrou
			
			System.out.println("Press 0 to put the default character \"O\" on position (1,0) or press 2 to put the default character \"O\" on position (1,3)...");
			int userChoice = userInput.nextInt();
			
			state.initialize_game_grid(userChoice); // Bazw ena O sti 8esi pou epilegei o user
			state.print_current_state_game_grid();
			
			System.out.println("COM plays first...\n");
			int currentTurn = MAX; // O max - ipologistis paizei prwtos
			
			while (true) {
				
				// Checks to see if game is over
				
				// An paei na paiksei o max kai iparxei idi SOS sto grid tote nikise o min
				if (currentTurn == MAX && state.check_if_SOS_found()) {
					
					previous_winner_is_COM = false;
					System.out.println("Player won! Congratulations");
					System.out.println("Play again? (y/n)...");
					playAgain = userInput.next().charAt(0);
					break;
				
				// An paei na paiksei o MIN kai iparxei idi SOS sto grid tote nikise o COM
				} else if (currentTurn == MIN && state.check_if_SOS_found()) {
					
					previous_winner_is_COM = true;
					System.out.println("COM won!");
					System.out.println("Try again? (y/n)...");
					playAgain = userInput.next().charAt(0);
					break;
				
				// An paei na paiksei o opoiosdipote alla den iparxei SOS sto grid
				// Kai ta kena tetragwna einai 0 sto pli8os tote exw isopalia
				} else if (!state.check_for_free_spots()) {
					
					System.out.println("It's a TIE!");
					System.out.println("Try again? (y/n)...");
					playAgain = userInput.next().charAt(0);
					break;
				}
				
				// Game is not over yet
				
				// Paei na paiksei o MAX
				if (currentTurn == MAX) {
					
					System.out.println("COM turn... Thinking...");
					// this is the most important part of the code. We call upon the minimax method in order to play the game, basically.
					myGame.minimax(state, Integer.MIN_VALUE, Integer.MAX_VALUE, MAX); // COM is a cheater and simulates every possible outcome in order to win
					state = state.next_game_state; // epilegw to kalitero paidi gia na synexisw na paizw
					state.print_current_state_game_grid();
					currentTurn = MIN; // o epomenos paiktis einai o min
				
				// Paei na paiksei o min
				} else {
					
					// TODO: Elegxo egkyrotitas
					System.out.println("Player turn...");
					System.out.println("Choose a symbol (s/o)...");
					userSymbol = Character.toUpperCase(userInput.next().charAt(0));
					System.out.println("Choose a row (1 to " + state.grid_size + ")...");
					userPosRow = userInput.nextInt();
					System.out.println("Choose a column (1 to " + state.grid_size + ")...");
					userPosColumn = userInput.nextInt();
					System.out.println ("User chose to put " + userSymbol + " in position (" + userPosRow + "," + userPosColumn + ")");
					
					System.out.println();
					
					state.game_grid[userPosRow - 1][userPosColumn - 1] = userSymbol; // Update the game grid
					
					state.print_current_state_game_grid();
					currentTurn = MAX; // O epomenos paiktis einai o max
				}
			}
		} while (playAgain == 'y');
		
		// Be sure to taunt the player if it's a tie or a loss
		if (previous_winner_is_COM && playAgain == 'n') System.out.println("Better luck next time, noob!");
		else System.out.println("Thanks for playing!");
		
		System.out.println("Press enter to exit...");
		// ylopoihsh tou press enter to exit
		// https://bit.ly/2y54h06
		try { System.in.read(); } catch (IOException e) { e.printStackTrace(); }
		
		// Housekeeping
		userInput.close();
	}
}