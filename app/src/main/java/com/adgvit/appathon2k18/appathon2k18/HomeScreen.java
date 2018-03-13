package com.adgvit.appathon2k18.appathon2k18;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.Log;
import android.util.Property;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.HeterogeneousExpandableList;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.jpardogo.android.googleprogressbar.library.FoldingCirclesDrawable;
import com.jpardogo.android.googleprogressbar.library.GoogleProgressBar;
import com.sothree.slidinguppanel.ScrollableViewHelper;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

public class HomeScreen extends AppCompatActivity {

    Bitmap bitmap1;
    public final static int QRcodeWidth = 500 ;
    SharedPreferences sp;
    String value;
    GoogleProgressBar mBar;
    ExpandableListView expListView;
    ImageView frameLayout;
    SlidingUpPanelLayout slidingUpPanelLayout;
    Button panelbtn;
    TextView setuptext;
    LinearLayout linearLayout;
    LinearLayout linearLayout2;
    ExpandableAdapter listAdapter;
    ArrayList<TimelineItems> arrayList = new ArrayList<>();
    int lastexpanded=-1;

    //navad
    String Reg_Num;
    int round,attempted;
    Button quiz;
    int check;
    TextView bol;

    //naman
    private TextView temp;

    private String[] times={"5:15 pm","5:30 pm","6:00 pm","6:15 pm","6:30 pm","7:15 pm","8:00 pm","10:30 pm","2:00 am",
            "3:15 pm","3:30 pm","4:00 pm","4:15 pm","4:45 pm","7:30 pm","8:30 pm","11:30 pm"};

    private String[] title={"Welcome to UniDev","Reporting Time","Commencement","Inauguration","Speakers take over","Break","Workshop is Resumed","Short Break","Wrap Up",
            "Registration Desk Setup","Reporting Time","Workshop Resumes","Break","Workshop Resumes","Break","Workshop Resumes","Workshop is Concluded"};

    private String[] details={"Registration Desks Setup","Reporting Time for Participants","Commencement of the Workshop","Inaugural address detailing chapter","Step by step walk-through","The time arrives for relaxation","Time to get back to Work","A short break for 30 mins!","Pack your bags and head home",
            "Registration Desks Setup","Reporting Time for Participants","The workshop resumes","Time for you to relax","Get back to work","Take a break","The final session","Hope to see you next year!"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //naman
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);



