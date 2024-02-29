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
import java.util.Scanner;
import java.util.Collections;
import java.io.IOException;
import java.lang.System;

public class SearchProblem {
	
	/**************************************** PEDIA ****************************************/
	
	public int ari8mosEpektasewn; 	// poses fores ekana paragwgi katastasewn-paidiwn - to 8elw gia na sygkrinw epidoseis
	public int ari8mosNodes; 		// synolikos ari8mos nodes pou dimiourgi8ikan genika - to 9elw gia na sykgrinw UCS kai evretikes - des grammi 271
	public double timeElapsed;		// synolikos xronos se milliseconds (10^-3 seconds) ektelesis tis sinartisis anazitisis (UCS i A*)
	
	public ArrayList<SearchProblemNode> metwpoAnazitisis; 	// metwpo anazitisis
	public ArrayList<SearchProblemNode> kleistoSynolo;		// kleisto synolo
	public ArrayList<SearchProblemNode> monopatiLysis;		// beltisto monopati mexri tin teliki katastasi
	
	/**************************************** KOINES ME8ODOI ****************************************/
	
	// briskei an i do8eisa katastasi anikei sto kleisto synolo
	public boolean containsInKleistoSynolo(SearchProblemNode katastasi) {
		
		// diatrexw to kleisto synolo
		for (int i = 0; i < this.kleistoSynolo.size(); i++) {
			
			// sygkrinw to string tis do8eisas katastaseis me ola ta strings twn katastasewn sto kleisto synolo
			// kai kanw return true an brw match
			if (katastasi.toString().equals(this.kleistoSynolo.get(i).toString())) { return true; }
		}
		
		// an ftasoume edw simainei oti i katastasi DEN einai sto kleisto synolo
		return false;
	}
	
	// pairnei tin teliki katastasi kai ektypwnei to beltisto monopati apo ti riza mexri tin teliki katastasi
	public void displayBeltistoMonopatiLysis(SearchProblemNode telikiKatastasi) {
		
		SearchProblemNode katastasiNode = telikiKatastasi;
		// pigenw epanaliptika ston parent ka8e katastasis ksekinontas apo tin teliki
		// kai ton eisagw sto monopati lysis
		// me to telos tis epanalipsis 8a exw brei to beltisto monopati lysis mono pou 8a einai stin anapodi seira ara prepei na to antistrepsw
		while (katastasiNode != null) {
			
			// to monopati 8a deixnei apo tin teliki katastasi pros tin riza
			this.monopatiLysis.add(katastasiNode);
			katastasiNode = katastasiNode.parent;
		}
		
		// Collections.reverse method takes a list as a parameter and returns the reversed list
		// antristrefw tin seira tou monopatiou lysis etsi wste na deixnei apo tin riza pros tin teliki katastasi
        Collections.reverse(this.monopatiLysis);
        
        // display the results
        for (int i = 0; i < this.monopatiLysis.size(); i++) {
        	
        	// arxiki katastasi
        	if (i == 0) {
        		
        		System.out.println("Katastasi " + (i + 1) + " : Kostos " + this.monopatiLysis.get(i).cost + " : " + this.monopatiLysis.get(i) + " - ARXIKI KATASTASI");
        		continue;
        	}
        	
        	// teliki katastasi
        	if (i == this.monopatiLysis.size() - 1) {
        		
        		System.out.println("Katastasi " + (i + 1) + " : Kostos " + this.monopatiLysis.get(i).cost + " : " + this.monopatiLysis.get(i) + " - TELIKI KATASTASI");
        		continue;
        	}
        	
        	// mi teliki katastasi
        	System.out.println("Katastasi " + (i + 1) + " : Kostos " + this.monopatiLysis.get(i).cost + " : " + this.monopatiLysis.get(i));
        }
	}
	
