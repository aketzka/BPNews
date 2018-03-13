package android.bignerdranch.com.bpnews;

/**
 * Created by aketza on 13.03.18.
 */

public class BPQuestion {
    private String mQuestionUrl;
    private String mAuthorUrl;
    private String mTitle;
    private String mAuthorPhotoUrl;
    private String mAuthorName;
    private String mQuestionDescription;
    private int mAnswered;
    private int mLooked;

    public String getQuestionDescription() {
        return mQuestionDescription;
    }

    public void setQuestionDescription(String questionDescription) {
        mQuestionDescription = questionDescription;
    }

    public String getAuthorName() {
        return mAuthorName;
    }

    public void setAuthorName(String authorName) {
        mAuthorName = authorName;
    }

    public String getAuthorPhotoUrl() {
        return mAuthorPhotoUrl;
    }

    public void setAuthorPhotoUrl(String authorPhotoUrl) {
        mAuthorPhotoUrl = authorPhotoUrl;
    }

    public int getAnswered() {
        return mAnswered;
    }

    public void setAnswered(int answered) {
        mAnswered = answered;
    }

    public int getLooked() {
        return mLooked;
    }

    public void setLooked(int looked) {
        mLooked = looked;
    }

    public String getQuestionUrl() {
        return mQuestionUrl;
    }

    public void setQuestionUrl(String questionUrl) {
        mQuestionUrl = questionUrl;
    }

    public String getAuthorUrl() {
        return mAuthorUrl;
    }

    public void setAuthorUrl(String authorUrl) {
        mAuthorUrl = authorUrl;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }
}
