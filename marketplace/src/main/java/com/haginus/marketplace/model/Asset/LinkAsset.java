package com.haginus.marketplace.model.Asset;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class LinkAsset extends Asset {
  @NotEmpty
  @Column(name = "link")
  private String link;
}