	// paragei tis katastaseis paidia
	public void expandChildrenOf(SearchProblemNode parentState) {
		
		// search through the string of the parent
		for (int i = 0; i < parentState.stringArray.length; i++) {
			
			// An brw to keno tote skiparw tin epanalipsi
			// Afto to kanw giati 8elw oi energeies parakatw na ektelountai MONO OTAN EXW BREI SFAIRA
			if (parentState.stringArray[i] == '-') { continue; }
			
			// evresi tou index tis kenis 8esis
			int indexKenis8esis = parentState.returnTheSoundOfSilence();
			
			// an eimai edw tote exw bei mia sfaira kai synexizw gia na brw to kostos metakinisis
			// to kostos metakinisis einai i apostasi tis sfairas apo to keno
			int kostosMetakinisis = Math.abs(i - indexKenis8esis);
			
			// An to k einai <= N tote moy epitrepetai na metakinisw ti sfaira
			// Genikos Algori8mos Anazitisis - Bima 6
			// Paragwgi paidiou
			if (kostosMetakinisis <= parentState.N) {
				
				SearchProblemNode childState = new SearchProblemNode(parentState.N);
				this.ari8mosNodes++; // afksanw ton ari8mo twn nodes kata 1
				// TODO: MIN KSEXASEIS NA KANEIS UPDATE TA PEDIA stringArray, cost kai parent tou kahmenou tou paidiou - DONE BELOW
				
				// pernaw ton pinaka apo ton parent sto child
				parentState.inheritStringArrayToChild(childState);
				// TODO: ektelw tin metakinisi tis mpalas - DONE BELOW
				// TODO: kane na ekteleitai i evresi tou index tis kenis 8esis mia fora - DONE
				// briskomai stin mpala me 8esi i apo to amesws apo panw for
				// 8a kanw swap me to index tis kenis 8esis pou brika me to returnSoundOfSIlence pio panw
				// den exei simasia na paizw me xrwmata - apla metakinw to gramma kai afinw tin isFinalState na kanei ti douleia tis
				childState.stringArray[indexKenis8esis] = parentState.stringArray[i]; // kanei swap (mesw overwrite) tin sfaira me to keno
				childState.stringArray[i] = '-'; // kanei overwrite
				
				// ananewnw to cost tou paidiou
				// to sinoliko kostos tou paidiou einai to kostos tou patera + to diko tou kostos metakinisis
				childState.cost = parentState.cost + kostosMetakinisis; // kosti gonewn paidevousi tekna
				
				// o pateras anagnwrise to paidi - twra prepei to paidi na anagnwrisei ton patera
				// ananewnw to parent tou paidiou
				childState.parent = parentState;
				
				// Genikos Algori8mos Anazitisis - Bima 7
				// Efoson mou epitrapike na metakinisw ti sfaira kai dimiourgisa paidi
				// tora prepei na balw tin katastasi paidi sto metwpo anazitisis. Den iparxei kapoio kritirio gia tin eisodo tou ara to bazw apla me ena add
				this.metwpoAnazitisis.add(childState);
			}
		}
	}
	
	/**************************************** UCS ****************************************/
	
	// Briskei tin prwti se seira katastasi tou metwpou anazitisis
	// Stin periptwsi mas, ekteloume UCS ara i prwti se seira katastasi einai i katastasi me to mikrotero kostos g(n)
	public SearchProblemNode removeAndReturnOptimalStateUCS() {
		
		SearchProblemNode returnValue = this.metwpoAnazitisis.get(0);
		
		// search the metwpo anazitisis
		for (int i = 0; i < this.metwpoAnazitisis.size(); i++) {
			
			if (this.metwpoAnazitisis.get(i).cost < returnValue.cost) {
				
				returnValue = this.metwpoAnazitisis.get(i);
			}
		}
		
		// brikame tin katastasi me to elaxisto kostos ara tin afairoume apo to metwpo anazitisis
		// TODO: An den doulevei etsi tote kanto kratontas to index tis katastasis - Doulevei kai etsi ara eimaste kala
		this.metwpoAnazitisis.remove(returnValue);
		
		// epestrepse tin katastasi
		return returnValue;
	}
	
