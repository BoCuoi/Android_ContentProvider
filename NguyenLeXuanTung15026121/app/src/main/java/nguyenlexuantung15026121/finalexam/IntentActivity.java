package nguyenlexuantung15026121.finalexam;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
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

        final ArrayList<Producer> tempArr = new ArrayList<>();

        int producer_id = getIntent().getIntExtra("producer_id", 0);
        String producer_name = getIntent().getStringExtra("producer_name");

        Producer producer1 = new Producer(1, "A");
        Producer producer2 = new Producer(2, "B");
        tempArr.add(producer1);
        tempArr.add(producer2);
        tempArr.add(new Producer(producer_id,producer_name));

        ///java.lang.NullPointerException: Attempt to invoke virtual method 'boolean java.lang.String.contains(java.lang.CharSequence)' on a null object reference
        ///Error set imageview compare contains charater on null field
//        tempArr.add(new Producer(producer_id, producer_name));
        final ProducerCollectionAdapter adapter = new ProducerCollectionAdapter(IntentActivity.this, R.layout.producer_item, tempArr);
        listView.setAdapter(adapter);


        btnIntentShow = (Button) findViewById(R.id.btnItentShow);
        btnIntentShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = listView.getChildCount() - 1; i >= 0; i--) {
                    v = listView.getChildAt(i);
                    CheckBox chk = (CheckBox) v.findViewById(R.id.ckbProducer);
                    if (chk.isChecked()) {
                        tempArr.remove(i);
                        Toast.makeText(IntentActivity.this, i + "", Toast.LENGTH_SHORT).show();
                    }
                }
                adapter.notifyDataSetChanged();

            }
        });


    }
}