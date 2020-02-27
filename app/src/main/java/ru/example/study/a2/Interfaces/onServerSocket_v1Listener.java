package ru.example.study.a2.Interfaces;

import ru.example.study.a2.Sockets.ServerThread;
import ru.example.study.a2.Sockets.SocketError;

public interface onServerSocket_v1Listener {
    void onRecive(ServerThread socket);
    void onDisconnected(ServerThread socket);
    void onConnected(ServerThread socket);
    void onConnect(SocketError cancel, ServerThread socket);
}
