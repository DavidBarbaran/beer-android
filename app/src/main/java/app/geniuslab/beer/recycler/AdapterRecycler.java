package app.geniuslab.beer.recycler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;

import app.geniuslab.beer.R;
import app.geniuslab.beer.model.Beer;

public class AdapterRecycler extends RecyclerView.Adapter<AdapterRecycler.viewHolder> {

    Context context ;
    List<Beer> beers;

    public AdapterRecycler(Context context, List<Beer> beers) {
        this.context = context;
        this.beers = beers;
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        TextView nombre , precio ;
        public viewHolder(View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.tview_nombre);
            precio = itemView.findViewById(R.id.tview_precio);
        }
    }

    @NonNull
    @Override
    public AdapterRecycler.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_recycler, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRecycler.viewHolder holder, int position) {
        Beer beer = beers.get(holder.getAdapterPosition());
        holder.nombre.setText(beer.getName());
        holder.precio.setText(beer.getPrice());
    }

    @Override
    public int getItemCount() {
        return beers.size();
    }
}
