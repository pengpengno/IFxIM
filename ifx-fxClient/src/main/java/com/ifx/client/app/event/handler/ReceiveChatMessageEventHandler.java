package com.ifx.client.app.event.handler;


import com.ifx.client.app.event.ChatEvent;

/***
 * Chat Message Handler {@link ChatEvent}
 */
public interface ReceiveChatMessageEventHandler {


    /**
     * Handle Chat Event
     * @param chatEvent
     */
    public void receiveChat(ChatEvent chatEvent);

}
