package com.haginus.marketplace.model.Asset;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class FileAsset extends Asset {
  @Column(name = "name")
  private String name;

  @NotEmpty
  @Column(name = "mime_type")
  private String mimeType;

  @Lob
  @Column(name = "content")
  private byte[] content;
}
