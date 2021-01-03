package de.themesh.dns

import com.google.common.io.BaseEncoding
import de.themesh.dns.answer.DNSAnswers
import de.themesh.dns.header.DNSHeaders
import de.themesh.dns.question.DNSQuestions
import de.themesh.dns.resolver.Resolver


class Main {

    static def QUERY_ME = "000501000001000000000000026d6502646500001c0001"
    static def ANSWER_ME = "c00c000100010000384000044b7e4c54"
    static def TOTAL_ME = "000481800001000100000000026d650264650000010001c00c000100010000384000044b7e4c54"
    static def ONE_QUESTION_THREE_ANSWERS = "00050100000100000000000006676f6f676c6502646500001c0001"

    public static void main(String[] args) {
        def b = BaseEncoding.base16().decode(ONE_QUESTION_THREE_ANSWERS.toUpperCase())
        def r = new Resolver("1.1.1.1", 53)
        Thread.start {
            r.run()
        }
        println Arrays.deepToString(r.resolve(b))
    }

    public static void maina(String[] args) {
        def b = BaseEncoding.base16().decode(ONE_QUESTION_THREE_ANSWERS.toUpperCase())

        def h = DNSHeaders.parse(b)

        def q = DNSQuestions.parse(b, h)

        def t = DNSAnswers.parse(b, h, q.length)

        println t




    }
}
