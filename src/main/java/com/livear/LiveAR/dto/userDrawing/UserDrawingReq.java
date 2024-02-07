package com.livear.LiveAR.dto.userDrawing;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class UserDrawingReq {
    @Getter
    @FieldDefaults(level = AccessLevel.PRIVATE)
    @Schema(title = "유저 그림 저장 객체")
    public static class SaveDrawing {
        @Schema(description = "유저 그림 도안 ID")
        Long drawingId;
    }

    @Getter
    @FieldDefaults(level = AccessLevel.PRIVATE)
    @Schema(title = "유저 그림 삭제 객체")
    public static class DeleteDrawing {
        @NotNull
        @Schema(description = "삭제할 유저 그림 ID 리스트")
        List<Long> userDrawingIdList;
    }
}
