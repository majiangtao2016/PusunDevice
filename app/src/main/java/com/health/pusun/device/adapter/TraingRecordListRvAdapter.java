package com.health.pusun.device.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.health.pusun.device.R;
import com.health.pusun.device.vo.UserRankVo;

import java.util.List;

/**
 * Created by majt on 16/7/21.
 */
public class TraingRecordListRvAdapter extends RecyclerView.Adapter<TraingRecordListRvAdapter.ViewHolder> {

    private List<UserRankVo> infoDeviceDtos;
    private Context context;

    public TraingRecordListRvAdapter(List<UserRankVo> infoDeviceDtos, Context context) {
        this.infoDeviceDtos = infoDeviceDtos;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.training_record_rv_item, null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final UserRankVo data = infoDeviceDtos.get(position);

        holder.ranks.setText("" + data.getRanking());
        holder.name.setText(data.getUserName());
        holder.scores.setText(data.getScore() + "分");

    }

    @Override
    public int getItemCount() {
        return infoDeviceDtos == null ? 0 : infoDeviceDtos.size();
    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        super.onViewRecycled(holder);
        if(null != holder) {
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView ranks, name, scores;
        public ViewHolder(View v) {
            super(v);
            ranks = (TextView) v.findViewById(R.id.ranks);
            name = (TextView) v.findViewById(R.id.name);
            scores = (TextView) v.findViewById(R.id.scores);
        }

    }
}
