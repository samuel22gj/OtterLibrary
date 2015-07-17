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

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
    private ListView operation_list;
    private ScrollView log_scrollview;
    private TextView log;

    private boolean mTimestampPrefix = false;

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

    /**
     * Change the display ratio of child views.
     *
     * @param operation The weight of Operation ListView. Set 0 to be {@code WRAP_CONTENT}
     * @param log The weight of Log ScrollView. Set 0 to be {@code WRAP_CONTENT}
     */
    public void setLayoutWeight(int operation, int log) {
        LinearLayout.LayoutParams lp;

        // Operation ListView
        lp = (LinearLayout.LayoutParams) this.operation_list.getLayoutParams();
        if (operation > 0) {
            lp.weight = operation;
        } else {
            lp.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            lp.weight = 0;
        }

        // Log ScrollView
        lp = (LinearLayout.LayoutParams) this.log_scrollview.getLayoutParams();
        if (log > 0) {
            lp.weight = log;
        } else {
            lp.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            lp.weight = 0;
        }
    }

    /** Set the message to the log view. */
    public void setLog(String message) {
        if (mTimestampPrefix) {
            message = addTimestampPrefix(message);
        }

        log.setText(message + "\n");
    }

    /** Append the message to the log view. */
    public void appendLog(String message) {
        if (mTimestampPrefix) {
            message = addTimestampPrefix(message);
        }

        log.append(message + "\n");
        log_scrollview.post(new Runnable() {
            public void run() {
                log_scrollview.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    private String addTimestampPrefix(String message) {
        // Get current time.
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss", Locale.US);
        String time = format.format(new Date());

        // Make multi line message align timestamp.
        if (message != null) message = message.replace("\n", "\n         ");

        return time + " " + message;
    }

    /**
     * Determine whether to add timestamp prefix in log or not. The default was disable.
     *
     * @param enabled {@code true} to enable, {@code false} to disable.
     */
    public void setTimestampPrefix(boolean enabled) {
        mTimestampPrefix = enabled;
    }

    /** Print the directory structure on log view. */
    public void printDirOnLog(File dir) {
        if (dir != null && dir.exists()) {
            StringBuilder tree = new StringBuilder();
            tree.append("Print " + dir.getAbsolutePath() + "\n");
            getDirStructure(tree, dir, 0);

            appendLog(tree.toString());
        }
    }

    private void getDirStructure(StringBuilder tree, File dir, int level) {
        File[] children = dir.listFiles();
        for (File child : children) {
            for (int i = 0; i < level; i++) {
                tree.append("    ");
            }

            if (child.isDirectory()) {
                tree.append(child.getName() + " (Dir)\n");
                getDirStructure(tree, child, level + 1);
            } else {
                tree.append(child.getName() + " (" + getFileSizeString(child) + ")\n");
            }
        }
    }

    private String getFileSizeString(File file) {
        if (file == null || !file.exists()) {
            return "";
        }

        double size = file.length();
        if (size < 1024) {
            return size + "Byte";
        } else if (size < 1024 * 1024){
            size /= 1024;
            return size + "K";
        } else if (size < 1024 * 1024 * 1024){
            size /= 1024 * 1024;
            return size + "M";
        } else if (size < (double) 1024 * 1024 * 1024 * 1024){
            size /= 1024 * 1024 * 1024;
            return size + "G";
        } else {
            size /= (double) 1024 * 1024 * 1024 * 1024;
            return size + "T";
        }
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

        switch (id) {
            case R.id.action_clear_log:
                log.setText("");
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