	// Uniform Cost Search
	public void uniformCostSearch() {
		
		System.out.println("Performing Uniform Cost Search...");
		
		boolean optimalPathFound = false;
		
		// arxiki katastasi is already inside metwpo anazitisis san riza - stoixeio me deikti 0
		
		// Efarmogi algori8mou apo diafaneies
		// Genikos Algori8mos Anazitisis - Bima 1 - DONE
		
		// Arxi epanaliptikou elegxou
		while (!optimalPathFound) {
			
			// Genikos Algori8mos Anazitisis - Bima 2
			// An to metwpo anazitisis einai adeio tote stamataw
			if (this.metwpoAnazitisis.isEmpty()) { System.out.println("Could not find an optimal path."); break; }
			
			// Genikos Algori8mos Anazitisis - Bima 3
			// Briskw tin prwti se seira katastasi tou metwpou anazitisis
			// Stin periptwsi mas, ekteloume UCS ara i prwti se seira katastasi einai i katastasi me to mikrotero kostos g(n)
			// Afou brw tin katastasi afti, tin afairw apo to metwpo anazitisis kai tin pros8etw sto kleisto synolo efoson den anikei idi se afto
			SearchProblemNode state = this.removeAndReturnOptimalStateUCS();
			
			// Genikos Algori8mos Anazitisis - Bima 4
			// Elegxw an i katastasi afti einai meros tou kleistou synolou. AN einai meros tou kleistoy synolou pigenw sto bima 2 me continue
			if (this.containsInKleistoSynolo(state)) { continue; }
			
			// an ftasoume edw simainei oti i katastasi afti DEN anikei sto kleisto synolo. Ara prepei na elegksw an einai teliki.
			// TODO: na min ksexasw na tin balw sto kleisto synolo an den einai teliki katastasi - DONE BELOW
			
			// Genikos Algori8mos Anazitisis - Bima 5
			// An afti i katastasi einai teliki, tote tipwnw ti lysi kai termatizw.
			if(state.isFinalState()) {
				
				optimalPathFound = true;
				this.displayBeltistoMonopatiLysis(state);
				break;
			}
			
			// An ftasoume edw simainei oti i katastasi afti DEN anikei sto kleisto synolo kai DEN einai teliki.
			// TODO: Ara afou kanw ta bimata 6 kai 7 na min ksexasw na tin balw sto kleisto synolo - DONE BELOW
			// Genikos Algori8mos Anazitisis - Bima 6
			// Efarmozw tous telestes metabasis dld afksanw ton ari8mo twn epektasewn kai epeita paragw tis katastaseis paidia
			this.ari8mosEpektasewn++;
			this.expandChildrenOf(state); // Edw mesa ginetai to bima 7 - bazw tis katastaseis paidia sto metwpo anazitisis
			
			// Genikos Algori8mos Anazitisis - Bima 8
			// Bazw tin katastasi gonea sto kleisto synolo
			this.kleistoSynolo.add(state);
			
			// Genikos Algori8mos Anazitisis - Bima 9
			// Pigenw pisw sto bima 2 pou einai i arxi tis epanalipsis
		}
	}
	
	/**************************************** ALPHA STAR ****************************************/
	
	// Briskei tin prwti se seira katastasi tou metwpou anazitisis
	// Stin periptwsi mas, ekteloume Alpha star ara i prwti se seira katastasi einai i katastasi me to mikrotero kostos g(n) + h(n)
	public SearchProblemNode removeAndReturnOptimalStateAstar() {
		
		SearchProblemNode returnValue = this.metwpoAnazitisis.get(0);
			
		// search the metwpo anazitisis
		for (int i = 0; i < this.metwpoAnazitisis.size(); i++) {
			
			/* TODO: Dokimase na to trekseis mono me tin evretiki kai des ti bgazei. Dld na xrisimopoiw mono to h(n) anti gia g(n) + h(n). Soy 8ymizei kati?
			 * Sygkrine ton ari8mo twn nodes pou dimiourgountai kai ton ari8mo epektasewn pou ginontai. Sygkrine ton xrono ektelesis twn UCS, A* kai tis me8odou mono me h(n)
			 * 8a paratiriseis oti synolika dimiourgei ligotera nodes, kanei ligoteres epektaseis, trexei pio grigora apo UCS kai A*, kai kataligei se apodekti teliki katastasi.
			 * Einai pliris diladi, omws i teliki katastasi stin opoia kataligei exei megalytero kostos apo aftin stin opoia kataligoun oi UCS kai A*. Ara den einai beltisti.
			 * Ti 8ymizei afto? To brikes, einai i Greedy Search 
			 */
			//if ( this.heuresticFunction(this.metwpoAnazitisis.get(i)) < this.heuresticFunction(returnValue) ) {
			if ( (this.metwpoAnazitisis.get(i).cost + this.heuresticFunctionDistanceFromCenterBelt(this.metwpoAnazitisis.get(i))) < (returnValue.cost + this.heuresticFunctionDistanceFromCenterBelt(returnValue)) ) {
				
				returnValue = this.metwpoAnazitisis.get(i);
			}
		}
		
		// brikame tin katastasi me to elaxisto kostos ara tin afairoume apo to metwpo anazitisis
		// TODO: An den doulevei etsi tote kanto kratontas to index tis katastasis - Doulevei kai etsi ara eimaste kala
		this.metwpoAnazitisis.remove(returnValue);
		
		// epestrepse tin katastasi
		return returnValue;
	}
	
