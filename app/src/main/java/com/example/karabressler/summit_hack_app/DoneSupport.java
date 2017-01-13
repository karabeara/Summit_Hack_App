package com.example.karabressler.summit_hack_app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.reimaginebanking.api.nessieandroidsdk.NessieError;
import com.reimaginebanking.api.nessieandroidsdk.NessieResultsListener;
import com.reimaginebanking.api.nessieandroidsdk.constants.TransactionMedium;
import com.reimaginebanking.api.nessieandroidsdk.models.Transfer;
import com.reimaginebanking.api.nessieandroidsdk.requestclients.NessieClient;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DoneSupport extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_done_support);

        Intent intent = getIntent();
        TextView txtCongrats = (TextView) findViewById(R.id.txtCongrats);
        double bill_amount = intent.getDoubleExtra("BILL AMOUNT", 1.0);
        txtCongrats.setText("You just transferred " + Double.toString(bill_amount) + " man");

        NessieClient client = NessieClient.getInstance("7a7490a0996b5b7d04f1c7315804c6fd");

        String billID = intent.getStringExtra("BILL ID");
        String payeeID = intent.getStringExtra("ACCOUNT ID"); // retrieve accont id from bill
        String currID = intent.getStringExtra("CURRENT USER ID");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        // bill's payment_amount

        Transfer transfer = new Transfer.Builder()
                .medium(TransactionMedium.BALANCE)
                .description("hey")
                .amount(bill_amount)
                .payeeId(payeeID)
                .transactionDate(df.format(new Date()))
                .build();

        client.TRANSFER.createTransfer(currID, transfer, new NessieResultsListener() {
            @Override
            public void onSuccess(Object result) {
                Toast.makeText(getApplicationContext(), "Transaction completed!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(NessieError error) {

                Context context = getApplicationContext();
                CharSequence text = "no way jose 2";
                int duration = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(context, text + "\n" + error.getMessage(), duration);
                toast.show();


            }
        });

        client.BILL.deleteBill(billID, new NessieResultsListener() {
            @Override
            public void onSuccess(Object result) {

            }

            @Override
            public void onFailure(NessieError error) {
                Context context = getApplicationContext();
                CharSequence text = "no way jose";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });


        Button homeButton = (Button) findViewById(R.id.home_button);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoneSupport.this, LandingPage.class);
                startActivity(intent);
            }
        });

    }
}
