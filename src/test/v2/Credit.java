package test.v2;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Credit extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_credit);
		
		// On met en gras et en italique la texView comportant le message 
		// @string/remarque
		TextView txtRemarque = (TextView) findViewById(R.id.txtRemarque);
		txtRemarque.setTypeface(null, Typeface.BOLD_ITALIC);
		
		// De même pour le textView comportant le message @string/developper
		TextView txtDevelopper = (TextView) findViewById(R.id.txtDevelopper);
		txtDevelopper.setTypeface(null, Typeface.BOLD_ITALIC);
		
		// Et on souligne le textView "Accueil"
		TextView txtGoHome = (TextView) findViewById(R.id.txtgoHome);
		txtGoHome.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
		
		// On ajoute un listenner à ce textView qui lorsque l'on 
		// clique dessus redirige vers la page d'accueil c.a.d
		// mainActivity
		txtGoHome.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
		        Intent myIntent = new Intent(Credit.this, MainActivity.class);
		        Credit.this.startActivity(myIntent);
			}
		});
	}

}
