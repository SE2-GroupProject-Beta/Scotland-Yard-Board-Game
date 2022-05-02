package com.example.scotland_yard_board_game;

import android.content.Context;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdManager.DiscoveryListener;
import android.net.nsd.NsdServiceInfo;
import android.util.Log;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class ClientNetworkClient {

    DiscoveryListener discoveryListener;
    NsdManager.ResolveListener resolveListener;
    ServerSocket socket;
    String serviceName = "scottlandYard";
    String serviceType = "_scottlandYard.tcp";
    NsdManager nsdManager;
    Context context;
    NsdServiceInfo mService;
    List<NsdServiceInfo> foundServices;
    int serverPort;
    InetAddress host;

    public ClientNetworkClient(Context context) {
       this.context = context;
        nsdManager = (NsdManager) context.getSystemService(Context.NSD_SERVICE);

       //first we need to init the listener so that we can pass it on to the systemService (network discovery service)
        initializeDiscoveryListener();

        //initialize ResultList
        foundServices = new ArrayList<NsdServiceInfo>();

        startDiscovery();

        //once we picked a discovered service from our list, we need to resolve the service
        //for this procedure we need to initialize a resolveListener Callback Object
        initializeResolveListener();

    }

    public void resolveService(int pos){
        nsdManager.resolveService(foundServices.get(0),resolveListener);

    }

    public void startDiscovery(){
        //we tell the manager to start discovering services
        nsdManager.discoverServices(this.serviceType
                , NsdManager.PROTOCOL_DNS_SD, this.discoveryListener);
    }

    public void tearDown(){
        if (nsdManager != null) {
            stopServiceDiscovery();
        }
    }

    private void stopServiceDiscovery(){
        nsdManager.stopServiceDiscovery(discoveryListener);
    }

    private void initializeDiscoveryListener() {

        // Instantiate a new DiscoveryListener
        discoveryListener = new NsdManager.DiscoveryListener() {

            // Called as soon as service discovery begins.
            @Override
            public void onDiscoveryStarted(String regType) {
                Log.d("service_discovery_started", "Service discovery started");
            }

            @Override
            public void onServiceFound(NsdServiceInfo service) {
                // A service was found! Do something with it.
                Log.d("service_found", "Service discovery success" + service);
                if (!service.getServiceType().equals(serviceType)) {
                    // Service type is the string containing the protocol and
                    // transport layer for this service.
                    Log.d("unknown_service_type", "Unknown Service Type: " + service.getServiceType());
                } else if (service.getServiceName().equals(serviceName)) {
                    // The name of the service tells the user what they'd be
                    // connecting to. It could be "Bob's Chat App".
                    Log.d("service_same_machine", "Same machine: " + serviceName);
                } else if(foundServices.contains(service) == false){
                        foundServices.add(service);
                        Log.d("add_found_service_to_list","Added a new service ("+service.getServiceName()+") to the list of results");
                }
                else{
                    Log.d("onServiceFound_fallback","We have fallen back at case distinguishment. Do something.. Discovered Service: "+service.toString()+"\n ResultList:"+foundServices.toString());
                }
            }

            @Override
            public void onServiceLost(NsdServiceInfo service) {
                // When the network service is no longer available.
                // Internal bookkeeping code goes here.
                Log.e("initializeDiscoveryListener_onserviceLost", "service lost: " + service);
            }

            @Override
            public void onDiscoveryStopped(String serviceType) {
                Log.i("initializeDiscoveryListener_onDiscoveryStopped", "Discovery stopped: " + serviceType);
            }

            @Override
            public void onStartDiscoveryFailed(String serviceType, int errorCode) {
                Log.e("initializeDiscoveryListener_onStartDiscoveryFailed", "Discovery failed: Error code:" + errorCode);
                nsdManager.stopServiceDiscovery(this);
            }

            @Override
            public void onStopDiscoveryFailed(String serviceType, int errorCode) {
                Log.e("initializeDiscoveryListener_onStopDiscoveryFailed", "Discovery failed: Error code:" + errorCode);
                nsdManager.stopServiceDiscovery(this);
            }
        };
    }

    /**
     * Initializes the resolve callback object instance var and also sets additional port and host members (they might be removed in future)
     */
    private void initializeResolveListener() {
        resolveListener = new NsdManager.ResolveListener() {
            @Override
            public void onResolveFailed(NsdServiceInfo serviceInfo, int errorCode) {
                // Called when the resolve fails. Use the error code to debug.
                Log.e("resolve_failed", "Resolve failed: " + errorCode);
            }

            @Override
            public void onServiceResolved(NsdServiceInfo serviceInfo) {
                Log.e("service_resolved", "Resolve Succeeded. " + serviceInfo);

                if (serviceInfo.getServiceName().equals(serviceName)) {
                    Log.d("same_ip", "Same IP.");
                    return;
                }

                mService = serviceInfo;
                serverPort = mService.getPort();
                host = mService.getHost();
            }
        };
    }




}

