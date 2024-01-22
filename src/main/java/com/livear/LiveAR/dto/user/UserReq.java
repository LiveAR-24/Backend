package com.livear.LiveAR.dto.user;

import com.livear.LiveAR.domain.User;
import com.livear.LiveAR.domain.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.UtilityClass;
import org.antlr.v4.runtime.misc.NotNull;

@UtilityClass
public class UserReq {
    @Getter
    @Setter
    @FieldDefaults(level = AccessLevel.PRIVATE)
    @Schema(title = "일반 사용자 회원가입 요청 객체")
    public static class Signup {

        @NotNull
        @Schema(description = "유저 닉네임")
        String nickname;

        @NotNull
        @Schema(description = "유저 기기 번호")
        String password;

        public User toEntity(){
            return User.builder()
                    .nickname(nickname)
                    .userRole(UserRole.ROLE_USER)
                    .password(password)
                    .build();
        }
    }

    @Getter
    @Setter
    @FieldDefaults(level = AccessLevel.PRIVATE)
    @Schema(title = "로그인 요청 객체")
    public static class Login {

        @NotNull
        @Schema(required = true, description = "유저 닉네임")
        String nickname;

        @Schema(required = true, description = "기기 번호)")
        String password;
    }



}
