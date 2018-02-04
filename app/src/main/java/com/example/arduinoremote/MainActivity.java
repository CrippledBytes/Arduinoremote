package com.example.arduinoremote;

        import android.app.Activity;
        import android.app.ProgressDialog;
        import android.content.Context;
        import android.content.DialogInterface;
        import android.net.ConnectivityManager;
        import android.net.DhcpInfo;
        import android.net.NetworkInfo;
        import android.net.wifi.WifiManager;
        import android.os.AsyncTask;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.text.format.Formatter;
        import android.util.Log;
        import android.view.View;
        import android.widget.EditText;
        import android.widget.TextView;

        import java.io.BufferedWriter;
        import java.io.IOException;
        import java.io.OutputStreamWriter;
        import java.io.PrintWriter;
        import java.math.BigInteger;
        import java.net.DatagramPacket;
        import java.net.DatagramSocket;
        import java.net.InetAddress;
        //import java.net.MulticastSocket;
        import java.net.InetSocketAddress;
        import java.net.InterfaceAddress;
        import java.net.NetworkInterface;
        import java.net.Socket;
        //import java.net.SocketException;
        //import java.net.SocketTimeoutException;
        import java.net.SocketException;
        import java.net.SocketTimeoutException;
        import java.net.UnknownHostException;
        import java.nio.ByteOrder;

        import static android.content.ContentValues.TAG;
        import static java.lang.Boolean.TRUE;

public class MainActivity extends AppCompatActivity {

