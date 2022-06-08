package com.haginus.common.clients.marketplace.dto.Asset;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileAssetResponseDto extends AssetResponseDto {
  private final String type = "file";

  private String name;
  private String mimeType;
}
