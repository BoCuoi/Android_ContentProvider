package nguyenlexuantung15026121.finalexam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import nguyenlexuantung15026121.finalexam.activity.AddActivity;
import nguyenlexuantung15026121.finalexam.activity.ProductActivity;
import nguyenlexuantung15026121.finalexam.activity.SearchActivity;

public class MainActivity extends AppCompatActivity {
    Button btnExitMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnExitMain = (Button) findViewById(R.id.btnMainExit);
        btnExitMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        // Load the defined menu resource
        menuInflater.inflate(R.menu.menu_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnAdd:
                Intent intentAdd = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intentAdd);
                return true;
            case R.id.mnSearch:
                Intent itentSearch = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(itentSearch);
                return true;
            case R.id.mnCV:
                Intent itentCV = new Intent(MainActivity.this, ProductActivity.class);
                startActivity(itentCV);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}