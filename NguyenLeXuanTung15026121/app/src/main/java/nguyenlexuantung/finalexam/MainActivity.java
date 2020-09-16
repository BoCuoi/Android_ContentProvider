package nguyenlexuantung.finalexam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import nguyenlexuantung.finalexam.activity.AddActivity;
import nguyenlexuantung.finalexam.activity.ProducerActivity;
import nguyenlexuantung.finalexam.activity.ProductActivity;
import nguyenlexuantung.finalexam.activity.SearchActivity;

public class MainActivity extends AppCompatActivity {
    Button btnExitMain;
    ListView listView;

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
        return true;
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
            case R.id.mnProducer:
                Intent itentProducer = new Intent(MainActivity.this, ProducerActivity.class);
                startActivity(itentProducer);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}