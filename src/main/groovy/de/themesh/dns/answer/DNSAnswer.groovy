package de.themesh.dns.answer

import de.themesh.dns.Byteable

class DNSAnswer implements Byteable {

    List<DNSAnswerResponse> answers = []

    DNSAnswer(List<DNSAnswerResponse> answers) {m
        this.answers = answers
    }

    public byte[] toByteArray() {
        List<Byte> bytes = []
        answers.each {
            bytes.addAll(it.toByteArray() as List<Byte>)
        }
        return bytes as byte[]
    }
}
