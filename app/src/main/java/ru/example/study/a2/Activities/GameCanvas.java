package ru.example.study.a2.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;

import ru.example.study.a2.Entities.Player;
import ru.example.study.a2.GameLogic.Commands;
import ru.example.study.a2.Interfaces.onClientSocket_v1Listener;
import ru.example.study.a2.Interfaces.onGameDraw;
import ru.example.study.a2.Sockets.MyThreadClient;

public class GameCanvas extends AppCompatActivity {

    public Bitmap[] bmp;
    public Bitmap[] CanvaBmp;
    final String LOG_TAG = "myLogs";
    Random random = new Random();
    public MyThreadClient client;
    int step;
    int[] width= new int[40];
    int[] height= new int[40];

    int widthPixels;
    int heightPixels;

    public static String serverAdressIp;

    public String picher[]={"start", "chanel","question","chanel","money", "audi",
            "adidas","question","adidas","adidas", "prison","vk","rocstar","vk","vk",
            "audi_right","pepsi","question_right","pepsi","pepsi","casino","al","question",
            "al","al","audi_bottom","bg","bg","rocstar_bottom","bg","police","hl","hl",
            "question_left","hl","audi_left","a","nk","question_left","nk"};

    public int[]colors={Color.BLUE,Color.RED,Color.GREEN,Color.YELLOW,Color.WHITE};

    private Player[] player;
    public int coordinat_x[]={111, 223, 301, 374, 452, 530,608, 686, 764, 842, 935, 935,935,
            935,935,935,935,935,935,935,935,842, 764, 686, 608, 530, 452, 374, 301, 223, 111,
            111,111,111,111,111,111,111,111,111 };
    public int coordinat_y[]={460,460,460,460,460,460,460,460,460,460,460,572, 650, 723, 801,
            879, 957, 1035, 1113, 1191, 1284,1284,1284,1284,1284,1284,1284,1284,1284,1284,1284,
            1191, 1113, 1035, 957, 879, 801, 723, 650, 572, 460 };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(new DrawView(this));

