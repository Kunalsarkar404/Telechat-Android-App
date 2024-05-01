package com.example.echochat;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        // Set up the navigation drawer
        setupNavigationDrawer();

        // Ensure toolbar is set as the support action bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set the default selected item
        navigationView.setCheckedItem(R.id.menu_home);
    }

    private void setupNavigationDrawer() {
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here
        int itemId = item.getItemId();
        if (itemId == R.id.menu_home) {
            // Handle Home selection (if needed)
            // Example: loadFragment(new HomeFragment());
        } else if (itemId == R.id.menu_new_group) {
            // Handle New Group selection (if needed)
            // Example: loadFragment(new NewGroupFragment());
        } else if (itemId == R.id.menu_contacts) {
            // Navigate to ContactActivity when "Contacts" is selected
            Intent contactsIntent = new Intent(MainActivity.this, ContactActivity.class);
            startActivity(contactsIntent);
        } else if (itemId == R.id.menu_calls) {
            // Handle Calls selection (if needed)
            // Example: loadFragment(new CallsFragment());
        } else if (itemId == R.id.menu_settings) {
            // Handle Settings selection (if needed)
            // Example: loadFragment(new SettingsFragment());
        }

        drawerLayout.closeDrawer(GravityCompat.START); // Close the drawer after item selection
        return true;
    }
}

