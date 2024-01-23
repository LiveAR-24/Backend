package com.livear.LiveAR.service;

import com.livear.LiveAR.domain.User;
import com.livear.LiveAR.dto.user.UserReq;
import com.livear.LiveAR.handler.exception.ErrorCode;
import com.livear.LiveAR.handler.exception.http.CustomBadRequestException;
import com.livear.LiveAR.handler.exception.http.CustomConflictException;
import com.livear.LiveAR.handler.exception.http.CustomNotFoundException;
import com.livear.LiveAR.repository.UserRepository;
import com.livear.LiveAR.security.TokenProvider;
import com.livear.LiveAR.security.dto.TokenResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;

    /**
     * 사용자 닉네임 중복 검사
     */
    public boolean isDuplicateNickname(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    /**
     * 회원가입
     */
    @Transactional
    public Long signup(UserReq.Signup userSignup) {
        if(!isDuplicateNickname(userSignup.getNickname())) {
            String rawPassword = userSignup.getPassword();
            String encPassword = passwordEncoder.encode(rawPassword);
            User user = userSignup.toEntity(encPassword);
            userRepository.save(user);
            return user.getUserId();
        }
        throw new CustomConflictException(ErrorCode.ALREADY_SAVED_NICKNAME);
    }

    /**
     * 로그인
     */
    public TokenResponseDto login(UserReq.Login userLogin) {
        if (isDuplicateNickname(userLogin.getNickname())) {
            User user = userRepository.findByNickname(userLogin.getNickname());

            if (!passwordEncoder.matches(userLogin.getPassword(), user.getPassword())) {
                throw new CustomBadRequestException(ErrorCode.INVALID_PASSWORD);
            }

            TokenResponseDto tokenResponseDto = tokenProvider.generateTokenResponse(user);
            userRepository.save(user);
            return tokenResponseDto;
        }

        throw new CustomNotFoundException(ErrorCode.NOT_FOUND_USER);
    }

}
