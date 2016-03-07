package com.prodevteam.tastebud;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

/**
 * Created by Belton on 2/25/2016.
 */
public class EmployeeActivity extends AppCompatActivity {

    private String restaurantName;

    private LinearLayout orderWrapper;
    private Button cancelButton;
    private Button completeButton;
    private TextView emailDisplay;
    private TextView restaurantDisplay;
    private ImageButton logoutImage;
    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        logoutImage = (ImageButton) findViewById(R.id.back_image_button);
        logoutButton = (Button) findViewById(R.id.back_button);
        emailDisplay = (TextView) findViewById(R.id.current_employee);
        restaurantDisplay = (TextView) findViewById(R.id.rest_name);
        orderWrapper = (LinearLayout) findViewById(R.id.order_wrapper);

        logoutImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLogoutClick();
            }
        });
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLogoutClick();
            }
        });
        String restaurantName = getIntent().getStringExtra("restaurantName");
        this.restaurantName = restaurantName;
        restaurantDisplay.setText(restaurantName.toCharArray(), 0, restaurantName.length());

        String email = getIntent().getStringExtra("email");
        emailDisplay.setText(email.toCharArray(), 0, email.length());

        new AsyncTask<String, Void, ArrayList<OrderData>>() {
            @Override
            protected ArrayList<OrderData> doInBackground(String... params) {
                ArrayList<OrderData> orders = App.sqlConnection.getPendingOrders(params[0]);
                return orders;
            }

            @Override
            protected void onPostExecute(ArrayList<OrderData> result) {
                for(OrderData m : result)
                    orderWrapper.addView(new EmployeeActivity.OrderItem(EmployeeActivity.this, m));
            }
        }.execute(restaurantName);

        cancelButton = (Button) findViewById(R.id.cancel_order_button);
        completeButton = (Button) findViewById(R.id.complete_order_button);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCancelButtonClicked();
            }
        });

        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCompleteButtonClicked();
            }
        });
    }

    private void onLogoutClick() {
        App.currentUser = null;
        startActivity(new Intent(this, LoginActivity.class));
    }

    private ArrayList<OrderData> onCancelButtonClicked() {
        final ArrayList<OrderData> checkedOrders = new ArrayList<>();
        for(int i = 0; i < orderWrapper.getChildCount(); i++) {
            OrderItem order = (OrderItem) orderWrapper.getChildAt(i);
            if(order.isChecked())
                checkedOrders.add(new OrderData(order));
        }
        new AsyncTask<String, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(String... params) {
                return App.sqlConnection.removeOrders(params[0], checkedOrders);
            }

            @Override
            protected void onPostExecute(Boolean result) {
                ArrayList<OrderItem> items = new ArrayList<>();
                if(result) {
                    for(int i = 0; i < orderWrapper.getChildCount(); i++) {
                        OrderItem order = (OrderItem) orderWrapper.getChildAt(i);
                        if(order.isChecked())
                            items.add(order);
                    }
                    for(OrderItem item : items)
                        orderWrapper.removeView(item);
                } else Toast.makeText(EmployeeActivity.this, "Error removing orders.", Toast.LENGTH_SHORT).show();
            }
        }.execute(restaurantName);
        return checkedOrders;
    }

    private void onCompleteButtonClicked() {
        onCancelButtonClicked();
        Toast.makeText(EmployeeActivity.this, "Order(s) filled.", Toast.LENGTH_SHORT).show();
    }

    private class PushNotificationRequest {
        private String n1;
        private String n2;
        public PushNotificationRequest(String name, String t) {
            n1 = name;
            n2 = t;
        }
    }

    public static class OrderData {
        private String name;
        private String price;
        private String customerName;
        private String token;

        public OrderData(String name, String price, String customerName, String token) {
            this.name = name;
            this.price = price;
            this.customerName = customerName;
            this.token = token;
        }

        public OrderData(OrderItem item) {
            this.name = item.getNameView().getText().toString();
            this.price = item.getPriceView().getText().toString();
            this.customerName = item.getCustomerNameView().getText().toString();
        }

        public String getToken() {
            return token;
        }

        public String getCustomerName() {
            return customerName;
        }

        public String getName() {
            return name;
        }

        public String getPrice() {
            return price;
        }
    }
    public class OrderItem extends RelativeLayout {
        private CheckBox checkBox;
        private TextView nameView;
        private TextView priceView;
        private TextView customerNameView;

        public OrderItem(Context context) {
            super(context);

            View.inflate(context, R.layout.order_item_layout, this);
            checkBox = (CheckBox) findViewById(R.id.order_checkbox);
            nameView = (TextView) findViewById(R.id.order_name);
            priceView = (TextView) findViewById(R.id.order_price);
            customerNameView = (TextView) findViewById(R.id.customer_name);
        }

        public OrderItem(Context context, OrderData m) {
            this(context);

            nameView.setText(m.getName().toCharArray(), 0, m.getName().length());
            priceView.setText(m.getPrice().toCharArray(), 0, m.getPrice().length());
            customerNameView.setText(m.getCustomerName().toCharArray(), 0, m.getCustomerName().length());
        }

        public TextView getCustomerNameView() {
            return customerNameView;
        }

        public TextView getNameView() {
            return nameView;
        }

        public TextView getPriceView() {
            return priceView;
        }

        public boolean isChecked() {
            return checkBox.isChecked();
        }
    }
}
