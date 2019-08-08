package com.rocca.umrah.kafala.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.annotation.NonNull;
import com.rocca.umrah.kafala.R;

public class UpdatesDialog extends Dialog {

    private Button btnAgree;

    public UpdatesDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_post_date_dialog_layout);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        btnAgree = findViewById(R.id.btn_agree);
        btnAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdatesDialog.this.dismiss();
            }
        });
    }
}
