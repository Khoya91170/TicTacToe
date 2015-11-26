package test.v2;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

public class ModeUnJoueur extends ActionBarActivity {

	private RadioGroup radioSymbol;
	private RadioGroup radioFirstToPlay;
	private Button btnPlay;
	private EditText editName;
	
	private String playerName;
	private int symbol;
	private int firstToPlay;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mode_un_joueur);
		
		// On souligne le text "Accueil"
		TextView txtGoHome = (TextView) findViewById(R.id.txtGoHome);
		txtGoHome.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
		
		// Récupération du bouton play
		btnPlay = (Button) findViewById(R.id.btnPlay);
		
		// Récupération des radiogroup du choix de symbol
		radioSymbol = (RadioGroup) findViewById(R.id.radioSymbol);
		// ainsi que celui du choix du joueur commençant la partie
		radioFirstToPlay = (RadioGroup) findViewById(R.id.radioFirstToPlay);
		editName = (EditText) findViewById(R.id.inNamePlayer);
		
		btnPlay.setOnClickListener(new View.OnClickListener() {
			
		    public void onClick(View v) {
		        // Do something in response to button play click
		    	symbol = 1;
		    	firstToPlay = 1;
		    	if(radioSymbol.getCheckedRadioButtonId() == R.id.radioCircle)
		    		symbol = 2;
		    	
		    	if(radioFirstToPlay.getCheckedRadioButtonId() == R.id.radioCircleFTP)
		    		firstToPlay = 2;

		    	
		    	playerName = editName.getText().toString();
		    	if(playerName.matches(""))
		    		playerName = "Inconnu";
		    	 
		        Intent myIntent = new Intent(ModeUnJoueur.this, Game.class);
		        
		        myIntent.putExtra("playerName", playerName);
		        myIntent.putExtra("symbol", symbol);
		        myIntent.putExtra("firstToPlay", firstToPlay); 
		        myIntent.putExtra("OnePlayer", true);
		        ModeUnJoueur.this.startActivity(myIntent);
		    }
		});
		
		txtGoHome.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
		        Intent myIntent = new Intent(ModeUnJoueur.this, MainActivity.class);
		        ModeUnJoueur.this.startActivity(myIntent);
			}
		});
	}
}
