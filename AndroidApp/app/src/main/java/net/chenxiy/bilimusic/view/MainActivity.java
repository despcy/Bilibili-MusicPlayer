package net.chenxiy.bilimusic.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import net.chenxiy.bilimusic.Constants;
import net.chenxiy.bilimusic.DataUtils;
import net.chenxiy.bilimusic.R;
import net.chenxiy.bilimusic.Repository;
import net.chenxiy.bilimusic.database.BiliDatabase;
import net.chenxiy.bilimusic.databinding.ActivityMainBinding;
import net.chenxiy.bilimusic.adapter.MainFragmentPageAdapter;
import net.chenxiy.bilimusic.databinding.DrawerHeaderBinding;
import net.chenxiy.bilimusic.network.biliapi.pojo.favfolder.add_del_folder.AddDelFolderResponse;
import net.chenxiy.bilimusic.network.biliapi.pojo.userinfo.UserResponse;
import net.chenxiy.bilimusic.service.MediaService;
import net.chenxiy.bilimusic.viewmodel.MainActivityViewModel;

public class MainActivity extends SuperActivity {

    public static boolean isPlaying=false;
    private static final String TAG = "MainActivityLog";
    private ActivityMainBinding mBinding;
    DrawerHeaderBinding drawerHeaderBinding;
    private MainFragmentPageAdapter fragmentPageAdapter;
    private MainActivityViewModel mViewModel;
    private SharedPreferences mPreferences;
    public  static String SHOULD_REFRESH="should refresh";
    private String sharedPrefFile =
            "net.chenxiy.bilimusic.Data";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mViewModel= ViewModelProviders.of(this).get(MainActivityViewModel.class);
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);

        drawerHeaderBinding=DataBindingUtil.inflate(getLayoutInflater(),R.layout.drawer_header,mBinding.navigationView,false);
        mBinding.navigationView.addHeaderView(drawerHeaderBinding.getRoot());

        fragmentPageAdapter=new MainFragmentPageAdapter(getSupportFragmentManager());
        mBinding.mainViewpager.setAdapter(fragmentPageAdapter);
        mBinding.mainViewpager.setOffscreenPageLimit(3);
        mBinding.mainViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                    setSelectedTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setSelectedTab(mBinding.mainViewpager.getCurrentItem());//initialize the tab


        mBinding.mainHead.txtList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelectedTab(Constants.SONG_LIST_INDEX);
                mBinding.mainViewpager.setCurrentItem(Constants.SONG_LIST_INDEX);
            }
        });
        mBinding.mainHead.txtSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelectedTab(Constants.WEB_VIEW_INDEX);
                mBinding.mainViewpager.setCurrentItem(Constants.WEB_VIEW_INDEX);
            }
        });
        mBinding.mainHead.txtDynamic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelectedTab(Constants.DYNAMIC_VIEW_INDEX);
                mBinding.mainViewpager.setCurrentItem(Constants.DYNAMIC_VIEW_INDEX);
            }
        });



        mBinding.navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_settings:
                        openSetting();
                        break;
                    case R.id.nav_login:
                        openLogin();
                        break;
                    case R.id.nav_about:
                        openAbout();
                        break;
                    case R.id.nav_exit:
                        openExit();
                        break;
                    case R.id.nav_rate:
                        openStore();
                        break;
                        default:
                            break;

                }
                mBinding.drawerLayout.closeDrawers();

                Log.d(TAG, "onNavigationItemSelected: "+menuItem.getTitle());
                return true;
            }
        });



        mBinding.mainHead.ivTitleMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mBinding.drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        Boolean refresh=getIntent().getBooleanExtra(SHOULD_REFRESH,false);
        if(refresh){
            reFetch(getApplicationContext());
        }



      //  drawerHeaderBinding=DrawerHeaderBinding.bind(mBinding.navigationView.getHeaderView(0));


        initSharedPreference();
        initCookieStatus();
        setDrawerHeader();
        CookieManager.getInstance().setAcceptCookie(true);



    }

    public static  void reFetch(Context context){
        if(!DataUtils.getCSRFTokenFromCookie().isEmpty()) {
            Repository.getInstance(context).fetchFavFolderData(DataUtils.getCookie(), DataUtils.getUserIDFromCookie());
        }
    }



    //    private void cookieManager2SharedPref(){
