package com.livear.LiveAR.dto.drawing;

import com.livear.LiveAR.domain.Drawing;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

public class DrawingRes {
    @Getter
    @Setter
    @Builder
    @Schema(name = "도안 정보 객체", description = "도안 정보 객체")
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Base {

        @Schema(description = "도안 아이디")
        Long drawingId;

        @Schema(description = "도안명")
        String title;

        @Schema(description = "도안 사진")
        String imageUrl;

        @Schema(description = "좋아요 여부")
        Boolean existLike;

        public static Base of(Drawing drawing, Boolean existLike) {
            return Base.builder()
                    .drawingId(drawing.getDrawingId())
                    .title(drawing.getTitle())
                    .imageUrl(drawing.getImageUrl())
                    .existLike(existLike)
                    .build();
        }
    }

    @Getter
    @Setter
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    @Schema(name = "도안 페이지 정보 객체", description = "도안 페이지 정보 객체")
    public static class Multiple {

        long totalCount;

        long maxPageCount;

        List<Base> drawingList;

        public static Multiple of(long totalCount, long maxPageCount, List<Base> drawingList) {
            return Multiple.builder()
                    .totalCount(totalCount)
                    .maxPageCount(maxPageCount)
                    .drawingList(drawingList)
                    .build();
        }
    }
}
