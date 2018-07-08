package app.geniuslab.beer.recycler;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import app.geniuslab.beer.R;
import app.geniuslab.beer.activity.DetailBeerActivity;
import app.geniuslab.beer.activity.EditBeerActivity;
import app.geniuslab.beer.activity.QRActivity;
import app.geniuslab.beer.connection.MyConnection;
import app.geniuslab.beer.model.Beer;

public class AdapterRecycler extends RecyclerView.Adapter<AdapterRecycler.viewHolder> {

    Context context ;
    List<Beer> beers;
    String nombre;
    OnClickBeer onClickBeer;

    public AdapterRecycler(Context context, List<Beer> beers) {
        this.context = context;
        this.beers = beers;
       // Log.e("sizeBeers", beers.size() + "");
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        TextView nombre , precio ;
        ImageView qrImage, editImage, deleteImage;
        public viewHolder(View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.tview_nombre);
            precio = itemView.findViewById(R.id.tview_precio);
            qrImage = itemView.findViewById(R.id.qr_image);
            editImage = itemView.findViewById(R.id.edit_image);
            deleteImage = itemView.findViewById(R.id.delete_image);
        }
    }


    public void setOnClickBeer(OnClickBeer onClickBeer) {
        this.onClickBeer = onClickBeer;
    }

    @NonNull
    @Override
    public AdapterRecycler.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_recycler, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterRecycler.viewHolder holder, int position) {
        Beer beer = beers.get(holder.getAdapterPosition());
       Log.e("beer_data",beer.getName()  + "");
        holder.nombre.setText(beer.getName());
        holder.precio.setText(beer.getPrice());
        holder.editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditBeerActivity.class);
                intent.putExtra("beer", beers.get(holder.getAdapterPosition()));
                context.startActivity(intent);
            }
        });
        holder.deleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickBeer.onDelete( beers.get(holder.getAdapterPosition()));
            }
        });
        holder.qrImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, QRActivity.class);
                intent.putExtra("beer", beers.get(holder.getAdapterPosition()));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return beers == null ? 0 : beers.size();
    }

    public void setBeers(List<Beer> beers) {
        this.beers = beers;
    }

    public interface OnClickBeer{
        void onDelete(Beer beer);
    }
}
