package com.traviatour.travianmockupkotlin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;

import java.util.ArrayList;
import java.util.List;

import ss.com.bannerslider.banners.Banner;
import ss.com.bannerslider.banners.DrawableBanner;
import ss.com.bannerslider.events.OnBannerClickListener;
import ss.com.bannerslider.views.BannerSlider;

public class MainActivity extends AppCompatActivity implements ObservableScrollViewCallbacks {

    private View mToolbarView;
    private ObservableScrollView mScrollView;
    private int mParallaxImageHeight;
    private BannerSlider bannerSlider;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupBannerSlider();

        BottomNavigationBar bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation);
        bottomNavigationBar
                .setMode(BottomNavigationBar.MODE_FIXED);

        bottomNavigationBar
                .setActiveColor(R.color.loginPrimaryDark)
                .setInActiveColor(R.color.iron)
                .setBarBackgroundColor(R.color.White);

        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_home_amber_24dp, "Home"))
                .addItem(new BottomNavigationItem(R.drawable.ic_assignment_amber_24dp, "My Booking"))
                .addItem(new BottomNavigationItem(R.drawable.ic_email_amber_24dp, "Inbox"))
                .addItem(new BottomNavigationItem(R.drawable.ic_account_circle_amber_24dp, "My Account"))
                .initialise();

        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
            }

            @Override
            public void onTabUnselected(int position) {
            }

            @Override
            public void onTabReselected(int position) {
            }
        });

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setTitle("");
        findViewById(R.id.toolbar).setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 120));

        mToolbarView = findViewById(R.id.toolbar);
        mToolbarView.setBackgroundColor(ScrollUtils.getColorWithAlpha(0, getResources().getColor(R.color.traveloka)));

        mScrollView = (ObservableScrollView) findViewById(R.id.scroll);
        mScrollView.setScrollViewCallbacks(this);

        mParallaxImageHeight = getResources().getDimensionPixelSize(R.dimen.parallax_image_height);


        mRecyclerView = (RecyclerView) findViewById(R.id.main_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        SnapHelper startSnapHelper = new StartSnapHelper();
        startSnapHelper.attachToRecyclerView(mRecyclerView);

        setupAdapter();
    }

    private void setupAdapter() {
        List<SinglePromoModel> mPromoData = getPromo();

        SectionPromoAdapter SectionAdapter = new SectionPromoAdapter();
        SectionAdapter.AddSection(new SectionPromoModel("Favourite Tour", mPromoData));
        SectionAdapter.AddSection(new SectionPromoModel("Best Deals", mPromoData));
        SectionAdapter.AddSection(new SectionPromoModel("Advance Booking", mPromoData));

        mRecyclerView.setAdapter(SectionAdapter);
    }

    private List<SinglePromoModel> getPromo() {
        List<SinglePromoModel> Promo = new ArrayList<>();
        Promo.add(new SinglePromoModel("Holiday+", R.drawable.travel, "Holiday"));
        Promo.add(new SinglePromoModel("Funtastic Deal", R.drawable.visa, "Funtastic Deal"));
        Promo.add(new SinglePromoModel("Fly Cheap", R.drawable.summer, "Fly Cheap"));
        Promo.add(new SinglePromoModel("Advance Booking", R.drawable.promo12, "Advance Booking"));
        Promo.add(new SinglePromoModel("Best Deal", R.drawable.promo22, "Best Deal"));
        Promo.add(new SinglePromoModel("Free Ride", R.drawable.promo1, "Free Ride"));
        return Promo;
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        onScrollChanged(mScrollView.getCurrentScrollY(), false, false);
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        int baseColor = getResources().getColor(R.color.traveloka);
        float alpha = Math.min(1, (float) scrollY / mParallaxImageHeight);
        mToolbarView.setBackgroundColor(ScrollUtils.getColorWithAlpha(alpha, baseColor));
    }

    @Override
    public void onDownMotionEvent() {
    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
    }

    private void setupBannerSlider(){
        bannerSlider = (BannerSlider) findViewById(R.id.banner_slider1);
        addBanners();

        bannerSlider.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(MainActivity.this, "Banner with position " + String.valueOf(position) + " clicked!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void addBanners(){
        List<Banner> banners=new ArrayList<>();

        //Add banners using drawable resource
        banners.add(new DrawableBanner(R.drawable.travel));
        banners.add(new DrawableBanner(R.drawable.visa));
        banners.add(new DrawableBanner(R.drawable.disc));
        banners.add(new DrawableBanner(R.drawable.summer));

        for (int i=0; i < banners.size(); i++){ banners.get(i).setScaleType(ImageView.ScaleType.FIT_XY); }
        bannerSlider.setBanners(banners);
    }
}
