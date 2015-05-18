package com.otter.otterlibrarydemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A template for demo activity which include a billboard and an operation list.
 */
public abstract class DemoActivity extends AppCompatActivity
        implements AdapterView.OnItemClickListener {

    /** Return a message which you want to show on billboard. */
    public abstract String setBillboard();

    /** Return a string array which include every operation name. */
    public abstract String[] setOperationItem();

    private TextView billboard;
    ListView operation_list;
    private ScrollView log_scrollview;
    private TextView log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_demo);
        billboard = (TextView) findViewById(R.id.billboard);
        operation_list = (ListView) findViewById(R.id.operation_list);
        log_scrollview = (ScrollView) findViewById(R.id.log_scrollview);
        log = (TextView) findViewById(R.id.log);

        billboard.setText(setBillboard());

        ArrayAdapter adapter = new ArrayAdapter(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                setOperationItem());
        operation_list.setAdapter(adapter);
        operation_list.setOnItemClickListener(this);
    }

    /** Set the weight of child views. The parameter must greater than zero. */
    public void setLayoutWeight(int billboard, int operation, int log) {
        if (billboard < 0 || operation < 0 || log < 0) {
            return;
        }
        LinearLayout.LayoutParams lp;
        lp = (LinearLayout.LayoutParams) this.billboard.getLayoutParams();
        lp.weight = billboard;
        lp = (LinearLayout.LayoutParams) this.operation_list.getLayoutParams();
        lp.weight = operation;
        lp = (LinearLayout.LayoutParams) this.log_scrollview.getLayoutParams();
        lp.weight = log;
    }

    /** Set the message to the log view. */
    public void setLog(String message) {
        message = enrichMessage(message);
        log.setText(message + "\n");
    }

    /** Append the message to the log view. */
    public void appendLog(String message) {
        message = enrichMessage(message);
        log.append(message + "\n");
        log_scrollview.post(new Runnable() {
            public void run() {
                log_scrollview.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    private String enrichMessage(String message) {
        // Get current time.
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss.SSS");
        String time = format.format(new Date());

        // Make multi line message align timestamp.
        message = message.replace("\n", "\n\t\t\t\t\t\t");

        return time + "\t" + message;
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
