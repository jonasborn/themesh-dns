package de.themesh.dns.answer.body

import com.google.common.io.BaseEncoding
import com.google.common.primitives.Shorts

class DNSAAAABodyDirect implements DNSResponseBody {

    Short a
    Short b
    Short c
    Short d

    Short e
    Short f
    Short g
    Short h

    DNSAAAABodyDirect() {
    }

    public DNSAAAABodyDirect(String s) {
        s = s.toUpperCase().replace(":", "")

        def l = s.collect {(it.size() < 1) ? "0000" : it.toUpperCase()}.collect {
            Shorts.fromByteArray(BaseEncoding.base32Hex().decode(it))
        }
        a = l[0]
        b = l[1]
        c = l[2]
        d = l[3]

        e = l[4]
        f = l[5]
        g = l[6]
        h = l[7]
    }

    public DNSAAAABodyDirect(Short a, Short b, Short c, Short d, Short e, Short f, Short g, Short h) {
        this.a = a
        this.b = b
        this.c = c
        this.d = d

        this.e = e
        this.f = f
        this.g = g
        this.h = h
    }

    @Override
    void fill(byte[] data) {
        a = ((short) data[0])
        b = ((short) data[1])
        c = ((short) data[2])
        d = ((short) data[3])

        e = ((short) data[4])
        f = ((short) data[5])
        g = ((short) data[6])
        h = ((short) data[7])
    }

    @Override
    byte[] toByteArray() {
        return [
                (byte) a,
                (byte) b,
                (byte) c,
                (byte) d,

                (byte) e,
                (byte) f,
                (byte) g,
                (byte) h,
        ]
    }


    @Override
    public String toString() {
        return [a, b, c, d, e, f, g, h].join(":")
    }

}
