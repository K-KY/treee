package com.oreo.finalproject_5re5_be.concat.entity;

import com.oreo.finalproject_5re5_be.global.entity.BaseEntity;
import com.oreo.finalproject_5re5_be.project.entity.Project;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "concat_tab")
public class ConcatTab extends BaseEntity {
    @Id
    private Long projectId;

    @MapsId // Project 엔티티의 ID를 ConcatTab의 ID로 사용
    @OneToOne
    @JoinColumn(name = "pro_seq")
    private Project project;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "option_seq")
    private ConcatOption option;

    @OneToMany(mappedBy = "concatTab", cascade = CascadeType.ALL)
    @Builder.Default
    private List<ConcatRow> concatRows = new ArrayList<>();

    @Column(name = "status")
    private Character status;

    @Column(name = "front_silence")
    private Float frontSilence;
}