	// TODO: Apodekti evretiki sinartisi - test me sta8erous orous - DES SEL 3 & 4 pdf
	/*
	 * Ti ginetai an apla epistrefw akyrous ari8mous?
	 * Kserw pws i h(n) prepei ipoxrewtika na einai > 0 gia mi telikes katastaseis kai isi me 0 gia telikes katastaseis.
	 *
	 * To prwto pragma pou skeftomai einai na dokimasw akyrous 8etikous ari8mous.
	 * O prwtos 8etikos ari8mos einai i monada. Einai apodekti afti i timi?
	 * Kserw pws an gia mia evretiki sinartisi isxyei pws gia ka8e katastasi n i timi h(n) einai mikroteri
	 * i to poly isi me tin pragmatiki apostasi a(n) tis n apo tin plisiesteri teliki katastasi tote i h(n) einai apodekti
	 * An i h(n) einai apodekti diladi h(n) <= a(n) gia ka8e n tote i A* einai pliris kai beltisti
	 * I monada einai apodekti san evretiki sinartisi ka8ws an den briskomai se teliki katastasi tote sigoura briskomai se apostasi
	 * toulaxiston enos bimatos apo tin teliki. Ara 8a dokimasw ti monada.
	 * Sigoura isxyei pws 1 <= a(n) pou einai to pragmatiko kostos ara den blaptei na ti dokimasw.
	 * 
	 * Paratirw pws einai apodekti kai me odigiei se swsto apotelesma
	 * 
	 * Episis paratirw pws opoiadipote timi megalyteri tis monadas einai la8os dioti an px briskomai ena bima makria apo tin teliki katastasi
	 * me pragmatiko a(n) = 1 kai emena i ektimisi mou einai megalyteri tis monadas px h(n) = 11 tote einai poly la8os
	 * dioti prepei gia ka8e katastasi na isxyei h(n) <= a(n). 
	 * 
	 * Ara oson afora tin epistrofi akyrwn 8etikwn ari8mwn, mono i monada mporei na epistrefetai
	 *
	 * TODO: Ara egw meta 8a dokimazw arnitikes times gia na dw pws paizei
	 * Paratirw pws me arnitikes times ginetai olo kai xeiroteri
	 */
	public int heuresticFunctionTest(SearchProblemNode state) {
		
		// an briskomai se teliki katastasi tote i evretiki prepei na epistrefei 0
		if (state.isFinalState()) { return 0; }
		
		// an ftasw edw simainei oti den briskomai se teliki katastasi ara synexizw
		
		/* epistrefei ti monada ws h(n) giati an den briskomai se teliki katastasi 
		 * tote 8a briskomai sigoyra se apostasi toulaxiston enos bimatos apo tin teliki
		 * ara dokimazw na epistrepsw ti monada kai na dw ti bgazei
		 * Episis sigoura 8a isxyei pws 1 <= a(n) pou einai to pragmatiko kostos mexri tin teliki katastasi
		 * TODO: Dokimase na deis ti ginetai an epistrefeis 2, 3, 4, i kapoion akyro ari8mo px kati poly megalo i kati arnitiko
		 */
		return 1;
		//return 2;
		//return 1111;
		//return 1111111;
		//return -1;
		//return -1111;
		//return -1111111;
		}
	
	// TODO: Apodekti evretiki sinartisi - idea me kentro string - DES SEL 5 & 6 pdf
	/*
	 * Me endiaferei na mazepsw oles tis mavres sfaires sto prwto miso tou pinaka (aristera) kai tis aspres sto deftero miso tou pinaka (deksia)
	 * gia na mporw na pw oti exw ftasei se mia teliki katastasi.
	 * Pairnw ws kritirio tis h(n) ton ari8mo twn mavrwn sfairwn pou briskontai deksia tou kentrou kai ton ari8mo twn asprwn pou briskontai aristera tou
	 * TODO: na testarw na parw ton ari8mo twn mavrwn mono, twn asprwn mono, kai to a8roisma gia na sygkrinw poia einai kalyteri - logika to a8roisma
	 * TODO: meta apo test nai, to a8roisma bgainei kalytero
	 */
	public int heuresticFunctionCenter(SearchProblemNode state) {
		
		// an briskomai se teliki katastasi tote i evretiki prepei na epistrefei 0
		if (state.isFinalState()) { return 0; }
		
		// an ftasw edw simainei oti den briskomai se teliki katastasi ara synexizw
		int returnValue = 0; // initialization
		
		// diatrexw ton pinaka
		for (int i = 0; i < state.stringArray.length; i++) {
			
			if (state.stringArray[i] == 'm' && i > (state.stringArray.length / 2)) { returnValue++; } // an brw mavri sfaira deksia tou kentrou tote afksanw to returnValue
			if (state.stringArray[i] == 'a' && i < (state.stringArray.length / 2)) { returnValue++; } // an brw aspri sfaira aristera tou kentrou tote afksanw to returnValue
		}
		
		// TODO: Dior8wsi sel 6 pdf
		// An exw h(n) = 0 kai stin teleftaia 8esi exw keno tote den eimai se teliki katastasi kai prepei na afksisw tin h(n)
		if (returnValue == 0 && state.stringArray[state.stringArray.length - 1] == '-') { returnValue++; }
		
		return returnValue;
	}
	
