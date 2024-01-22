package com.livear.LiveAR.service;

import com.livear.LiveAR.domain.User;
import com.livear.LiveAR.dto.user.UserReq;
import com.livear.LiveAR.handler.exception.ErrorCode;
import com.livear.LiveAR.handler.exception.http.CustomBadRequestException;
import com.livear.LiveAR.handler.exception.http.CustomForbiddenException;
import com.livear.LiveAR.handler.exception.http.CustomNotFoundException;
import com.livear.LiveAR.repository.UserRepository;
import com.livear.LiveAR.security.TokenProvider;
import com.livear.LiveAR.security.dto.TokenResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
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
            User user = userSignup.toEntity();
            userRepository.save(user);
            return user.getUserId();
        }
        else{
            throw new CustomNotFoundException(ErrorCode.ALREADY_SAVED_NICKNAME);
        }
    }

    /**
     * 로그인
     */
    public TokenResponseDto login(UserReq.Login userLogin) {
        TokenResponseDto tokenResponseDto = null;
        if (isDuplicateNickname(userLogin.getNickname())) {
            User user = userRepository.findByNickName(userLogin.getNickname());

            if (!passwordEncoder.matches(userLogin.getPassword(), user.getPassword())) {
                throw new CustomBadRequestException(ErrorCode.INVALID_PASSWORD);
            }

            tokenResponseDto = tokenProvider.generateTokenResponse(user);

            userRepository.save(user);
        } else {
            new CustomNotFoundException(ErrorCode.NOT_FOUND_USER);
        }
        return tokenResponseDto;
    }

}
