package com.example.scotland_yard_board_game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.ortiz.touchview.TouchImageView;

public class GameScreen extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    //mapZoom is the id of zoomable image (jpg)
    private TouchImageView mapZoom = null;

    //implement draggable player button
    private Button button;
    private static final String BUTTON_VIEW_TAG = "DRAGGABLE BUTTON";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);
        findViews();
        implementEvents();

        //print image coordinates
        mapZoom = (TouchImageView) findViewById(R.id.mapZoom);

        mapZoom.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {


                int x = (int)event.getX();
                int y = (int)event.getY();

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.i("TAG", "touched down: (" + x + ", " + y + ")");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.i("TAG", "moving: (" + x + ", " + y + ")");
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.i("TAG", "touched up");
                        break;
                }
                Toast.makeText(getApplicationContext(), "I was touched at (" + x + ", " + y + ")", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

    }

    //PopupMenu Transportation
    //Show Popup Menu on button
    public void showPopup(View v){
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.popup_menu);
        popup.show();
    }

    //click handling of every single item
    @Override
    public boolean onMenuItemClick(MenuItem item){
        switch (item.getItemId()){
            case R.id.itemTaxi:
                Toast.makeText(this, "Taxi selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.itemBus:
                Toast.makeText(this, "Bus selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.itemUnderground:
                Toast.makeText(this, "Underground selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.itemFerry:
                Toast.makeText(this, "Ferry selected", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }
    }
    //drag object
    //response to long press on a view
    public boolean onLongClick(View view){
        // Create a new ClipData.Item from object's tag
        ClipData.Item item = new ClipData.Item((CharSequence) view.getTag());

        //This will create a new ClipDescription object within the
        //ClipData, and set its MIME type entry to "text/plain"
        String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
        ClipData data = new ClipData(view.getTag().toString(),mimeTypes,item);

        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);

        view.startDragAndDrop(data, shadowBuilder, view, 0);

        view.setVisibility(View.INVISIBLE);
        return true;
    }
    public boolean onDrag(View view, DragEvent event){
        int action = event.getAction();

        switch(action){
            case DragEvent.ACTION_DRAG_STARTED:
                if(event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)){
                    return true;
                }
                return false;
            case DragEvent.ACTION_DRAG_ENTERED:
                view.getBackground().setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_IN);
                view.invalidate();

                return true;
            case DragEvent.ACTION_DRAG_LOCATION:
                return true;
            case DragEvent.ACTION_DRAG_EXITED:
                view.getBackground().clearColorFilter();
                view.invalidate();

                return true;
            case DragEvent.ACTION_DROP:
                ClipData.Item item = event.getClipData().getItemAt(0);

                String dragData = item.getText().toString();

                Toast.makeText(this, "Dragged data is " + dragData, Toast.LENGTH_SHORT).show();

                view.getBackground().clearColorFilter();
                view.invalidate();

                View v = (View) event.getLocalState();
                ViewGroup owner = (ViewGroup) v.getParent();
                owner.removeView(v);
                LinearLayout container = (LinearLayout) view;
                container.addView(v);
                v.setVisibility(View.VISIBLE);

                return true;
            case DragEvent.ACTION_DRAG_ENDED:
                view.getBackground().clearColorFilter();

                view.invalidate();

                if(event.getResult())
                    Toast.makeText(this, "Character has been moved", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this,"Character has not been moved",Toast.LENGTH_SHORT).show();

                return true;

            default:
                Log.e("DragDrop", "Unknown action type received by OnDragListener");
                break;
        }
                return false;

    }// onDrag done

    private void findViews(){
        button = (Button) findViewById(R.id.charButton);
        button.setTag(BUTTON_VIEW_TAG);
    }

    private void implementEvents(){
        button.setOnLongClickListener(this::onLongClick);

        findViewById(R.id.charButton).setOnDragListener(this::onDrag);
    }


}