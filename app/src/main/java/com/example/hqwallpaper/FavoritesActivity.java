package com.example.hqwallpaper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class FavoritesActivity extends AppCompatActivity {

    private RecyclerView rvFavorites;
    private TextView tvFavorites;
    private ArrayList<Wallpaper> wallpapersList;
    private DbHelper dbHelper;
    private WallpaperAdapter adapter;
    private Toolbar toolbar;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        rvFavorites = findViewById(R.id.rvFavorites);
        tvFavorites = findViewById(R.id.tvFavorites);
        toolbar = findViewById(R.id.toolBar);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Favorites");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        dbHelper = new DbHelper(FavoritesActivity.this);
        wallpapersList = new ArrayList<>();

        wallpapersList = dbHelper.getAllFavorites();
        if (wallpapersList.size() == 0) {
            tvFavorites.setVisibility(View.VISIBLE);
        } else {
            adapter = new WallpaperAdapter(wallpapersList, new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Wallpaper selectedWallpaper = wallpapersList.get(position);
                    Intent detailIntent = new Intent(FavoritesActivity.this, LatestDetailActivity.class);
                    detailIntent.putExtra("wallpaper", selectedWallpaper);
                    startActivity(detailIntent);
                }
            });
            adapter.setFavoriteListner(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Wallpaper currentWall = wallpapersList.get(position);
                    if (dbHelper.isFavoriteWallpaper(currentWall)) {
                        int deleteCount = dbHelper.deleteFavoriteWallpaper(currentWall);
                        if (deleteCount > 0) {
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(FavoritesActivity.this, "Unable to delete form favorite", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        long insertCount = dbHelper.addToFavoriteWallpaper(currentWall);
                        if (insertCount > -1) {
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(FavoritesActivity.this, "Unable to add favorite", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
            rvFavorites.setLayoutManager(new GridLayoutManager(FavoritesActivity.this, 3));
            rvFavorites.setAdapter(adapter);
        }

    }
}