package test.v2;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class ModeDeuxJoueurs extends ActionBarActivity {
	
	private RadioGroup radioSymbolJ1;
	private RadioGroup radioFirstToPlay;
	
	private String nameJ1;
	private String nameJ2;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mode_deux_joueurs);
		
		// On souligne le text "Accueil"
		TextView txtGoHome = (TextView) findViewById(R.id.txtGoHome);
		txtGoHome.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
		// Et on ajoute un listenner à ce textView afin que le click sur ce
		// TextView redirige vers la page d'accueil
		txtGoHome.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
		        Intent myIntent = new Intent(ModeDeuxJoueurs.this, MainActivity.class);
		        ModeDeuxJoueurs.this.startActivity(myIntent);
			}
		});
		
		// On récupère les radios buttons correspondant aux symboles
		RadioButton crossJ1 = (RadioButton) findViewById(R.id.radioCrossJ1);
		RadioButton circleJ1 = (RadioButton) findViewById(R.id.radioCircleJ1);
		RadioButton crossJ2 = (RadioButton) findViewById(R.id.radioCrossJ2);
		RadioButton circleJ2 = (RadioButton) findViewById(R.id.radioCircleJ2);
		
		// On récupère les radioGroupe
    	radioSymbolJ1 = (RadioGroup) findViewById(R.id.radioSymbolJ1);
    	radioFirstToPlay = (RadioGroup) findViewById(R.id.radioFirstToPlay);
		
		// Et on ajoute un listenner à chacun de ces radioButton
		crossJ1.setOnClickListener(new ListennerRadioButton());
		circleJ1.setOnClickListener(new ListennerRadioButton());
		crossJ2.setOnClickListener(new ListennerRadioButton());
		circleJ2.setOnClickListener(new ListennerRadioButton());
		
		// Puis on récupère le bouton "play" et on lui assigne un écouteur
		// Ce bouton déclenchera une intention afin de passer à la partie
		// De plus, on fourni quelques paramètres à la future activité
		Button btnPlay = (Button) findViewById(R.id.btnPlay);
		btnPlay.setOnClickListener(new View.OnClickListener() {
			
		    public void onClick(View v) {
		        // Do something in response to button play click
		    	int symbolJ1 = 1, symbolJ2 =2;
		    	int firstToPlay;

		    	if(radioSymbolJ1.getCheckedRadioButtonId() == R.id.radioCircleJ1)
		    	{
		    		symbolJ1 = 2;
		    		symbolJ2 = 1;
		    	}
		    	
		    	if(radioFirstToPlay.getCheckedRadioButtonId() == R.id.radioJ1First)
		    		firstToPlay = symbolJ1;
		    	else
		    		firstToPlay = symbolJ2;

		    	EditText etNameJ1 = (EditText) findViewById(R.id.inNamePlayer1);
		    	EditText etNameJ2 = (EditText) findViewById(R.id.inNamePlayer2);
		    	
		    	nameJ1 = etNameJ1.getText().toString();
		    	nameJ2 = etNameJ2.getText().toString();
		    	
		    	if(nameJ1.matches(""))
		    		nameJ1 = "Inconnu";
		    	if(nameJ2.matches(""))
		    		nameJ2 = "Inconnu";
		    	 
		        Intent myIntent = new Intent(ModeDeuxJoueurs.this, Game2Player.class);
		        
		        myIntent.putExtra("nameJ1", nameJ1);
		        myIntent.putExtra("nameJ2", nameJ2);
		        
		        // Pas besoin d'envoyer le symbole de J2 car à 
		        // partir du symbole de J1 on connait celui de J2
		        myIntent.putExtra("symbolJ1", symbolJ1);
		        
		        myIntent.putExtra("firstToPlay", firstToPlay); 

		        ModeDeuxJoueurs.this.startActivity(myIntent);
		    }
		});
	}
	
	
	private class ListennerRadioButton implements View.OnClickListener
	{

		@Override
		public void onClick(View v) {
			int id = v.getId();
			switch (id)
			{
			// Si le joueur J1 a choisit la croix, alors on selectionne le rond pour 
			// le joueur J2
			case R.id.radioCrossJ1 :
				RadioButton radioCircleJ2 = (RadioButton) findViewById(R.id.radioCircleJ2);
				radioCircleJ2.setChecked(true);
				break;
				
			// Si le joueur J1 a choisit le rond, alors on selectionne la croix pour 
			// le joueur J2
			case R.id.radioCircleJ1 :
				RadioButton radioCrossJ2 = (RadioButton) findViewById(R.id.radioCrossJ2);
				radioCrossJ2.setChecked(true);
				break;
				
			// Si le joueur J2 a choisit la croix, alors on selectionne le rond pour 
			// le joueur J1
			case R.id.radioCrossJ2 :
				RadioButton radioCircleJ1 = (RadioButton) findViewById(R.id.radioCircleJ1);
				radioCircleJ1.setChecked(true);
				break;
				
			// Si le joueur J2 a choisit le rond, alors on selectionne la croix pour 
			// le joueur J1
			case R.id.radioCircleJ2 :
				RadioButton radioCrossJ1 = (RadioButton) findViewById(R.id.radioCrossJ1);
				radioCrossJ1.setChecked(true);
				break;
				
			default :
				break;
			}
		}
		
	}
}
