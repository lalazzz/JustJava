package com.example.android.justjava;

/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 *
 * package com.example.android.justjava;
 */

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.android.justjava.R.id.uName;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /**
     * This method is called when the order button is clicked.
     */
    int quantity = 1;

    public void submitOrder(View view) {

        //Check box for the whipped cream
        CheckBox whippedCreamBox = (CheckBox)findViewById(R.id.WhippedCream);
        Boolean haswhippedCream = whippedCreamBox.isChecked();
        Log.v("Main Actiivty","Has whippedcream :" + haswhippedCream);

        //Check Box for the chocolate Chip
        CheckBox chocoChipBox = (CheckBox)findViewById(R.id.ChocolateChip);
        Boolean haschocoChip = chocoChipBox.isChecked();
        Log.v("Main Actiivty","Has whippedcream :" + haschocoChip);

        //Retreiving the user name to the submit order
        EditText userName = (EditText) findViewById(uName);
        String showName = userName.getText().toString();
        Log.v("Main Actiivty","User Name :" + showName);

        // When submit order is triggered, the calculate method will be updated to price.
        int price = calculatePrice(haswhippedCream,haschocoChip);
        //String priceMessage = orderSummary(price); display the string message individually can be done using
        //the line below is being combined rather than the extra line above
        //messageView(orderSummary(price,haswhippedCream,haschocoChip,showName)); This is use to show the content on the application layout text.

            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:")); // only email apps should handle this
            intent.putExtra(Intent.EXTRA_EMAIL, "emailaddress@emailaddress.com");
            intent.putExtra(Intent.EXTRA_SUBJECT, "Mia's Cafe Order Summary for " + showName);
            intent.putExtra(Intent.EXTRA_TEXT, orderSummary(price,haswhippedCream,haschocoChip,showName));
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(Intent.createChooser(intent, "Send Email"));
            }

    }
    /**
     * Calculates the price of the order.
     * param quantity is the number of cups of coffee ordered
     * param priceOfMocha is the price for a cup of mocha $5
     */
    private int calculatePrice(boolean haswhippedCream, boolean haschocoChip) {
        //price of one coffee
        int basePrice = 5;

        //additional of whipped cream
        if (haswhippedCream){
            basePrice= basePrice + 1;
        }

        //additional of choco chip
        if (haschocoChip){
            basePrice = basePrice + 2;
        }
        return quantity * basePrice;
    }

    private String orderSummary(int price,boolean haswhippedCream,boolean haschocoChip,String showName){
        String priceMessage = "It will be "+quantity+ " cup(s) of coffee." + "\nPlease pay $"+ price + " at the cashier, ";
        priceMessage = priceMessage +"\n"+ getString(R.string.order_summary_whipped_cream, haswhippedCream);
        priceMessage = priceMessage +"\n"+ getString(R.string.order_summary_chocolate, haschocoChip);
        priceMessage = priceMessage +"\n"+ getString(R.string.thankYou) +"\n" + getString(R.string.summaryName,showName);
        return priceMessage;
    }

    public void plus(View view) {
        //Mia way to do the maximum range of quantity. Including if and else. hmmmm...
        if (quantity <100){
            quantity = quantity + 1;
            quantityView(quantity);
        }
        else{
            Toast.makeText(this,"Sorry, you have reach the maximum quantity of beverages.",Toast.LENGTH_SHORT).show();
            return;
        }
    }

    public void minus(View view) {
        if (quantity ==1) {

            //Showing the toast when it have hit the minimum.
            Context context = getApplicationContext();
            CharSequence text = "Sorry, please order at least 1 cup of beverage. Thank you.";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            //Remember to put return for the if method.
            return;
        }
            quantity = quantity - 1;
            quantityView(quantity);
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void quantityView(int number) {
        TextView quantityNum = (TextView) findViewById(R.id.Num_TextView);
        quantityNum.setText("" + number);
    }
    /**
     * This method displays the given text on the screen it will be comment off  after being send to email.
    private void messageView(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.totalPrice_TextView);
        orderSummaryTextView.setText(message);
    }
     */

}