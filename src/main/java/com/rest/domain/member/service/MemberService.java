package com.rest.domain.member.service;

import com.rest.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    public final MemberRepository memberRepository;
}
