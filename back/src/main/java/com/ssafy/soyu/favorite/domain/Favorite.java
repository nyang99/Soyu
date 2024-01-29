package com.ssafy.soyu.favorite.domain;

import com.ssafy.soyu.station.domain.Station;
import com.ssafy.soyu.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "favorite")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Favorite {

    @Id
    @GeneratedValue
    @Column(name = "favorite_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "station_id")
    private Station station;

    public Favorite(Member member, Station station) {
        this.member = member;
        this.station = station;
    }
}