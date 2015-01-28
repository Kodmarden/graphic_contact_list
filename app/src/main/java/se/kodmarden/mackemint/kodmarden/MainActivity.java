package se.kodmarden.mackemint.kodmarden;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * This class implements the interfaces OnTouchListener and OnDragListener
 * This means that the textviews and layouts can be implicitly assigned to View actions
 */
public class MainActivity extends Activity implements View.OnTouchListener, View.OnDragListener
{

    final String LOGCAT = "debug";

    TextView[] _textView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * This is a really nifty way of assigning View events without locking it in.
         */
        findViewById(R.id.olleView).setOnTouchListener(this);
        findViewById(R.id.pinkLayout).setOnDragListener(this);
        findViewById(R.id.yellowLayout).setOnDragListener(this);

        /**
         * TextViews in the corner
         */
        _textView = new TextView[4];

        _textView[0] = (TextView) findViewById(R.id.ringView);
        _textView[1] = (TextView) findViewById(R.id.smsView);
        _textView[2] = (TextView) findViewById(R.id.editView);
        _textView[3] = (TextView) findViewById(R.id.mailView);


    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
            view.startDrag(null, shadowBuilder, view, 0);
            view.setVisibility(View.INVISIBLE);
            return true;
        } else {
            return false;
        }
    }



    public boolean onDrag(View layoutview, DragEvent dragevent) {
        int action = dragevent.getAction();
        switch (action) {
            case DragEvent.ACTION_DRAG_STARTED:
                Log.d(LOGCAT, "Drag event started");
                setVisibility(View.VISIBLE);
                break;
            case DragEvent.ACTION_DRAG_ENTERED:
                Log.d(LOGCAT, "Drag event entered into "+layoutview.toString());
                break;
            case DragEvent.ACTION_DRAG_EXITED:
                Log.d(LOGCAT, "Drag event exited from "+layoutview.toString());
                break;
            case DragEvent.ACTION_DROP:
                Log.d(LOGCAT, "Dropped");
                View view = (View) dragevent.getLocalState();
                ViewGroup owner = (ViewGroup) view.getParent();
                owner.removeView(view);
                LinearLayout container = (LinearLayout) layoutview;
                container.addView(view);
                view.setVisibility(View.VISIBLE);
                setVisibility(View.INVISIBLE);
                break;
            case DragEvent.ACTION_DRAG_ENDED:
                Log.d(LOGCAT, "Drag ended");
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * Sets visibility of aggregator TextViews
     * @param a visible state
     */
    void setVisibility(int a)
    {
        for (int i = 0 ; i < _textView.length; i++)
            _textView[i].setVisibility(a);
    }
}
