package de.themesh.dns.resolver

import de.themesh.dns.header.DNSHeader
import de.themesh.dns.header.DNSHeaders
import de.themesh.dns.question.DNSQuestion
import de.themesh.dns.util.ByteUtils

import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class Resolver implements Runnable {

    private static boolean running = true

    static {
        Resolver.addShutdownHook {
            running = false
        }
    }

    Map<Short, CountDownLatch> locks = [:]
    Map<Short, byte[]> responses = [:]
    List<Short> invalid = []
    DatagramSocket socket = new DatagramSocket()
    String host
    int port

    /**
     * Creates a new Resolver instance, used to resolve raw DNS entries
     * @param host Any ip or domain name, is resolved used the system resolver
     * @param port A port leading to an DNS server
     */
    Resolver(String host, int port) {
        this.host = host
        this.port = port
    }

    /**
     * Sends a message to a DNS server and waits for the response
     * @param data Any DNS package data
     * @return The raw response from the server or null, if it timed out
     */
    public byte[] resolve(byte[] data) {
        //Used to extract the specific id of the dns package
        def header = DNSHeaders.parse(data)
        DatagramPacket packet = new DatagramPacket(
                data, 0, data.length, InetAddress.getByName(host), port
        ) //Not sure about the TTL of the system resolver
        socket.send(packet)
        //Used to lock this function until a response was received
        CountDownLatch latch = new CountDownLatch(1)
        locks.put(header.id, latch)
        latch.await(1000, TimeUnit.MILLISECONDS)
        def r = responses.get(header.id)
        invalid.add(header.id)
        return r
    }


    public byte[] resolve(DNSHeader header, DNSQuestion question) {
        byte[] total = ByteUtils.join(header.toByteArray(), question.toByteArray())
        return resolve(total)
    }


    @Override
    void run() {
        while (running) {
            byte[] buffer = new byte[512]
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length)
            socket.receive(packet)
            def data = packet.data
            def header = DNSHeaders.parse(data)
            def latch = locks.get(header.id)
            if (latch != null) {
                responses.put(header.id, data)
            }
        }

    }
}
