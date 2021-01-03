package de.themesh.dns.answer.body

import de.themesh.dns.Byteable

interface DNSResponseBody extends Byteable {

    void fill(byte[] data)

}