//        SharedPreferences.Editor preferenceEditor=mPreferences.edit();
//        preferenceEditor.putString("cookie",DataUtils.getCookie());
//        preferenceEditor.apply();
//    }
//
//    private void sharedPref2CookieManager(){
//        String cookie=mPreferences.getString("cookie","");
//        CookieManager.getInstance().setCookie("bilibili.com",cookie);
//    }
    private void initCookieStatus(){


        if(DataUtils.getCSRFTokenFromCookie().isEmpty()){
            //User is not login
            //set drawer item as logout
            mBinding.navigationView.getMenu().findItem(R.id.nav_login).setTitle(R.string.login);
        }else{
            //User is in Login status
            mBinding.navigationView.getMenu().findItem(R.id.nav_login).setTitle(R.string.logout);
        }
        Log.d(TAG, "initCookieStatus: USERID:"+ DataUtils.getUserIDFromCookie()+" CSRF:"+ DataUtils.getCSRFTokenFromCookie());
    }

    private void initSharedPreference(){
        PreferenceManager.setDefaultValues(this,R.xml.setting_preference,false);

    }

    private void openSetting(){
        startActivity(new Intent(this,Settings.class));

    }
    private void openLogin(){
        if(DataUtils.getCSRFTokenFromCookie().isEmpty()){
            //User is not login
            startActivity(new Intent(this,Login.class));
            finish();

        }else{
            //User is in Login status
            CookieManager.getInstance().removeAllCookies(null);
            CookieManager.getInstance().flush();
            BiliDatabase.getInstance(getApplicationContext()).clearAllTables();
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }


    }
    private void openStore(){
        String url = "https://play.google.com/store/apps/details?id=net.chenxiy.bilimusic";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
    private void openAbout(){

       // startActivity(new Intent(this,About.class));

        String url = "https://github.com/yangchenxi/BiliMusicPlayer";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
    private void openExit(){
        mViewModel.appMediaBrowserHelper.stopService();
        finish();
    }


    @Override
    protected void onResume() {

        super.onResume();
        Log.d(TAG, "onResume: ");
        initCookieStatus();


    }

    private void setSelectedTab(Integer TabSelected){//set tab selected style
        if(TabSelected== Constants.SONG_LIST_INDEX){
            mBinding.mainHead.txtList.setTextAppearance(R.style.ToolBar_Title_Selected);
            mBinding.mainHead.txtSearch.setTextAppearance(R.style.ToolBar_Title_Normal);
            mBinding.mainHead.txtDynamic.setTextAppearance(R.style.ToolBar_Title_Normal);

        }else if(TabSelected==Constants.WEB_VIEW_INDEX){
            mBinding.mainHead.txtList.setTextAppearance(R.style.ToolBar_Title_Normal);
            mBinding.mainHead.txtSearch.setTextAppearance(R.style.ToolBar_Title_Selected);
            mBinding.mainHead.txtDynamic.setTextAppearance(R.style.ToolBar_Title_Normal);

        }else if(TabSelected==Constants.DYNAMIC_VIEW_INDEX){
            mBinding.mainHead.txtList.setTextAppearance(R.style.ToolBar_Title_Normal);
            mBinding.mainHead.txtSearch.setTextAppearance(R.style.ToolBar_Title_Normal);
            mBinding.mainHead.txtDynamic.setTextAppearance(R.style.ToolBar_Title_Selected);

        }else{
            mBinding.mainHead.txtList.setTextAppearance(R.style.ToolBar_Title_Normal);
            mBinding.mainHead.txtSearch.setTextAppearance(R.style.ToolBar_Title_Normal);
            mBinding.mainHead.txtDynamic.setTextAppearance(R.style.ToolBar_Title_Normal);
        }

    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: Mainactivity");
    }

    private void setDrawerHeader(){// only for test
        //TODO detect networkchange callback

        Integer userId= DataUtils.getUserIDFromCookie();

        if(userId==-1){
            return;
        }


        Repository.getInstance(getBaseContext()).getApiService().getUser(userId).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                Log.d(TAG, "onResponse: "+response.body().getUserData().getName());
                drawerHeaderBinding.setUserData(response.body().getUserData());


               // drawerHeaderBinding.headerLayout.
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {

            }
        });
    }


//----------------------------------------------------------------------------------------

    public void OnClickAddFolder(View v){

        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext(),R.style.Theme_AppCompat_Light_Dialog);

// Create the AlertDialog
        View dialogView=getLayoutInflater().inflate(R.layout.dialog_addfavfolder,null);
        EditText editText=dialogView.findViewById(R.id.editText);
        CheckBox checkBox=dialogView.findViewById(R.id.checkBox);


        builder.setTitle(R.string.newFavFolder)
                .setView(dialogView)
                .setPositiveButton(R.string.create, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String filename=editText.getText().toString().trim();
                        Boolean isPublic=checkBox.isChecked();
                        Integer type=0;
                        if (!filename.isEmpty()){
                            if(isPublic){
                                type=Constants.PUBLIC_FAV_FOLDER;
                            }else{
                                type=Constants.PRIVATE_FAV_FOLDER;
                            }
                            Repository.getInstance(getApplicationContext()).addNewFavFolder(filename, type, new Callback<AddDelFolderResponse>() {
                                @Override
                                public void onResponse(Call<AddDelFolderResponse> call, Response<AddDelFolderResponse> response) {
                                    Log.d(TAG, "onResponse: "+response.code());
                                    if(response.body().getCode()==0){
                                        Toast.makeText(getApplicationContext(), R.string.createSuccessfully,Toast.LENGTH_SHORT).show();
                                        reFetch(getApplicationContext());
                                    }else{
                                        Toast.makeText(getApplicationContext(),response.body().getMessage(),Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<AddDelFolderResponse> call, Throwable t) {
                                    Toast.makeText(getApplicationContext(),"NETWORK FAILURE",Toast.LENGTH_SHORT).show();

                                }
                            });

                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create th
        AlertDialog dialog = builder.create();
        dialog.show();


    }





}
