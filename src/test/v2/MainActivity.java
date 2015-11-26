package test.v2;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // On recupère tous les TextView de l'activité
        TextView txtWelcome = (TextView) findViewById(R.id.txtBienvenue);
        TextView txtOnePlayer = (TextView) findViewById(R.id.txtOnePlayer);
        TextView txtTwoPlayer = (TextView) findViewById(R.id.txtTwoPlayer);
        TextView txtCredit = (TextView) findViewById(R.id.txtCredit);
        
        // On met tous ces TextView en gras et en italique
        txtOnePlayer.setTypeface(null, Typeface.BOLD_ITALIC);
        txtTwoPlayer.setTypeface(null, Typeface.BOLD_ITALIC);
        txtCredit.setTypeface(null, Typeface.BOLD_ITALIC);
        txtWelcome.setTypeface(null, Typeface.BOLD_ITALIC);
        
        // Et on ajoute à chacun des TextView un écouteur qui va permettre 
        // à chaque clic sur les textView de rediriger vers la bonne activité
        
        txtOnePlayer.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
	            Intent myIntent = new Intent(MainActivity.this, ModeUnJoueur.class);
	            MainActivity.this.startActivity(myIntent);
				
			}
		});
        
        
        txtTwoPlayer.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
		        Intent myIntent = new Intent(MainActivity.this, ModeDeuxJoueurs.class);
		        MainActivity.this.startActivity(myIntent);
			}
		});
        
        txtCredit.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
		        Intent myIntent = new Intent(MainActivity.this, Credit.class);
		        MainActivity.this.startActivity(myIntent);
			}
		});
    }
}
