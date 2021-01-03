package de.themesh.dns.question

import de.themesh.dns.Byteable

public class DNSQuestion  implements Byteable {
    int length
    List<DNSQuestionQuery> questions = []

    DNSQuestion(List<DNSQuestionQuery> questions) {
        this.questions = questions
    }

    DNSQuestion(int length, List<DNSQuestionQuery> questions) {
        this.length = length
        this.questions = questions
    }

    public byte[] toByteArray() {
        List<Byte> bytes = []
        questions.each {
            bytes.addAll(it.toByteArray() as List<Byte>)
        }
        return bytes as byte[]
    }
}

