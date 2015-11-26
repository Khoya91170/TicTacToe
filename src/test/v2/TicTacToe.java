package test.v2;

import java.util.Random;

public class TicTacToe {
	
	private final int TAILLE_GRILLE = 9;
	private final char EMPTY = ' ';
	
	// J1 et J2 représentent les symboles choisit par chacun des joueurs
	// c.a.d soit 'X' soit 'O'
	private char J1;
	// J2 correspond à l'ordinateur dans le cas où l'utilisateur
	// joue avec l'ordinateur
	private char J2; 
	private char whoIsPlaying; // Joueur qui a la main c.a.d soit J1 soit J2
	
	private char grille []; // Chaque case de la grille contiendra soit J1 soit J2 soit EMPTY
	private int nbCasePleine; // Nombre de case remplie dans la grille
	
	
	// Constructeur avec deux paramètres
	// Ce constructeur sera utilisé lors d'une partie humain vs IA
	public TicTacToe(char J1, char whoIsPlaying)
	{
		grille = new char[TAILLE_GRILLE];
		nbCasePleine = 0;
		this.J1 = J1;
		// Selon ce que l'humain a choisit comme symbole,
		// on choisit le symbole contraire pour l'ordinateur
		if(J1 == 'X')
		{
			J2 = 'O';
		}
		else
			J2 = 'X';
		
		// On récupère le joueur qui a la main,
		// Dans le constructeur, cela correspond au joueur
		// qui commence à jouer
		this.whoIsPlaying = whoIsPlaying;
		
		// On initialise toutes les cases de la grille à vide
		for(int i=0; i<TAILLE_GRILLE; i++)
		{
			grille[i] = EMPTY;
		}
	}
	
	// Constructeur avec trois paramètres
	// Ce constructeur sera utilisé lors d'une partie humain vs humain
	public TicTacToe(char J1, char J2, char whoIsPlaying)
	{
		grille = new char[TAILLE_GRILLE];
		nbCasePleine = 0;
		
		// On récupère les symboles choisit par les joueurs
		this.J1 = J1;
		this.J2 = J2;
		
		// On récupère le joueur qui doit commencer
		this.whoIsPlaying = whoIsPlaying;
		
		// On initialise toutes les cases de la grille à vide
		for(int i=0; i<TAILLE_GRILLE; i++)
		{
			grille[i] = EMPTY;
		}
	}
	
	// Méthode qui permet à un joueur (IA ou humain) de jouer
	// Paramètre :
	// int position : Position où le joueur a choisi de jouer
	public void setCoup(int position)
	{
		// on ajoute dans la grille à la case de numéro 'position'
		// le symbole du joueur qui vient de jouer
		grille[position] = whoIsPlaying;
		nbCasePleine++;
		
		// On inverse la main
		if(whoIsPlaying == J1)
			whoIsPlaying = J2;
		else
			whoIsPlaying = J1;
	}
	
	// Fonction qui permet à l'ordinateur de choisir son coup et de jouer
	// Retourne la position (int) à laquelle à jouer l'ordinateur
	public int getMoveOrdi()
	{
		// Vérification de la possibilité de gagner au prochain coup
		// Si possibilité, alors on joue à cette position
		for(int i=0; i<TAILLE_GRILLE; i++)
		{
			if(grille[i] == EMPTY)
			{
				grille[i] = J2;
				if(getWinner() == J2){
					setCoup(i);
					return i;
				}
				// On remet la case à vide
				grille[i] = EMPTY;
			}
		}
		
		// On bloque le prochain coup de J1 si ce coup est gagnant
		for(int i=0; i<TAILLE_GRILLE; i++)
		{
			if(grille[i] == EMPTY)
			{
				grille[i] = J1;
				if(getWinner() == J1){
					//setCoup(i, J2);
					setCoup(i);
					return i;
				}
				// On remet la case à vide
				grille[i] = EMPTY;
			}
		}
		

		
		// Sinon, on choisit une case vide de manière aléatoire 
		// et on joue à cette position
		Random random = new Random();
		int positionToPlay = random.nextInt(TAILLE_GRILLE);
		
		while(grille[positionToPlay] != EMPTY)
		{
			positionToPlay = random.nextInt(TAILLE_GRILLE);
		}
		setCoup(positionToPlay);
		
		return positionToPlay;
	}
	
	// Fonction qui retourne le gagnant (soit J1 soit J2)
	// Retourne EMPTY si aucun gagnant
	public char getWinner()
	{
		// Parcours des lignes
		for(int i=0; i<=6; i = i+3)
		{
			if(grille[i] == J1 && grille[i+1] == J1 && grille[i+2] == J1)
			{
				return J1;
			}
			if(grille[i] == J2 && grille[i+1] == J2 && grille[i+2] == J2)
			{
				return J2;
			}
		}
		
		// Parcours des colonnes
		for(int i=0; i<=2; i++)
		{
			if(grille[i] == J1 && grille[i+3] == J1 && grille[i+6] == J1)
			{
				return J1;
			}
			if(grille[i] == J2 && grille[i+3] == J2 && grille[i+6] == J2)
			{
				return J2;
			}
		}
		
		// Parcours des diagonales
		
		// 1ere diagonale
		if(grille[0] == J1 && grille[4] == J1 && grille[8] == J1)
			return J1;
		
		if(grille[0] == J2 && grille[4] == J2 && grille[8] == J2)
			return J2;
		
		// 2eme diagonale
		if(grille[2] == J1 && grille[4] == J1 && grille[6] == J1)
			return J1;
		
		if(grille[2] == J2 && grille[4] == J2 && grille[6] == J2)
			return J2;
					
		return EMPTY;
	}
	
	// Méthode qui réinitialise toutes les cases de la grille à vide
	public void clearGrille()
	{
		for(int i=0; i<TAILLE_GRILLE; i++)
		{
			grille[i] = EMPTY;
		}
	}
	
	// Méthode qui permet de relancer le jeu
	// PS : On ne relance pas le jeu à proprement parler
	// 		mais on réinitialise la grille ainsi que le nombre de cases remplies
	public void restart()
	{
		// On réinitialise la grille 
		clearGrille();
		// Ainsi que le nombre de cases remplies
		nbCasePleine = 0;
	}

	// Fonction qui retourne le nombre de cases remplies
	public int getNbCasePleine()
	{
		return nbCasePleine;
	}
	
	// Méthode qui permet d'indiquer au jeu le joueur qui a la main
	public void setWhoIsPlaying(char player)
	{
		whoIsPlaying = player;
	}
	
	
}
