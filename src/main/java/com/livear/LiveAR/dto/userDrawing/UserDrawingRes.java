package com.livear.LiveAR.dto.userDrawing;

import com.livear.LiveAR.domain.UserDrawing;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

public class UserDrawingRes {
    @Getter
    @Setter
    @Builder
    @Schema(name = "유저 그림 정보 객체", description = "유저 그림 정보 객체")
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Base {

        @Schema(description = "유저 그림 아이디")
        Long userDrawingId;

        @Schema(description = "유저 그림 도안 아이디")
        Long drawingId;

        @Schema(description = "유저 그림 사진")
        String imageUrl;

        public static UserDrawingRes.Base of(UserDrawing userDrawing) {
            return Base.builder()
                    .userDrawingId(userDrawing.getUserDrawingId())
                    .drawingId(userDrawing.getDrawing().getDrawingId())
                    .imageUrl(userDrawing.getImageUrl())
                    .build();
        }
    }

    @Getter
    @Setter
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    @Schema(name = "유저 그림 페이지 정보 객체", description = "유저 그림 페이지 정보 객체")
    public static class Multiple {

        long totalCount;

        long maxPageCount;

        List<UserDrawingRes.Base> drawingList;

        public static UserDrawingRes.Multiple of(long totalCount, long maxPageCount, List<UserDrawingRes.Base> drawingList) {
            return UserDrawingRes.Multiple.builder()
                    .totalCount(totalCount)
                    .maxPageCount(maxPageCount)
                    .drawingList(drawingList)
                    .build();
        }
    }
}
