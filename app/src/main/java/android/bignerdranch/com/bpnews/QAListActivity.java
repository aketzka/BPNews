package android.bignerdranch.com.bpnews;

import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QAListActivity extends AppCompatActivity {

    public static String TAG = "AppCompatActivity";
    private static String QAUrl = "http://www.babyplan.ru/questions/";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private List<BPQuestion> mQAList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qalist);
        mRecyclerView = (RecyclerView)findViewById(R.id.qa_list_activity_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mQAList = new ArrayList<>();
        mAdapter = new QAListAdapter();
        mRecyclerView.setAdapter(mAdapter);
        getQAList();
    }

    private class GetQAListTask extends AsyncTask<Void, Void, Void>{
        List<BPQuestion> mBPQuestions;
        @Override
        protected Void doInBackground(Void... voids) {
            mBPQuestions = new ArrayList<>();
            try {
                Document document = Jsoup.connect(QAUrl).get();
                Elements questions = document.select("tr");
                for (Element question: questions) {
                    BPQuestion bpQuestion = new BPQuestion();
                    String qUrl = question.select("td[class=col_f_forum]").first()
                            .select("a[href]").first()
                            .absUrl("href");
                    bpQuestion.setQuestionUrl(qUrl);
                    String qTitle = question.select("td[class=col_f_forum]").first()
                            .select("a[href]").text();
                    bpQuestion.setTitle(qTitle);
                    String qAuthorUrl = question.select("td[class=col_f_post]").first()
                            .select("a[href]").first()
                            .absUrl("href");
                    bpQuestion.setAuthorUrl(qAuthorUrl);
                    String qAuthorPhotoUrl = question.select("td[class=col_f_post]").first()
                            .select("img[src]").first()
                            .absUrl("src");
                    bpQuestion.setAuthorPhotoUrl(qAuthorPhotoUrl);
                    String qAuthorName = question.select("td[class=col_f_post]").first()
                            .select("li")
                            .select("a[title]").text();
                    bpQuestion.setAuthorName(qAuthorName);
                    String qDescription = question.select("td[class=col_f_forum]").first()
                            .select("span").text();
                    bpQuestion.setQuestionDescription(qDescription);
                    mBPQuestions.add(bpQuestion);
                }
                Log.i(TAG, "Connection successful");
            }catch (IOException ioe){
                Log.i(TAG, "Connection failed: ", ioe);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            setQAList(mBPQuestions);
        }
    }

    public void setQAList(List<BPQuestion> qaList){
        mQAList = qaList;
        mAdapter.notifyDataSetChanged();
    }

    public void getQAList(){
        GetQAListTask task = new GetQAListTask();
        task.execute();
    }

    private class QAListViewHolder extends RecyclerView.ViewHolder{
        public ImageView mAuthorImage;
        public TextView mAuthorName;
        public TextView mQuestionText;
        public TextView mQuestionDescription;

        public QAListViewHolder(@NonNull View itemView) {
            super(itemView);
            mAuthorImage = (ImageView) itemView.findViewById(R.id.qalist_element_author_image);
            mAuthorName = (TextView) itemView.findViewById(R.id.qalist_element_author_name);
            mQuestionText = (TextView) itemView.findViewById(R.id.qalist_element_title);
            mQuestionDescription = (TextView) itemView.findViewById(R.id.qalist_element_description);
        }
    }

    private class QAListAdapter extends RecyclerView.Adapter<QAListViewHolder>{

        @NonNull
        @Override
        public QAListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.qalist_element, parent, false);
            return new QAListViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull QAListViewHolder holder, int position) {
            BPQuestion question = mQAList.get(position);
            holder.mQuestionText.setText(question.getTitle());
            holder.mAuthorName.setText(question.getAuthorName());
            holder.mQuestionDescription.setText(question.getQuestionDescription());
        }

        @Override
        public int getItemCount() {
           return mQAList.size();
        }
    }
}
