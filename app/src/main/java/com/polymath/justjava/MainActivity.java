package com.polymath.justjava;
/*
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        boolean whippedCream = hasWhippedCream();
        boolean chocolate = hasChocolate();
        int price = calculatePrice(whippedCream, chocolate);

        String bodyOfEmail = createOrderSummary(price, whippedCream, chocolate);
        String email = "coffeeorderpremium@gmail.com";
        String subject = getString(R.string.mail_subject);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, bodyOfEmail);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }


        displayMessage(bodyOfEmail);
    }

    private String createOrderSummary(int price, boolean whippedCream, boolean chocolate) {
        String name = getName();

        String orderSummary = getString(R.string.order_summary_name, name) + "\n";
        orderSummary += getString(R.string.order_summary_whipped_cream, whippedCream) + "\n";
        orderSummary += getString(R.string.order_summary_chocolate, chocolate) + "\n";
        orderSummary += getString(R.string.order_summary_quantity, quantity) + "\n";
        NumberFormat india = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
        String type = india.format(price);
        orderSummary += getString(R.string.order_summary_total) + type + "\n";
        orderSummary += getString(R.string.thank_you) + "\n";
        return orderSummary;
    }

    private String getName() {
        EditText nameEditText = (EditText) findViewById(R.id.name_edit_text);
        return nameEditText.getText().toString();
    }

    private boolean hasChocolate() {
        CheckBox chocolate = (CheckBox) findViewById(R.id.chocolate);
        return chocolate.isChecked();
    }

    private boolean hasWhippedCream() {
        CheckBox whippedCream = (CheckBox) findViewById(R.id.whipped_cream);
        return whippedCream.isChecked();
    }

    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

    public void increment(View view) {
        if (quantity == 100) {
            Toast.makeText(this, getString(R.string.too_many_coffees), Toast.LENGTH_SHORT).show();
            return;
        }
        quantity++;
        displayQuantity(quantity);
    }

    public void decrement(View view) {
        if (quantity == 1) {
            Toast.makeText(this, getString(R.string.too_few_coffees), Toast.LENGTH_SHORT).show();
            return;
        }
        quantity--;
        displayQuantity(quantity);
    }

    public int calculatePrice(boolean whippedCream, boolean chocolate) {
        int price = 5;

        if (whippedCream) {
            price += 1;
        }
        if (chocolate) {
            price += 2;
        }
        return (quantity * price);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /*
     * This method displays the given price on the screen.
     */
//    private void displayPrice(int number) {
//        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
//        orderSummaryTextView.setText();
//    }

}