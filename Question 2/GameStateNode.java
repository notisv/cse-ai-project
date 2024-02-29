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

import java.util.ArrayList;

// Game_state_node class is an instance of our current game state
// Diladi: ta nodes sto dentro tis ektelesis tou paixnidiou - Des sinimmeni eikona
public class GameStateNode { // To prwto game_state_node einai i riza tou dentrou tis ektelesis tou paixnidiou
	
	public int grid_size; 									// Diastasi plegmatos paixnidiou
	public char[][] game_grid; 								// To plegma tou paixnidiou mas me diastaseis NxN
	public ArrayList<GameStateNode> next_possible_states; 	// ArrayList of next possible game states
	public GameStateNode next_game_state; 					// The best possible move to execute is the best of the next_possible states
	
	
	// Constructor
	public GameStateNode (int grid_size) {
		
		this.grid_size = grid_size;										// initialization
		this.game_grid = new char[grid_size][grid_size]; 				// Arxikopoiw sto mege8os grid pou 8elw
		this.next_possible_states = new ArrayList<GameStateNode>();		// initialization
	}
	
	// Initialize the game_grid according to the dimensions given
	public void initialize_game_grid(int defaultO_pos) {
		
		for (int i = 0; i < this.grid_size; i++) {
			
			for (int j = 0; j < this.grid_size; j++) {
				
				this.game_grid[i][j] = '-';
			}
		}
		
		if (defaultO_pos == 0) this.game_grid[1][0] = 'O';
		if (defaultO_pos == 2) this.game_grid[1][2] = 'O';
	}
	
	// Print the current state's game grid
	public void print_current_state_game_grid () {
		
		System.out.println();
		System.out.println("Printing the current state's game grid...");
		
		for (int i = 0; i < this.grid_size; i++) {
			
			for (int j = 0; j < this.grid_size; j++) {
				
				System.out.print(this.game_grid[i][j]);
				System.out.print(' ');
			}
			System.out.println();
		}
		System.out.println();
	}
	
	// check for any free spots
	public boolean check_for_free_spots() {
		
		for (int i = 0; i < this.grid_size; i++) {
			
			for (int j = 0; j < this.grid_size; j++) {
				
				if (this.game_grid[i][j] == '-') return true;
			}
		}
		
		return false;
	}
	
	// Constructor gia ta paidia nodes pou mpainoun sto game tree ton kanw xeirokinita stin me8odo minimax grammes simiwmenes me TODO:
	
	// Method for the child node-state to inherit game grid from parent
	public void inherit_to_child(GameStateNode child) {
		
		for (int i = 0; i < this.grid_size; i++) {
			
			for (int j = 0; j < this.grid_size; j++) {
				
				child.game_grid[i][j] = this.game_grid[i][j];
			}
		}
	}
	
	// Check if there is an SOS formed on the game grid - game is over if an SOS is found
	public boolean check_if_SOS_found () {
	
		String should_be_sos = ""; // initialize
		
		// Elegxos gia sos stis grammes
		for (int i = 0; i < this.grid_size; i++) {
			
			for (int j = 0; j <this.grid_size; j++) {
				
				should_be_sos += this.game_grid[i][j];
			}
			
			if (should_be_sos.equals("SOS")) return true;
			else should_be_sos = ""; // purge any unneeded values stored in our flag
		}
		
		// Elegxos gia sos stis stiles
		for (int i = 0; i < this.grid_size; i++) {
			
			for (int j = 0; j < this.grid_size; j++) {
				
				should_be_sos += this.game_grid[j][i];
			}
			
			if (should_be_sos.equals("SOS")) return true;
			else should_be_sos = ""; // purge any unneeded values stored in our flag
		}
		
		// Elegxos gia sos stin kyria diagwnio
		for (int i = 0; i < this.grid_size; i++) {
			
			should_be_sos += this.game_grid[i][i];
		}
		
		if (should_be_sos.equals("SOS")) return true;
		else should_be_sos = ""; // purge any unneeded values stored in our flag
		
		// Elegxos gia sos stin defterevousa diagwnio
		for (int i = 0; i < this.grid_size; i++) {
			
			should_be_sos += this.game_grid[i][3-i-1];
		}
		
		if (should_be_sos.equals("SOS")) return true;
		else should_be_sos = ""; // purge any unneeded values stored in our flag
		
		// An ftasw edw simainei oti den exei bre8ei sos pou8ena ara epistrefw false
		return false;
	}
}