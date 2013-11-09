package mobile.app.dev;

import mobile.app.dev.ueb01.Calculator;
import mobile.app.dev.ueb02.CalculatorExtended;
import mobile.app.dev.ueb03.TodoDetailActivity;
import mobile.app.dev.ueb03.TodoListActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void uebung1(View v){
    	Intent intent = new Intent(this, Calculator.class);
    	startActivity(intent);
    }
    
    public void uebung2(View v){
    	Intent intent = new Intent(this, CalculatorExtended.class);
    	startActivity(intent);
    }
    
    public void uebung3(View v){
    	Intent intent = new Intent(this, TodoListActivity.class);
    	startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}

