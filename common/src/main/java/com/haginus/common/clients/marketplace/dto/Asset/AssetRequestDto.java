package com.haginus.common.clients.marketplace.dto.Asset;

import lombok.*;

import javax.validation.constraints.AssertTrue;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssetRequestDto {
  private String id;

  private String type;

  private String link;

  private String name;
  private String mimeType;
  private String content;

  @AssertTrue(message = "Please select a correct type and match attributes for it.")
  boolean isValid() {
    if(id != null) return true;
    if(Objects.equals(type, "link")) {
      return link != null && link.length() > 0;
    } else if(Objects.equals(type, "file")) {
      return name != null && mimeType != null && content != null;
    }
    return false;
  }
}
