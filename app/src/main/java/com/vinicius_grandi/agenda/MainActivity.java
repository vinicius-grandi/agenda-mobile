package com.vinicius_grandi.agenda;

import android.app.Notification;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.core.app.NotificationChannelCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.vinicius_grandi.agenda.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        notifyMe("created");
    }

    private void notifyMe(String message) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NotificationChannelCompat.DEFAULT_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("hello")
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        Notification notification = builder.build();
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        notificationManager.notify(1, notification);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_toggle_theme) {
            toggleTheme();
        }

        if (id == R.id.action_exit_app) {
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void toggleTheme() {
        View backgroundView = findViewById(R.id.nav_host_fragment_content_main);
        Drawable background = backgroundView.getBackground();
        int previousColor = Color.TRANSPARENT;
        if (background instanceof ColorDrawable)
            previousColor = ((ColorDrawable) background).getColor();
        int black = getIntColor(R.color.black);
        int color = previousColor == black ? getIntColor(R.color.white) : black;
        backgroundView.setBackgroundColor(color);
    }

    private int getIntColor(int colorId) {
        // ContentCompat.getColor(context, color) returns a color int like 0x00000000
        return ContextCompat.getColor(this, colorId);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onResume() {
        super.onResume();
        notifyMe("resumed");
    }

    @Override
    protected void onStop() {
        super.onStop();
        notifyMe("stopped");
    }

    @Override
    protected void onDestroy() {
        Log.v("TEST onStart", "The app is destroyed!");
        notifyMe("destroyed");
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.v("TEST onStart", "The app is started");
    }
}