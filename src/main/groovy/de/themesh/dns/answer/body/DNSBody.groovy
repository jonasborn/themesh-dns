package de.themesh.dns.answer.body

class DNSBody {

    public static DNSABodyDirect a(int a, int b, int c, int d) {
        return new DNSABodyDirect(a, b, c, d)
    }

    public static DNSAAAABodyDirect aaaa(short a, short b, short c, short d, short e, short f, short g, short h) {
        return new DNSAAAABodyDirect(a, b, c, d, e, f, g, h)
    }

    public static DNSAAAABodyDirect aaaa(String s) {
        return new DNSAAAABodyDirect(s)
    }

}
