//
//import android.graphics.Bitmap;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//import com.squareup.picasso.Picasso;
//import com.stego.lytbf.stegoim.R;
//
//import java.io.File;
//
//public class MainActivitytest extends AppCompatActivity {
//
//    File small, large;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        small = new File(this.getCacheDir().getAbsolutePath()+"/small.jpg");
//        large = new File(this.getCacheDir().getAbsolutePath()+"/large.jpg");
//
//        AsyncTask task1 = new AsyncTask() {
//            @Override
//            protected Void doInBackground(Object... params) {
//                try
//                {
//
//                    Bitmap smalls[] = new Bitmap[10];
//                    Bitmap larges[] = new Bitmap[10];
//
//                    for (int i = 0; i < 10; i++) {
//                        smalls[i] = Picasso.with(getApplicationContext()).load(small)
//                                .resize(1280, 1280)
//                                .onlyScaleDown()
//                                .centerInside()
//                                .get();
//
//                        larges[i] = Picasso.with(getApplicationContext()).load(large)
//                                .resize(1280, 1280)
//                                .onlyScaleDown()
//                                .centerInside()
//                                .get();
//                    }
//
//                    for (int i = 0; i < 9; i++)
//                    {
//                        for (int j = i+1; j < 10; j++)
//                        {
//                            Log.d("wenhaoclog",
//                                    "large " + i + " vs " + j + ": " +
//                                            compare(larges[i], larges[j]));
//                            Log.d("wenhaoclog",
//                                    "small " + i + " vs " + j + ": " +
//                                            compare(smalls[i], smalls[j]));
//                        }
//                    }
//                }
//                catch (Exception e)
//                {
//                    e.printStackTrace();
//                }
//                return null;
//            }
//        };
//        task1.execute();
//    }
//
//    static boolean compare(Bitmap m1, Bitmap m2)
//    {
//        int w1 = m1.getWidth();
//        int w2 = m2.getWidth();
//        int h1 = m1.getHeight();
//        int h2 = m2.getHeight();
//
//        if (w1 != w2 || h1 != h2)
//        {
//            Log.d("wenhaoclog", "different dimension");
//            return false;
//        }
//
//        for (int w = 0; w < w1; w++)
//        {
//            for (int h = 0; h < h1; h++)
//            {
//                int p1 = m1.getPixel(w, h);
//                int p2 = m2.getPixel(w, h);
//                if (p1 != p2)
//                    return false;
//            }
//        }
//        return true;
//    }
//}
