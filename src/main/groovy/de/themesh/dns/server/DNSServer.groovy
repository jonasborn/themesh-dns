package de.themesh.dns.server

import de.themesh.dns.DNSType
import de.themesh.dns.answer.DNSAnswer
import de.themesh.dns.answer.DNSAnswerResponse
import de.themesh.dns.answer.body.DNSABodyDirect
import de.themesh.dns.answer.body.DNSResponseBody
import de.themesh.dns.header.DNSHeader
import de.themesh.dns.question.DNSQuestion
import de.themesh.dns.question.DNSQuestionQuery

import java.util.function.Consumer
import java.util.function.Function

class DNSServer implements Runnable {

    int port
    DatagramSocket socket
    List<DNSServerListener> listeners = []

    DNSServer(int port) {
        this.port = port
        socket = new DatagramSocket(port)
    }

    public void run() {
        byte[] buffer = new byte[512];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length)
        socket.receive(packet)
        handle(packet)
    }


    public <T extends DNSResponseBody> void add(Consumer<Pipeline<T>> consumer, Class<T> cls) {

    }

    public static class Pipeline<Z extends DNSResponseBody> {
        DNSHeader header
        DNSQuestion question
        DNSAnswer answer
        List<Z> bodies


    }

    public void addModifier(Function<ModifierSet, Boolean> condition, Function<ModifierSet, DNSAnswer> function) {

    }



    public void addMod(Function<ConditionInput, Boolean> condition, Function<ModInput, DNSAnswerResponse> function) {

    }

    public static class Mod {
        Function<ConditionInput, Boolean> condition
        Function<DNSQuestionQuery, DNSAnswerResponse> function

        Mod(Function<ConditionInput, Boolean> condition, Function<DNSQuestionQuery, DNSAnswerResponse> function) {
            this.condition = condition
            this.function = function
        }
    }

    public static class ModSettings {
        int ttl
    }

    public static class ConditionInput {
        DNSHeader header
        DNSQuestionQuery query

    }

    public static class ModInput extends ConditionInput {
        ModSettings settings
    }

    public static class ModifierSet {
        DNSHeader header
        DNSQuestion question

        ModifierSet(DNSHeader header, DNSQuestion question) {
            this.header = header
            this.question = question
        }
    }

    public static class Modifier<T extends DNSResponseBody> {

    }


    public void handle(DatagramPacket packet) {

    }

    public static void main(String[] args) {
        def d = new DNSServer(5300)

        d.addModifier({
            it.question.questions.find {it.name.endsWith("facebook.com")}
        }, {

            it.question.questions.findAll {it.name.endsWith("facebook.com")}.collect {
                if (DNSType.A.is(it.type)) return new DNSAnswerResponse(
                        it.name, it.type, it.cls, 60, new DNSABodyDirect(127,0,0,1)
                )

            }.findAll {it != null}

        })

        d.addMod({
            it.query.name.endsWith("facebook.com")
        }, {
            it.settings.ttl = 1200
            return new DNSAnswerResponse(
                    it.query.name,
                    it.query.type,
                    it.query.cls,
                    60,
                    new DNSABodyDirect(127, 0,0,1)
            )
        })

    }

}
