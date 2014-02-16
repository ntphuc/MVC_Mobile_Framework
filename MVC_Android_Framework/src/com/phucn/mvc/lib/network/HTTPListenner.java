
package com.phucn.mvc.lib.network;

public interface HTTPListenner {

    /**
     * this function use to client listen the response from server
     * @param Message from server
     */
    public abstract void onReceiveSuccess(HTTPMessage mes);

    /**
     * This function use to notify to highter class the error if it appear
     * @param error
     */
    public abstract void onReceiveError(HTTPResponse response);
}

