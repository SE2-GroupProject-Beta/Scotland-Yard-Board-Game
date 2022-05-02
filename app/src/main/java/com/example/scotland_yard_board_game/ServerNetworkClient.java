package com.example.scotland_yard_board_game;

import android.content.Context;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdManager.RegistrationListener;
import android.net.nsd.NsdServiceInfo;
import android.util.Log;


import java.net.ServerSocket;

public class ServerNetworkClient {

    int localPort;
    ServerSocket socket;
    RegistrationListener registrationListener;
    String serviceName = "scottlandYard";
    String serviceType = "_scottlandYard.tcp";
    Context context;

    public ServerNetworkClient(Context context) {
        //first initialize a Server socket on the next open port number
        initializeServerSocket();

        this.localPort = socket.getLocalPort();


        //after the port number has been determined
        // we want to initialize the registration listener for further callbacks from the android suite
        initializeRegistrationListener();

        //get context from the activity
        this.context = context;


        //now we are ready to register the service on the network
        registerService(this.localPort);
    }

    public void registerService(int port) {
        // Create the NsdServiceInfo object, and populate it.
        NsdServiceInfo serviceInfo = new NsdServiceInfo();

        // The name is subject to change based on conflicts
        // with other services advertised on the same network.
        serviceInfo.setServiceName(this.serviceName);
        serviceInfo.setServiceType(this.serviceType);
        serviceInfo.setPort(port);


        NsdManager nsdManager = (NsdManager)this.context.getSystemService(Context.NSD_SERVICE);

        nsdManager.registerService(
                serviceInfo, NsdManager.PROTOCOL_DNS_SD, registrationListener);
    }

    public void initializeServerSocket() {
        // Initialize a server socket on the next available port.
        try{
            this.socket = new ServerSocket(0);
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        // Store the chosen port.
    }

    public void initializeRegistrationListener() {
        this.registrationListener = new RegistrationListener() {

            @Override
            public void onServiceRegistered(NsdServiceInfo NsdServiceInfo) {
                // Save the service name. Android may have changed it in order to
                // resolve a conflict, so update the name you initially requested
                // with the name Android actually used.
                serviceName = NsdServiceInfo.getServiceName();
            }

            @Override
            public void onRegistrationFailed(NsdServiceInfo serviceInfo, int errorCode) {
                // Registration failed! Put debugging code here to determine why.
                Log.d("NsdServiceInfo_failed","NetworkClient::initializeRegistrationListener::onRegistrationFailed"+serviceInfo.toString());
            }

            @Override
            public void onServiceUnregistered(NsdServiceInfo arg0) {
                // Service has been unregistered. This only happens when you call
                // NsdManager.unregisterService() and pass in this listener.
                Log.d("NsdServiceInfo_unregistered", "Service "+arg0.getServiceName()+" has been unregistered. ("+ arg0.toString()+")");
            }

            @Override
            public void onUnregistrationFailed(NsdServiceInfo serviceInfo, int errorCode) {
                // unregistration failed. Put debugging code here to determine why.
                Log.d("NsdServiceInfo_unregistration_failed", "NetworkClient::initializeRegistrationListener::onUnregistrationFailed"
                        +"\n"+"errorCode: "+errorCode + "\n" + serviceInfo.toString());
            }
        };
    }




}

