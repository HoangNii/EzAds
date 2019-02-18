package cheoapp.admin.ezlife.com.ezad;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class EzAdDialog {

    static String JSON_URL = "https://wgames.ezlifetech.com/EZAds/EZAds.json";

    static String KEY_JSON_EZ_AD = "json_ezAd";

    private Context context;

    private List<String> tagThisApp;

    private AppEz appEz;

    private DialogInterface.OnCancelListener onCancelListener;

    private Dialog dialog;

    private int margin;

    public EzAdDialog(Context context,DialogInterface.OnCancelListener onCancelListener){
        this.context = context;
        this.onCancelListener = onCancelListener;
    }


    public void showDialog(){
        dialog = new Dialog(context,R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_ezad);
        if(dialog.getWindow()!=null)
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        SharedPreferences preferences =  PreferenceManager.getDefaultSharedPreferences(context);
        String json = preferences.getString(KEY_JSON_EZ_AD,"");
        if(json.length()<1){
            onCancelListener.onCancel(dialog);
        }else {
            appEz = getAppShow(json);
            if(appEz==null){
                onCancelListener.onCancel(dialog);
            }
            setupValue();
        }

        dialog.show();

    }

    private void setupValue() {
        TextView tvName = dialog.findViewById(R.id.tvName);
        TextView tvDes = dialog.findViewById(R.id.tvDes);
        ImageView imgIcon = dialog.findViewById(R.id.imgIcon);
        View btnCancel = dialog.findViewById(R.id.btnCancel);
        View btnInstall = dialog.findViewById(R.id.btnInstall);
        RecyclerView rcv = dialog.findViewById(R.id.rcv);
        ImageView imgBanner = dialog.findViewById(R.id.imgBanner);

        tvName.setText(appEz.getAppName());
        tvDes.setText(appEz.getAppDesc());
        Glide.with(context).load(appEz.getAppIcon()).into(imgIcon);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCancelListener.onCancel(dialog);
            }
        });
        btnInstall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCancelListener.onCancel(dialog);
                goToStore(appEz.getAppId());
            }
        });

        if(appEz.getAppScreen().length<2){
            rcv.setVisibility(View.GONE);
            imgBanner.getLayoutParams().width = (int) (context.getResources().getDisplayMetrics().heightPixels/2.2);
            imgBanner.getLayoutParams().height = (int) (context.getResources().getDisplayMetrics().widthPixels/2.2);
            Glide.with(context)
                    .load(appEz.getAppScreen()[0])
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.bg_load))
                    .into(imgBanner);
            return;
        }else {
            imgBanner.setVisibility(View.GONE);
        }


        rcv.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        rcv.setHasFixedSize(true);
        rcv.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                ImageView view = new ImageView(context);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                if(appEz.getAppDir().equals("p")){
                    params.height = context.getResources().getDisplayMetrics().heightPixels/2;
                    params.width = context.getResources().getDisplayMetrics().widthPixels/2;
                }else {
                    params.height = (int) (context.getResources().getDisplayMetrics().widthPixels/2.2);
                    params.width = (int) (context.getResources().getDisplayMetrics().heightPixels/2.2);
                }

                margin = convertDpToPixel(10);
                params.setMargins(margin,margin, 0,margin);
                view.setLayoutParams(params);

                return new RecyclerView.ViewHolder(view) {
                    @Override
                    public String toString() {
                        return super.toString();
                    }
                };
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

                Glide.with(context)
                        .load(appEz.getAppScreen()[position])
                        .apply(new RequestOptions()
                        .placeholder(R.drawable.bg_load))
                        .into((ImageView) holder.itemView);

                if(position==appEz.getAppScreen().length-1){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        ((ViewGroup.MarginLayoutParams)holder.itemView.getLayoutParams())
                                .setMargins(margin,margin, margin,margin);
                    }
                }
            }

            @Override
            public int getItemCount() {
                return appEz.getAppScreen().length;
            }
        });


  }

    private AppEz getAppShow(String json){
        ArrayList<AppEz> appEzs = new ArrayList<>();
        JSONArray jsonArray = null;
        try {
            JSONObject object =  new JSONObject(json);
            jsonArray = object.getJSONArray("ads");
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                AppEz appEz = new AppEz();
                String appId = jsonObject.getString("appId");
                String[] appTag = jsonObject.getString("appTag").split(",");
                String appIcon = jsonObject.getString("appIcon");
                String appName = jsonObject.getString("appName");
                String appDesc = jsonObject.getString("appDesc");
                String appDirection = jsonObject.getString("appDirection");

                JSONArray arrJson = jsonObject.getJSONArray("appScreen");
                String[] appScreen = new String[arrJson.length()];
                for(int ii = 0; ii < arrJson.length(); ii++){
                    appScreen[ii] = arrJson.getString(ii);
                }

                Integer appRank = jsonObject.getInt("adsRank");
                appEz.setAppId(appId);
                appEz.setAppTag(appTag);
                appEz.setAppIcon(appIcon);
                appEz.setAppName(appName);
                appEz.setAppDesc(appDesc);
                appEz.setAppDir(appDirection);
                appEz.setAppScreen(appScreen);
                appEz.setAppRank(appRank);

                if(context.getPackageName().equals(appEz.getAppId())){
                    tagThisApp = Arrays.asList(appEz.getAppTag());
                } else if(!isAppInstalled(appEz.getAppId())){
                        appEzs.add(appEz);
                    }


            }


        } catch (JSONException e) {
            e.printStackTrace();
            onCancelListener.onCancel(dialog);
        }

        //remove tag equals
        if(tagThisApp!=null){
            for (AppEz appEz:appEzs){
                String[] tag = appEz.getAppTag();
                for (String t:tag){
                    if(tagThisApp.contains(t)){
                        appEzs.remove(appEz);
                    }
                }
            }
        }


        //get model
        int max = 0;
        int[] count = new int[appEzs.size()];
        for (int i=0;i<appEzs.size();i++){
            AppEz appEz = appEzs.get(i);
            max = max + appEz.getAppRank();
            count[i] = max;
        }
        int random = new Random().nextInt(max);

        for (int check=0;check<count.length;check++){
            if(random<count[check]){
                return appEzs.get(check);
            }
        }

        return null;
    }


    public static void loadJson(Context context){
        LoadJson loadJson = new LoadJson(PreferenceManager.getDefaultSharedPreferences(context));
        loadJson.execute();
    }

    private boolean isAppInstalled(String packageName) {
        try {
            context.getPackageManager().getApplicationInfo(packageName, 0);
            return true;
        }
        catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    private void goToStore(String appId) {
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appId)));
        } catch (android.content.ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id="+appId)));
        }
    }

    private int convertDpToPixel(float dp){
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return Math.round(px);
    }

}
