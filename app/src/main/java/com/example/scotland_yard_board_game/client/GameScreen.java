package com.example.scotland_yard_board_game.client;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.scotland_yard_board_game.R;
import com.ortiz.touchview.TouchImageView;

public class GameScreen extends AppCompatActivity { // extends View {
    private static final String TAG = "GameScreen";
    private ConstraintLayout gameScreenLayout;
    private TouchImageView gameBoardView; //  = null;
    private final int BOARD_MAX_X = 4368;
    private final int BOARD_MAX_Y = 3312;

    private Paint paint = new Paint();
    private Path path = new Path();

    //implement draggable player button
    /*
    private Button button;
    private static final String BUTTON_VIEW_TAG = "DRAGGABLE BUTTON"; */

    /*
    public GameScreen(Context context, AttributeSet attributeSet) {
        // super(context, attributeSet);
        paint.setAntiAlias(true);
        paint.setColor(Color.GREEN);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5f);
    } */

    /*
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(path, paint);
    } */

    @SuppressLint("ClickableViewAccessibility") // todo: remove later?
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);
        // findViews(); // todo: necessary?
        // implementEvents(); // todo: necessary?

        gameScreenLayout = (ConstraintLayout) findViewById(R.id.gameScreenLayout);
        gameBoardView = (TouchImageView) findViewById(R.id.gameBoardView);
        gameBoardView.setMaxZoom(5);

        // Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.game_background_bmp);
        // Bitmap mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true); // must be a copy and be mutable
        // Canvas canvas = new Canvas(mutableBitmap);

        /*

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.GREEN);
        canvas.drawCircle(300, 200, 100, paint); */

        gameBoardView.setOnTouchListener((view, motionEvent) -> {
            int maxScreenWidth = gameScreenLayout.getWidth(); // screen size
            int maxScreenHeight = gameScreenLayout.getHeight();

            int screenX = (int) motionEvent.getX(); // touched screen coordinates
            int screenY = (int) motionEvent.getY();

            RectF rectF = gameBoardView.getZoomedRect(); // current left, top, zoom factor
            double left = rectF.left;
            double top = rectF.top;
            double zoomFactor = gameBoardView.getCurrentZoom();


            double right = rectF.right; // probably not needed
            double bottom = rectF.bottom; // probably not needed


            // calculation of boardX
            double conversionFactor = (double) BOARD_MAX_Y / maxScreenHeight; // 5.710344827586207
            double currentBoardWidth = (double) (BOARD_MAX_X * zoomFactor / conversionFactor);
            int offsetX = (int) (maxScreenWidth / 2 - currentBoardWidth / 2);
            int negativeOffsetX = 0;
            int currentBoardX;
            if (offsetX > 0) {
                currentBoardX = (int) ((screenX - offsetX) * conversionFactor / zoomFactor);
            } else {
                negativeOffsetX = (int) (left * BOARD_MAX_X * (zoomFactor * 2) / conversionFactor); // todo: not correct yet
                currentBoardX = (int) (screenX * conversionFactor / zoomFactor) + negativeOffsetX;
            }
            int negativeOffsetY = (int) (top * BOARD_MAX_Y * (zoomFactor * 2) / conversionFactor); // todo: not correct yet
            int currentBoardY = (int) (screenY * conversionFactor / zoomFactor) + negativeOffsetY;

            // paint.setStyle(Paint.Style.FILL);
            // paint.setColor(Color.GREEN);
            // canvas.drawCircle(300, 200, 100, paint);

            Log.d(TAG, "onTouch: maxScreenWidth = " + maxScreenWidth +
                    ", maxScreenHeight = " + maxScreenHeight);
            Log.d(TAG, "onTouch: left = " + left + ", top = " + top +
                    ", zoom factor = " + zoomFactor);
            Log.d(TAG, "onTouch: right = " + right + ", bottom = " + bottom);
            Log.d(TAG, "onTouch: screenX, screenY = " + screenX + ", " + screenY);
            Log.d(TAG, "onTouch: conversionFactor = " + conversionFactor);
            Log.d(TAG, "onTouch: currentBoardWidth = " + currentBoardWidth);
            Log.d(TAG, "onTouch: offsetX = " + offsetX);
            if (offsetX < 0 ) {
                Log.d(TAG, "onTouch: offsetX < 0");
            }
            Log.d(TAG, "onTouch: negativeOffsetX = " + negativeOffsetX +
                    ", negativeOffsetY = " + negativeOffsetY);
            Log.d(TAG, "***** onTouch: currentBoardX = " + currentBoardX +
                    ", currentBoardY = " + currentBoardY + " *****");
            Log.d(TAG, "onTouch: delta-height = " + (bottom - top));



            // Bitmap bitmap = new Bitmap("game_background_bmp.bmp");
            // Canvas canvas = new Canvas(gameBoardView.;


            /*
            public Bitmap createImage(){
                Bitmap image = bmp.copy(Bitmap.Config.RGB_565, true);
                Canvas canvas = new Canvas(image);
                Paint paint = new Paint();
                paint.setStyle(Paint.Style.STROKE);
                paint.setColor(Color.GREEN);


                touchX=touchX- image.getWidth() / 2;
                touchY=touchY- image.getHeight() / 2;

                canvas.drawCircle(touchX, touchY, radius, paint);
                System.out.println("Drew a circle at "+touchX+" " + touchY+" with a radius of "+radius+".");
                return image;
            } */

            return true;
        });
    }


    /*
    public void onDraw(Canvas canvas){
        super..onDraw(canvas);
        canvas.drawRect(r, new Paint());
    } */

    //PopupMenu Transportation
    //Show Popup Menu on button
    /*
    public void showPopup(View v){
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.popup_menu);
        popup.show();
    } */

    //click handling of every single item
    /*
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
    } */

    //Find view and set Tag to draggable view
    /*
    private void findViews(){
        button = (Button) findViewById(R.id.charButton);
        button.setTag(BUTTON_VIEW_TAG);
    } */

    //implement LongClick and DragListener
    /*
    private void implementEvents(){
        button.setOnLongClickListener(this::onLongClick);

        findViewById(R.id.station1).setOnDragListener(this::onDrag);
        findViewById(R.id.station9).setOnDragListener(this::onDrag);
    } */

    //drag object
    //response to long press on a view
    /*
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
    } */

    //called method at drag event
    /*
    public boolean onDrag(View view, DragEvent event){
        // Defines a variable to store the action type for the incoming event
        int action = event.getAction();

        // Handles each of the expected events
        switch(action){
            case DragEvent.ACTION_DRAG_STARTED:
                // Determines if this View can accept the dragged data
                if(event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)){
                    // returns true to indicate that the View can accept the dragged data.
                    return true;
                }
                // Returns false. During the current drag and drop operation, this View will
                // not receive events again until ACTION_DRAG_ENDED is sent.
                return false;
            case DragEvent.ACTION_DRAG_ENTERED:
                // Applies a YELLOW or any color tint to the View, when the dragged view entered into drag acceptable view
                // Return true; the return value is ignored.
                view.getBackground().setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_IN);
                view.invalidate();

                return true;
            case DragEvent.ACTION_DRAG_LOCATION:
                // Ignore the event
                return true;
            case DragEvent.ACTION_DRAG_EXITED:
                // Re-sets the color tint to blue, if you had set the BLUE color or any color in ACTION_DRAG_STARTED. Returns true; the return value is ignored.
                //view.getBackground().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);
                //If u had not provided any color in ACTION_DRAG_STARTED then clear color filter.
                view.getBackground().clearColorFilter();
                // Invalidate the view to force a redraw in the new tint
                view.invalidate();

                return true;
            case DragEvent.ACTION_DROP:
                // Gets the item containing the dragged data
                ClipData.Item item = event.getClipData().getItemAt(0);
                // Gets the text data from the item.
                String dragData = item.getText().toString();

                // Displays a message containing the dragged data.
                Toast.makeText(this, "Dragged data is " + dragData, Toast.LENGTH_SHORT).show();

                // Turns off any color tints
                view.getBackground().clearColorFilter();
                view.invalidate();

                //remove dragged view, cast view into LinearLayout, add dragged view, set visible
                View v = (View) event.getLocalState();
                ViewGroup owner = (ViewGroup) v.getParent();
                owner.removeView(v);
                LinearLayout container = (LinearLayout) view;
                container.addView(v);
                v.setVisibility(View.VISIBLE);

                // Returns true. DragEvent.getResult() will return true.
                return true;
            case DragEvent.ACTION_DRAG_ENDED:
                // Turns off any color tinting
                view.getBackground().clearColorFilter();

                view.invalidate();

                // Does a getResult(), and displays what happened.
                if(event.getResult())
                    Toast.makeText(this, "Character has been moved", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this,"Character has not been moved",Toast.LENGTH_SHORT).show();

                return true;
            //unknown action type received
            default:
                Log.e("DragDrop", "Unknown action type received by OnDragListener");
                break;
        }
        return false;

    }// onDrag done */
}