	// TODO: Apodekti evretiki sinartisi - test me idea apostasis apo kentro - DES SEL 8 & 9 PDF gia eksigisi
	public int heuresticFunctionDistanceFromCenter(SearchProblemNode state) {
		
		// an briskomai se teliki katastasi tote i evretiki prepei na epistrefei 0
		if (state.isFinalState()) { return 0; }
		
		// an ftasw edw simainei oti den briskomai se teliki katastasi ara synexizw
		int returnValue = 0; // initialization
		
		for (int i = 0; i < state.stringArray.length; i++) {
			
			// an brw mavri sfaira deksia tou kentrou tote afksanw to returnValue me ta kritiria tis sel 8
			if (state.stringArray[i] == 'm' && i > (state.stringArray.length / 2)) {
				
				// TODO: na testarw poes times bgazoun kalytero apotelesma
				// eimai konta sto kentro
				if (Math.abs(i - state.stringArray.length / 2) <= 2) { returnValue += 1; } // i kai += 2 
				// eimai makria apo to kentro
				else { returnValue += 3; } // i kai += 4
			}
			
			// an brw aspri sfaira aristera tou kentrou tote afksanw to returnValue me ta kritiria tis sel 8
			if (state.stringArray[i] == 'a' && i < (state.stringArray.length / 2)) {
				
				// TODO: na testarw poes times bgazoun kalytero apotelesma
				// eimai konta sto kentro
				if (Math.abs(i - state.stringArray.length / 2) <= 2) { returnValue += 1; } // i kai += 2
				// eimai makria apo to kentro
				else { returnValue += 3; } // i kai += 4
			}
		}
		
		// TODO: Dior8wsi sel 6 pdf
		// An exw h(n) = 0 kai stin teleftaia 8esi exw keno tote den eimai se teliki katastasi kai prepei na afksisw tin h(n)
		if (returnValue == 0 && state.stringArray[state.stringArray.length - 1] == '-') { returnValue++; }
		
		return returnValue;
	}
	
	// TODO: Apodekti evretiki sinartisi - test me idea apostasis apo kentro beltiwmeni - DES SEL 10 & 11 PDF gia eksigisi
	public int heuresticFunctionDistanceFromCenterBelt(SearchProblemNode state) {
		
		// an briskomai se teliki katastasi tote i evretiki prepei na epistrefei 0
		if (state.isFinalState()) { return 0; }
		
		// an ftasw edw simainei oti den briskomai se teliki katastasi ara synexizw
		int returnValue = 0; // initialization
		
		if (state.N == 3) {
			
			for (int i = 0; i < state.stringArray.length; i++) {
				
				// an brw mavri sfaira deksia tou kentrou tote afksanw to returnValue me ta kritiria tis sel 10
				if (state.stringArray[i] == 'm' && i > (state.stringArray.length / 2)) {
				
					// eimai konta sto kentro
					if (Math.abs(i - state.stringArray.length / 2) <= 2) { returnValue += 1; }
					// eimai makria apo to kentro
					else { returnValue += 2; }
				}
			
				// an brw aspri sfaira aristera tou kentrou tote afksanw to returnValue me ta kritiria tis sel 10
				if (state.stringArray[i] == 'a' && i < (state.stringArray.length / 2)) {
				
					// eimai konta sto kentro
					if (Math.abs(i - state.stringArray.length / 2) <= 2) { returnValue += 1; }
					// eimai makria apo to kentro
					else { returnValue += 2; }
				}
			}
			
			// TODO: Dior8wsi sel 6 pdf
			// An exw h(n) = 0 kai stin teleftaia 8esi exw keno tote den eimai se teliki katastasi kai prepei na afksisw tin h(n)
			if (returnValue == 0 && state.stringArray[state.stringArray.length - 1] == '-') { returnValue++; }
		
		} else { // state.N > 3
			
			for (int i = 0; i < state.stringArray.length; i++) {
				
				// an brw mavri sfaira deksia tou kentrou tote afksanw to returnValue me ta kritiria tis sel 10
				if (state.stringArray[i] == 'm' && i > (state.stringArray.length / 2)) {
				
					// eimai konta sto kentro
					if (Math.abs(i - state.stringArray.length / 2) <= 3) { returnValue += 1; }
					// eimai makria apo to kentro
					else { returnValue += 2; }
				}
			
				// an brw aspri sfaira aristera tou kentrou tote afksanw to returnValue me ta kritiria tis sel 10
				if (state.stringArray[i] == 'a' && i < (state.stringArray.length / 2)) {
				
					// eimai konta sto kentro
					if (Math.abs(i - state.stringArray.length / 2) <= 3) { returnValue += 1; }
					// eimai makria apo to kentro
					else { returnValue += 2; }
				}
			}
			
			// TODO: Dior8wsi sel 6 pdf
			// An exw h(n) = 0 kai stin teleftaia 8esi exw keno tote den eimai se teliki katastasi kai prepei na afksisw tin h(n)
			if (returnValue == 0 && state.stringArray[state.stringArray.length - 1] == '-') { returnValue++; }
		}
		
		return returnValue;
	}
		
