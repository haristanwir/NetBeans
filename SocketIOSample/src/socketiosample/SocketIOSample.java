/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketiosample;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import io.socket.engineio.client.transports.WebSocket;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONObject;

/**
 *
 * @author Haris Tanwir
 */
public class SocketIOSample implements Runnable {

    /**
     * @param args the command line arguments
     */
    static Integer users = 1;
    static Integer tps = 1;
    //static Integer total_messages = 1;
    static Integer duration_in_mins = 1;
    private static ArrayList mutexArray = new ArrayList();
    static ThroughputController tpsController = null;
    final OkHttpClient httpClient = new OkHttpClient();

    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        SocketIOSample.users = Integer.parseInt(args[0]);
        SocketIOSample.duration_in_mins = Integer.parseInt(args[1]);
        SocketIOSample.tps = Integer.parseInt(args[2]);
        SocketIOSample.tpsController = new ThroughputController(SocketIOSample.tps);
        for (int i = 0; i < users; i++) {
            SocketIOSample socketIOSample = new SocketIOSample();
            Thread thread = new Thread(socketIOSample);
            thread.start();
            //Thread.sleep(200);
            //System.out.println(socketIOSample.hashCode());
        }
    }

    public String sendGet() throws Exception {
        JSONObject jsonObj = null;
        Request request = new Request.Builder()
                .url("https://backend-gettoken-secure-chatbot-test.apps.icp4app.jazz.com.pk/api/getToken/923058652178")
                .addHeader("Authorization", "Bearer MzZiODViYmNmOWM4NTAxNWY3ZTQ1NDUwMGM3ODgyZmEzODhiYjM0ZjI3ZWQ4ZjcwNGU0ZTg5MjdhZDJjNzZmNDc2ODQyNGNiZWNlMTE0ZWVjY2NkYzlkZTJhYzA1ZDUxNTA1MzgzMDc2MzA1ZjViNzI3YmYzYzYxM2JmYzBkZDY0YTU1ZTZkM2VjNDI5NTUyOWZhYzMyMWExYjBmZDNkNzljZGE5ZWNmM2EyYjkwZmY2MTM2MGFiMTBmODFlYTZhNWUzZTkxMDdmMDEzMzdlOTk2MmYyZGU2YzY3M2VmNGI1OTM4M2JkMzZhZWE3ZDZjZTIwNWI2YWU4Zjc2ZmUxZA==")
                .build();
        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            // Get response body
            jsonObj = new JSONObject(response.body().string());
        }
        return (String) jsonObj.get("auth");
    }

    private String sendPost(String json) throws Exception {
        JSONObject jsonObj = null;
        RequestBody formBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        Request request = new Request.Builder()
                .url("http://orc-conversation-pt-chatbot-test.apps.icp4app.jazz.com.pk/api/user/auth")
                .addHeader("Content-Type", "application/json")
                .post(formBody)
                .build();
        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            // Get response body
            jsonObj = new JSONObject(response.body().string());
        }
        return (String) jsonObj.get("token");
    }

    private void sendPost2(String json) throws Exception {
        JSONObject jsonObj = null;
        //System.out.println(json);
        RequestBody formBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        Request request = new Request.Builder()
                .url("http://orc-conversation-pt-chatbot-test.apps.icp4app.jazz.com.pk/api/completeUserRequest")
                .addHeader("Content-Type", "application/json")
                .post(formBody)
                .build();
        System.out.println(new Date() + " - Sent!!!");
        long s_time = System.currentTimeMillis();
        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            // Get response body
            //System.out.println(response.body().string());
            System.out.println(System.currentTimeMillis() - s_time + " msec");
            System.out.println(new Date() + " - Received!!!");
        }
        //return response.body().string();
    }

    @Override
    public void run() {
        try {
            String sendGet = sendGet();
            String authJSONString = "{\"msisdn\":\"923058652178\",\"msisdnList\":[],\"auth\":\"" + sendGet + "\",\"channel\":\"JazzWorldChatbot\",\"user_pic\":\"https://usrpic.jazz.com.pk/usr/1121/pic.jpg\",\"type\":\"prepaid\",\"network\":\"jazz\",\"jazzWorldSessionID\":\"44e46627-e34e-4f16-a3c4-ec46f1e38946\"}";
            String sendPost = sendPost(authJSONString);

            synchronized (SocketIOSample.mutexArray) {
                SocketIOSample.mutexArray.add(this.hashCode());
            }
            while (SocketIOSample.mutexArray.size() < SocketIOSample.users) {
                System.out.println("Waiting!!!");
            }

            /*
            new Thread() {
                public void run() {
                    try {
                        JSONObject jsonObj = new JSONObject("{\"userQuery\":\"hi\",\"previousIntent\":\"call_details_record\",\"channelType\":\"JazzWorldChatbot\",\"token\":\"" + sendPost + "\",\"languagePrefer\":\"en\",\"feedback\":\"I am very Happy\",\"name\":null,\"intent\":null,\"jazz_pacakges_name\":null,\"jazz_packages_type\":null,\"package_durations\":null,\"other_number\":null,\"package_details\":null,\"botName\":\"Tanya\",\"OTP\":null,\"cdr_filter\":{\"cdr_dates_array\":null,\"cdr_dates_range\":null,\"cdr_duration\":null,\"cdr_filter_type\":null},\"timeZone\":\"Asia/Karachi\",buySIMData:{SIMOptions:null,city:null,SIMType:null,fullName:null,CNIC:null,contactNumber:null,numberToBeReplaced:null,email:null,contactTime:null,additionalInfo:null,acceptTermsAndConditions:null,isMNPSent:false,buy_replace_sim_get_user_details:null,buy_sim_mnp_get_user_details:null,sim_replace_kyc_done:null,address:null},duplicateBillData:{billMonth:null},existingComplaintData:{existingComplaintsOptions:null},complaintData:null,rechargeData:{creditCardNo:null,jazzCashNo:null,rechargeOptions:null,scratchCardNo:null,rechargeAmount:null,mpin:null},taxCertificateData:{certificateYear:null}}");
                        long startTime = System.currentTimeMillis();
                        long runTime = SocketIOSample.duration_in_mins * 60 * 1000;
                        long endTime = startTime + runTime;
                        while (System.currentTimeMillis() < endTime) {
                        //for (int i = 0; i < total_messages; i++) {
                            SocketIOSample.tpsController.evaluateTPS();
                            sendPost2(jsonObj.toString());
                            //socket.emit("receiveUserQuery", jsonObj);
                            //System.out.println(new Date() + " - Sent!!!");
                        }
                    } catch (Exception ex1) {
                        ex1.printStackTrace();
                    }
                }
            }.start();
             */
            IO.Options opts = new IO.Options();
            opts.transports = new String[]{WebSocket.NAME};
            opts.reconnection = false;
            opts.forceNew = true;

            Socket socket = IO.socket("http://orc-conversation-pt-chatbot-test.apps.icp4app.jazz.com.pk");

            socket.connect();

            socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    System.out.println("Connected: " + Arrays.toString(args));
                    try {
                        
                        new Thread() {
                            public void run() {
                                try {
                                    
                                    JSONObject jsonObj = new JSONObject("{\"userQuery\":\"hi\",\"previousIntent\":\"call_details_record\",\"channelType\":\"JazzWorldChatbot\",\"token\":\"" + sendPost + "\",\"languagePrefer\":\"en\",\"feedback\":\"I am very Happy\",\"name\":null,\"intent\":null,\"jazz_pacakges_name\":null,\"jazz_packages_type\":null,\"package_durations\":null,\"other_number\":null,\"package_details\":null,\"botName\":\"Tanya\",\"OTP\":null,\"cdr_filter\":{\"cdr_dates_array\":null,\"cdr_dates_range\":null,\"cdr_duration\":null,\"cdr_filter_type\":null},\"timeZone\":\"Asia/Karachi\",buySIMData:{SIMOptions:null,city:null,SIMType:null,fullName:null,CNIC:null,contactNumber:null,numberToBeReplaced:null,email:null,contactTime:null,additionalInfo:null,acceptTermsAndConditions:null,isMNPSent:false,buy_replace_sim_get_user_details:null,buy_sim_mnp_get_user_details:null,sim_replace_kyc_done:null,address:null},duplicateBillData:{billMonth:null},existingComplaintData:{existingComplaintsOptions:null},complaintData:null,rechargeData:{creditCardNo:null,jazzCashNo:null,rechargeOptions:null,scratchCardNo:null,rechargeAmount:null,mpin:null},taxCertificateData:{certificateYear:null}}");
                                    long startTime = System.currentTimeMillis();
                                    long runTime = SocketIOSample.duration_in_mins * 60 * 1000;
                                    long endTime = startTime + runTime;
                                    while (System.currentTimeMillis() < endTime) 
                                    {
                                        //for (int i = 0; i < total_messages; i++) {
                                        SocketIOSample.tpsController.evaluateTPS();
                                        synchronized (socket) {
                                            long s_time = System.currentTimeMillis();
                                            socket.emit("receiveUserQuery", jsonObj);
                                            System.out.println(System.currentTimeMillis() - s_time + " msec");
                                        }
                                        System.out.println(new Date() + " - Sent!!!");
                                    }
                                    socket.disconnect();
                                    
                                } catch (Exception ex1) {
                                    ex1.printStackTrace();
                                }
                            }
                        }.start();
                        
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }).on("serverResponse", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    //System.out.println("serverResponse: " + Arrays.toString(args));
                    System.out.println(new Date() + " - Received!!!");
                }
            }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    System.out.println("disconnect: " + Arrays.toString(args));
                }
            });
            //socket.disconnect();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
