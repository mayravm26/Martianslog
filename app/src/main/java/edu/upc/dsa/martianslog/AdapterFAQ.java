package edu.upc.dsa.martianslog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.upc.dsa.martianslog.models.FAQ;

public class AdapterFAQ extends RecyclerView.Adapter<AdapterFAQ.ViewHolder> {
    public List<FAQ> faqs;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView question,answer;
        public View layout;
        public ViewHolder(@NonNull View FAQview) {
            super(FAQview);
            layout =FAQview;
            question=(TextView)FAQview.findViewById(R.id.question);
            answer=(TextView)FAQview.findViewById(R.id.answer);

        }

    }
    public void setData(List<FAQ> myDataset){
        faqs = myDataset;
        notifyDataSetChanged();
    }

    public void add(int position, FAQ item) {
        faqs.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        faqs.remove(position);
        notifyItemRemoved(position);
    }

    public AdapterFAQ() {
        faqs = new ArrayList<>();
    }

    public AdapterFAQ(List<FAQ> myDataset) {
        faqs = myDataset;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.activity_faqcard, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FAQ p = faqs.get(position);
        final String question = p.getQuestion();
        final String answer= p.getAnswer();
        holder.question.setText("Question: "+ question);
        holder.answer.setText("Answer: " + answer);
    }

    @Override
    public int getItemCount() {
        return faqs.size();
    }


}
