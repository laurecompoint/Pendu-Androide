package com.example.pendu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
        //return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.menu_other:
               // Toast.makeText(this, "Other menu selected", Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, GamePendu.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);


        }

    }
    public void startgame(View view){
        startActivity(new Intent(this, GamePendu.class));
    }

}
