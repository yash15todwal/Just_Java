package com.example.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    int quantity = 1;
    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view)
    {
        CheckBox whippedCreamCheckbox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckbox.isChecked();
        CheckBox chocolateCheckbox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckbox.isChecked();
        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();
        int price=calculatePrice(hasWhippedCream,hasChocolate);
        String pricemessage = createOrderSummary(name,price,hasWhippedCream,hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java Order for "+name);
        intent.putExtra(Intent.EXTRA_TEXT, pricemessage);
        startActivity(intent);
    }

    public void increment(View view)
    {
        if(quantity==100)
        {
            Toast.makeText(this,"You cannot have more than 100 cup of coffees!",Toast.LENGTH_SHORT).show();
            return ;
        }
        quantity=quantity+1;
        displayQuantity(quantity);
    }

    public void decrement(View view)
    {
        if(quantity==1)
        {
            Toast.makeText(this,"You cannot have less than 1 cup of coffees!",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity=quantity-1;
        displayQuantity(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }

    private String createOrderSummary( String name, int price, boolean addWhippedCream, boolean addChocolate)
    {
        String pricemessage = name+"\nAdd Whipped Cream? "+addWhippedCream+"\nAdd Chocolate? "+addChocolate+"\nQuantity: "+quantity+"\nTotal: $" + price;
        pricemessage+="\n"+getString(R.string.thank_you);
        return pricemessage;
    }
    /**
     * Calculates the price of the order.
     *
     */
    private int calculatePrice(boolean addWhippedCream,boolean addChocolate) {
        int basePrice = 5;
        if(addWhippedCream){
            basePrice+=1;
        }
        if(addChocolate){
            basePrice+=2;
        }
        return quantity * basePrice;
    }
}