	// Alpha Star
	// idia me tin UCS alla ekei pou diaferei einai sto kritirio epilogis tis prwtis se seiras katastasis tou metwpou anazitisis
	// I diafora afti fainetai stin me8odo removeAndReturnOptimalStateAstar ston ipologismo tou kritiriou epilogis katastasewn - grammi 241
	// i UCS epelege tin katastasi me to mikrotero g(n) enw i A* epilegei tin katastasi me to mikrotero g(n) + h(n)
	public void alphaStar() {
		
		System.out.println("Performing Alpha Star search...");
		
		boolean optimalPathFound = false;
		
		// arxiki katastasi is already inside metwpo anazitisis san riza - stoixeio me deikti 0
		
		// Efarmogi algori8mou apo diafaneies
		// Genikos Algori8mos Anazitisis - Bima 1 - DONE
		
		// Arxi epanaliptikou elegxou
		while (!optimalPathFound) {
			
			// Genikos Algori8mos Anazitisis - Bima 2
			// An to metwpo anazitisis einai adeio tote stamataw
			if (this.metwpoAnazitisis.isEmpty()) { System.out.println("Could not find an optimal path."); break; }
			
			// Genikos Algori8mos Anazitisis - Bima 3
			// Briskw tin prwti se seira katastasi tou metwpou anazitisis
			// Stin periptwsi mas, ekteloume UCS ara i prwti se seira katastasi einai i katastasi me to mikrotero kostos g(n)
			// Afou brw tin katastasi afti, tin afairw apo to metwpo anazitisis kai tin pros8etw sto kleisto synolo efoson den anikei idi se afto
			SearchProblemNode state = this.removeAndReturnOptimalStateAstar();
			
			// Genikos Algori8mos Anazitisis - Bima 4
			// Elegxw an i katastasi afti einai meros tou kleistou synolou. AN einai meros tou kleistoy synolou pigenw sto bima 2 me continue
			if (this.containsInKleistoSynolo(state)) { continue; }
			
			// an ftasoume edw simainei oti i katastasi afti DEN anikei sto kleisto synolo. Ara prepei na elegksw an einai teliki.
			// TODO: na min ksexasw na tin balw sto kleisto synolo an den einai teliki katastasi - DONE BELOW
			
			// Genikos Algori8mos Anazitisis - Bima 5
			// An afti i katastasi einai teliki, tote tipwnw ti lysi kai termatizw.
			if(state.isFinalState()) {
				
				optimalPathFound = true;
				this.displayBeltistoMonopatiLysis(state);
				break;
			}
			
			// An ftasoume edw simainei oti i katastasi afti DEN anikei sto kleisto synolo kai DEN einai teliki.
			// TODO: Ara afou kanw ta bimata 6 kai 7 na min ksexasw na tin balw sto kleisto synolo - DONE BELOW
			// Genikos Algori8mos Anazitisis - Bima 6
			// Efarmozw tous telestes metabasis dld afksanw ton ari8mo twn epektasewn kai epeita paragw tis katastaseis paidia
			this.ari8mosEpektasewn++;
			this.expandChildrenOf(state); // Edw mesa ginetai to bima 7 - bazw tis katastaseis paidia sto metwpo anazitisis
			
			// Genikos Algori8mos Anazitisis - Bima 8
			// Bazw tin katastasi gonea sto kleisto synolo
			this.kleistoSynolo.add(state);
			
			// Genikos Algori8mos Anazitisis - Bima 9
			// Pigenw pisw sto bima 2 pou einai i arxi tis epanalipsis
		}
	}
	
