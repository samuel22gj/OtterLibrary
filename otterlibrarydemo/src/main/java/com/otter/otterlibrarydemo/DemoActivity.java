package com.otter.otterlibrarydemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * A template for demo activity which include a billboard and an operation list.
 */
public abstract class DemoActivity extends AppCompatActivity
        implements AdapterView.OnItemClickListener {

    /** Return a message which you want to show on billboard. */
    public abstract String setBillboard();

    /** Return a string array which include every operation name. */
    public abstract String[] setOperationItem();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_demo);
        TextView billboard = (TextView) findViewById(R.id.billboard);
        ListView operation_list = (ListView) findViewById(R.id.operation_list);

        billboard.setText(setBillboard());

        ArrayAdapter adapter = new ArrayAdapter(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                setOperationItem());
        operation_list.setAdapter(adapter);
        operation_list.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_demo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
