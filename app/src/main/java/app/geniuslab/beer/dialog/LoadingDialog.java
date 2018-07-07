package app.geniuslab.beer.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.StyleRes;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import app.geniuslab.beer.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoadingDialog extends AlertDialog {

    @BindView(R.id.title_text)
    TextView titleText;

    @BindView(R.id.message_text)
    TextView messageText;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.accept_linear)
    LinearLayout acceptLinear;

    private String title;
    private String message;
    private Context context;

    public LoadingDialog(Context context) {
        super(context);
        this.context = context;
        initDialog();
    }

    public LoadingDialog(Context context, String title, String message) {
        super(context);
        this.title = title;
        this.message = message;
        this.context = context;
        initDialog();
    }

    protected LoadingDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initDialog();
    }

    protected LoadingDialog(Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        initDialog();
    }


    private void initDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.dialog_loading, null);
        ButterKnife.bind(this, view);
        setView(view);
        titleText.setText(title);
        messageText.setText(message);
        setCancelable(false);
    }

    public void showMessage(String message) {
        messageText.setText(message);
        progressBar.setVisibility(View.GONE);
        acceptLinear.setVisibility(View.VISIBLE);
    }

    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        acceptLinear.setVisibility(View.GONE);
    }

    @OnClick(R.id.accept_btn)
    public void actionAccept() {
        cancel();
    }
}