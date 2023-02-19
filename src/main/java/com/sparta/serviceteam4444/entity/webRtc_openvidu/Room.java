package com.sparta.serviceteam4444.entity.webRtc_openvidu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long roomId;
    @Column(nullable = false)
    private String roomTitle;
    @Column(nullable = false)
    private String sessoinId;
}
