package nguyenlexuantung15026121.finalexam.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import nguyenlexuantung15026121.finalexam.R;
import nguyenlexuantung15026121.finalexam.models.Producer;

public class ProducerCollectionAdapter extends ArrayAdapter<Producer> {

    Activity context = null;
    ArrayList<Producer> producerArrayList = null;
    int layoutID;

    public ProducerCollectionAdapter(@NonNull Activity context, int textViewResourceId, @NonNull ArrayList<Producer> objects) {
        super(context, textViewResourceId, objects);
        this.context = context;
        this.producerArrayList = objects;
        this.layoutID = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        convertView = layoutInflater.inflate(layoutID, null);

        if (producerArrayList.size() > 0 && position >= 0) {
            Producer producer = producerArrayList.get(position);
            TextView tvName = convertView.findViewById(R.id.tvProducer);
            tvName.setText(producer.getProducer_id() + " " + producer.getProducer_name()); // OR producer.toString()

            ImageView imgItem = (ImageView) convertView.findViewById(R.id.img_item);
            if( producer.getProducer_name().contains("A")){
                imgItem.setImageResource(R.drawable.ic_star);
            }
        }

        /// java.lang.IllegalStateException: ArrayAdapter requires the resource ID to be a TextView
        // return super.getView(position, convertView, parent);
        return convertView;
    }


    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
