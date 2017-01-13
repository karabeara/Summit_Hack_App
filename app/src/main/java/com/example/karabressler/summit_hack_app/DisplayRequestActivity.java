package com.example.karabressler.summit_hack_app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.reimaginebanking.api.nessieandroidsdk.NessieError;
import com.reimaginebanking.api.nessieandroidsdk.NessieResultsListener;
import com.reimaginebanking.api.nessieandroidsdk.models.Account;
import com.reimaginebanking.api.nessieandroidsdk.models.Bill;
import com.reimaginebanking.api.nessieandroidsdk.requestclients.NessieClient;

public class DisplayRequestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_display_request);



        Intent intent = getIntent();
        String accID = intent.getStringExtra("ACCOUNT ID");
        String billID = intent.getStringExtra("BILL ID");
        String currentUserID = intent.getStringExtra("CURRENT USER ID");

        NessieClient client = NessieClient.getInstance("7a7490a0996b5b7d04f1c7315804c6fd");
        client.BILL.getBill(billID, new NessieResultsListener() {
            @Override
            public void onSuccess(Object result) {
                Bill bill = (Bill) result;
                String amount = Double.toString(bill.getPaymentAmount());
                amount = amount.substring(0, amount.length() - 2);
                amount = "$" + amount;

                ((TextView) findViewById(R.id.ask)).setText(amount);
            }

            @Override
            public void onFailure(NessieError error) {
                Toast.makeText(getApplicationContext(), "uh oh", Toast.LENGTH_LONG).show();
            }
        });

        client.ACCOUNT.getAccount(accID, new NessieResultsListener() {
            @Override
            public void onSuccess(Object result) {
                Account acc = (Account) result;

                String fullBio = acc.getNickname();

                int importantIndex = fullBio.indexOf('\b');
                int spaceIndex = fullBio.indexOf(' ');
                String name = fullBio.substring(0, spaceIndex);
                String bio = fullBio.substring(importantIndex + 1);

                TextView txtName = (TextView) findViewById(R.id.txtName);
                TextView txtBio = (TextView) findViewById(R.id.txtBio);
                txtName.setText(name);

                txtBio.setText(bio);

            }

            @Override
            public void onFailure(NessieError error) {
                Context context = getApplicationContext();
                CharSequence text = "no way";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });

        TextView txtSupport = (TextView) findViewById(R.id.support);

        txtSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NessieClient client = NessieClient.getInstance("7a7490a0996b5b7d04f1c7315804c6fd");
                client.BILL.getBill(getIntent().getStringExtra("BILL ID"), new NessieResultsListener() {
                    @Override
                    public void onSuccess(Object result) {
                        Bill bill = (Bill) result;
                        Intent intent = new Intent(DisplayRequestActivity.this, DoneSupport.class);
                        intent.putExtra("ACCOUNT ID", bill.getAccountId());
                        intent.putExtra("BILL ID", bill.getId());
                        intent.putExtra("CURRENT USER ID", "5877fe9b1756fc834d8eb2ea");
                        intent.putExtra("BILL AMOUNT", bill.getPaymentAmount());
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(NessieError error) {

                    }
                });
            }
        });
    }
}
