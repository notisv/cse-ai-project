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

import java.util.Random;

public class SearchProblemNode { 		// mia katastasi
	
	public int N; 						// mege8os tou N
	public char[] stringArray; 			// to string panw sto opoio ergazomaste me morfi px gia n=3: ama-mma apo8ikevmeno se morfi pinaka
	public int cost; 					// to g(n) ka8e katastasis
	public SearchProblemNode parent;	// progonos tis katastasis - aftos pou tin ebale sto metwpo anazitisis - an einai null tote to node einai i arxiki katastasi
	
	// constructor
	public SearchProblemNode (int N) {
		
		this.N = N; 							// mege8os tou N
		this.stringArray = new char[2*N + 1]; 	// 8eseis 1 ws kai (2*N + 1) tou string
		this.cost = 0; 							// Arxikopoiw to kostos se 0. 8ymizw pws 0 kostos exei MONO i arxiki katastasi - riza
		this.parent = null; 					// Arxikopoiw to parent se null. 8ymizw pws null parent exei MONO i arxiki katastasi - riza
	}

	// briskei se poia 8esi katoikei to keno - to 8elw gia na brw ta kosti twn katastasewn
	public int returnTheSoundOfSilence() {
		
		int  SimonGarfunkel =  -11;
		
		for (int i = 0; i < this.stringArray.length; i++) {
			
			if (this.stringArray[i] == '-') { SimonGarfunkel = i; }
		}
		
		return SimonGarfunkel;
	}
	
	// elegxos an i katastasi einai teliki
	public boolean isFinalState () {
		
		/* Gia na einai mia katastasi teliki prepei oles oi mavres sfaires
		 * na einai topo8etimenes aristera apo tis aspres KAI na iparxei mia aspri sfaira stin pio deksia 8esi
		 * Ara diabazw apo aristera pros ta deksia kai arxika 8a elegxw poses mavres exw dei mexri na dw aspri
		 * An dw aspri kai o ari8mos twn mavrwn einai ligoteros apo N tote simainei oti kapou meta apo aftin tin
		 * aspri 8a iparxei mavri sfaira, ara i katastasi den einai teliki
		 */
		
		// Prepei na elegksw an i teleftaia 8esi, i pio deksia, exei aspri sfaira. An exei keno den einai teliki
		if (this.stringArray[this.stringArray.length - 1] == '-') { return false; }
		
		/* Diabazw apo aristera pros ta deksia kai arxika 8a elegxw poses mavres exw dei mexri na dw aspri
		 * An dw aspri kai o ari8mos twn mavrwn einai ligoteros apo N tote simainei oti kapou meta apo aftin tin
		 * aspri 8a iparxei mavri sfaira, ara i katastasi den einai teliki
		 */
		
		int numberOfBlacksSeenSoFar = 0;
		
		for (int i = 0; i < this.stringArray.length; i++) {
			
			// blepw mavri sfaira
			if (this.stringArray[i] == 'm') { numberOfBlacksSeenSoFar++; }
			
			// an ftasw edw tote blepw aspri sfaira gia prwti fora
			if (this.stringArray[i] == 'a') {
				
				// An dw aspri kai o ari8mos twn mavrwn einai ligoteros apo N tote simainei oti 
				// kapou meta apo aftin tin aspri 8a iparxei mavri sfaira, ara i katastasi den einai teliki
				if(numberOfBlacksSeenSoFar < this.N) { return false; }
			}
		}
		
		// An ftasw edw simainei oti oles oi mavres sfaires einai topo8etimenes aristera apo tis aspres 
		// KAI iparxei mia aspri sfaira stin pio deksia 8esi, ara i katastasi einai teliki kai epistrefw trueeeeeeeeeee
		
		return true;
	}
	
	// Method for the child node-state to inherit stringArray from parent
	public void inheritStringArrayToChild (SearchProblemNode child) {
	
		for (int i = 0; i < this.stringArray.length; i++) {
			
			child.stringArray[i] = this.stringArray[i];
		}
	}
	
	// toString me8odos
	public String toString () {
		
		String nodeString = "";
		for (int i = 0; i < this.stringArray.length; i++) { nodeString += this.stringArray[i]; }
		
		return nodeString;
	}
	
	// tyxaia dimiourgia arxikou string
	public void generateRandomStartingState () {
		
		Random rand = new Random();
		int numberOfWhites = 0;
		int numberOfBlacks = 0;
		
		// initialize tin kentriki 8esi se keni
		this.stringArray[this.stringArray.length / 2] = '-';
		
		// initialize tin teleftaia 8esi se keni
		this.stringArray[this.stringArray.length - 1] = 'm';
		numberOfBlacks++;
		
		for (int i = 0; i < this.stringArray.length; i++) { 
			
			// kentriki 8esi idi einai keni
			if (i == (this.stringArray.length / 2)) { continue; }
			// teleftaia 8esi einai idi mavri
			if (i == (this.stringArray.length - 1)) { continue; }
			
			// bazw aspres i mavres sfaires stis 8eseis me pi8anotita 50% kai kala
			int randomChance = rand.nextInt(2);
			
			// gia miden bazw aspro
			if ((randomChance == 0) && (numberOfWhites < this.N)) {
				
				this.stringArray[i] = 'a';
				numberOfWhites++;
			}
				
			// gia ena bazw mavro
			if ((randomChance == 1) && (numberOfBlacks < this.N)) {
				
				this.stringArray[i] = 'm';
				numberOfBlacks++;
			}
		}
		
		// tidy things up if lady luck wasnt on our side
		for (int i = 0; i < this.stringArray.length; i++) {
			
			// kentriki 8esi idi einai keni
			if (i == (this.stringArray.length / 2)) { continue; }
			
			// teleftaia 8esi einai idi mavri
			if (i == (this.stringArray.length - 1)) { continue; }
			
			if ((this.stringArray[i] == '\u0000') && (numberOfWhites < this.N)) {
				
				this.stringArray[i] = 'a';
				numberOfWhites++;
			}
			
			if ((this.stringArray[i] == '\u0000') && (numberOfBlacks < this.N)) {
				
				this.stringArray[i] = 'm';
				numberOfBlacks++;
			}
		}
	}
}