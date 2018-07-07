package app.geniuslab.beer.activity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import app.geniuslab.beer.R;
import app.geniuslab.beer.connection.MyConnection;
import app.geniuslab.beer.connection.RestApi;
import app.geniuslab.beer.model.Beer;
import app.geniuslab.beer.recycler.AdapterRecycler;
import app.geniuslab.beer.session.Preference;
import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.profile_picture)
    ImageView profileImage;

    private RestApi restApi = RestApi.RETROFIT.create(RestApi.class);
    private Preference preference;

    RecyclerView recyclerView;
    AdapterRecycler adapter;

    Context context=this;
    List<Beer> beers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        preference = Preference.getIntance(this);
        recyclerView = findViewById(R.id.recyclerview_home);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdapterRecycler(this,null);
        recyclerView.setAdapter(adapter);

        loadData();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertData();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
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

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            startActivity(new Intent(HomeActivity.this, RegisterBeerActivity.class));
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

     void loadData(){
       restApi.getdrink().enqueue(new Callback<JsonArray>() {
           @Override
           public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
              beers = new ArrayList<>();
               for (JsonElement beer:response.body()){
                   beers.add(new Beer(beer.getAsJsonObject().get("id").getAsInt(),
                           beer.getAsJsonObject().get("name").getAsString(),
                           beer.getAsJsonObject().get("image").getAsString(),
                           beer.getAsJsonObject().get("price").getAsString()));
               }
               Toast.makeText(context,"El servicio esta iniciado correctamente "  + beers.size(), Toast.LENGTH_LONG).show();
           }

           @Override
           public void onFailure(Call<JsonArray> call, Throwable t) {

           }
       });
    }
    void insertData(){
        MyConnection sqlite = new MyConnection(context,null,null,2);
        SQLiteDatabase db = sqlite.getWritableDatabase();
        for(Beer beer : beers){
            sqlite.insertBeer(beer.getName(),beer.getPrice(),beer.getImage(),db);
        }
        Toast.makeText(context,"Se inserto correctamente "  + beers.size(), Toast.LENGTH_LONG).show();
        updateData();


    }

    @Override
    protected void onResume() {
        super.onResume();
        updateData();
    }
    private void updateData(){
        MyConnection sqlite = new MyConnection(context,null,null,2);
        SQLiteDatabase db = sqlite.getWritableDatabase();
        adapter.setBeers(sqlite.getList(db));
        adapter.notifyDataSetChanged();
    }
}
