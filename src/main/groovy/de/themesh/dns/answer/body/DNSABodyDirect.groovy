package de.themesh.dns.answer.body

class DNSABodyDirect implements DNSResponseBody {

    Integer a
    Integer b
    Integer c
    Integer d

    public DNSABodyDirect() {
    }

    public DNSABodyDirect(Integer a, Integer b, Integer c, Integer d) {
        this.a = a
        this.b = b
        this.c = c
        this.d = d
    }

    @Override
    void fill(byte[] data) {
        a = ((int) data[0])
        b = ((int) data[1])
        c = ((int) data[2])
        d = ((int) data[3])
    }

    @Override
    byte[] toByteArray() {
        return [
                (byte) a,
                (byte) b,
                (byte) c,
                (byte) d,
        ]
    }


    @Override
    public String toString() {
        return [a, b, c, d].join(".")
    }
}
