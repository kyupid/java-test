package org.example;

public class TrainJourney {
    public int price;
    public TrainJourney onward;
    public TrainJourney(int p, TrainJourney t) {
        price = p;
        onward = t;
    }

    // 파괴적 갱신: 기존 객체를 수정
    static TrainJourney link(TrainJourney a, TrainJourney b) {
        if (a == null) {
            return b;
        }
        TrainJourney t = a;
        while (t.onward != null) {
            t = t.onward;
        }
        t.onward = b;
        return a;
    }

    // 함수형 프로그래밍: 새로운 객체를 만들어서 반환
    static TrainJourney append(TrainJourney a, TrainJourney b) {
        return a == null ? b : new TrainJourney(a.price, append(a.onward, b));
    }
}
