package com.example.karabressler.summit_hack_app;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.reimaginebanking.api.nessieandroidsdk.NessieError;
import com.reimaginebanking.api.nessieandroidsdk.NessieResultsListener;
import com.reimaginebanking.api.nessieandroidsdk.constants.BillStatus;
import com.reimaginebanking.api.nessieandroidsdk.models.Bill;
import com.reimaginebanking.api.nessieandroidsdk.models.PostResponse;
import com.reimaginebanking.api.nessieandroidsdk.requestclients.NessieClient;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CreateWish extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_create_wish);

        TextView post_request = (TextView) findViewById(R.id.post_request);
        post_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String account_id = getIntent().getStringExtra("CURRENT USER ID");

                NessieClient client = NessieClient.getInstance("7a7490a0996b5b7d04f1c7315804c6fd");
                client.BILL.getBillsByAccount(account_id, new NessieResultsListener() {
                    @Override
                    public void onSuccess(Object result) {

                        List<Bill> billList = (List<Bill>) result;

                        if (!billList.isEmpty()) {

                            Context context = getApplicationContext();
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(context, "You already have a pending request", Toast.LENGTH_LONG);
                            toast.show();

                        } else {

                            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

                            EditText bio = (EditText) findViewById(R.id.bio);
                            EditText gift = (EditText) findViewById(R.id.gift);
                            EditText amount = (EditText) findViewById(R.id.amount);

                            Bill bill = new Bill.Builder()
                                    .paymentAmount(Integer.parseInt(amount.getText().toString()))
                                    .nickname(bio.getText().toString())
                                    .status(BillStatus.PENDING)
                                    .paymentDate(df.format(new Date()))
                                    .recurringDate(1)
                                    .payee(gift.getText().toString())
                                    .build();


                            NessieClient client = NessieClient.getInstance("7a7490a0996b5b7d04f1c7315804c6fd");
                            client.BILL.createBill(account_id, bill, new NessieResultsListener() {
                                @Override
                                public void onSuccess(Object result) {
                                    PostResponse<Bill> response = (PostResponse<Bill>) result;
                                    Bill bill = response.getObjectCreated();
                                    Context context = getApplicationContext();
                                    CharSequence text = "Success";
                                    int duration = Toast.LENGTH_SHORT;

                                    Toast toast = Toast.makeText(context, text, duration);
                                    toast.show();
                                }

                                @Override
                                public void onFailure(NessieError error) {
                                    Context context = getApplicationContext();
                                    CharSequence text = "no way jose1";
                                    int duration = Toast.LENGTH_SHORT;

                                    Toast toast = Toast.makeText(context, text + "\n" + error.getMessage(), duration);
                                    toast.show();

                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(NessieError error) {
                        Context context = getApplicationContext();
                        CharSequence text = "no way jose22";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();

                    }
                });
            }
        });


    }

}