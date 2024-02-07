package com.livear.LiveAR.dto.admin;

import com.livear.LiveAR.domain.Drawing;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class AdminReq {
    @Getter
    @FieldDefaults(level = AccessLevel.PRIVATE)
    @Schema(title = "도안 생성 객체")
    public static class CreateDrawing {

        @NotNull
        @Schema(required = true, description = "도안 이름")
        String title;

        public Drawing toEntity(String imageUrl){
            return Drawing.builder()
                    .title(title)
                    .imageUrl(imageUrl)
                    .build();
        }
    }

    @Getter
    @FieldDefaults(level = AccessLevel.PRIVATE)
    @Schema(title = "도안 삭제 객체")
    public static class DeleteDrawing {
        @NotNull
        @Schema(description = "삭제할 도안 ID 리스트")
        List<Long> drawingIdList;
    }
}
