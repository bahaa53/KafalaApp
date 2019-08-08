package com.rocca.umrah.kafala.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.rocca.umrah.kafala.R;
import com.rocca.umrah.kafala.interfaces.OnAdvDeleted;

public class DeleteAdvDialog extends Dialog {

    private OnAdvDeleted onAdvDeleted;
    private Button btnAgree;
    private TextView textView;
    private EditText editText;

    public DeleteAdvDialog(@NonNull Context context, OnAdvDeleted onAdvDeleted) {
        super(context);
        this.onAdvDeleted = onAdvDeleted;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_delete_adv_layout);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        btnAgree = findViewById(R.id.btn_agree);
        textView = findViewById(R.id.tv_text);
        editText = findViewById(R.id.et_text);
        btnAgree.setOnClickListener(v -> {
            if (TextUtils.isEmpty(editText.getText().toString().trim())) {
                textView.setVisibility(View.VISIBLE);
            } else {
                DeleteAdvDialog.this.dismiss();
                onAdvDeleted.notifywhenAdvDeleted(editText.getText().toString().trim());
            }
        });
    }
}
