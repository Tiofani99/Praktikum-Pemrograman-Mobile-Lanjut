package com.tiooooo.academy.ui.reader.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tiooooo.academy.R;
import com.tiooooo.academy.data.source.local.entity.ModuleEntity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ModuleListAdapter extends RecyclerView.Adapter<ModuleListAdapter.ModuleViewHolder> {

    private final MyAdapterClickListener listener;
    private List<ModuleEntity> listModules = new ArrayList<>();

    ModuleListAdapter(MyAdapterClickListener listener) {
        this.listener = listener;
    }

    public void setModules(List<ModuleEntity> modules) {
        if (listModules == null) return;
        listModules.clear();
        listModules.addAll(modules);
    }


    @NonNull
    @Override
    public ModuleListAdapter.ModuleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_module_list_custom, parent, false);
        return new ModuleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ModuleListAdapter.ModuleViewHolder holder, int position) {
        ModuleEntity module = listModules.get(position);
        holder.bind(module);
        if (holder.getItemViewType() == 0) {
            holder.textTitle.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.colorTextSecondary));
        } else {
            holder.itemView.setOnClickListener(view -> {
                listener.onItemClicked(holder.getAdapterPosition(), listModules.get(holder.getAdapterPosition()).getmModuleId());

            });
        }
    }

    @Override
    public int getItemCount() {
        return listModules.size();
    }

    public class ModuleViewHolder extends RecyclerView.ViewHolder {

        final TextView textTitle;

        public ModuleViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.text_module_title);

        }

        void bind(ModuleEntity module) {
            textTitle.setText(module.getmTitle());
        }
    }

    @Override
    public int getItemViewType(int position) {
        int modulePosition = listModules.get(position).getmPosition();
        if(modulePosition == 0)return 1;
        else if(listModules.get(modulePosition -1).ismRead()) return 1;
        else return 0;
    }
}

interface MyAdapterClickListener {
    void onItemClicked(int position, String moduleId);
}