        DisplayMetrics displaymetrics = getResources().getDisplayMetrics();
        widthPixels=displaymetrics.widthPixels;
        heightPixels=displaymetrics.heightPixels;

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x= (int)event.getX();
        int y= (int)event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                if (x < 300)
                    client.setCommand("sendMessage", "STEP:ACCEPT");
                if (x > 300 && x < 600)
                    client.setCommand("sendMessage", "OFFER_ACCEPT:ACCEPT");
                if (x > 600)
                    client.setCommand("sendMessage", "OFFER_ACCEPT:REJECT");
            break;
        }
        return super.onTouchEvent(event);
    }


    public int x(int i)
    {
        if (i == 0) {
            width[i] = (widthPixels - 982) / 2;
            width[i + 1] = width[i] + 149;
        }
        if (i > 1 && i < 11) {
            width[i] = width[i - 1] + 76;
        }
        if (i == 11) {
            width[i] = width[i - 1];
        }
        if (i > 11 && i <= 20) {
            width[i] = width[i - 1];
        }
        if (i <= 30 && i > 20) {
            width[i] = width[i - 1] - 76;
        }
        if (i == 30) {
            width[i] = width[i - 1] - 149;
        }
        if (i > 30 && i < 40) {
            width[i] = width[i - 1];
        }
        return width[i];
    }
    public int y(int i)
    {
        if (i >= 0 && i <=10)
        {
            height[i] = 400;
        }
        if (i == 11)
        {
            height[i] = 549;
        }
        if (i > 11 && i <= 20)
        {
            height[i] =height[i-1]+ 76;
        }
        if (i <=30 && i>20)
        {
            height[i] = height[i-1];
        }
        if (i == 31)
        {
            height[i] = 1157;
        }
        if (i > 31 && i < 40)
        {
            height[i] = height[i - 1] - 76;
        }
        return height[i];
    }



   class DrawView extends SurfaceView implements SurfaceHolder.Callback, onGameDraw,onClientSocket_v1Listener
   {

       Canvas canvas;
       private SurfaceHolder surfaceHolder;

       public DrawView(Context context)
       {
           super(context);
           getHolder().addCallback(this);
           client = new MyThreadClient(serverAdressIp,25565);
           client.start();
           client.addLitener(this);
           client.addClientSocket_v1Listener(this);
           bmp= new Bitmap[40];
           CanvaBmp= new Bitmap[40];
           for(int i=0; i<40;i++)
           {
               bmp[i]=BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(picher[i] , "drawable", getPackageName()));
               CanvaBmp[i]=bmp[i];
           }
       }

       @Override
       public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
       {

       }

       @Override
       public void surfaceCreated(SurfaceHolder holder)
       {
           surfaceHolder=getHolder();

       }

       @Override
       public void surfaceDestroyed(SurfaceHolder holder)
       {

       }

       void card(Canvas canvas){
           for (int i = 0; i < 40; i++) {
               canvas.drawBitmap(CanvaBmp[i], x(i), y(i), null);}

       }

       private Bitmap processingBitmap(Bitmap src,int color){
           Bitmap dest = Bitmap.createBitmap(
                   src.getWidth(), src.getHeight(), src.getConfig());

           for(int x = 0; x < src.getWidth(); x++){
               for(int y = 0; y < src.getHeight(); y++){

                   int pixelColor = src.getPixel(x, y);
                   int pixelRed = Color.red(pixelColor);
                   int pixelGreen = Color.green(pixelColor);
                   int pixelBlue = Color.blue(pixelColor)+pixelGreen+pixelRed;
                   if(pixelBlue>550){
                    int newpixel=Color.argb(127,Color.red(color),Color.green(color),Color.blue(color));
                    dest.setPixel(x, y, newpixel);
                   }
                   else
                       dest.setPixel(x, y, pixelColor);
               }
           }
           return dest;
       }

       @Override
       public void onRecive(String Text) {
           Commands cmds = new Commands(Text);
           switch (cmds.getHead()){
               case "GAME":
                   switch (cmds.getFirst()){
                       case "COUNT_PLAYERS":
                           String[] res = cmds.getParams();
                           int count = Integer.parseInt(res[1]);
                           player= new Player[count];

                           break;
                   }

                   break;
               case "MAP_FIELDS":
                   String[] ids = cmds.getParams();
                   int idi[] = new int[ids.length];

                   for(int i=0;i<40;i++){
                       idi[i]=Integer.parseInt(ids[i]);
                   }

                   for(int i=0;i<40;i++){
                       CanvaBmp[i]=bmp[i];
                       if(idi[i]!=-1){
                            CanvaBmp[i]=processingBitmap(bmp[i],colors[idi[i]]);


                       }
                   }
                   updateCanvas();
                   break;
               case "PLAYERS_INFORMATION":
                   String[] param = cmds.getParams();
                   int paramCount=8;
                   for(int j=0;j<player.length;j++){
                       if(j*paramCount<param.length) {
                           player[j] = new Player(j, "");
                           player[j].money = Integer.parseInt(param[0 + j * paramCount]);
                           player[j].square.square_one = Integer.parseInt(param[1 + j * paramCount]);
                           player[j].square.square_two = Integer.parseInt(param[2 + j * paramCount]);
                           player[j].x = Integer.parseInt(param[3 + j * paramCount]);
                           player[j].y = Integer.parseInt(param[4 + j * paramCount]);
                           player[j].name_player = param[5 + j * paramCount];
                           player[j].id = Integer.parseInt(param[6 + j * paramCount]);
                           player[j].position=Integer.parseInt((param[7 + j * paramCount]));
                       }
                   }
                   updateCanvas();
                   break;

           }


       }


       private void updateCanvas(){
           if (player != null&&surfaceHolder!=null) {
               // try {
               canvas = null;
               Paint brush = new Paint();
             /*  if (canvas != null)
                   canvas.drawColor(Color.WHITE);*/

              // for (int j = 0; j < 12; j++) {
                   canvas = surfaceHolder.lockCanvas(null);
                   canvas.drawColor(Color.WHITE);
                   card(canvas);
                   for (int p = 0; p < player.length; p++) {
                      // int regularStep=player[p].stepCount-(player[p].stepCount*j/12);
                       if(player[p]!=null){
                        brush.setColor(colors[p]);
                        canvas.drawCircle(coordinat_x[player[p].position], coordinat_y[player[p].position], 20, brush);
                       }

                   }

                   if (canvas != null) {
                       surfaceHolder.unlockCanvasAndPost(canvas);
                   }
//                   try {
//                       Thread.sleep(100);
//                   } catch (InterruptedException e) {
//                       e.printStackTrace();
//                   }

               //}

           }
       }

       @Override
       public void onError(String Text) {

       }

       @Override
       public void stepPlayer() {

       }


       @Override
       public void stepOtherPlayer(String[] message) {
           updateCanvas();

       }



   }
}