	/**************************************** MAIN ****************************************/
	
	public static void main (String[] args) {
		
		Scanner userInput = new Scanner(System.in);
		
		// ektelesi UCS
		SearchProblem mySearchProblemUCS = new SearchProblem();
		
		mySearchProblemUCS.metwpoAnazitisis = new ArrayList<SearchProblemNode>();
		mySearchProblemUCS.kleistoSynolo = new ArrayList<SearchProblemNode>();
		mySearchProblemUCS.monopatiLysis = new ArrayList<SearchProblemNode>();
		
		System.out.println("Starting Search Problem Program...");
		System.out.println("Type N value:");
		
		int userNChoice = userInput.nextInt();
		SearchProblemNode state = new SearchProblemNode(userNChoice);	// Dimiourgw tin arxiki katastasi
		mySearchProblemUCS.metwpoAnazitisis.add(state);					// Bazw tin arxiki katastasi sto metwpo anazitisis - Genikos Algori8mos Anazitisis - Bima 1
		mySearchProblemUCS.ari8mosEpektasewn = 0; 						// arxikopoiw ton ari8mo twn epektasewn se 0
		mySearchProblemUCS.ari8mosNodes = 1; 							// arxikopoiw ton ari8mo twn nodes se 1 - i riza mono arxika
		
		System.out.println("Would you like to input a starting string state? (y/n)");
		char userStartingStringChoice = userInput.next().charAt(0);
		
		// apo8ikevw to string tis arxikis katastasis giati 8a to xreiastw gia tin A star parakatw
		String startingString = ""; // initialization
		
		// user inputs the string
		if (userStartingStringChoice == 'y') {
			
			System.out.println("Please input a valid starting string state in the following form: ex. \"ama-mma\". No error checks are performed so be careful.");
			
			String userStartingString = userInput.next();
			startingString = userStartingString; // krataw to arxiko string giati 8a to xreiastw gia tin A star parakatw
			System.out.print("The starting string state is: ");
			//System.out.println(userStartingString);
			//System.out.println();
			
			// Pernaw to string tou xristi sto arxiko node katastasis
			state.stringArray = userStartingString.toCharArray();
			state.cost = 0; // arxikopoiw to kostos tis rizas se 0
			
			// testing
			//for (int i = 0; i < state.stringArray.length; i++) { System.out.print(state.stringArray[i] + " "); }
			//System.out.println();
			System.out.println(state);
			System.out.println();
			
		} else { // userStartingStringChoice == 'n'
			
			state.generateRandomStartingState();
			state.cost = 0; // arxikopoiw to kostos tis rizas se 0
			System.out.print("OK. The computer chose a starting string for you. The starting string state is: ");
			// testing
			//for (int i = 0; i < state.stringArray.length; i++) { System.out.print(state.stringArray[i] + " "); }
			//System.out.println();
			System.out.println(state);
			startingString = state.toString(); // pernaw to arxiko string stin metabliti
			System.out.println();
		}
		
		// testing
		//if (state.isFinalState()) { System.out.println("Teliki katastasi"); }
		//else { System.out.println("Mi-teliki katastasi"); }
		
		// testing
		/*
		char[] testArray = {'a', 'm', 'a', '-', 'm', 'm', 'a'};
		char[] testArray1 = {'m', 'm', 'm', '-', 'a', 'a', 'a'};
		SearchProblemNode state1 = new SearchProblemNode(3);
		state1.parent = state;
		state1.stringArray = testArray;
		state1.cost = 1;
		SearchProblemNode state2 = new SearchProblemNode(3);
		state2.parent = state1;
		state2.stringArray = testArray;
		state2.cost = 2;
		SearchProblemNode state3 = new SearchProblemNode(3);
		state3.parent = state2;
		state3.stringArray = testArray1;
		state3.cost = 3;
		mySearchProblem.displayBeltistoMonopatiLysis(state3);
		*/
		
		// testing
		/*
		char[] testArray1 = {'a', 'm', 'a', '-', 'm', 'm', 'a'};
		char[] testArray2 = {'a', 'm', 'a', '-', 'm', 'm', 'a'};
		char[] testArray3 = {'m', 'm', 'm', 'a', '-', 'a', 'a'};
		
		SearchProblemNode state1 = new SearchProblemNode(3);
		state1.stringArray = testArray1;
		
		SearchProblemNode state2 = new SearchProblemNode(3);
		state2.stringArray = testArray1;
		
		SearchProblemNode state3 = new SearchProblemNode(3);
		state3.stringArray = testArray3;
		
		mySearchProblem.kleistoSynolo.add(state1);
		if (mySearchProblem.containsInKleistoSynolo(state2)) { System.out.println("STATE 2 IS IN KLEISTO SYNOLO"); }
		*/
		
		long startTime; // initialization
		long finishTime; // initialization
		
		startTime = System.nanoTime(); 			// start the timer
		mySearchProblemUCS.uniformCostSearch(); // ektelw tin UCS gia tin eisodo pou do8ike
		finishTime = System.nanoTime(); 		// end the timer
		
		System.out.println("Synolikos ari8mos nodes UCS: " + mySearchProblemUCS.ari8mosNodes);
		System.out.println("Synolikos ari8mos epektasewn UCS: " + mySearchProblemUCS.ari8mosEpektasewn);
		
		mySearchProblemUCS.timeElapsed = finishTime - startTime; 													// calculate time elapsed in nanoseconds
		mySearchProblemUCS.timeElapsed = (double) (mySearchProblemUCS.timeElapsed / 1000000); 						// convert time to milliseconds
		System.out.println("Synolikos xronos ektelesis UCS: " + mySearchProblemUCS.timeElapsed + " milliseconds"); 	// display time in millis
		
		/*
		// Display time elapsed in seconds
		if (mySearchProblem.timeElapsedUCS >= (double) (1000)) {
			
			System.out.println("Synolikos xronos ektelesis: " +  (double) (mySearchProblem.timeElapsedUCS / 1000) + " seconds"); // display time in seconds
			
		} else {
			
			System.out.println("Synolikos xronos ektelesis: " + mySearchProblem.timeElapsedUCS + " milliseconds"); // display time in millis
		}
		*/
		
		System.out.println();
		System.out.println();
		
		// tora ektelw tin A* me tin idia eisodo gia na sygkrinw ari8mo epektasewn kai xrono ektelesis
		SearchProblem mySearchProblemAstar = new SearchProblem();
		
		mySearchProblemAstar.metwpoAnazitisis = new ArrayList<SearchProblemNode>();
		mySearchProblemAstar.kleistoSynolo = new ArrayList<SearchProblemNode>();
		mySearchProblemAstar.monopatiLysis = new ArrayList<SearchProblemNode>();
		
		SearchProblemNode anotherState = new SearchProblemNode(userNChoice); 	// Dimiourgw tin arxiki katastasi
		mySearchProblemAstar.metwpoAnazitisis.add(anotherState); 				// Bazw tin arxiki katastasi sto metwpo anazitisis - Genikos Algori8mos Anazitisis - Bima 1
		mySearchProblemAstar.ari8mosEpektasewn = 0; 							// arxikopoiw ton ari8mo twn epektasewn se 0
		mySearchProblemAstar.ari8mosNodes = 1; 									// arxikopoiw ton ari8mo twn nodes se 1 - i riza mono arxika
		
		// Pernaw to string tis UCS stin A*
		anotherState.stringArray = startingString.toCharArray();
		anotherState.cost = 0; // arxikopoiw to kostos tis rizas se 0
		
		startTime = System.nanoTime(); 		// start the timer
		mySearchProblemAstar.alphaStar(); 	// ektelw tin A* gia tin eisodo pou do8ike
		finishTime = System.nanoTime(); 	// end the timer
		
		System.out.println("Synolikos ari8mos nodes A*: " + mySearchProblemAstar.ari8mosNodes);
		System.out.println("Synolikos ari8mos epektasewn A*: " + mySearchProblemAstar.ari8mosEpektasewn);
		
		mySearchProblemAstar.timeElapsed = finishTime - startTime; 														// calculate time elapsed in nanoseconds
		mySearchProblemAstar.timeElapsed = (double) (mySearchProblemAstar.timeElapsed / 1000000); 						// convert time to milliseconds
		System.out.println("Synolikos xronos ektelesis A*: " + mySearchProblemAstar.timeElapsed + " milliseconds"); 	// display time in millis
		
		System.out.println();
		System.out.println("Press enter to exit...");
		// ylopoihsh tou press enter to exit
		// https://bit.ly/2y54h06
		try { System.in.read(); } catch (IOException e) { e.printStackTrace(); }
		
		// Housekeeping
		userInput.close();
	}
}
