package se.kodmarden.mackemint.kodmarden;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This class implements the interfaces OnTouchListener and OnDragListener
 * This means that the textviews and layouts can be implicitly assigned to View actions
 */
public class MainActivity extends Activity implements View.OnTouchListener, View.OnDragListener
{

    final String LOGCAT = "mainActivity";



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

        findViewById(R.id.callLayout).setOnDragListener(this);
        findViewById(R.id.smsLayout).setOnDragListener(this);
        findViewById(R.id.mailLayout).setOnDragListener(this);
        findViewById(R.id.editLayout).setOnDragListener(this);

//        findViewById(R.id.background).setOnDragListener(this);

        /**
         * TextViews in the corner
         */
        _textView = new TextView[4];

        _textView[0] = (TextView) findViewById(R.id.callView);
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



    public boolean onDrag(View layoutView, DragEvent dragevent) {
        int action = dragevent.getAction();
        switch (action) {
            case DragEvent.ACTION_DRAG_STARTED:
                Log.d(LOGCAT, "Drag event started");
                setVisibility(View.VISIBLE);
                break;
            case DragEvent.ACTION_DRAG_ENTERED:
                switch(layoutView.getId())
                {
                    case R.id.callLayout:
                    case R.id.smsLayout:
                    case R.id.mailLayout:
                    case R.id.editLayout:
                        layoutView.setBackgroundColor(0xFFFF0000);
                        break;

                }
                break;
            case DragEvent.ACTION_DRAG_EXITED:
                resetBackground(layoutView);
                break;
            case DragEvent.ACTION_DROP:

                View view = (View) dragevent.getLocalState();
                ViewGroup owner = (ViewGroup) view.getParent();
                owner.removeView(view);
                RelativeLayout container = (RelativeLayout) findViewById(R.id.center);
                container.addView(view);
                view.setVisibility(View.VISIBLE);
                setVisibility(View.INVISIBLE);
                String msg;

                switch(layoutView.getId())
                {

                    case R.id.callLayout:
                        msg = "Called ";
                        concatMessage(msg);
                        break;
                    case R.id.smsLayout:
                        msg = "Texted ";
                        concatMessage(msg);
                        break;
                    case R.id.mailLayout:
                        msg = "Mailed ";
                        concatMessage(msg);
                        break;
                    case R.id.editLayout:
                        msg = "Edited ";
                        concatMessage(msg);
                        break;
                    default:
                        setVisibility(View.INVISIBLE);
                        break;


                }

                break;
            case DragEvent.ACTION_DRAG_ENDED:
                resetBackground(layoutView);
                
                break;
            default:
                break;
        }
        return true;
    }

    private void concatMessage(String msg) {
        msg = msg.concat("Olle");
        displayToast(msg);
    }

    private void resetBackground(View layoutView) {
        switch(layoutView.getId())
        {
            case R.id.callLayout:
            case R.id.smsLayout:
            case R.id.mailLayout:
            case R.id.editLayout:
                layoutView.setBackgroundColor(0xFFFFFFFF);
                break;

        }
        return;
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

    private void displayToast(String msg)
    {
        Toast.makeText(this.getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

    }
}
