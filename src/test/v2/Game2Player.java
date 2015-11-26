package test.v2;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Game2Player extends ActionBarActivity {

	private Intent activityLauncher; // Intention qui lance Game2Player
	private final int TAILLE_GRILLE = 9;
	
	private Button listBtn[]; // Cases de la grille du morpion
	
	private TextView displayInfo; // TextView qui affiche le joueur devant jouer mais aussi le gagnant en fin de partie
	
	private TextView infoJ1; // TextView qui affiche le nom du joueur 1 ainsi que le nombre de parties gagn�es
	private TextView infoJ2; // Idem sauf que pour J2
	
	private TicTacToe game; // Classe qui simule le jeu du morpion
	
	// Les caract�res repr�sentent soit 'X' soit 'O' 
	// On fournira ces symboles � l'objet game
	private char J1; // Symbole choisi par J1
	private char J2; // Symbole choisi par J2
	private char whoIsPlaying; // Joueur qui a la main c.a.d soit J1 soit J2
	private char firstToPlay; // Le joueur qui doit commencer la partie
	
	private String nameJ1; // Nom joueur 1
	private String nameJ2; // Nom joueur 2
	
	private int cptVictoireJ1; // Nombre de victoires de J1
	private int cptVictoireJ2; // Nombre de victoires de J2
	
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game2_player);
		
		// On souligne le text "Accueil"
		TextView txtGoHome = (TextView) findViewById(R.id.txtgoHome);
		txtGoHome.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
		
		displayInfo = (TextView) findViewById(R.id.txtInfo);
		displayInfo.setTypeface(null, Typeface.BOLD_ITALIC);
		
		infoJ1 = (TextView) findViewById(R.id.txtinfoJ1);
		infoJ2 = (TextView) findViewById(R.id.txtinfoJ2);
		
		cptVictoireJ1 = 0;
		cptVictoireJ2 = 0;

		
		listBtn = new Button[TAILLE_GRILLE];
		listBtn[0] = (Button) findViewById(R.id.button1);
		listBtn[1] = (Button) findViewById(R.id.button2);
		listBtn[2] = (Button) findViewById(R.id.button3);
		listBtn[3] = (Button) findViewById(R.id.button4);
		listBtn[4] = (Button) findViewById(R.id.button5);
		listBtn[5] = (Button) findViewById(R.id.button6);
		listBtn[6] = (Button) findViewById(R.id.button7);
		listBtn[7] = (Button) findViewById(R.id.button8);
		listBtn[8] = (Button) findViewById(R.id.button9);
		
		Button btnRestart = (Button) findViewById(R.id.btnRestart);
		btnRestart.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				reinitializeGame();
			}
		});
		
		// Suite au clique sur le texte "Accueil", on g�n�re
		// une intention qui permettra de retourner � la page d'accueil
		txtGoHome.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
		        Intent myIntent = new Intent(Game2Player.this, MainActivity.class);
		        Game2Player.this.startActivity(myIntent);
			}
		});
		


		
		// R�cup�ration des donn�es transmises par l'intention
		activityLauncher = getIntent();
		if(activityLauncher != null)
		{
			// R�cup�ration des noms
			
			nameJ1 = activityLauncher.getStringExtra("nameJ1");
			nameJ2 = activityLauncher.getStringExtra("nameJ2");
			
			// Affichage des noms
			
			infoJ1.setText(nameJ1 + " : " + cptVictoireJ1);
			infoJ2.setText(nameJ2 + " : " + cptVictoireJ2);
			
			// R�cup�ration des symboles
			
			int symbolJ1 = activityLauncher.getIntExtra("symbolJ1", -1);
			
			if(symbolJ1 == 1)
			{
				J1 = 'X';
				J2 = 'O';
				
			}
			else
			{
				J1 = 'O';
				J2 = 'X';
			}
			
			// On regarde qui commence
			
			int thefirstToPlay = activityLauncher.getIntExtra("firstToPlay", -1);
			
			if(thefirstToPlay == symbolJ1)
			{
				whoIsPlaying = J1;
			}
			else
				whoIsPlaying = J2;
			
			firstToPlay = whoIsPlaying;
			
			
			// On d�clare un objet TicTacToe c.a.d le jeu

			game = new TicTacToe(J1, J2, whoIsPlaying);

			newGame();
		}

			
		
	}
	
	// M�thode qui recommence la partie
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
	
	// M�thode lance le jeu
	private void newGame()
	{
		// on efface la grille
		game.clearGrille();
		
		// On r�initialise la vue repr�sentant la grille du morpion
		for(int i=0; i<TAILLE_GRILLE; i++)
		{
			listBtn[i].setBackgroundResource(R.drawable.blankbis);
			listBtn[i].setOnClickListener(new ListennerGrille(i));
		}
		// On indique quel joueur � la main 
		if(whoIsPlaying == J1)
			displayInfo.setText("A " + nameJ1);
		else
			displayInfo.setText("A " + nameJ2);			
	}
	
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
	
	// M�thode qui r�initialise la vue repr�sentant la grille du morpion
	private void cleanTheBoard()
	{
		for(int i=0; i<TAILLE_GRILLE; i++)
		{
			listBtn[i].setEnabled(true);
			listBtn[i].setBackgroundResource(R.drawable.blankbis);
		}
	}
	
	// M�thode qui desactive tous les boutons de la vue repr�sentant la grille
	private void disableBoard()
	{
		for(int i=0; i<TAILLE_GRILLE; i++)
		{
			listBtn[i].setEnabled(false);
		}
	}

	// M�thode qui permet � un joueur de jouer � la position donn�e en param�tre
	public void setCoup(int position)
	{
		if(game.getNbCasePleine()!= 9)
		{
			// On affiche le symbole correspondant au joueur qui vient de jouer
			// a la case de position 'position'
			if(whoIsPlaying == 'X')
				listBtn[position].setBackgroundResource(R.drawable.red_cross);
			else
				listBtn[position].setBackgroundResource(R.drawable.green_circle);
			// On desactive ce bouton
			listBtn[position].setEnabled(false);
			// On indique au jeu TicTacToe qu'un joueur � jouer � cette case
			// Le jeu va automatiquement savoir quel joueur a jou�
			game.setCoup(position);
			
			// On indique � qui est le tour
			if(whoIsPlaying == J1)
			{
				whoIsPlaying = J2;
				displayInfo.setText("A " + nameJ2);
			}
			else
			{
				whoIsPlaying = J1;
				displayInfo.setText("A " + nameJ1);
			}
				
			// On v�rifie � chaque fois si l'un des deux joueurs a gagn�
			// ou bien s'il y a �galit�
			if(game.getWinner() == J1)
			{
				
				displayInfo.setText(nameJ1 + " a gagn� !");
				cptVictoireJ1++;
				infoJ1.setText(nameJ1 + " : " + cptVictoireJ1);
				disableBoard();
			}
			else if(game.getWinner() == J2)
			{
				displayInfo.setText(nameJ2 + " a gagn� !");
				cptVictoireJ2++;
				infoJ2.setText(nameJ2 + " : " + cptVictoireJ2);
				disableBoard();
			}
			else if(game.getNbCasePleine() == 9)
			{
				displayInfo.setText("Egalit�");
			}
		}

		
	}
	
	
	// Ecouteur de la classe Game2Player qui permet de retient la position de chaque 
	// bouton dans la vue
	// Au clique de ce bouton on appelle la m�thode setCoup de la classe Game
	private class ListennerGrille implements View.OnClickListener
	{
		int position;
		
		public ListennerGrille(int position)
		{
			this.position = position;
		}
		
		@Override
		public void onClick(View v) {
			Game2Player.this.setCoup(position);
		}
		
	}
}
