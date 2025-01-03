package com.rest.domain.member.dto;

import com.rest.domain.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MemberDto {
    private Long id;
    private String username;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public MemberDto(Member member){
        this.id = member.getId();
        this.username = member.getUsername();
        this.createdDate = member.getCreatedDate();
        this.modifiedDate = member.getModifiedDate();
    }
}
