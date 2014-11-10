package org.openlp.lite.service;


import org.openlp.lite.R;
import org.openlp.lite.service.SimpleGestureFilter.SimpleGestureListener;
import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

public class SwipeScreenExample extends Activity implements SimpleGestureListener{
		    private SimpleGestureFilter detector;
    TextView tv;
		@Override
		    public void onCreate(Bundle savedInstanceState) {
		        super.onCreate(savedInstanceState);
		        setContentView(R.layout.swipe_screen);
		        tv = (TextView)findViewById(R.id.sample);
		        // Detect touched area 
		        detector = new SimpleGestureFilter(this,this);
		}
		 
    @Override
    public boolean dispatchTouchEvent(MotionEvent me){
    	// Call onTouchEvent of SimpleGestureFilter class
         this.detector.onTouchEvent(me);
       return super.dispatchTouchEvent(me);
    }
	@Override
	 public void onSwipe(int direction) {
	  String str = "";
	 
	  switch (direction) {
	 
	  case SimpleGestureFilter.SWIPE_RIGHT :
          str = "Swipe Right";

	                                           break;
	  case SimpleGestureFilter.SWIPE_LEFT :  str = "Swipe Left";
	                                                 break;
	  case SimpleGestureFilter.SWIPE_DOWN :  str = "Swipe Down";
	                                                 break;
	  case SimpleGestureFilter.SWIPE_UP :    str = "Swipe Up";
	                                                 break;
	 
	  }
	   //Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
        tv.setText(str);
        tv.setTextSize(20);
        tv.setTypeface(Typeface.DEFAULT);
        tv.setTypeface(Typeface.DEFAULT_BOLD);
	 }
	 
	 @Override
	 public void onDoubleTap() {
	    Toast.makeText(this, "Double Tap", Toast.LENGTH_SHORT).show();
	 }
		 
  }