package com.haginus.marketplace.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Asset {
  @Id
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "name", nullable = false)
  private String name;

  @NotEmpty
  @Column(name = "mime_type", nullable = false)
  private String mimeType;

  @Lob
  @Column(name = "content", nullable = false)
  private byte[] content;

  @ManyToOne
  @JoinColumn(name = "listing_id")
  private Listing listing;

}
