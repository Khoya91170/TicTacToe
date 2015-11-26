package test.v2;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Game extends ActionBarActivity {

	private Intent activityLauncher; // Intention qui lance Game
	
	private final int TAILLE_GRILLE = 9;
	
	private Button listBtn[]; // Cases de la grille du morpion
	
	private TextView displayInfo; // TextView qui affiche le joueur devant jouer mais aussi le gagnant en fin de partie
	
	private TextView infoJ1; // TextView qui affiche le nom du joueur 1 ainsi que le nombre de parties gagnées
	private TextView infoJ2; // Idem sauf que pour J2
	
	private TicTacToe game; // Classe qui simule le jeu du morpion
	
    // Les caractères représentent soit 'X' soit 'O' 
    // On fournira ces symboles à l'objet game
	private char J1;// Symbole choisi par J1
    private char J2; // Symbole choisi par J2
	private char whoIsPlaying; // Joueur qui a la main c.a.d soit J1 soit J2
	private char firstToPlay; // Le joueur qui doit commencer la partie

	private String nameJ1; // Nom joueur 1
	
	private int cptVictoireJ1; // Nombre de victoires de J1
	private int cptVictoireJ2; // Nombre de victoires de l'IA

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		
		// On souligne le texte "Accueil"
		TextView txtGoHome = (TextView) findViewById(R.id.txtgoHome);
		txtGoHome.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
		
		displayInfo = (TextView) findViewById(R.id.txtInfo);
		displayInfo.setTypeface(null, Typeface.BOLD_ITALIC);
		
		infoJ1 = (TextView) findViewById(R.id.txtinfoJ1);
		infoJ2 = (TextView) findViewById(R.id.txtinfoJ2);
		
		cptVictoireJ1 = 0;
		cptVictoireJ2 = 0;

		
		listBtn = new Button[TAILLE_GRILLE];
		listBtn[0] = (Button) findViewById(R.id.btn1);
		listBtn[1] = (Button) findViewById(R.id.btn2);
		listBtn[2] = (Button) findViewById(R.id.btn3);
		listBtn[3] = (Button) findViewById(R.id.btn4);
		listBtn[4] = (Button) findViewById(R.id.btn5);
		listBtn[5] = (Button) findViewById(R.id.btn6);
		listBtn[6] = (Button) findViewById(R.id.btn7);
		listBtn[7] = (Button) findViewById(R.id.btn8);
		listBtn[8] = (Button) findViewById(R.id.btn9);
		
		Button btnRestart = (Button) findViewById(R.id.btnRestart);
		btnRestart.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				reinitializeGame();
			}
		});
		
		// Suite au clique sur le texte "Accueil", on génère
		// une intention qui permettra de retourner à la page d'accueil
		txtGoHome.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
		        Intent myIntent = new Intent(Game.this, MainActivity.class);
		        Game.this.startActivity(myIntent);
			}
		});
		


		
		// Récupération des données transmises par l'intention
		activityLauncher = getIntent();
		if(activityLauncher != null)
		{
			
			// Récupération du nom de J1
			nameJ1 = activityLauncher.getStringExtra("playerName");
			
			// Affichage des noms
			infoJ1.setText(nameJ1 + " : " + cptVictoireJ1);
			infoJ2.setText("Ordinateur : " + cptVictoireJ2);
			
			int symbolIntent = activityLauncher.getIntExtra("symbol", -1);
			if(symbolIntent == 1)
			{
				J1 = 'X';
				J2 = 'O';
			}		
			else
			{
				J1 = 'O';
				J2 = 'X';
			}
			
			// On récupère qui commence 
			int firstToPlayIntent = activityLauncher.getIntExtra("firstToPlay", -1);
			if(firstToPlayIntent == 1)
				whoIsPlaying = 'X';
			else
				whoIsPlaying = 'O';
			
			firstToPlay = whoIsPlaying;
			
			// Appel du constructeur TicTacToe 
			game = new TicTacToe(J1,whoIsPlaying);
			// On lance le jeu
			newGame();
		}

			
		
	}
	
	// Méthode qui recommence la partie
	public void restart()
	{
		// On active tous les boutons de la grille
		for(int i=0; i<TAILLE_GRILLE; i++)
		{
			listBtn[i].setEnabled(true);
		}
		// On relance la partie (dans la classe TicTacToe)
		game.restart();
		// On lance le jeu
		newGame();
		
	}
	
	// Méthode lance le jeu
	private void newGame()
	{
		// on efface la grille
		game.clearGrille();
		
		// On initialise la vue représentant la grille du morpion
		for(int i=0; i<TAILLE_GRILLE; i++)
		{
			listBtn[i].setBackgroundResource(R.drawable.blankbis);
			listBtn[i].setOnClickListener(new ListennerGrille(i));
		}
		
		// On indique quel joueur à la main 
		displayInfo.setText("A votre tour");
		// Si au lancement du jeu l'IA doit commencer on appelle la méthode moveOrdi()
		if(whoIsPlaying == J2)
		{
			displayInfo.setText("A l'ordinateur");
			moveOrdi();
		}
			
	}
	
	// Méthode qui réinitialise le jeu 
	private void reinitializeGame()
	{
		
		// On efface la grille
		cleanTheBoard();
		// On relance le jeu TicTacToe
		game.restart();
		
		// On inverse le joueur qui doit commencer
		if(firstToPlay == J1)
			firstToPlay = J2;
		else
			firstToPlay = J1;
		
		whoIsPlaying = firstToPlay;
		game.setWhoIsPlaying(whoIsPlaying);
	
		
		// Et on relance le jeu
		newGame();
	}
	
	
	// Méthode qui réinitialise la vue représentant la grille du morpion
	private void cleanTheBoard()
	{
		for(int i=0; i<TAILLE_GRILLE; i++)
		{
			listBtn[i].setEnabled(true);
			listBtn[i].setBackgroundResource(R.drawable.blankbis);
		}
	}
	
	// Méthode qui desactive tous les boutons de la vue représentant la grille
	private void disableBoard()
	{
		for(int i=0; i<TAILLE_GRILLE; i++)
		{
			listBtn[i].setEnabled(false);
		}
	}

	// Méthode qui permet à un joueur de jouer à la position donnée en paramètre
	public void setCoup(int position)
	{
		if(game.getNbCasePleine()!= 9)
		{
			// On affiche le symbole correspondant au joueur J1
			// a la case où J1 a joué
			if(J1 == 'X')
				listBtn[position].setBackgroundResource(R.drawable.red_cross);
			else
				listBtn[position].setBackgroundResource(R.drawable.green_circle);
			// Et on désactive la case
			listBtn[position].setEnabled(false);
			// On indique au jeu TicTacToe qu'un joueur à jouer à cette case
			// Le jeu va automatiquement savoir quel joueur a joué
			game.setCoup(position);
			// On indique que c'est à l'ordinateur de jouer
			displayInfo.setText("A l'ordinateur");
			whoIsPlaying = J2;

			if(game.getWinner() == J1) // Si J1 a gagné
			{
				displayInfo.setText(nameJ1 + "a gagné !");
				cptVictoireJ1++;
				infoJ1.setText(nameJ1 + " : " + cptVictoireJ1);
				// On desactive la vue représentant la grille
				disableBoard();
			}
			else if(game.getNbCasePleine() == 9)
			{
				displayInfo.setText("Egalité");
			}
			else
				moveOrdi(); // On fait jouer l'ordinateur

			if(game.getWinner() == J2) // Si l'ordi a gagné
			{
				
				displayInfo.setText("L'ordinateur a gagné !");
				cptVictoireJ2++;
				infoJ2.setText("Ordinateur : " + cptVictoireJ2);
				disableBoard(); // On desactive la vue représentant la grille
			}
			else if(game.getNbCasePleine() == 9)
			{
				displayInfo.setText("Egalité");
			}
		}

		
	}
	
	// Méthode qui fait jouer l'ordinateur
	private void moveOrdi()
	{
		// On fait jouer l'ordinateur avec la classe TicTacToe
		// Et on récupère la position où l'ordi a joué
		int pos = game.getMoveOrdi();
		// Et on met à jour la vue représentant la grille
		if(J2 == 'X')
			listBtn[pos].setBackgroundResource(R.drawable.red_cross);
		else
			listBtn[pos].setBackgroundResource(R.drawable.green_circle);
		// On desactive le bouton où a joué l'ordi
		listBtn[pos].setEnabled(false);
		// On indique à J1 que c'est à lui de jouer
		displayInfo.setText("A votre tour");
		
	}

	// Ecouteur de la classe Game qui permet de retient la position de chaque 
	// bouton dans la vue
	// Au clique de ce bouton on appelle la méthode setCoup de la classe Game
	private class ListennerGrille implements View.OnClickListener
	{
		int position;
		
		public ListennerGrille(int position)
		{
			this.position = position;
		}
		
		public void onClick(View v) {
			Game.this.setCoup(position);
		}
		
	}
	
}
