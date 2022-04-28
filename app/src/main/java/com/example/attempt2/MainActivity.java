package com.example.attempt2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Point;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ViewGroup mainLayout;
    private ImageView image;
    private int xDelta;
    private int yDelta;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText nickname;
    private Spinner dropdownColours;

    private Button addBtn;
    private Button editBtn;

    //new
    private Button station1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainLayout = (RelativeLayout) findViewById(R.id.main);
        image = (ImageView) findViewById(R.id.figure1);

        image.setOnTouchListener(onTouchListener());

        station1 = findViewById(R.id.station1);
        Point point = getCenterPointOfView(station1);

        station1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                image.setX(point.x);
                image.setY(point.y);


                /*
                if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                    ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
                    ViewGroup.MarginLayoutParams p2 = (ViewGroup.MarginLayoutParams) image.getLayoutParams();
                    p2.setMarginStart(p.getMarginStart());
                    p2.setMargins(p);
                    view.requestLayout();
                }

                 */
            }
        } );

    }

    private Point getCenterPointOfView(View view) {

        int[] location = new int[2];
        view.getLocationOnScreen(location);

        return new Point(location[0], location[1]);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mymenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                createNewPopupDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private OnTouchListener onTouchListener() {
        return new OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                final int x = (int) event.getRawX();
                final int y = (int) event.getRawY();

                switch (event.getAction() & MotionEvent.ACTION_MASK) {

                    case MotionEvent.ACTION_DOWN:
                        RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams)
                                view.getLayoutParams();

                        xDelta = x - lParams.leftMargin;
                        yDelta = y - lParams.topMargin;
                        break;

                    case MotionEvent.ACTION_UP:

                        break;

                    case MotionEvent.ACTION_MOVE:
                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view
                                .getLayoutParams();
                        layoutParams.leftMargin = x - xDelta;
                        layoutParams.topMargin = y - yDelta;
                        layoutParams.rightMargin = 0;
                        layoutParams.bottomMargin = 0;
                        view.setLayoutParams(layoutParams);
                        break;
                }

                mainLayout.invalidate();
                return true;
            }
        };
    }

    public void createNewPopupDialog() {
        dialogBuilder = new AlertDialog.Builder(this);
        //LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        View addPlayerPopup = getLayoutInflater().inflate(R.layout.popup,null);
        dialogBuilder.setTitle("Add new player");

        //nickname = addPlayerPopup.findViewById(R.id.editTextTextPersonName);
        dropdownColours = (Spinner) addPlayerPopup.findViewById(R.id.dropdownColours);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.colours_list));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdownColours.setAdapter(adapter);

        dialogBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (!dropdownColours.getSelectedItem().toString().equalsIgnoreCase("Choose colour…")) {
                    Toast.makeText(MainActivity.this, dropdownColours.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                    dialogInterface.dismiss();

                    //new
                    ImageView newPlayer = new ImageView(MainActivity.this);
                    newPlayer.setImageResource(R.drawable.blue);
                    addPlayer(newPlayer, 100, 100);
                    //new end
                }
            }
        });

        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        dialogBuilder.setView(addPlayerPopup);
        dialog = dialogBuilder.create();
        dialog.show();
    }

    public void addPlayer(ImageView figure, int width, int height) {
        mainLayout = (RelativeLayout) findViewById(R.id.main);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, height);
        layoutParams.setMargins(100, 100, 0, 0);
        figure.setLayoutParams(layoutParams);
        mainLayout.addView(figure);
    }

}