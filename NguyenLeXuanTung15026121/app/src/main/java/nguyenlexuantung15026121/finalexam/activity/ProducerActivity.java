package nguyenlexuantung15026121.finalexam.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import nguyenlexuantung15026121.finalexam.IntentActivity;
import nguyenlexuantung15026121.finalexam.MainActivity;
import nguyenlexuantung15026121.finalexam.R;

/// R package error if create Activity not in root package
public class ProducerActivity extends AppCompatActivity {
    Button btnClose, btnSave;
    EditText edtID, edtName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producer);

        edtID = (EditText) findViewById(R.id.edtProducerID);
        edtName = (EditText) findViewById(R.id.edtProducerName);
        btnClose = (Button) findViewById(R.id.btnCloseProducer);
        btnSave = (Button) findViewById(R.id.btnSaveProducer);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent postProducerIntent = new Intent(ProducerActivity.this, IntentActivity.class);
                postProducerIntent.putExtra("producer_id", Integer.parseInt(edtID.getText().toString()));
                postProducerIntent.putExtra("producer_name", edtName.getText().toString());
                startActivityForResult(postProducerIntent,9999);
            }
        });
    }
}