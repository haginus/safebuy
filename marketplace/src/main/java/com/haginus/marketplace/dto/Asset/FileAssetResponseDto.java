package com.haginus.marketplace.dto.Asset;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class FileAssetResponseDto extends AssetResponseDto {
  private final String type = "file";

  private String name;
  private String mimeType;
}
