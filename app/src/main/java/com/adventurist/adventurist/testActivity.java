//package com.adventurist.adventurist;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.ActionBarDrawerToggle;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//import androidx.core.view.GravityCompat;
//import androidx.drawerlayout.widget.DrawerLayout;
//
//import android.annotation.SuppressLint;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.widget.TextView;
//import android.widget.Toast;
//
//
//import com.google.android.material.navigation.NavigationView;
//
//public class testActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
//
//
//    DrawerLayout drawerLayout;
//    NavigationView navigationView;
//    Toolbar toolbar;
//    Menu menu;
//    TextView textView;
//    @SuppressLint("MissingInflatedId")
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_test);
//
//        /*---------------------Hooks------------------------*/
//        drawerLayout=findViewById(R.id.drawer_layout);
//        navigationView=findViewById(R.id.nav_view);
//        textView=findViewById(R.id.textView);
//        toolbar=findViewById(R.id.toolbar);
//        navigationView.bringToFront();
//
//        ActionBarDrawerToggle toggle=new
//                ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
//        drawerLayout.addDrawerListener(toggle);
//        toggle.syncState();
//        navigationView.setNavigationItemSelectedListener(this);
//        navigationView.setCheckedItem(R.id.nav_home);
//
//
//
//
//        menu = navigationView.getMenu();
//        menu.findItem(R.id.nav_logout).setVisible(false);
//        menu.findItem(R.id.nav_profile).setVisible(false);
//
//    }
//
//    @Override
//    public void onBackPressed(){
//        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
//            drawerLayout.closeDrawer(GravityCompat.START);
//        }
//        else
//        {super.onBackPressed();
//        }
//    }
//
//
//
//    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
////        switch (menuItem.getItemId()) {
////            case R.id.nav_home: break; case R.id.nav_bus:
////                Intent intent = new Intent(MainActivity.this, Bus.class);
////                startActivity(intent);
////                break;
////            case R.id.nav_login: menu.findItem(R.id.nav_logout).setVisible(true);
////                menu.findItem(R.id.nav_profile).setVisible(true);
////                menu.findItem(R.id.nav_login).setVisible(false);
////                break;
////            case R.id.nav_logout: menu.findItem(R.id.nav_logout).setVisible(false);
////                menu.findItem(R.id.nav_profile).setVisible(false);
////                menu.findItem(R.id.nav_login).setVisible(true);
////                break;
////            case R.id.nav_share: Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show(); break;
////        }
////        drawerLayout.closeDrawer(GravityCompat.START);
//        return true;
//    }
//
//
//}