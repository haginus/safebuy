package com.haginus.marketplace.dto.Asset;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class LinkAssetResponseDto extends AssetResponseDto {
  private final String type = "link";

  private String link;
}
