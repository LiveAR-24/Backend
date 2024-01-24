package com.livear.LiveAR.dto.admin;

import com.livear.LiveAR.domain.Drawing;
import com.livear.LiveAR.domain.User;
import com.livear.LiveAR.domain.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AdminReq {
    @Getter
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    @Schema(title = "도안 생성 객체")
    public static class CreateDrawing {

        @Schema(description = "도안 이름")
        String title;

        public Drawing toEntity(String imageUrl){
            return Drawing.builder()
                    .title(title)
                    .imageUrl(imageUrl)
                    .build();
        }
    }
}
