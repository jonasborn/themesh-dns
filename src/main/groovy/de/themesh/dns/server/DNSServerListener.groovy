package de.themesh.dns.server

import de.themesh.dns.answer.DNSAnswer
import de.themesh.dns.answer.body.DNSResponseBody
import de.themesh.dns.header.DNSHeader
import de.themesh.dns.question.DNSQuestion


@FunctionalInterface
interface DNSServerListener<T> {

    void handle()

    public static class Pipeline<Z extends DNSResponseBody> {
        DNSHeader header
        DNSQuestion question
        DNSAnswer answer
        List<Z> bodies


    }

}