    private TextView tv;
    private TextView tvError;
    private String SERVER_IP = "192.168.0.106";
    private static final String TAG = "Arduino Remote";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.IP_target);
        tvError = (TextView) findViewById(R.id.Error_target);
        if (isWifiConnected()) {

                WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                int ipAddress = wifiManager.getConnectionInfo().getIpAddress();

                // Convert little-endian to big-endianif needed
                if (ByteOrder.nativeOrder().equals(ByteOrder.LITTLE_ENDIAN)) {
                    ipAddress = Integer.reverseBytes(ipAddress);
                }

                byte[] ipByteArray = BigInteger.valueOf(ipAddress).toByteArray();

                String ipAddressString;
                try {
                    ipAddressString = InetAddress.getByAddress(ipByteArray).getHostAddress();
                } catch (UnknownHostException ex) {
                    Log.e("WIFIIP", "Unable to get host address.");
                    ipAddressString = null;
                }
                if (ipAddressString != null) {
                    byte[] payload = "ip".getBytes();

                    ipAddressString = ipAddressString.substring(0, ipAddressString.lastIndexOf(".") + 1);
                }
                ScanNetwork findIt = new ScanNetwork();
                findIt.execute(ipAddressString);

        } else {
            new AlertDialog.Builder(this)
                    .setTitle("No Wifi Connection")
                    .setMessage("It looks like your Wifi connection is off. Please turn it " +
                            "on and try again")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            killMe();
                        }
                    }).setIcon(android.R.drawable.ic_dialog_alert).show();
        }
    }

    public void onClick(View view) {
        if(isWifiConnected()){
            EditText et = (EditText) findViewById(R.id.editText);
            String str = et.getText().toString();
            SendCommand sc = new SendCommand();
            sc.execute(str);
        }
    }

    public void killMe() {
        android.os.Process.killProcess(android.os.Process.myPid());
        super.onDestroy();
    }

    public void pow_btn_Click(View view) {
        if(isWifiConnected()) {
            SendCommand sc = new SendCommand();
            sc.execute("set UART 16");
        }
    }

    public void chan_up_btn_Click(View view) {
        if(isWifiConnected()) {
            SendCommand sc = new SendCommand();
            sc.execute("set UART 2A");
        }
    }

    public void chan_dwn_btn_Click(View view) {
        if(isWifiConnected()) {
            SendCommand sc = new SendCommand();
            sc.execute("set UART 2D");
        }
    }

    public void vol_dwn_btn_Click(View view) {
        if(isWifiConnected()){
            SendCommand sc = new SendCommand();
            sc.execute("set UART 29");
        }
    }

    public void vol_up_btn_Click(View view) {
        if(isWifiConnected()) {
            SendCommand sc = new SendCommand();
            sc.execute("set UART 26");
        }
    }

    public void btn_0_Click(View view) {
        if(isWifiConnected()) {
            SendCommand sc = new SendCommand();
            sc.execute("set UART 15");
        }
    }

    public void btn_1_Click(View view) {
        if(isWifiConnected()) {
            SendCommand sc = new SendCommand();
            sc.execute("set UART 02");
        }
    }

    public void btn_2_Click(View view) {
        if(isWifiConnected()){
            SendCommand sc = new SendCommand();
            sc.execute("set UART 05");
        }
    }

    public void btn_3_Click(View view) {
        if(isWifiConnected()) {
            SendCommand sc = new SendCommand();
            sc.execute("set UART 06");
        }
    }

    public void btn_4_Click(View view) {
        if(isWifiConnected()) {
            SendCommand sc = new SendCommand();
            sc.execute("set UART 09");
        }
    }

    public void btn_5_Click(View view) {
        if(isWifiConnected()) {
            SendCommand sc = new SendCommand();
            sc.execute("set UART 0A");
        }
    }

    public void btn_6_Click(View view) {
        if(isWifiConnected()) {
            SendCommand sc = new SendCommand();
            sc.execute("set UART 0D");
        }
    }

    public void btn_7_Click(View view) {
        if(isWifiConnected()) {
            SendCommand sc = new SendCommand();
            sc.execute("set UART 0E");
        }
    }

    public void btn_8_Click(View view) {
        if (isWifiConnected()) {
            SendCommand sc = new SendCommand();
            sc.execute("set UART 11");
        }
    }

    public void btn_9_Click(View view) {
        if (isWifiConnected()) {
            SendCommand sc = new SendCommand();
            sc.execute("set UART 12");
        }
    }

    public void btn_ok_Click(View view) {
        if (isWifiConnected()) {
            SendCommand sc = new SendCommand();
            sc.execute("set UART 36");
        }
    }

    public void btn_menu_Click(View view) {
        if (isWifiConnected()) {
            SendCommand sc = new SendCommand();
            sc.execute("set UART 21");
        }
    }

    public void btn_exit_Click(View view) {
        if (isWifiConnected()) {
            SendCommand sc = new SendCommand();
            sc.execute("set UART 3D");
        }
    }

    public void btn_guide_Click(View view) {
        if (isWifiConnected()) {
            SendCommand sc = new SendCommand();
            sc.execute("set UART 1A");
        }
    }

    public void btn_lft_Click(View view) {
        if (isWifiConnected()) {
            SendCommand sc = new SendCommand();
            sc.execute("set UART 31");
        }
    }

    public void btn_rgt_Click(View view) {
        if (isWifiConnected()) {
            SendCommand sc = new SendCommand();
            sc.execute("set UART 32");
        }
    }

    public void btn_up_Click(View view) {
        if (isWifiConnected()) {
            SendCommand sc = new SendCommand();
            sc.execute("set UART 2E");
        }
    }

    public void btn_dwn_Click(View view) {
        if (isWifiConnected()) {
            SendCommand sc = new SendCommand();
            sc.execute("set UART 35");
        }
    }

    private boolean isWifiConnected() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        //return networkInfo != null && networkInfo.isConnected();
        return networkInfo != null && (ConnectivityManager.TYPE_WIFI == networkInfo.getType()) && networkInfo.isConnected();
    }


    private class ScanNetwork extends AsyncTask<String, String, String> {
        private int UDPPORT = 7682;
        byte[] buf = new byte[250];
        private String strip = "nope";
        ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params) {
            byte[] payload = "ip".getBytes();
            String currentIP;
            for (int i = 2; i < 255; i++) {
                currentIP = params[0] + Integer.valueOf(i).toString();
                publishProgress("IP: " + currentIP);
                try (DatagramSocket clientSocket = new DatagramSocket()) {
                    InetAddress address = InetAddress.getByName(currentIP);
                    clientSocket.setSoTimeout(200);
                    DatagramPacket multi = new DatagramPacket(payload, payload.length, address, UDPPORT);
                    DatagramPacket msgPacket = new DatagramPacket(buf, buf.length);
                    clientSocket.send(multi);
                    // Receive the information and print it.
                    clientSocket.receive(msgPacket);

                    String msg = new String(msgPacket.getData()).trim();
                    if (msg.length() > 10) {
                        //strip = msg.substring(0, msg.indexOf(".0", 10));
                        strip = msg.substring(0, msg.length() - 3);
                        i = 1000;
                    }

                } catch (SocketException e) {
                    Log.e(TAG, Log.getStackTraceString(e));
                } catch (UnknownHostException e) {
                    Log.e(TAG, Log.getStackTraceString(e));
                } catch (IOException e) {
                    Log.e(TAG, Log.getStackTraceString(e));
                }
            }
            return strip;
        }

        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();
            if (!result.equals("nope")) {
                SERVER_IP = result;
                tv.setText(result);
            }
        }
        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(MainActivity.this,
                    "Networkscan",
                    "Wait for networkscan...");
        }
        @Override
        protected void onProgressUpdate(String... progress) {

            // Update the ProgressBar
            progressDialog.setMessage(String.valueOf(progress[0]));
        }

    }
    private class SendCommand extends AsyncTask<String,Void,String> {
        private int SERVERPORT = 1588;

        @Override
        protected String doInBackground(String... params) {
            try {
                InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
                Socket socket = new Socket(serverAddr, SERVERPORT);
                socket.setSoTimeout(1000);
                String str = new String(params[0]);
                PrintWriter out = new PrintWriter(new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream())),
                        true);
                out.println(str);
                out.flush();
                socket.close();
            } catch (SocketTimeoutException e){
                tvError.setText("Socket Timeout :(");
                Log.e(TAG, Log.getStackTraceString(e));
            } catch (UnknownHostException e) {
                tvError.setText("Host onbekend send");
                Log.e(TAG, Log.getStackTraceString(e));
            } catch (IOException e) {
                tvError.setText("IOException send");
                Log.e(TAG, Log.getStackTraceString(e));
            } catch (Exception e) {
                tvError.setText("Fehler send");
                Log.e(TAG, Log.getStackTraceString(e));
            }
            return null;
        }
        @Override
        protected void onPostExecute(String result){
        }
        @Override
        protected void onPreExecute() {

        }

    }
}