        setContentView(R.layout.activity_home_screen);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);


        mBar = (GoogleProgressBar) findViewById(R.id.google_progress2);
        mBar.setIndeterminateDrawable(new FoldingCirclesDrawable.Builder(this).colors(getResources().getIntArray(R.array.progressLoader)).build());
        mBar.setVisibility(View.INVISIBLE);
        expListView = (ExpandableListView) findViewById(R.id.modulesList);
        slidingUpPanelLayout = findViewById(R.id.sliding_layout);
        setuptext=findViewById(R.id.setup);
        linearLayout=findViewById(R.id.proglayout);
        linearLayout2=findViewById(R.id.lists);

        //naman
        temp=(TextView)findViewById(R.id.temp);

        //navad
        bol=findViewById(R.id.BoL);
        int count = 0;
        for(String Name: title)
        {
            arrayList.add(new TimelineItems(times[count],Name,details[count]));
            count++;
        }




        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+5:30"));
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("HH");
        // you can get seconds by adding  "...:ss" to it
        date.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
        String localTime = date.format(currentLocalTime);
        Log.w(localTime, "1");

        frameLayout=findViewById(R.id.homeLayout);
        //TODO Color Status Bar
        Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color

        if (localTime.equals("06")||localTime.equals("07")||localTime.equals("08")||localTime.equals("09")||localTime.equals("10"))
        {
            frameLayout.setBackgroundResource(R.drawable.one);
        }
        if (localTime.equals("11")||localTime.equals("12")||localTime.equals("13")||localTime.equals("14")||localTime.equals("15")||localTime.equals("16"))
        {
            frameLayout.setBackgroundResource(R.drawable.two);
        }
        if(localTime.equals("17")||localTime.equals("18"))
        {
            frameLayout.setBackgroundResource(R.drawable.one);
        }
        if(localTime.equals("19")||localTime.equals("20"))
        {
            frameLayout.setBackgroundResource(R.drawable.three);
        }
        if (localTime.equals("21")||localTime.equals("22")||localTime.equals("23")||localTime.equals("24")||localTime.equals("00")||localTime.equals("01")||localTime.equals("02")||localTime.equals("03")||localTime.equals("04")||localTime.equals("05"))
        {
            frameLayout.setBackgroundResource(R.drawable.four);
        }



        List<String> listDataHeader = new ArrayList<String>();
        //HashMap<String, List<String>> listDataChild = new HashMap<String, List<String>>();

        listDataHeader.add("Timeline");
        listDataHeader.add("Wi-Fi");
        listDataHeader.add("Food");
        listDataHeader.add("Quiz");
        listDataHeader.add("Goodies");

        /*
        List<String> tm = new ArrayList<String>();
        List<String> wf = new ArrayList<String>();
        List<String> fd = new ArrayList<String>();
        List<String> qz = new ArrayList<String>();

        tm.add("First");
        wf.add("Second");
        fd.add("Second");
        qz.add("Second");

        listDataChild.put(listDataHeader.get(0), tm); // Header, Child data
        listDataChild.put(listDataHeader.get(1), wf);
        listDataChild.put(listDataHeader.get(2), fd);
        listDataChild.put(listDataHeader.get(3), qz);
        */
        listAdapter = new ExpandableAdapter(this, listDataHeader);
        expListView.setAdapter(listAdapter);


        slidingUpPanelLayout.setScrollableViewHelper(new NestedScrollableViewHelper());
        linearLayout.setOnTouchListener(new RelativeLayoutTouchListener());
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastexpanded != -1
                        && groupPosition != lastexpanded) {
                    expListView.collapseGroup(lastexpanded);
                }
                lastexpanded = groupPosition;
            }
        });
        //navad
        firebase();
    }


    @Override
    public void onBackPressed() {
        slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
    }

    public void fullTime(View v){
        Intent intent=new Intent(HomeScreen.this, Timeline.class);
        startActivity(intent);
    }

    //navad
    public void quizbtn(View v)
    {
        sp = HomeScreen.this.getSharedPreferences("key", 0);
        Reg_Num = sp.getString("Reg_Num", "");


        switch (firebase())
        {
            case 0:
                Toast.makeText(getApplicationContext(),"The quiz is not live yet.",Toast.LENGTH_LONG).show();
                break;
            case 1:
                Toast.makeText(getApplicationContext(),"You have already attempted the quiz!",Toast.LENGTH_LONG).show();
                break;
            case 2:
                Intent in = new Intent(HomeScreen.this,Quiz.class);
                in.putExtra("round",round);
                startActivity(in);
                break;
        }


    }

    public void WifiDetails (View v){
        sp= this.getSharedPreferences("key", 0);
        value=sp.getString("wUser","");
        if(value.isEmpty()) {
           new GetWifi().execute();
        }
    }

    public void CheckFood(View v){
        sp= this.getSharedPreferences("key", 0);
        Boolean foodstats=sp.getBoolean("fcheck",false);
        if(!foodstats) {
            new CheckFoodStatus().execute();
        }
    }

    class CheckFoodStatus extends AsyncTask<Void,Void,Void>{

        Boolean foodStats;

        @Override
        protected Void doInBackground(Void... voids) {
            sp = HomeScreen.this.getSharedPreferences("key", 0);
            String Reg_Num = sp.getString("Reg_Num", "");
            FirebaseDatabase mFirebaseDatabase=FirebaseDatabase.getInstance();
            DatabaseReference UserRef=mFirebaseDatabase.getReference().child(Reg_Num).child("refreshmentsStatus");
            UserRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    foodStats =dataSnapshot.getValue(Boolean.class);
                    SharedPreferences.Editor sedt1 = sp.edit();
                    sedt1.putBoolean("fcheck", foodStats);
                    sedt1.apply();
                    listAdapter.notifyDataSetChanged();

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            return null;
        }
    }

    public class RelativeLayoutTouchListener implements View.OnTouchListener {
        private float downY, upY;

        public void onTopToBottomSwipe() {
            slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            // activity.doSomething();
        }

        public void onBottomToTopSwipe() {
            slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
        }

        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    downY = event.getY();
                    return true;
                }
                case MotionEvent.ACTION_UP: {
                    upY = event.getY();

                    float deltaY = downY - upY;


                    // swipe vertical?
                        // top or down
                        if (deltaY < 0) {
                            this.onTopToBottomSwipe();
                            expListView.collapseGroup(lastexpanded);
                            return true;
                        }
                        if (deltaY > 0) {
                            this.onBottomToTopSwipe();
                            return true;
                        }


                    return false; // no swipe horizontally and no swipe vertically
                }// case MotionEvent.ACTION_UP:
            }
            return false;
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        sp= HomeScreen.this.getSharedPreferences("key", 0);
        value=sp.getString("food","");
        if(value.isEmpty()) {
            new ImageLoading().execute();
        }
    }


    public class NestedScrollableViewHelper extends ScrollableViewHelper {
        public int getScrollableViewScrollPosition(View scrollableView, boolean isSlidingUp) {
            if (scrollableView instanceof NestedScrollView) {
                if(isSlidingUp){
                    return scrollableView.getScrollY();
                } else {
                    NestedScrollView nsv = ((NestedScrollView) scrollableView);
                    View child = nsv.getChildAt(0);
                    return (child.getBottom() - (nsv.getHeight() + nsv.getScrollY()));
                }
            } else {
                return 0;
            }
        }
    }

    class GetWifi extends AsyncTask<Void,Void,Void>{

        final String[] userName = new String[1];
        final String[] passWord = new String[1];

        @Override
        protected Void doInBackground(Void... voids) {
            sp = HomeScreen.this.getSharedPreferences("key", 0);
            String Reg_Num = sp.getString("Reg_Num", "");
            FirebaseDatabase mFirebaseDatabase=FirebaseDatabase.getInstance();
            DatabaseReference UserRef=mFirebaseDatabase.getReference().child(Reg_Num).child("wifiUsername");
            DatabaseReference PassRef=mFirebaseDatabase.getReference().child(Reg_Num).child("wifiPassword");
            UserRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    userName[0] =dataSnapshot.getValue(String.class);
                    SharedPreferences.Editor sedt1 = sp.edit();
                    sedt1.putString("wUser", userName[0]);
                    sedt1.apply();
                    listAdapter.notifyDataSetChanged();
                    Log.d("Hello",userName[0]);


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            PassRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    passWord[0] =dataSnapshot.getValue(String.class);
                    SharedPreferences.Editor sedt1 = sp.edit();
                    sedt1.putString("wPass", passWord[0]);
                    sedt1.apply();
                    listAdapter.notifyDataSetChanged();
                    Log.d("Hello",passWord[0]);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            return null;
        }
    }

    class ImageLoading extends AsyncTask<Void,Void,Void> {

        //ProgressDialog progressDialog=new ProgressDialog(HomeScreen.this);
        @Override
        protected void onPreExecute() {
           // progressDialog.setMessage("Loading...Please Wait");
           // progressDialog.setTitle("Welcome to App-A-Thon!");
           // progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
           // progressDialog.show();
            frameLayout.getBackground().setAlpha(90);
            linearLayout2.setAlpha(0.4f);
            linearLayout.setAlpha(1);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            mBar.setVisibility(View.VISIBLE);
            setuptext.setVisibility(View.VISIBLE);
            mBar.setFocusable(true);
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            setuptext.setVisibility(View.INVISIBLE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            frameLayout.getBackground().setAlpha(150);
            linearLayout2.setAlpha(1);
            mBar.setVisibility(View.INVISIBLE);
            //progressDialog.dismiss();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Void doInBackground(Void... params) {
            String partreg = sp.getString("Reg_Num", "");
            String attendance;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            String qrattend = partreg + "_food";
            try {
                bitmap1 = TextToImageEncode(qrattend);
                bitmap1.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] b = baos.toByteArray();
                attendance = Base64.encodeToString(b, Base64.DEFAULT);
                SharedPreferences.Editor sedt1 = sp.edit();
                sedt1.putString("food", attendance);
                sedt1.apply();

            } catch (WriterException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public Bitmap TextToImageEncode(String Value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QRcodeWidth, QRcodeWidth, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ? ContextCompat.getColor(HomeScreen.this,R.color.QRCodeBlack):ContextCompat.getColor(HomeScreen.this,R.color.colorWhite);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }


    public int firebase()
    {

        FirebaseDatabase db=FirebaseDatabase.getInstance();
        DatabaseReference rf=db.getReference();
        DatabaseReference rf1=rf.child("Quiz");

        rf1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                    round=Integer.parseInt(ds.getValue().toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        sp = HomeScreen.this.getSharedPreferences("key", 0);
        Reg_Num = sp.getString("Reg_Num", "").trim();
        DatabaseReference rf2=rf.child(Reg_Num);
        rf2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnap)
            {
                for(DataSnapshot ds1:dataSnap.getChildren())
                {
                    String att=ds1.getKey().toString().trim();
                    if(att.equalsIgnoreCase("attempted"))
                    {
                        attempted = Integer.parseInt(ds1.getValue().toString());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });


        if(round==0)
            check=0;
        else if(attempted==round && round!=0)
            check=1;
        else if(attempted!=round)
            check=2;
        return check;
    }



    //naman
    public void settimeline()
    {
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        //temp.setText(currentDateTimeString);

    }



    public  void lefttab()
    {
        int i=0;


        i--;

    }

    public void righttab()
    {
        int i=0;


        i++;
    }


}
