package com.haginus.common.clients.marketplace.dto.Asset;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LinkAssetResponseDto extends AssetResponseDto {
  private final String type = "link";

  private String link;
}
