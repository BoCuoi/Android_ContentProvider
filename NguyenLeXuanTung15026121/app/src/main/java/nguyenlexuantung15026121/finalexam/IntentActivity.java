package nguyenlexuantung15026121.finalexam;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import nguyenlexuantung15026121.finalexam.adapter.ProducerCollectionAdapter;
import nguyenlexuantung15026121.finalexam.models.Producer;

public class IntentActivity extends AppCompatActivity {
    ListView listView;
    Button btnIntentShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent);

        listView = (ListView) findViewById(R.id.lvProducer);

        ArrayList<Producer> tempArr = new ArrayList<>();

        int producer_id = getIntent().getIntExtra("producer_id", 0);
        String producer_name = getIntent().getStringExtra("producer_name");

        Producer producer1 = new Producer(1,"A");
        Producer producer2 = new Producer(2,"B");
        tempArr.add(producer1);
        tempArr.add(producer2);
        tempArr.add(new Producer(producer_id, producer_name));
        final ProducerCollectionAdapter adapter = new ProducerCollectionAdapter(IntentActivity.this, R.layout.producer_item, tempArr);
        listView.setAdapter(adapter);




        btnIntentShow = (Button) findViewById(R.id.btnItentShow);
        btnIntentShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = "";
                for (int i = 0; i < adapter.getCount(); i++) {
                    Producer producer = adapter.getItem(i);
                    Toast.makeText(IntentActivity.this, producer.getProducer_name(),Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
}